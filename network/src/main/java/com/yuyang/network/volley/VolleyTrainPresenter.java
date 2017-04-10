package com.yuyang.network.volley;

import android.support.v4.app.FragmentManager;

import com.yuyang.network.volley.netWork.ops.LFServiceOps;
import com.yuyang.network.volley.netWork.services.LFServiceError;
import com.yuyang.network.volley.netWork.services.LFServiceReqModel;
import com.yuyang.network.volley.netWork.services.OnServiceListener;

/**
 * Created by yuyang on 2017/4/10.
 */

public class VolleyTrainPresenter {
    private VolleyTrainFragment4 fragment;
    private FragmentManager fragmentManager;

    public VolleyTrainPresenter(VolleyTrainFragment4 fragment){
        this.fragment = fragment;
        fragmentManager = fragment.getFragmentManager();
    }

    public void loadUserInfo(){
        GitUserInfoRequest request = new GitUserInfoRequest();

        LFServiceReqModel.Builder builder = new LFServiceReqModel.Builder();
        builder.setRequest(request)
                .setShowProgress(true)
                .setResponseClass(User.class)
                .setServiceListener(new OnServiceListener<User>() {

                    @Override
                    public void onServiceSuccess(User response, String token) {
                        /*if (response.succeeded()) {
                        }*/
                        fragment.showUserInfo(response);
                    }

                    @Override
                    public void onServiceFail(LFServiceError error, String token) {
                        super.onServiceFail(error, token);
                        fragment.showDialog(error.getErrorMsg());
                    }
                });
        LFServiceOps.loadData(builder.build(), fragment.loading, fragmentManager);
    }
}
