package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.specialList.simple;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.Arrays;
import java.util.List;

public class DataInfoBean {
    public int logo;
    public int cover;
    public String title;
    public String subTitle;

    public static List<DataInfoBean> init() {
        DataInfoBean r1 = new DataInfoBean();
        r1.cover = R.mipmap.a;
        r1.logo = R.mipmap.wechat_icon_1;
        r1.title = "主标题-1";
        r1.subTitle = "副标题-1";

        DataInfoBean r2 = new DataInfoBean();
        r2.cover = R.mipmap.b;
        r2.logo = R.mipmap.wechat_icon_2;
        r2.title = "主标题-2";
        r2.subTitle = "副标题-2";

        DataInfoBean r3 = new DataInfoBean();
        r3.cover = R.mipmap.c;
        r3.logo = R.mipmap.wechat_icon_3;
        r3.title = "主标题-3";
        r3.subTitle = "副标题-3";

        DataInfoBean r4 = new DataInfoBean();
        r4.cover = R.mipmap.d;
        r4.logo = R.mipmap.wechat_icon_4;
        r4.title = "主标题-4";
        r4.subTitle = "副标题-4";

        DataInfoBean r5 = new DataInfoBean();
        r5.cover = R.mipmap.e;
        r5.logo = R.mipmap.wechat_icon_1;
        r5.title = "主标题-5";
        r5.subTitle = "副标题-5";

        DataInfoBean r6 = new DataInfoBean();
        r6.cover = R.mipmap.f;
        r6.logo = R.mipmap.wechat_icon_2;
        r6.title = "主标题-6";
        r6.subTitle = "副标题-6";

        DataInfoBean r7 = new DataInfoBean();
        r7.cover = R.mipmap.g;
        r7.logo = R.mipmap.wechat_icon_3;
        r7.title = "主标题-7";
        r7.subTitle = "副标题-7";

        DataInfoBean r8 = new DataInfoBean();
        r8.cover = R.mipmap.h;
        r8.logo = R.mipmap.wechat_icon_4;
        r8.title = "主标题-8";
        r8.subTitle = "副标题-8";

        return Arrays.asList(new DataInfoBean[]{r1, r2, r3, r4, r5, r6, r7, r8 });
    }
}
