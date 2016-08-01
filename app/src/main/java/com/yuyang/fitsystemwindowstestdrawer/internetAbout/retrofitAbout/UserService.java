package com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Get请求个人信息
 */
public interface UserService {
    /**
     * 根据userName获取对应的GitHub用户信息
     * 如果不需要转换成Json数据,可以用了ResponseBody;
     * @param userName
     * @return call
     */
    @GET("users/{userName}")
    Call<UserInfoBean> getUserInfo(@Path("userName") String userName);
}
