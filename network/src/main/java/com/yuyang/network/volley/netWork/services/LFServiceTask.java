package com.yuyang.network.volley.netWork.services;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.yuyang.network.volley.netWork.base.LFBaseRequest;
import com.yuyang.network.volley.netWork.utils.NetUtil;


/**
 * 封装当前服务相关的信息，发送请求，处理服务回调(解耦presenter和framework)
 * Created by youj on 2015/5/6.
 */
public class LFServiceTask<T> implements LFServiceListener<T> {
    private static final int TOKEN_INVALID_STATUS_CODE = -1;
    private OnServiceListener<T> serviceListener;
    private String token;
    private boolean isQuietService;
    private ServiceCacheControl cacheControl;
    private boolean isProcessServiceError = true;//是否要进行统一的服务错误处理, 默认为true
    private DialogFragment fragment;

    private LFServiceErrorMockModel mockModel;//mock 服务错误Model

    public LFServiceTask(OnServiceListener<T> serviceListener, LFServiceErrorMockModel mockModel, boolean isProcessServiceError) {
        this.serviceListener = serviceListener;
        this.mockModel = mockModel;
        this.isProcessServiceError = isProcessServiceError;
    }

    public void sendService(LFBaseRequest request, Class responseClass, ServiceCacheControl cacheControl, DialogFragment fragment) {
        this.token = request.getToken();
        this.cacheControl = cacheControl;
        this.fragment = fragment;

        if (!NetUtil.checkNetwork() && !isQuietService) {
            ServiceEvent serviceEvent = new ServiceEvent(new LFServiceError(EServiceErrorType.ERROR_NOT_CONNECTED, "当前网络未连接,请稍后重试"));
            processServiceEvent(serviceEvent);
            return;
        }

        LFServiceController.sendService(request, responseClass, cacheControl, this);
    }

    @Override
    public void onServiceCallBack(ServiceEvent<T> serviceEvent) {
        processServiceEvent(serviceEvent);
        fragment.dismiss();
    }

    private void processServiceEvent(ServiceEvent<T> serviceEvent) {
        if (null == serviceEvent) return;

        if (serviceEvent.isSuccessCallback()) {
            //success callback
            if (null != serviceListener) {
                serviceListener.onServiceSuccess(serviceEvent.getResponse(), token);
            }
        } else {
            //error callback
            if (null != serviceListener) {
                serviceListener.onServiceFail(serviceEvent.getError(), token);
            }
        }

    }

}
