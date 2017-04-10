package com.yuyang.network.volley.netWork.services;

import com.yuyang.network.volley.baseView.LdBaseRequest;
import com.yuyang.network.volley.netWork.base.LFBaseRequest;

/**
 * Created by yuyang on 2017/4/10.
 */

public class LFServiceReqModel {

    private Builder builder;

    public LFServiceReqModel(Builder builder) {
        this.builder = builder;
    }

    public LFBaseRequest getRequest() {
        return builder.request;
    }

    @SuppressWarnings("unchecked")
    public <T> Class<T> getResponseClass() {
        return builder.responseClass;
    }

    public boolean isShowProgress() {
        return builder.isShowProgress;
    }

    @SuppressWarnings("unchecked")
    public <T> OnServiceListener<T> getServiceListener() {
        return builder.serviceListener;
    }

    public ServiceCacheControl getCacheControl() {
        return builder.serviceCacheControl;
    }

    public boolean isProcessServiceError() {
        return builder.isProcessServiceError;
    }

    public LFServiceErrorMockModel getMockModel() {
        return builder.mockModel;
    }

    public static class Builder<T>{
        public Builder() {

        }

        /**
         * 请求实体
         */
        private LFBaseRequest request;

        /**
         * 响应实体类型
         */
        private Class<T> responseClass;

        /**
         * 服务回调接口
         */
        private OnServiceListener<T> serviceListener;

        /**
         * 缓存控制
         */
        private ServiceCacheControl serviceCacheControl;

        /**
         * 是否显示加载对话框, 默认false
         */
        private boolean isShowProgress;

        /**
         * 是否要进行统一的服务错误处理, 默认为true
         */
        private boolean isProcessServiceError = false;

        private LFServiceErrorMockModel mockModel;

        public Builder setRequest(LFBaseRequest request) {
            this.request = request;
            return this;
        }

        @SuppressWarnings("unchecked")
        public Builder setResponseClass(Class<T> responseClass) {
            this.responseClass = responseClass;
            return this;
        }

        @SuppressWarnings("unchecked")
        public Builder setServiceListener(OnServiceListener<T> serviceListener) {
            this.serviceListener = serviceListener;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder setCacheControl(ServiceCacheControl serviceCacheControl) {
            this.serviceCacheControl = serviceCacheControl;
            return this;
        }

        public Builder setShowProgress(boolean isShowProgress) {
            this.isShowProgress = isShowProgress;
            return this;
        }

        public Builder setProcessServiceError(boolean isProcessServiceError) {
            this.isProcessServiceError = isProcessServiceError;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder setMockModel(LFServiceErrorMockModel mockModel) {
            this.mockModel = mockModel;
            return this;
        }

        public LFServiceReqModel build() {
            return new LFServiceReqModel(this);
        }

    }
}
