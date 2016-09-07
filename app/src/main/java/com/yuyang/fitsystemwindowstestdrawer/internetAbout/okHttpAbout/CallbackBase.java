package com.yuyang.fitsystemwindowstestdrawer.internetAbout.okHttpAbout;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class CallbackBase<T> {

    /**
     * UI Thread
     *
     * @param request
     */
    public void onBefore(Request request) {
        //ZHU yuyang 显示加载对话框等操作
    }

    /**
     * UI Thread
     *
     * @param
     */
    public void onAfter() {
        //ZHU yuyang 隐藏加载对话框等操作
    }

    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress, long total) {
        //ZHU yuyang 更新进度条
    }

    /**
     * if you parse reponse code in parseNetworkResponse, you should make this method return true.
     *
     * @param response
     * @return
     */
    public boolean validateReponse(Response response) {
        return response.isSuccessful();
    }

    /**
     * Thread Pool Thread
     *
     * @param response
     */
    public abstract T parseNetworkResponse(Response response) throws Exception;

    public abstract void onError(Call call, Exception e);

    public abstract void onResponse(T response);


    public static CallbackBase CALLBACK_DEFAULT = new CallbackBase() {

        @Override
        public Object parseNetworkResponse(Response response) throws Exception {
            //ZHU yuyang 返回参数一般都有基类，可以将返回值通过gson工具转换为基类
            return null;
        }

        @Override
        public void onError(Call call, Exception e) {
            //ZHU yuyang 统一的错误处理
        }

        @Override
        public void onResponse(Object response) {
            //ZHU yuyang 处理 parseNetworkResponse 方法返回的对象
        }
    };

}