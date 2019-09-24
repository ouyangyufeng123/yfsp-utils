package com.yf.server.security;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * Created by ouyangyufeng on 2019/9/24.
 */
public class YLBase64Util {
    private static String PEM_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789._";
    private static final char[] ALPHABET;
    private static int[] toInt;

    public YLBase64Util() {
    }

    public static String base64enCodeStr(String value) {
        byte[] b = value.getBytes();
        Base64 base64 = new Base64();
        b = base64.encode(b);
        return new String(b);
    }

    public static String base64DeCodeStr(String encodeStr) {
        byte[] b = encodeStr.getBytes();
        Base64 base64 = new Base64();
        b = base64.decode(b);
        return new String(b);
    }

    public static String encode(byte[] buf) {
        int size = buf.length;
        char[] ar = new char[(size + 2) / 3 * 4];
        int a = 0;

        byte b2;
        byte mask;
        for(int i = 0; i < size; ar[a++] = ALPHABET[b2 & mask]) {
            byte b0 = buf[i++];
            byte b1 = i < size?buf[i++]:0;
            b2 = i < size?buf[i++]:0;
            mask = 63;
            ar[a++] = ALPHABET[b0 >> 2 & mask];
            ar[a++] = ALPHABET[(b0 << 4 | (b1 & 255) >> 4) & mask];
            ar[a++] = ALPHABET[(b1 << 2 | (b2 & 255) >> 6) & mask];
        }

        switch(size % 3) {
            case 1:
                --a;
                ar[a] = 45;
            case 2:
                --a;
                ar[a] = 45;
            default:
                return new String(ar);
        }
    }

    public static byte[] decodeBuffer(String s) {
        int delta = s.endsWith("--")?2:(s.endsWith("=")?1:0);
        byte[] buffer = new byte[s.length() * 3 / 4 - delta];
        short mask = 255;
        int index = 0;

        for(int i = 0; i < s.length(); i += 4) {
            int c0 = toInt[s.charAt(i)];
            int c1 = toInt[s.charAt(i + 1)];
            buffer[index++] = (byte)((c0 << 2 | c1 >> 4) & mask);
            if(index >= buffer.length) {
                return buffer;
            }

            int c2 = toInt[s.charAt(i + 2)];
            buffer[index++] = (byte)((c1 << 4 | c2 >> 2) & mask);
            if(index >= buffer.length) {
                return buffer;
            }

            int c3 = toInt[s.charAt(i + 3)];
            buffer[index++] = (byte)((c2 << 6 | c3) & mask);
        }

        return buffer;
    }

    public static void main(String[] args) throws Exception {
        byte batchNum = 10;

        for(int j = 0; j < 20; ++j) {
            while(j % batchNum == 0) {
                System.out.println(j);
                ++j;
            }
        }

    }

    static {
        ALPHABET = PEM_STR.toCharArray();
        toInt = new int[128];

        for(int i = 0; i < ALPHABET.length; toInt[ALPHABET[i]] = i++) {
            ;
        }

    }
}
