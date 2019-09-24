package com.yf.server.exception;

import com.yf.server.response.BaseResponse;

/**
 *
 * @author ouyangyufeng
 * @date 2019/9/24
 */
public class ErrorInfo<T> {
    public BaseResponse.Status status;
    private String errorCode;
    private String errorMessage;
    private String extMessage;

    public ErrorInfo() {
    }

    public BaseResponse.Status getStatus() {
        return this.status;
    }

    public void setStatus(BaseResponse.Status status) {
        this.status = status;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getExtMessage() {
        return this.extMessage;
    }

    public void setExtMessage(String extMessage) {
        this.extMessage = extMessage;
    }
}
