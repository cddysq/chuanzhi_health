package com.czjk.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * @Author: Haotian
 * @Date: 2019/12/25 14:49
 * @Description: 七牛云工具类
 */
public class QiNiuUtils {
    private static final String ACCESSKEY = "H9jH4SEy2dXKqUgcqyv9Zleokamr2DdM6x6LpQvF";
    private static final String SECRETKEY = "eppGy9DIIvwcObRxEyREhRfW2ur5kR97At-Y01No";
    private static final String BUCKET = "yiyayo";

    /**
     * 使用文件路径上传文件
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     */
    public static void uploadToQiNiu(String filePath, String fileName) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration( Region.region0() );
        //构造配置类
        UploadManager uploadManager = new UploadManager( cfg );
        Auth auth = Auth.create( ACCESSKEY, SECRETKEY );
        String upToken = auth.uploadToken( BUCKET );
        try {
            Response response = uploadManager.put( filePath, fileName, upToken );
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson( response.bodyString(), DefaultPutRet.class );
            //putRet.key 文件名
            System.out.println( putRet.key );
            System.out.println( "上传成功" );
        } catch (QiniuException ex) {
            Response r = ex.response;
            try {
                System.err.println( r.bodyString() );
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    /**
     * 使用字节流上传文件
     *
     * @param bytes    文件字节流
     * @param fileName 默认不指定key的情况下，以文件内容的hash值作为文件名
     */
    public static void uploadToQiNiu(byte[] bytes, String fileName) {
        Configuration cfg = new Configuration( Region.region0() );
        //构造配置类
        UploadManager uploadManager = new UploadManager( cfg );
        Auth auth = Auth.create( ACCESSKEY, SECRETKEY );
        String upToken = auth.uploadToken( BUCKET );
        try {
            Response response = uploadManager.put( bytes, fileName, upToken );
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson( response.bodyString(), DefaultPutRet.class );
            //返回文件名
            System.out.println( putRet.key );
            System.out.println( "上传成功" );
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println( r.toString() );
            try {
                System.err.println( r.bodyString() );
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名
     */
    public static void deleteFileFromQiNiu(String fileName) {
        Configuration cfg = new Configuration( Region.region0() );
        Auth auth = Auth.create( ACCESSKEY, SECRETKEY );
        BucketManager bucketManager = new BucketManager( auth, cfg );
        try {
            bucketManager.delete( BUCKET, fileName );
            System.out.println( "删除成功" );
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println( ex.code() );
            System.err.println( ex.response.toString() );
        }
    }
}