package com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout.normalWay;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout.LoadingDialogFragment;
import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;

/**
 使用Fragment来保存对象，用于恢复数据
    如果重新启动你的Activity需要恢复大量的数据，重新建立网络连接，或者执行其他的密集型操作，这样因为配置发生变化而完全
 重新启动可能会是一个慢的用户体验。并且，使用系统提供的onSaveInstanceState()的回调中，使用Bundle来完全恢复你
 Activity的状态是可能是不现实的（Bundle不是设计用来携带大量数据的（例如bitmap），并且Bundle中的数据必须能够被
 序列化和反序列化），这样会消耗大量的内存和导致配置变化缓慢。在这样的情况下，当你的Activity因为配置发生改变而重启，
 你可以通过保持一个Fragment来缓解重新启动带来的负担。这个Fragment可以包含你想要保持的有状态的对象的引用。
    当Android系统因为配置变化关闭你的Activity的时候，你的Activity中被标识保持的fragments不会被销毁。你可以在你的
 Activity中添加这样的fragements来保存有状态的对象。
    在运行时配置发生变化时，在Fragment中保存有状态的对象
 a) 继承Fragment，声明引用指向你的有状态的对象
 b) 当Fragment创建时调用setRetainInstance(boolean)
 c) 把Fragment实例添加到Activity中
 d) 当Activity重新启动后，使用FragmentManager对Fragment进行恢复
 */
public class FragmentRetainDataActivity extends Activity {
    private RetainedFragment dataFragment;
    private LoadingDialogFragment loadingDialog;
    private ImageView imageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("onCreate");
        setContentView(R.layout.activity_fragment_retain_data);
        imageView = (ImageView) findViewById(R.id.fragment_retain_data_img);

        //通过FragmentManager获取Activity中保持的fragment
        FragmentManager fragmentManager = getFragmentManager();
        dataFragment = (RetainedFragment) fragmentManager.findFragmentByTag("data");
        if (dataFragment == null){
            dataFragment = new RetainedFragment();
            fragmentManager.beginTransaction().add(dataFragment, "data").commit();
        }
        //从持有的fragment中获取数据
        bitmap = collectMyLoadedData();
        initData();
    }

    private void initData() {
        //如果数据为空，则加载数据
        if (bitmap == null){
            loadingDialog = new LoadingDialogFragment();
            loadingDialog.setCancelable(false);
            loadingDialog.show(getFragmentManager(), "loading");
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            ImageRequest imageRequest = new ImageRequest(
                    "http://img.my.csdn.net/uploads/201407/18/1405652589_5125.jpg",
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            bitmap = response;
                            imageView.setImageBitmap(bitmap);
                            //将这个数据保存在fragment中
                            dataFragment.setData(bitmap);
                            loadingDialog.dismiss();
                        }
                    },0, 0, Bitmap.Config.RGB_565, null);
            requestQueue.add(imageRequest);
        }else {
            imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * 从dataFragment中获取数据
     * @return
     */
    private Bitmap collectMyLoadedData() {
        return dataFragment.getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i("onDestroy");
    }
}
