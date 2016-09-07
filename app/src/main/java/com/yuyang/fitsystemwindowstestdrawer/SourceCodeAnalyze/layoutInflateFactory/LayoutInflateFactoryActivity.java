package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.layoutInflateFactory;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 重置LayoutInflater 的 factory
 */
public class LayoutInflateFactoryActivity extends AppCompatActivity {
    private static final String TAG = "LayoutInflateFactory";

    private Toolbar mToolbar;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                Log.e(TAG, "name = " + name);
                int n = attrs.getAttributeCount();
                for (int i = 0; i < n; i++) {
                    Log.e(TAG, attrs.getAttributeName(i) + " , " + attrs.getAttributeValue(i));
                }
                /*if (name.equals("TextView")) {
                    Button button = new Button(context, attrs);
                    return button;
                }*/

                //你可以在这里直接new自定义View
                //你可以在这里将系统类替换为自定义View
                //就像上面那样将TextView替换为Button

                //由于AppCompatActivity类已经重写了：
                /*public void installViewFactory() {
                    LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                    if (layoutInflater.getFactory() == null) {
                        LayoutInflaterCompat.setFactory(layoutInflater, this);
                    } else {
                        Log.i(TAG, "The Activity's LayoutInflater already has a Factory installed"
                                + " so we can not install AppCompat's");
                    }
                }*/
                //所以我们在这里在AppCompatActivity之前调用setFactory就会顶替掉系统的factory，所以做如下操作：
                //TODO yuyang 这里的getDelegate()是AppCompatActivity的方法，获取的是AppCompatDelegateImplV7类
                //appcompat 创建view代码
                AppCompatDelegate delegate = getDelegate();
                View view = delegate.createView(parent, name, context, attrs);

                //统一设置文字颜色
                if ( view!= null && (view instanceof TextView)) {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.colorAccent));
                }

                return view;
            }
        });
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout_inflate_factory);
        findViews();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        textView = (TextView) findViewById(R.id.textView);

        mToolbar.setTitle("重置LayoutInflater的factory");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
