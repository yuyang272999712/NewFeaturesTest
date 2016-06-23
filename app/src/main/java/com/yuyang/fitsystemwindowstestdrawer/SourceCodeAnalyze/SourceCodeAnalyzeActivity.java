package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.asyncTask.AsyncTaskSimpleActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.drawableAbout.DrawableAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.fragmentAbout.FragmentAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.handlerAndMessage.HandlerAndMessageActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.layoutInflate.LayoutInflateActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.touchEventDispatch.ViewTouchEventActivity;

/**
 * 源码解析
 */
public class SourceCodeAnalyzeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_code);
    }

    public void gotoLayoutInflateActivity(View view){
        Intent intent = new Intent(this, LayoutInflateActivity.class);
        startActivity(intent);
    }

    public void gotoViewTouchEvent(View view){
        Intent intent = new Intent(this, ViewTouchEventActivity.class);
        startActivity(intent);
    }

    public void gotoDrawableAboutActivity(View view){
        Intent intent = new Intent(this, DrawableAboutActivity.class);
        startActivity(intent);
    }

    public void gotoHandlerUseActivity(View view){
        Intent intent = new Intent(this, HandlerAndMessageActivity.class);
        startActivity(intent);
    }

    public void gotoAsyncTaskUseActivity(View view){
        Intent intent = new Intent(this, AsyncTaskSimpleActivity.class);
        startActivity(intent);
    }

    public void gotoFragmentAboutActivity(View view){
        Intent intent = new Intent(this, FragmentAboutActivity.class);
        startActivity(intent);
    }
}
