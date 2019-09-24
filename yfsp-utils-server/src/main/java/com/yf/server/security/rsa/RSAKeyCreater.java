package com.yf.server.security.rsa;

import org.apache.tomcat.util.codec.binary.Base64;
import java.security.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Created by ouyangyufeng on 2019/9/24.
 */
public class RSAKeyCreater {
    public RSAKeyCreater() {
    }

    public static void createKeyPairs() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            KeyPairGenerator e = KeyPairGenerator.getInstance("RSA", "BC");
            e.initialize(512, new SecureRandom());
            KeyPair pair = e.generateKeyPair();
            PublicKey pubKey = pair.getPublic();
            PrivateKey privKey = pair.getPrivate();
            byte[] pk = pubKey.getEncoded();
            byte[] privk = privKey.getEncoded();
            String strpk = new String(Base64.encodeBase64(pk));
            String strprivk = new String(Base64.encodeBase64(privk));
            System.out.println("公钥Base64编码:" + strpk);
            System.out.println("私钥Base64编码:" + strprivk);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }

    public static void main(String[] args) {
        createKeyPairs();
    }
}
