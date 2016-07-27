package com.yuyang.fitsystemwindowstestdrawer.jsoup;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by yuyang on 16/7/26.
 * TODO yuyang 网络爬虫抓取工具
 * jsoup介绍：http://www.open-open.com/jsoup/dom-navigation.htm
 * 爬虫抓取执行类，核心解析代码请自行实现借口
 */
public class ExtractService {
    /**
     * 抓取数据
     * @param rule 抓取规则
     * @param callBack 抓取结果回调
     */
    public static void extract(Rule rule, ExtractResultCallBack callBack) {
        if (callBack == null){
            throw new RuntimeException("不写回调你搞毛？！");
        }
        // 进行对rule的必要校验
        if (!validateRule(rule)){
            callBack.onError("抓取规则错误！");
        }
        try {
            /**
             * 解析rule
             */
            String url = rule.getUrl();
            String[] params = rule.getParams();
            String[] values = rule.getValues();
            String resultTagName = rule.getResultTagName();
            int type = rule.getType();
            int requestType = rule.getRequestMethod();

            Connection conn = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
            // 设置查询参数
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    conn.data(params[i], values[i]);
                }
            }

            // 设置请求类型
            Document doc = null;
            switch (requestType) {
                case Rule.GET:
                    doc = conn.timeout(100000).get();
                    break;
                case Rule.POST:
                    doc = conn.timeout(100000).post();
                    break;
            }

            //处理返回数据
            Elements results = new Elements();
            switch (type) {
                case Rule.CLASS:
                    results = doc.getElementsByClass(resultTagName);
                    break;
                case Rule.ID:
                    Element result = doc.getElementById(resultTagName);
                    results.add(result);
                    break;
                case Rule.SELECTION:
                    results = doc.select(resultTagName);
                    break;
                default:
                    //当resultTagName为空时默认去body标签
                    if (TextUtils.isEmpty(resultTagName)) {
                        results = doc.getElementsByTag("body");
                    }
            }
            //回调，将抽取结果交给调用者处理
            callBack.onSuccess(results);
        } catch (IOException e) {
            e.printStackTrace();
            callBack.onError("抓取过程中出现错误！");
        }
    }

    /**
     * 对传入的参数进行必要的校验
     */
    private static boolean validateRule(Rule rule) {
        String url = rule.getUrl();
        if (TextUtils.isEmpty(url)) {
            Log.e("抓取网络数据失败", "url不能为空！");
            return false;
        }
        if (!(url.startsWith("http://") || url.startsWith("https://"))) {
            Log.e("抓取网络数据失败", "url:"+url+"的格式不正确！");
            return false;
        }

        if (rule.getParams() != null && rule.getValues() != null) {
            if (rule.getParams().length != rule.getValues().length) {
                Log.e("抓取网络数据失败", "参数的键值对个数不匹配！");
                return false;
            }
        }

        return true;
    }
}
