package com.czjk.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @Author: Haotian
 * @Date: 2019/12/28 16:43
 * @Description: 短信发送工具类
 */
public class SMSUtils {

    private static final String ACCESSKEYID = "LTAI4FvW1pB55RxgiDA4FNJV";
    private static final String ACCESSKEYSECRET = "e52TSSbx3ZbgUJbU5FUsyUH9z5QgTw";

    /**
     * 发送短信验证码
     */
    public static final String VALIDATE_CODE = "SMS_181855484";

    /**
     * 体检预约成功通知
     */
    public static final String ORDER_NOTICE = "SMS_181855484";

    /**
     * 发送短信
     *
     * @param templateCode 短信模板ID。请在控制台模板管理页面模板CODE一列查看。
     * @param phoneNumbers 接收短信的手机号码。国内短信：11位手机号码。
     * @param authCode     验证码
     */
    public static void sendShortMessage(String templateCode, String phoneNumbers, String authCode) {
        DefaultProfile profile = DefaultProfile.getProfile( "cn-hangzhou", ACCESSKEYID, ACCESSKEYSECRET );
        IAcsClient client = new DefaultAcsClient( profile );
        CommonRequest request = new CommonRequest();
        request.setMethod( MethodType.POST );
        request.setDomain( "dysmsapi.aliyuncs.com" );
        request.setVersion( "2017-05-25" );
        request.setAction( "SendSms" );
        request.putQueryParameter( "RegionId", "cn-hangzhou" );
        request.putQueryParameter( "PhoneNumbers", phoneNumbers );
        //短信签名名称。请在控制台签名管理页面签名名称一列查看。
        request.putQueryParameter( "SignName", "传智健康" );
        request.putQueryParameter( "TemplateCode", templateCode );
        request.putQueryParameter( "TemplateParam", "{\"code\":\"" + authCode + "\"}" );
        try {
            CommonResponse response = client.getCommonResponse( request );
            System.out.println( response.getData() );
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}