package com.test.yuyang.launchmode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    public void startFirstActivity(View view){
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
    }

    public void startSecondActivity(View view){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void startThirdActivity(View view){
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }

    public void startFourthActivity(View view){
        Intent intent = new Intent(this, FourthActivity.class);
        startActivity(intent);
    }

}
