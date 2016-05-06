package com.yuyang.fitsystemwindowstestdrawer.webview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.FileUtils;

import java.io.File;

/**
 * mUploadMessage.onReceiveValue(null) ,否则网页会阻塞。
 * 最后，在打release包的时候，因为我们会混淆，要特别设置不要混淆WebChromeClient子类里面的openFileChooser方法，由于不是继承的方法，所以默认会被混淆，然后就无法选择文件了。
 */
public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private Button jsButton;
    private Toolbar toolbar;

    private ValueCallback mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private String mCameraFilePath;

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

            //For Android 5.0
            /*@TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("NewApi")
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                if (mUploadMessage != null){
                    mUploadMessage.onReceiveValue(null);
                }
                mUploadMessage = filePathCallback;
                startActivityForResult(createDefaultOpenableIntent(), FILECHOOSER_RESULTCODE);
                return true;
            }*/
        });
    }

    private void setAction() {
        webView.loadUrl("http://pan.baidu.com/wap/home");
//        webView.loadUrl("file:///android_asset/demo.html");
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
                mUploadMessage.onReceiveValue(null);
                mUploadMessage = null;
                return;
            }

            String path =  FileUtils.getPath(this, result);
            Uri uri = Uri.fromFile(new File(path));

            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mUploadMessage.onReceiveValue(new Uri[]{result});
            }else {
                mUploadMessage.onReceiveValue(result);
            }*/
            mUploadMessage.onReceiveValue(uri);

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
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File externalDataDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
        File cameraDataDir = new File(externalDataDir.getAbsolutePath() +
                File.separator + "yuyang_camera");
        cameraDataDir.mkdirs();
        mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator +
                System.currentTimeMillis() + ".jpg";
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));
        return cameraIntent;
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
