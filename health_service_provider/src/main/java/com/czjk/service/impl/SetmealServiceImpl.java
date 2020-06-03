package com.czjk.service.impl;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.czjk.constant.RedisConstant;
import com.czjk.dao.SetmealDao;
import com.czjk.entity.PageResult;
import com.czjk.entity.QueryPageBean;
import com.czjk.pojo.Setmeal;
import com.czjk.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 体检套餐服务实现类
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/6/3 15:12
 **/
@Service(interfaceClass = SetmealService.class)
@Transactional(rollbackFor = Exception.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${out_put_path}")
    private String outPutPath;  //从属性文件中读取要生成的html对应的目录

    @Override
    public void add(Setmeal setmeal, Integer[] checkGroupIds) {
        setmealDao.add( setmeal );
        //绑定套餐和检查组的多对多关系
        this.setSetmealAndCheckGroup( setmeal.getId(), checkGroupIds );
        //将图片名称保存到Redis
        jedisPool.getResource().sadd( RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg() );
        //当添加套餐后需要重新生成静态页面（套餐列表页面、套餐详情页面）
        generateMobileStaticHtml();
    }

    /**
     * 生成当前添加方法所需的静态页面
     */
    private void generateMobileStaticHtml() {
        //准备模板文件中所需的数据
        List<Setmeal> list = setmealDao.findAll();
        //生成套餐列表静态页面
        generateMobileSetmealListHtml( list );
        //生成套餐详情静态页面（多个）
        generateMobileSetmealDetailHtml( list );
    }

    /**
     * 生成套餐列表静态页面
     *
     * @param list 页面数据
     */
    private void generateMobileSetmealListHtml(List<Setmeal> list) {
        Map<String, Object> dataMap = new HashMap<>();
        //为模板提供数据，用于生成静态页面
        dataMap.put( "setmealList", list );
        generateHtml( "mobile_setmeal.ftl", "m_setmeal.html", dataMap );
    }

    /**
     * 生成套餐详情静态页面
     *
     * @param list 页面数据
     */
    private void generateMobileSetmealDetailHtml(List<Setmeal> list) {
        for (Setmeal setmeal : list) {
            Map<String, Object> dataMap = new HashMap<>();
            //为模板提供对应id所有数据
            dataMap.put( "setmeal", setmealDao.findById( setmeal.getId() ) );
            generateHtml( "mobile_setmeal_detail.ftl", "setmeal_detail_" + setmeal.getId() + ".html", dataMap );
        }
    }

    /**
     * 通用的方法，用于生成静态页面
     *
     * @param templateName freeMarker模板名
     * @param htmlPageName 生成后的页面名
     * @param dateMap      页面所需数据
     */
    private void generateHtml(String templateName, String htmlPageName, Map<String, Object> dateMap) {
        //获得配置对象
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        BufferedWriter writer = null;
        try {
            //选定生成模板
            Template template = configuration.getTemplate( templateName );
            //构造输出流
            writer = new FileWriter( outPutPath + htmlPageName, "UTF-8" ).getWriter( false );
            //输出文件
            template.process( dateMap, writer );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //流不为空进行关闭
                Objects.requireNonNull( writer ).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        //使用 PageHelper 完成分页查询
        Page<Setmeal> page = PageHelper.startPage(
                queryPageBean.getCurrentPage(), queryPageBean.getPageSize() ).
                doSelectPage( () -> setmealDao.selectByCondition( queryPageBean.getQueryString() ) );
        return PageResult.builder()
                //返回总条数
                .total( page.getTotal() )
                //返回分页数据集合
                .rows( page.getResult() ).build();
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById( id );
    }

    @Override
    public Setmeal findBySetmeal(Integer id) {
        return setmealDao.findBySetmeal( id );
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    /**
     * 绑定套餐和检查组的多对多关系
     *
     * @param setmealId     套餐id
     * @param checkGroupIds 检查组id
     */
    public void setSetmealAndCheckGroup(Integer setmealId, Integer[] checkGroupIds) {
        if (ArrayUtil.isNotEmpty( checkGroupIds )) {
            for (Integer checkGroupId : checkGroupIds) {
                Map<String, Integer> map = new HashMap<>( 0 );
                map.put( "setmealId", setmealId );
                map.put( "checkgroupId", checkGroupId );
                setmealDao.setSetmealAndCheckGroup( map );
            }
        }
    }
}