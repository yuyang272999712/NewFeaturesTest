package com.yuyang.fitsystemwindowstestdrawer.webview;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

/**
 * Created by yuyang on 16/5/6.
 */
public class JavaForJs {
    private Context mContext;

    public JavaForJs(Context context){
        this.mContext = context;
    }

    @JavascriptInterface
    public void callHandler(String optName,String loginJson,Object function){
        //TODO 用于操作native
        LoginBean bean = JSON.parseObject(loginJson,LoginBean.class);
        Toast.makeText(mContext, optName + "。" +bean.toString(), Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void login(String username,String password){//对应js中xxx.hello("")
        //TODO 用于操作native
        LoginBean loginBean = new LoginBean(username,password);
        String json = JSON.toJSONString(loginBean);
        Toast.makeText(mContext, loginBean.toString(), Toast.LENGTH_SHORT).show();
    }
}
