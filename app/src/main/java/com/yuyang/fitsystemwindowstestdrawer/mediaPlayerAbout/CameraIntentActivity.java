package com.yuyang.fitsystemwindowstestdrawer.mediaPlayerAbout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.io.File;

/**
 * 通过调用第三方应用拍摄照片
 */
public class CameraIntentActivity extends AppCompatActivity {
    private static final int TAKE_PICTURE = 0;

    private Uri outputFileUri;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_intent);

        imageView = (ImageView) findViewById(R.id.camera_intent_img);
    }

    /**
     * 拍摄照片回去缩略图
     * @param view
     */
    public void takePhotoThumb(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    /**
     * 指定照片输出路径
     * @param view
     */
    public void takePhoto(View view){
        //创建输出文件
        File file = new File(Environment.getExternalStorageDirectory(), "yuyang.jpg");
        outputFileUri = Uri.fromFile(file);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK){
            if (data != null){
                if (data.hasExtra("data")){
                    Bitmap thumbnail = data.getParcelableExtra("data");
                    imageView.setImageBitmap(thumbnail);
                }
            }else {
                /*imageView.setImageURI(outputFileUri);*/
                //或者
                int width = imageView.getWidth();
                int height = imageView.getHeight();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(outputFileUri.getPath(), options);
                int imageWidth = options.outWidth;
                int imageHeight = options.outHeight;
                //确定图片缩小多少
                int scaleFactor = Math.min(imageWidth/width, imageHeight/height);
                //将图像文件解码为缩放大小以填充视图
                options.inJustDecodeBounds = false;
                options.inSampleSize = scaleFactor;
                options.inPurgeable = true;
                Bitmap bitmap = BitmapFactory.decodeFile(outputFileUri.getPath(), options);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
