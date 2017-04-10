package com.yuyang.network.volley.netWork.services;

/**
 * 封装服务相关的错误信息（含client和server端）
 */
public class LFServiceError {

    /**
     * 错误类型
     */
    private EServiceErrorType errorType;

    /**
     * 错误信息
     */
    private String errorMsg;

    public LFServiceError() {

    }

    public LFServiceError(EServiceErrorType errorType, String errorMsg) {
        this.errorType = errorType;
        this.errorMsg = errorMsg;
    }

    public EServiceErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(EServiceErrorType errorType) {
        this.errorType = errorType;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
