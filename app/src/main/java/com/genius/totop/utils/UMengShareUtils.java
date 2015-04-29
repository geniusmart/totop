package com.genius.totop.utils;

import android.app.Activity;

import com.totop.genius.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

public class UMengShareUtils {

    /**
     * @功能描述 : 添加微信平台分享
     * @return
     */
    public static void addWXPlatform(Activity activity) {
        // 注意：在微信授权的时候，必须传递appSecret
        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appId = "wx967daebe835fbeac";
        String appSecret = "5bb696d9ccd75a38c8a0bfe0675559b3";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(activity, appId, appSecret);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(activity, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    /**
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
     *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
     *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     * @return
     */
    public static void addQQQZonePlatform(Activity activity) {
        String appId = "1104595382";
        String appKey = "AuYQNWEXY02oUpi2";
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity,
                appId, appKey);
        qqSsoHandler.setTargetUrl("http://www.ladgift.com/");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }

    /**
     * 添加所有的平台</br>
     */
    public static void addCustomPlatforms(Activity activity){
        addQQQZonePlatform(activity);
        addWXPlatform(activity);
    }

    public static void share(Activity activity){
        share(activity,null);
    }

    public static void share(Activity activity,String iconUrl){
        UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
        mController.setShareContent("红丫帽九块九包邮，便宜又实用，我正在购买产品，你也来吧！");
        // 设置分享图片, 参数2为图片的url地址
//        if(null == iconUrl){
//            mController.setShareMedia(new UMImage(activity, R.drawable.ic_launcher));
//        }else{
//            mController.setShareMedia(new UMImage(activity, iconUrl));
//        }

        String url = "http://www.ladgift.com/upload/icon/20150411/60ecec11-654d-477e-b4a5-cd1f9c8ac387.jpg";
        mController.setShareMedia(new UMImage(activity, url));
        mController.setShareMedia(new UMImage(activity, R.drawable.ic_launcher));
        mController.getConfig().removePlatform(SHARE_MEDIA.SINA);
        mController.getConfig().removePlatform(SHARE_MEDIA.TENCENT);
        mController.openShare(activity, false);
    }
}
