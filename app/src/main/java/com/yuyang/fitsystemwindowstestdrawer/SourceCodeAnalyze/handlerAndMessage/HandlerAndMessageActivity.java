package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.handlerAndMessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Handler完全解析
 */
public class HandlerAndMessageActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_message);
    }

    public void gotoHandler(View view){
        Intent intent = new Intent(this, HandlerSimpleActivity.class);
        startActivity(intent);
    }

    public void gotoHandlerThread(View view){
        Intent intent = new Intent(this, HandlerTreadSimpleActivity.class);
        startActivity(intent);
    }

    public void gotoHandlerTest(View view){
        Intent intent = new Intent(this, LoadLocationAlbumActivity.class);
        startActivity(intent);
    }

    public void gotoIdleHandler(View view){
        Intent intent = new Intent(this, IdleHandlerActivity.class);
        startActivity(intent);
    }
}
