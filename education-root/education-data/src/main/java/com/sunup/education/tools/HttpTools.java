package com.sunup.education.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author lime
 */
@Slf4j
public class HttpTools {

    public static final JSONObject get(String url) {
        JSONObject res = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            HttpGet get = new HttpGet(url);
            client = HttpClientBuilder.create().build();
            response = client.execute(get);
            res = JSON.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                response.close();
                client.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }

        }
        return res;
    }

    public static final HttpEntity post(String url, String param) {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(param, StandardCharsets.UTF_8));
            client = HttpClientBuilder.create().build();
            response = client.execute(post);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return response == null ? null : response.getEntity();
    }

    public static final HttpEntity post(String url, List<NameValuePair> params) {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
            client = HttpClientBuilder.create().build();
            response = client.execute(post);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return response == null ? null : response.getEntity();
    }

    /**
     * @param url    请求地址
     * @param params BasicNameValuePair 类型的键值对 列表
     * @return
     */
    public static final JSONObject poststr(String url, List<NameValuePair> params) {
        return getJsonObject(post(url, params));
    }

    /**
     * @param url   请求地址
     * @param param json 格式的字符串
     * @return
     */
    public static final JSONObject poststr(String url, String param) {
        return getJsonObject(post(url, param));
    }

    private static JSONObject getJsonObject( HttpEntity post) {
        HttpEntity entity;
        JSONObject res = null;
        try {
            entity = post;
            if (entity != null) {
                res = JSON.parseObject(EntityUtils.toString(entity, StandardCharsets.UTF_8));
                entity.getContent().close();
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return res;
    }


}
