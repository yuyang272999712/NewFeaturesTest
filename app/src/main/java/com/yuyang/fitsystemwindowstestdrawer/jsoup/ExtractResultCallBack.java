package com.yuyang.fitsystemwindowstestdrawer.jsoup;

import org.jsoup.select.Elements;

/**
 * Created by yuyang on 16/7/26.
 * 抓取结果回调
 */
public interface ExtractResultCallBack {
    void onSuccess(Elements results);
    void onError(String errorMsg);
}
