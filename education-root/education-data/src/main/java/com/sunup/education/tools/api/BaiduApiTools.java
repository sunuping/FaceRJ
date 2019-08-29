package com.sunup.education.tools.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunup.education.tools.HttpTools;
import com.sunup.education.tools.StringTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import static com.sunup.education.tools.PrintTools.print;

/**
 * @author lime
 * 百度API 工具
 */
@Slf4j
public class BaiduApiTools {
    private static final String apiKey = "GIBPVce7A3j5S01FBmnDyuDZ";
    private static final String secretKey = "GjN3SMxzal48PbpswN0vl9oea2zjhhpq";
    /**
     * access token url
     */
    private static final String accesTokenUrl = StringTools.bufferAppendToString("https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=", apiKey, "&client_secret=", secretKey);
    /**
     * 在线活体检测url
     */
    private static final String onlineLivingDetectionUrl = "https://aip.baidubce.com/rest/2.0/face/v3/faceverify";
    private static final String onlineLivingVideoDetectionUrl = "https://aip.baidubce.com/rest/2.0/face/v1/faceliveness/verify";
    private static String accesstoken = "24.034efb37650da6d41907dc1dd033ee3b.2592000.1569494683.282335-17083116";

    /**
     * 获取百度access token
     *
     * @return
     */
    public static final String getAccesToken() {
        if (accesstoken != null) {
            return accesstoken;
        }

        List<NameValuePair> params = new ArrayList<>(1);
        accesstoken = HttpTools.poststr(accesTokenUrl, params).getString("access_token");
        print(accesstoken);
        return accesstoken;
    }

    /**
     * 在线活体检测
     *
     * @return
     */
    public static final JSONObject onlineLivingDetection(String image) {
        String url = StringTools.bufferAppendToString(onlineLivingDetectionUrl, "?access_token=", getAccesToken());
        List<Image> images = new ArrayList<>(1);
        images.add(Image.builder().image(image).image_type(Image.BASE64).face_field("age,beauty,expression,gender").build());
        JSONObject res = HttpTools.poststr(url, JSON.toJSONString(images));
        print(res.toJSONString());
//        double faceProbability = res.getJSONObject("result").getDouble("face_liveness");
        return res;
    }

    public static final JSONObject onlineLivingVideoDetection(String video) {
        String url = StringTools.bufferAppendToString(onlineLivingVideoDetectionUrl, "?access_token=", getAccesToken());
        List<NameValuePair> params = new ArrayList<>(1);
        params.add(new BasicNameValuePair("video_base64", video));
        JSONObject res = HttpTools.poststr(url, params);
        return res;
    }

    public static void main(String[] args) {
//        log.debug(BaiduApiTools.onlineLivingDetection(Base64AndImgConverTools.converBase64(new File("E:\\1.jpg"))) + "");
        log.debug(BaiduApiTools.getAccesToken());
    }
}
