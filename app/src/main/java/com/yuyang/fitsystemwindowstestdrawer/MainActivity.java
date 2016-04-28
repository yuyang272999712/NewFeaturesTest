package com.yuyang.fitsystemwindowstestdrawer;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.MetricsAbout.MetricsActivity;
import com.yuyang.fitsystemwindowstestdrawer.mvp.activity.UserInfoMvpTest;
import com.yuyang.fitsystemwindowstestdrawer.cardViewPager.CardViewPager;
import com.yuyang.fitsystemwindowstestdrawer.dragHelperTest.DefinedViewTest;
import com.yuyang.fitsystemwindowstestdrawer.horizontalFling.HorizontalFlingActivity;
import com.yuyang.fitsystemwindowstestdrawer.largeImage.LargeImageTest;
import com.yuyang.fitsystemwindowstestdrawer.preferenceAbout.PreferenceDemo;
import com.yuyang.fitsystemwindowstestdrawer.animationAbout.MyActivity;
import com.yuyang.fitsystemwindowstestdrawer.animationAbout.PropertyAnimationActivity;
import com.yuyang.fitsystemwindowstestdrawer.replaceViewHolder.ListTestActivity;
import com.yuyang.fitsystemwindowstestdrawer.service.BackgroundService;
import com.yuyang.fitsystemwindowstestdrawer.tantan.TantanActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView image;
    private Button button;
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
    private TextView textView;
    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashHandler.getInstance().start(getApplicationContext());

        /*//请求一个窗口进度条特性风格
        requestWindowFeature(Window.FEATURE_PROGRESS);
        //设置进度条可视
        setProgressBarVisibility(true);
        setSecondaryProgress(70);
        setProgress(50);*/
        MyApplication.getInstance().setName("wocao");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("开发测试");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Animator animator = createAnimator(image);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        image.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animator.start();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Pair imagePair = Pair.create(image, "image");
                    Pair buttonPair = Pair.create(button, "button");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, imagePair, buttonPair);
                    startActivity(intent, options.toBundle());
                }else {
                    startActivity(intent);
                }
            }
        });

        image = (ImageView) findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animator animator = createAnimator(v);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (image.getVisibility() == View.VISIBLE) {
                            image.setVisibility(View.INVISIBLE);
                        }else {
                            image.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animator.start();
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PreferenceDemo.class);
                startActivity(intent);
            }
        });

        PreferenceManager.setDefaultValues(this, R.xml.preference, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean apply_wireless = sharedPreferences.getBoolean("apply_wireless",false);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText(apply_wireless+"");

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MetricsActivity.class);
                startActivity(intent);
            }
        });

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HorizontalFlingActivity.class);
                startActivity(intent);
            }
        });

        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DefinedViewTest.class);
                startActivity(intent);
            }
        });

        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserInfoMvpTest.class);
                startActivity(intent);
            }
        });

        button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListTestActivity.class);
                startActivity(intent);
            }
        });

        button8 = (Button) findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LargeImageTest.class);
                startActivity(intent);
            }
        });

        button9 = (Button) findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CardViewPager.class);
                startActivity(intent);
            }
        });

        button10 = (Button) findViewById(R.id.button10);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TantanActivity.class);
                startActivity(intent);
            }
        });

        button11 = (Button) findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PropertyAnimationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(this, BackgroundService.class));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Animator createAnimator(View v){
        Animator animator;
        if(v.getVisibility() == View.VISIBLE){
            animator = ViewAnimationUtils.createCircularReveal(
                    v,
                    v.getWidth()/2,
                    v.getHeight()/2,
                    v.getWidth(),
                    0
            );
        }else {
            animator = ViewAnimationUtils.createCircularReveal(
                    v,
                    v.getWidth(),
                    v.getHeight(),
                    0,
                    v.getWidth()
            );
        }
        animator.setDuration(1000);
        animator.setInterpolator(new DecelerateInterpolator());
        return animator;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
