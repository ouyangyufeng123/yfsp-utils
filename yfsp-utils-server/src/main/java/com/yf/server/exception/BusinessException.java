package com.yf.server.exception;

/**
 *
 * @author ouyangyufeng
 * @date 2019/9/24
 */
public class BusinessException extends Exception {
    private String errorCode;
    private String errorMessage;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.errorMessage = message;
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
}
