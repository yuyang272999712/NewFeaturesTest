package com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Retrofit接口申明
 * TODO yuyang Retrofit注解说明
 * @Path：所有在网址中的参数（URL的问号前面），如：
 *      http://102.10.10.132/api/Accounts/{accountId}
 *
 * @Query：URL问号后面的参数，如：
 *      http://102.10.10.132/api/Comments?access_token={access_token}
 *
 * @QueryMap：相当于多个@Query，如：
 *      http://102.10.10.132/api/News?newsId={资讯id}&type={类型}...
 *      @GET("News")
 *      Call<NewsBean> getItem(@QueryMap Map<String, String> map);
 *
 * @Field：用于POST请求，提交单个数据，如：
 *      http://102.10.10.132/api/News/{newsId}?access_token={access_token}
 *      @FormUrlEncoded
 *      @POST("News/{newsId}")
 *      Call<NewsBean> reportComment(@Path("newsId") String newsId,@Query("access_token") String access_token,@Field("reason") String reason);
 *
 * @Body：相当于多个@Field，以对象的形式提交(CommentBean为自定义的类)，如：
 *      http://102.10.10.132/api/News/{newsId}?access_token={access_token}
 *      @POST("News/{newsId}")
 *      Call<NewsBean> reportComment(@Path("newsId") String newsId,@Query("access_token") String access_token,@Body CommentBean bean);
 *
 * TODO yuyang ！！！注：
 *  1、使用@Field时记得添加@FormUrlEncoded
 *  2、若需要重新定义接口地址，可以使用@Url，将地址以参数的形式传入即可。如
        @GET
        Call<List<Activity>> getActivityList(@Url String url,@QueryMap Map<String, String> map);
        Call<List<Activity>> call = service.getActivityList("http://115.159.198.162:3001/api/ActivitySubjects", map);
 */
public interface ApiService {
    /**
     * 根据userName获取对应的GitHub用户信息
     * 如果不需要转换成Json数据,可以用了ResponseBody;
     * @param userName
     * @return call
     */
    @GET("users/{userName}")
    Call<UserInfoBean> getUserInfo(@Path("userName") String userName);
}
