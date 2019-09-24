package com.yf.server.exception;

/**
 *
 * @author ouyangyufeng
 * @date 2019/9/24
 */
public enum DefaultError implements IError {
    SYSTEM_INTERNAL_ERROR("0000", "System Internal Error"),
    TOKEN_AUTH_FAILED("0001", "Token Auth Failed"),
    TOKEN_IS_NULL("0002", "Token Is Null"),
    TOKEN_NOT_EXITS("0003", "Token Not Exits"),
    SIGN_AUTH_FAILED("0004", "Sign Auth Failed"),
    SIGN_IS_NULL("0005", "Sign Is Null"),
    INPUT_VALUE_NOT_FULL("0006", "Input Value Not Full"),
    ENCODE_MD5_ERROR("0007", "Encoder MD5 Error"),
    SAVE_ERROR("0008", "SAVE Error");

    String errorCode;
    String errorMessage;

    private DefaultError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private DefaultError(String errorCode, String errorMessage, String extErrorMsg) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return "SYS." + this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}