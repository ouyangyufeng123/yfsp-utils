package com.yf.server.security;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ouyangyufeng on 2019/9/24.
 */
public class TokenModel implements Serializable {
    private static final long serialVersionUID = -1L;
    private String clientId;
    private String clientType;
    private String aid;
    private long timestamp;

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientType() {
        return this.clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getAid() {
        return this.aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public TokenModel() {
    }

    public TokenModel(String clientId, String clientType, String aid) {
        this.clientId = clientId;
        this.clientType = clientType;
        this.aid = aid;
        this.timestamp = (new Date()).getTime();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("aid : ").append(this.aid).append(",").append("clientId : ").append(this.clientId).append(",").append("clientType : ").append(this.clientType).append(",").append("timestamp:").append(this.timestamp);
        return sb.toString();
    }

    public static enum ClientType {
        UNKNOWN(0),
        MOBILE(1),
        BROWSER(2);

        protected int code;

        private ClientType(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }

        public static TokenModel.ClientType parse(int typeCode) {
            TokenModel.ClientType clientType = UNKNOWN;
            switch(typeCode) {
                case 0:
                    clientType = UNKNOWN;
                    break;
                case 1:
                    clientType = MOBILE;
                    break;
                case 2:
                    clientType = BROWSER;
                    break;
                default:
                    clientType = BROWSER;
            }

            return clientType;
        }
    }
}
