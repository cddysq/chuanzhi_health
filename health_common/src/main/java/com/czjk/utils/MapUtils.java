package com.czjk.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * @Author: Haotian
 * @Date: 2020/1/9 10:27
 * @Description: 地图工具类
 */
public class MapUtils {
    /**
     * Baidu地图通过地址获取经纬度
     */
    public static String getLngAndLat(String address) {
        String location = "";
        address = address.replace( " ", "" );
        //http://api.map.baidu.com/geocoder/v2/?address="地址"&output=json&ak="你的AK"
        String url = "http://api.map.baidu.com/geocoding/v3/?address=" + address + "&output=json&ak=r1XnCPbNZpsUGfyXgTGBzQ1e5XbEzxhy";
        try {
            String json = loadJSON( url );
            JSONObject obj = JSONObject.parseObject( json );
            if (obj.get( "status" ).toString().equals( "0" )) {
                double lng = obj.getJSONObject( "result" ).getJSONObject( "location" ).getDouble( "lng" );
                double lat = obj.getJSONObject( "result" ).getJSONObject( "location" ).getDouble( "lat" );
                location = lng + "," + lat;
            } else {
                System.out.println( "未找到相匹配的经纬度！" );
            }
        } catch (Exception e) {
            System.out.println( "未找到相匹配的经纬度，请检查地址！" );
        }
        return location;
    }

    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL( url );
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader( new InputStreamReader( yc.getInputStream(), "UTF-8" ) );
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append( inputLine );
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }
}