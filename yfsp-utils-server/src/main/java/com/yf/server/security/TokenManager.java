package com.yf.server.security;

import com.yf.server.exception.BaseBusinessException;
import com.yf.server.exception.DefaultError;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by ouyangyufeng on 2019/9/24.
 */
public class TokenManager extends TokenModel {
    private static final String PREFIX = "____";
    private String tokenStr;

    public String toTokenStr() {
        return this.tokenStr;
    }

    public TokenManager(String clientId, String clientType, String aid) {
        super(clientId, clientType, aid);
        String token = (new Date()).getTime() + "____" + clientId + "____" + clientType + "____" + aid;

        try {
            this.tokenStr = YLBase64Util.encode(token.getBytes("UTF-8"));
        } catch (Exception var6) {
            throw new BaseBusinessException("MD5 general token failed", var6);
        }
    }

    public TokenManager(String tokenValue) {
        if(StringUtils.isBlank(tokenValue)) {
            throw new BaseBusinessException(DefaultError.TOKEN_IS_NULL);
        } else {
            try {
                String e = new String(YLBase64Util.decodeBuffer(tokenValue), "UTF-8");
                String[] params = e.split("____");
                this.setTimestamp(Long.parseLong(params[0]));
                this.setClientId(params[1]);
                this.setClientType(params[2]);
                this.setAid(params[3]);
            } catch (UnsupportedEncodingException var4) {
                System.out.println("TokenModel auth token error" + var4);
                throw new BaseBusinessException(DefaultError.TOKEN_AUTH_FAILED);
            }
        }
    }

    public TokenModel createToken(TokenModel tokenModel) {
        return null;
    }

    public boolean checkToken(TokenModel tokenModel) {
        return false;
    }

    public TokenModel getToken(String authentication) {
        return null;
    }

    public void deleteToken(String token) {
    }
}

