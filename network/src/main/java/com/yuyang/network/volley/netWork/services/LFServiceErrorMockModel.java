package com.yuyang.network.volley.netWork.services;

/**
 * Created by youj on 2015/7/3.
 * <p/>
 * 封装服务错误的mock信息
 */
public class LFServiceErrorMockModel {

    /**
     * mock错误类型
     */

    private EMockServiceErrorType mockErrorType;

    public LFServiceErrorMockModel(EMockServiceErrorType mockErrorType) {
        this.mockErrorType = mockErrorType;
    }

    public EMockServiceErrorType getMockErrorType() {
        return mockErrorType;
    }

    public enum EMockServiceErrorType {
        MOCK_NO_NET,//无网络
        MOCK_TOKEN_INVALID,//token失效
        MOCK_COMMON;//通用错误
    }

}
