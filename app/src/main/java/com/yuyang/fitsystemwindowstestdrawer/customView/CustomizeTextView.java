package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * View构造函数的自定属性继承关系测试
 * 一、public TypedArray obtainStyledAttributes (AttributeSet set, int[] attrs, int defStyleAttr, int defStyleRes)
 *      set：属性值的集合
 *      attrs：我们要获取的属性的资源ID的一个数组，如同ContextProvider中请求数据库时的Projection数组，
 *            就是从一堆属性中我们希望查询什么属性的值
 *      defStyleAttr：这个是当前Theme中的一个attribute，是指向style的一个引用，
 *                    当在layout xml中和style中都没有为View指定属性时，
 *                    会从Theme中这个attribute指向的Style中查找相应的属性值，
 *                    这就是defStyle的意思，如果没有指定属性值，就用这个值，所以是默认值，
 *                    但这个attribute要在Theme中指定，且是指向一个Style的引用，如果这个参数传入0表示不向Theme中搜索默认值
 *                    （就是本例中的ActivityTheme中CustomizeStyle属性）
 *      defStyleRes：这个也是指向一个Style的资源ID，
 *                  但是仅在defStyleAttr为0或defStyleAttr不为0但Theme中没有为defStyleAttr属性赋值时起作用
 *
 *
 * 总结：
 *      1. 直接在XML中定义>style定义>由defStyleAttr定义的值>(defStyleRes指定的默认值>=直接在Theme中指定的值)
 *      2. defStyleAttr（即defStyle）不为0且在当前Theme中可以找到这个attribute的定义时，defStyleRes不起作用
 *         所以本例中attr_four虽然在defStyleRes（DefaultCustomizeStyle）中定义了，但取到的值仍为null。
 */
public class CustomizeTextView extends TextView {
    private static final String TAG = "－－"+CustomizeTextView.class.getSimpleName()+"－－";

    /**
     * 用代码动态创建一个view而不使用布局文件xml inflate，那么此实现就可以了。
     * @param context
     */
    public CustomizeTextView(Context context) {
        this(context, null);
    }

    /**
     * 此构造函数多了一个AttributeSet类型的参数，在通过布局文件xml创建一个view时，
     * 这个参数会将xml里设定的属性传递给构造函数。如果采用xml inflate的方法却没有在代码里实现此构造，那么运行时就会报错
     * @param context
     * @param attrs
     */
    public CustomizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.CustomizeStyle);
    }

    public CustomizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Customize, defStyleAttr,
                R.style.DefaultCustomizeStyle);

        String one = a.getString(R.styleable.Customize_attr_one);
        String two = a.getString(R.styleable.Customize_attr_two);
        String three = a.getString(R.styleable.Customize_attr_three);
        String four = a.getString(R.styleable.Customize_attr_four);
        Log.i(TAG, "one:" + one);
        Log.i(TAG, "two:" + two);
        Log.i(TAG, "three:" + three);
        Log.i(TAG, "four:" + four);
        a.recycle();
    }
}
