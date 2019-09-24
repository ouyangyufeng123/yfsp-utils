package com.yf.server.security.rsa;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by ouyangyufeng on 2019/9/24.
 */
public class RSAUtil {
    public RSAUtil() {
    }

    public String encryptData(String data, PublicKey publicKey) {
        try {
            Cipher e = Cipher.getInstance("RSA");
            e.init(1, publicKey);
            byte[] dataToEncrypt = data.getBytes("utf-8");
            byte[] encryptedData = e.doFinal(dataToEncrypt);
            String encryptString = Base64.encodeBase64String(encryptedData);
            return encryptString;
        } catch (Exception var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public String decryptData(String data, PrivateKey privateKey) {
        try {
            Cipher e = Cipher.getInstance("RSA");
            e.init(2, privateKey);
            byte[] descryptData = Base64.decodeBase64(data);
            byte[] descryptedData = e.doFinal(descryptData);
            String srcData = new String(descryptedData, "utf-8");
            return srcData;
        } catch (Exception var7) {
            var7.printStackTrace();
            return null;
        }
    }
}

