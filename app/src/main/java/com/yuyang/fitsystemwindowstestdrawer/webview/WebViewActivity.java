package com.yuyang.fitsystemwindowstestdrawer.webview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.FileUtils;

import java.io.File;
import java.util.List;

/**
 * 如果未选择文件mUploadMessage.onReceiveValue(null)必须被调用,否则网页会阻塞。
 * 最后，在打release包的时候，因为我们会混淆，要特别设置不要混淆WebChromeClient子类里面的openFileChooser方法，由于不是继承的方法，所以默认会被混淆，然后就无法选择文件了。
 */
public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private Button jsButton;
    private Toolbar toolbar;

    private ValueCallback mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private String mCameraFilePath;
    private static final String NATIVE_METHOD_HANDLE = "WebViewJavascriptBridge";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        findViews();
        initDatas();
        setAction();
    }

    private void findViews() {
        webView = (WebView) findViewById(R.id.web_view);
        jsButton = (Button) findViewById(R.id.web_view_js_btn);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void initDatas() {
        setSupportActionBar(toolbar);

        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);//支持JS交互

        //TODO yuyang 添加JS代码与java程序交互的桥梁，JS调用时 NATIVE_METHOD_HANDLE 相当于类名
        // new JavaForJs(this)中的方法就是 JS 调用的方法
        //对应js中的WebViewJavascriptBridge.xxx
        webView.addJavascriptInterface(new JavaForJs(this), NATIVE_METHOD_HANDLE);

        webView.setWebViewClient(new WebViewClient(){
            /**
             * 该方法返回false－由系统浏览器处理webView中的url点击
             * 返回true－自己处理url点击
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.getTitle();
                getSupportActionBar().setTitle("webView加载完成");
            }
        });

        //TODO yuyang 如果想用自带进度条的WebView请修改ProgressWebView的WebChromeClient
        webView.setWebChromeClient(new WebChromeClient(){
            // For Android 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(null);
                }
                mUploadMessage = uploadMsg;
                startActivityForResult(createDefaultOpenableIntent(), FILECHOOSER_RESULTCODE);
            }

            // For Android 3.0+
            public void openFileChooser( ValueCallback uploadMsg, String acceptType ) {
                this.openFileChooser(uploadMsg);
            }

            //For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
                this.openFileChooser(uploadMsg, acceptType);
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("NewApi")
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                if (mUploadMessage != null){
                    mUploadMessage.onReceiveValue(null);
                }
                mUploadMessage = filePathCallback;
                startActivityForResult(createDefaultOpenableIntent(), FILECHOOSER_RESULTCODE);
                return true;
            }
        });
    }

    private void setAction() {
//        webView.loadUrl("http://pan.baidu.com/wap/home");//百度云盘，测试文件上传功能
        webView.loadUrl("file:///android_asset/demo.html");
        //TODO yuyang Java调用JS
        jsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:forJava('jsButton调用结果')");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();

            File cameraFile = new File(mCameraFilePath);
            if (cameraFile.exists()) {//如果存在，说明调用的是系统相机
                // 通知系统刷新相册
                result = Uri.fromFile(cameraFile);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));
            }

            if (result == null) {
                //TODO yuyang 不管resultCode是什么此方法必需被调用一次，否则会造成阻塞
                mUploadMessage.onReceiveValue(null);
                mUploadMessage = null;
                return;
            }

            //TODO yuyang 这里做这个转换是因为锤子手机的兼容问题，content provider提供的URI锤子手机无法上传
            String path =  FileUtils.getPath(this, result);
            Uri uri = Uri.fromFile(new File(path));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mUploadMessage.onReceiveValue(new Uri[]{uri});
                //TODO yuyang 不能使用WebChromeClient.FileChooserParams.parseResult(resultCode, intent)，因为拍照的话intent的值可能是空
                //mUploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
            }else {
                mUploadMessage.onReceiveValue(uri);
            }

            mUploadMessage = null;
        }
    }

    /**
     * webView受理返回事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 创建系统调用广播
     * @return
     */
    private Intent createDefaultOpenableIntent() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");

        Intent chooser = createChooserIntent(createCameraIntent()
                //createCamcorderIntent(),
                //createSoundRecorderIntent()
        );
        chooser.putExtra(Intent.EXTRA_INTENT, i);
        return chooser;
    }

    private Intent createChooserIntent(Intent... intents) {
        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
        chooser.putExtra(Intent.EXTRA_TITLE, "请选择");
        return chooser;
    }

    /**
     * 调用拍照的广播
     * @return
     */
    private Intent createCameraIntent() {
        /**
         * TODO yuyang
         * Android系统安装第三方拍照应用后使用MediaStore.ACTION_IMAGE_CAPTURE会提示选择拍照应用，
         * 但部分第三方应用不返回拍照结果，为了能正常获取返回，所以有了这段代码，用以直接打开Android默认的相机。
         */
        /*
        有的手机相机名称不是这个默认名称
        final Intent cameraIntent = getPackageManager().getLaunchIntentForPackage("com.android.camera");
        if (cameraIntent != null) {
            cameraIntent.setPackage("com.android.camera");
        }*/
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo: list) {
            if (resolveInfo.activityInfo.applicationInfo.sourceDir.startsWith("/system/app"))
            {
                intent.setClassName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name);
                break;
            }
        }


        File externalDataDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
        File cameraDataDir = new File(externalDataDir.getAbsolutePath() +
                File.separator + "yuyang_camera");
        cameraDataDir.mkdirs();
        mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator +
                System.currentTimeMillis() + ".jpg";
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));
        return intent;
    }

/**
 * 调用录像的广播
 */
    /*private Intent createCamcorderIntent() {
        return new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
    }*/

/**
 * 调用录音的广播
 */
    /*private Intent createSoundRecorderIntent() {
        return new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
    }*/
}
