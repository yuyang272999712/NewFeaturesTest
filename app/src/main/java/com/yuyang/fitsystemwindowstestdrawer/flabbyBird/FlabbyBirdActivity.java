package com.yuyang.fitsystemwindowstestdrawer.flabbyBird;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * FlabbyBird游戏
 */
public class FlabbyBirdActivity extends AppCompatActivity {
    GameFlabbyBirdView mGame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mGame = new GameFlabbyBirdView(this);
        setContentView(mGame);
    }
}
