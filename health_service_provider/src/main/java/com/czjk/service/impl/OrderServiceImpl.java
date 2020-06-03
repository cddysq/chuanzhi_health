package com.czjk.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.czjk.constant.MessageConstant;
import com.czjk.dao.MemberDao;
import com.czjk.dao.OrderDao;
import com.czjk.dao.OrderSettingDao;
import com.czjk.entity.Result;
import com.czjk.pojo.Member;
import com.czjk.pojo.Order;
import com.czjk.pojo.OrderSetting;
import com.czjk.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 体检预约服务
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/6/3 15:10
 **/
@Service(interfaceClass = OrderService.class)
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Result order(Map<String, Object> map) {
        //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        Date orderDate = Convert.toDate( map.get( "orderDate" ) );
        OrderSetting orderSetting = orderSettingDao.findByOrderDate( orderDate );
        if (orderSetting == null) {
            //指定日期没有进行预约设置，无法完成体检预约
            return Result.builder().flag( false ).message( MessageConstant.SELECTED_DATE_CANNOT_ORDER ).build();
        }

        //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            //已经约满，无法预约
            return Result.builder().flag( false ).message( MessageConstant.ORDER_FULL ).build();
        }

        //3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
        String telephone = Convert.toStr( map.get( "telephone" ) );
        Member member = memberDao.findByTelephone( telephone );
        if (member != null) {
            //会员存在，判断是否为重复预约
            Order order = Order.builder()
                    .memberId( member.getId() )
                    .orderDate( orderDate )
                    .setmealId( Convert.toInt( map.get( "setmealId" ) ) ).build();
            List<Order> orderList = orderDao.findByCondition( order );
            if (ObjectUtil.isNotEmpty( orderList )) {
                //能查询到数据，说明用户在重复预约，无法完成再次预约
                return Result.builder().flag( false ).message( MessageConstant.HAS_ORDERED ).build();
            }
        } else {
            //当前用户不是会员，将前端数据进行封装
            member = Member.builder()
                    .name( Convert.toStr( map.get( "name" ) ) )
                    .phoneNumber( telephone )
                    .idCard( Convert.toStr( map.get( "idCard" ) ) )
                    .sex( Convert.toStr( map.get( "sex" ) ) ).build();
            //自动注册会员
            memberDao.add( member );
        }

        //4、预约成功,保存预约信息
        Order order = Order.builder().memberId( member.getId() )
                .orderDate( orderDate )
                .orderType( Convert.toStr( map.get( "orderType" ) ) )
                .orderStatus( Order.ORDERSTATUS_NO )
                .address( Convert.toStr( map.get( "address" ) ) )
                .setmealId( Convert.toInt( map.get( "setmealId" ) ) ).build();
        orderDao.add( order );
        //更新当日的已预约人数 +1
        orderSetting.setReservations( orderSetting.getReservations() + 1 );
        orderSettingDao.updateReservationsByOrderDate( orderSetting );
        return Result.builder().flag( true ).message( MessageConstant.ORDER_SUCCESS ).data( order.getId() ).build();
    }

    @Override
    public Map<String, Object> findById(Integer id) {
        Map<String, Object> map = orderDao.findById4Detail( id );
        if (map != null) {
            //处理日期格式
            String date = DateUtil.format( Convert.toDate( map.get( "orderDate" ) ), "yyyy-MM-dd" );
            map.put( "orderDate", date );
        }
        return map;
    }
}