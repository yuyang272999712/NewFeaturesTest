package com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc;

import android.app.Activity;

import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation.ContentView;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation.ViewInject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 */
public class ViewInjectUtils {
    private static final String METHOD_SET_CONTENTVIEW = "setContentView";
    private static final String METHOD_FIND_VIEW_BY_ID = "findViewById";

    public static void inject(Activity activity){
        injectContentView(activity);
    }

    /**
     * 注入主布局文件
     * 通过传入的activity对象，获得它的Class类型，判断是否写了ContentView这个注解，如果写了，读取它的value，然后得到setContentView这个方法，使用invoke进行调用；
     * @param activity
     */
    private static void injectContentView(Activity activity){
        Class<? extends Activity> clazz = activity.getClass();
        // 查询类上是否存在ContentView注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null){
            int contentViewLayoutId = contentView.value();
            try {
                Method method = clazz.getMethod(METHOD_SET_CONTENTVIEW, int.class);
                method.setAccessible(true);
                method.invoke(activity, contentViewLayoutId);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 注入所有的控件
     * 获取声明的所有的属性，遍历，找到存在ViewInject注解的属性，或者其value，然后去调用findViewById方法，最后把值设置给field
     * @param activity
     */
    private void injectViews(Activity activity){
        Class<? extends Activity> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        // 遍历所有成员变量
        for (Field field:fields){
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null){
                int viewId = viewInject.value();
                if (viewId != -1){
                    try {
                        Method method = clazz.getMethod(METHOD_FIND_VIEW_BY_ID, int.class);
                        Object resView = method.invoke(activity, viewId);
                        field.setAccessible(true);
                        field.set(activity, resView);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
