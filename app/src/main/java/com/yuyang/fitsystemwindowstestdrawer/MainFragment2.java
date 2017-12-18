package com.yuyang.fitsystemwindowstestdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.Canvas.CanvasAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.Game2048.Game2048Activity;
import com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.SQLiteDbAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.SensorAbout.SensorAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout.ConfigChangesActivity;
import com.yuyang.fitsystemwindowstestdrawer.contentProvider.ContentProviderActivity;
import com.yuyang.fitsystemwindowstestdrawer.effect360AppIntroduce.Effect360AppIntroduceActivity;
import com.yuyang.fitsystemwindowstestdrawer.eventBus.EventBusAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.flabbyBird.FlabbyBirdActivity;
import com.yuyang.fitsystemwindowstestdrawer.homeDemo.HomeDemoActivity;
import com.yuyang.fitsystemwindowstestdrawer.imageLoader.ImageLoaderActivity;
import com.yuyang.fitsystemwindowstestdrawer.keepAlive.SportsActivity;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.MyIOCActivity;
import com.yuyang.fitsystemwindowstestdrawer.notificationDialogPopupWindowToast.NotificationDialogPopupToastActivity;
import com.yuyang.fitsystemwindowstestdrawer.pictureSelect.PictureSelectActivity;
import com.yuyang.fitsystemwindowstestdrawer.rxJava.RxJavaActivity;
import com.yuyang.fitsystemwindowstestdrawer.service.ServiceActivity;
import com.yuyang.fitsystemwindowstestdrawer.softInput.SoftInputActivity;
import com.yuyang.fitsystemwindowstestdrawer.telephony_sms.TelephonyAndSmsActivity;

/**
 * Created by yuyang on 2016/6/2.
 */
public class MainFragment2 extends Fragment {
    private View mContentView;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button10;
    private Button button11;
    private Button button12;
    private Button button13;
    private Button button14;
    private Button button15;
    private Button button16;
    private Button button17;
    private Button button18;
    private Button button19;
    private Button button20;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_main_2, container, false);
        return mContentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        button1 = (Button) mContentView.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TelephonyAndSmsActivity.class);
                startActivity(intent);
            }
        });
        button2 = (Button) mContentView.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        button3 = (Button) mContentView.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FlabbyBirdActivity.class);
                startActivity(intent);
            }
        });
        button4 = (Button) mContentView.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Effect360AppIntroduceActivity.class);
                startActivity(intent);
            }
        });
        button5 = (Button) mContentView.findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NotificationDialogPopupToastActivity.class);
                startActivity(intent);
            }
        });
        button6 = (Button) mContentView.findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeDemoActivity.class);
                startActivity(intent);
            }
        });
        button7 = (Button) mContentView.findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfigChangesActivity.class);
                startActivity(intent);
            }
        });
        button8 = (Button) mContentView.findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PictureSelectActivity.class);
                startActivity(intent);
            }
        });
        button9 = (Button) mContentView.findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SQLiteDbAboutActivity.class);
                startActivity(intent);
            }
        });
        button10 = (Button) mContentView.findViewById(R.id.button10);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyIOCActivity.class);
                startActivity(intent);
            }
        });
        button11 = (Button) mContentView.findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Game2048Activity.class);
                startActivity(intent);
            }
        });
        button12 = (Button) mContentView.findViewById(R.id.button12);
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ContentProviderActivity.class);
                startActivity(intent);
            }
        });
        button13 = (Button) mContentView.findViewById(R.id.button13);
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ImageLoaderActivity.class);
                startActivity(intent);
            }
        });
        button14 = (Button) mContentView.findViewById(R.id.button14);
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventBusAboutActivity.class);
                startActivity(intent);
            }
        });
        button15 = (Button) mContentView.findViewById(R.id.button15);
        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ServiceActivity.class);
                startActivity(intent);
            }
        });
        button16 = (Button) mContentView.findViewById(R.id.button16);
        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SensorAboutActivity.class);
                startActivity(intent);
            }
        });
        button17 = (Button) mContentView.findViewById(R.id.button17);
        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RxJavaActivity.class);
                startActivity(intent);
            }
        });
        button18 = (Button) mContentView.findViewById(R.id.button18);
        button18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CanvasAboutActivity.class);
                startActivity(intent);
            }
        });
        button19 = (Button) mContentView.findViewById(R.id.button19);
        button19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SoftInputActivity.class);
                startActivity(intent);
            }
        });
        button20 = (Button) mContentView.findViewById(R.id.button20);
        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SportsActivity.class);
                startActivity(intent);
            }
        });
    }
}
