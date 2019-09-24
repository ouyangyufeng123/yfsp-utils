package com.yf.server.security.rsa;

import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by ouyangyufeng on 2019/9/24.
 */
public class RSATest {
    public RSATest() {
    }

    public static void testRsa() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            String e = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJPSsAm9Po08VtGKQx86TuOYu/7BTOtwYlFQvjQCEs3aTeUOH3p9pgd3pw14Num0n/l3Sk3d1av4hzZJvlODfScCAwEAAQ==";
            String strprivk = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAk9KwCb0+jTxW0YpDHzpO45i7/sFM63BiUVC+NAISzdpN5Q4fen2mB3enDXg26bSf+XdKTd3Vq/iHNkm+U4N9JwIDAQABAkAch9iUOKNfDRtQnBfyagWZ5fu64sIe2vUO7r+XOCM6+a/BvKV+5aMRpR6ts8OyEz9F+KCagc8eSEO0DAFjurQ5AiEA72jh09XwAHpvUONQu8JyziZtB5Cpf/y2iCC3ucxJ510CIQCeEQ+2sd4jC7P+wdCB0K1HxXtslxD3Bq50yVtsyI3CUwIhAJUpQ4o4QNALeE9tUV+qRt0qE8Qi3Xhge1lVCSM5pNIBAiACY0OXgOxYHy8i5A6gR2S2ttb8dvO8p48vGHOXGxh5HQIgfiKMcSTfflaQBBgzDFvaVnsfs2ajbv9tNcWuAP7u6aA=";
            X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decodeBase64(e.getBytes()));
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(strprivk.getBytes()));
            KeyFactory keyf = KeyFactory.getInstance("RSA", "BC");
            PublicKey pubKey = keyf.generatePublic(pubX509);
            PrivateKey privKey = keyf.generatePrivate(priPKCS8);
            RSAUtil rsaUtil = new RSAUtil();
            String data = "battery-manage/inter/enterpriseService/syncEnterypriseDatas?appKey=11112222333&sign=abc";
            System.out.println("加密前字符串data：" + data);
            String encryptData = null;
            if(pubKey != null && data != null && !"".equals(data)) {
                encryptData = rsaUtil.encryptData(data, pubKey);
                System.out.println("加密后字符串encryptData：" + encryptData);
            }

            String descryptData = null;
            if(privKey != null && encryptData != null && !"".equals(encryptData)) {
                descryptData = rsaUtil.decryptData(encryptData, privKey);
                System.out.println("解密后字符串descryptData：" + descryptData);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

    }

    public static void main(String[] args) {
        testRsa();
    }
}
