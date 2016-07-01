package com.yuyang.fitsystemwindowstestdrawer.Game2048;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.ViewInjectUtils;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation.ContentView;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation.ViewInject;

/**
 * 模拟2048小游戏
 */
@ContentView(R.layout.activity_game_2048)
public class Game2048Activity extends AppCompatActivity implements Game2048Layout.OnGame2048Listener {
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.id_game2048)
    private Game2048Layout game2048Layout;
    @ViewInject(R.id.id_score)
    private TextView scoreText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        game2048Layout.setOnGame2048Listener(this);
    }

    @Override
    public void onScoreChange(int score) {
        scoreText.setText("Score: "+score);
    }

    @Override
    public void onGameOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Game Over");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                game2048Layout.restart();
            }
        });
        builder.show();
    }
}
