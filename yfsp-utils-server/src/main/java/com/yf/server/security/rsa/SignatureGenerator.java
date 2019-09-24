package com.yf.server.security.rsa;

import com.yf.server.security.MD5Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by ouyangyufeng on 2019/9/24.
 */
public class SignatureGenerator {
    public SignatureGenerator() {
    }

    public static String generate(String urlResourcePart, Map<String, String> params, String secretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        LinkedList parameters = new LinkedList(params.entrySet());
        Collections.sort(parameters, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Map.Entry<String, String> map1 = (Map.Entry<String, String>)o1;
                Map.Entry<String, String> map2 = (Map.Entry<String, String>)o2;
                return ((String)map1.getKey()).compareTo((String)map2.getKey());
            }
        });
        StringBuilder sb = new StringBuilder();
        sb.append(urlResourcePart).append("_");
        Iterator baseString = parameters.iterator();

        while(baseString.hasNext()) {
            Map.Entry param = (Map.Entry)baseString.next();
            sb.append((String)param.getKey()).append("=").append((String)param.getValue()).append("_");
        }

        sb.append(secretKey);
        String baseString1 = URLEncoder.encode(sb.toString(), "UTF-8");
        return MD5Utils.md5(baseString1);
    }

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HashMap params = new HashMap();
        params.put("aid", "1");
        params.put("token", "MTUxMTg1OTQ4ODk1Nl9fX18xMTExMjIyMjMzM19fX19CUk9XU0VSX19fXzE-");
        params.put("appKey", "1234567890");
        String urlResourcePart = "/battery-manage/admin/accountSer/getAccountByAid";
        String sign = generate(urlResourcePart, params, "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAgmZzTy2Bb");
        System.out.println(sign);
    }
}

