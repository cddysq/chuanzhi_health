package com.czjk.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2019/12/29 18:32
 * @Description: freemarker页面静态化测试
 */
public class FreemarkerTest {
    @Test
    public void conversion() throws Exception {
        //1.创建配置类
        Configuration configuration = new Configuration( Configuration.getVersion() );
        //2.设置模板所在的目录
        configuration.setDirectoryForTemplateLoading( new File( "G:\\ftl" ) );
        //3.设置字符集
        configuration.setDefaultEncoding( "utf-8" );
        //4.加载模板
        Template template = configuration.getTemplate( "Hello.ftl" );
        //5.创建数据模型
        Map map = new HashMap();
        map.put( "name", "freemarker" );
        map.put( "message", "快速入门" );
        map.put( "success", true );

        List goodsList = new ArrayList();

        Map goods1 = new HashMap();
        goods1.put( "name", "苹果" );
        goods1.put( "price", 5.8 );

        goodsList.add( goods1 );

        Map goods2 = new HashMap();
        goods2.put( "name", "香蕉" );
        goods2.put( "price", 2.5 );

        goodsList.add( goods2 );

        map.put( "goodsList", goodsList );
        //6.创建输出流对象
        FileWriter writer = new FileWriter( new File( "G:\\ftl\\Hello.html" ) );
        //7.输出
        template.process( map, writer );
        //8.关闭输出流对象
        writer.close();
    }
}