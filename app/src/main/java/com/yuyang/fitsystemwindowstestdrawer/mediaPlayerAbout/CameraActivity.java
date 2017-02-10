package com.yuyang.fitsystemwindowstestdrawer.mediaPlayerAbout;

import android.graphics.Rect;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 直接调用摄像头
 * 需要<uses-permission android:name="android.permission.CAMERA"/>权限
 *
 * Camera类可以调整摄像头设置、指定图片首选项和拍摄照片。
 * 要访问摄像头可使用它的静态open方法：
 *  Camera camera = Camera.open();
 * 使用完后记得释放它：
 *  camera.release();
 *
 * Camera的预览需要SurfaceView
 *  camera.setPreviewDisplay(holder);//holder指SurfaceHolder
 *  camera.startPreview();
 * SurfaceHolder销毁后需要停止预览：
 *  camera.stopPreview();
 *
 * 拍摄照片
 *  调用Camera对象的takePicture,并传入一个ShutterCallback和两个PictureCallback实现（一个用于RAW图像，一个用于JPEG编码的图像）
 */
public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private static final String TAG = "CameraActivity";

    private Camera camera;
    private Camera.Parameters parameters;
    private SurfaceView surfaceView;

    private Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            //快门关闭时执行一些操作
        }
    };
    private Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //对图像的原始数据做一些处理
        }
    };
    private Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //将图像的JPEG数据保存到SD卡
            FileOutputStream outputStream = null;
            String path = Environment.getExternalStorageDirectory() + "/yuyang_camera.jpg";
            try {
                outputStream = new FileOutputStream(path);
                outputStream.write(data);
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        surfaceView = (SurfaceView) findViewById(R.id.camera_surface_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera = Camera.open();

        parameters = camera.getParameters();
        /**
         * 设置自动对焦
         */
        //找出可用的对焦模式
        if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)){
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            /*camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    Log.e(TAG, "AutoFocus:"+success);
                }
            });*/
        }
        /**
         * 设置对焦区域
         */
        //确定当前设备是否支持对焦区域功能
        int focusAreaCount = parameters.getMaxNumFocusAreas();//获取支持多少个对焦区域
        if (focusAreaCount > 0) {
            Camera.Area area = new Camera.Area(new Rect(-500, -500, 500,500), 0);
            ArrayList<Camera.Area> listArea = new ArrayList<Camera.Area>();
            listArea.add(area);
            parameters.setFocusAreas(listArea);
        }

        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.setFixedSize(400,300);
    }

    /**
     * 拍摄图片
     * @param view
     */
    public void takePicture(View view){
        camera.takePicture(shutterCallback, rawCallback, jpegCallback);
    }

    /**
     * 修改图像详细信息（地理位置、相机信息、拍摄时间等）
     * @param view
     */
    public void modifyExif(View view){
        File file = new File(Environment.getExternalStorageDirectory(), "/yuyang_camera.jpg");
        try {
            ExifInterface exif = new ExifInterface(file.getCanonicalPath());
            //读取摄像头模型和位置属性
            String model = exif.getAttribute(ExifInterface.TAG_MODEL);
            Toast.makeText(this,"摄像头模型："+model, Toast.LENGTH_SHORT).show();
            //设置摄像头的品牌
            exif.setAttribute(ExifInterface.TAG_MAKE, "yuyang's phone");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.release();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            //TODO yuyang 在SurfaceView上显示预览
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //camera.stopPreview();
    }
}
