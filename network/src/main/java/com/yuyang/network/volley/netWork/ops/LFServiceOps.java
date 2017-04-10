package com.yuyang.network.volley.netWork.ops;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.yuyang.network.volley.netWork.services.LFServiceReqModel;
import com.yuyang.network.volley.netWork.services.LFServiceTask;
import com.yuyang.volley_packaging.annotation.RequestAnnotation;

/**
 * Created by yuyang on 2017/4/10.
 */

public class LFServiceOps {

    public static LFServiceTask loadData(LFServiceReqModel reqModel, final DialogFragment loading, FragmentManager fragmentManager) {
        String path = checkReqModel(reqModel);

        if (TextUtils.isEmpty(path)) {
            return null;
        }

        reqModel.getRequest().setUrl(path);
        reqModel.getRequest().setToken(path+System.currentTimeMillis());

        if (reqModel.getCacheControl() != null) {
            String cacheKey = reqModel.getCacheControl().getCacheKey();
            reqModel.getCacheControl().setCacheKey(cacheKey == null ? path : path + cacheKey);
        }

        //显示全局遮罩
        if (reqModel.isShowProgress()) {
            loading.show(fragmentManager,"loading");
        }

        LFServiceTask task = new LFServiceTask(reqModel.getServiceListener(), reqModel.getMockModel(), reqModel.isProcessServiceError());
        task.sendService(reqModel.getRequest(), reqModel.getResponseClass(), reqModel.getCacheControl(), loading);
        return task;
    }

    private static String checkReqModel(LFServiceReqModel reqModel) {
        if (null == reqModel || null == reqModel.getRequest() || null == reqModel.getResponseClass()) {
            return null;
        }
        //check path
        RequestAnnotation requestAnnotation = reqModel.getRequest().getClass().getAnnotation(RequestAnnotation.class);
        if (requestAnnotation == null || TextUtils.isEmpty(requestAnnotation.path())) {
            return null;
        }

        return requestAnnotation.path();
    }

}
