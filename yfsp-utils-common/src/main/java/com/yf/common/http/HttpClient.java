package com.yf.common.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 接口调用工具
 * @author ouyangyufeng
 * @date 2019/9/24
 */
public class HttpClient {

    /**
     * @param url:    接口地址
     * @param map:    请求参数
     * @param method: 请求方法
     * @return JSONObject
     */
    public static JSONObject getHttpClient(String url, Map map, String method) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject paramJson = new JSONObject();
        HttpResponse response = null;

        int mapSize = 0;
        if (map != null && (mapSize = map.size()) > 0) {
            for (int i = 0; i < mapSize; i++) {
                paramJson.putAll(map);
            }
        }

        try {
            if (method.equals("POST")) {
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
                httpPost.setHeader("Accept", "application/json");
                httpPost.setEntity(new StringEntity(paramJson.toString(), Charset.forName("UTF-8")));
                response = httpClient.execute(httpPost);
            }
            if (method.equals("GET")) {
                HttpGet httpGet = new HttpGet(url);
                httpGet.addHeader("Content-Type", "application/json; charset=utf-8");
                httpGet.setHeader("Accept", "application/json");
                response = httpClient.execute(httpGet);
            }
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed:" + response.getStatusLine());
            } else {
                String resultStr = EntityUtils.toString(response.getEntity());
                return JSONObject.parseObject(resultStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
