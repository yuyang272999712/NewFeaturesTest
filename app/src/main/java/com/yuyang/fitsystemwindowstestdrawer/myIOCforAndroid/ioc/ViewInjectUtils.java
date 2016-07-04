package com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc;

import android.app.Activity;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation.ContentView;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation.EventBase;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation.OnClick;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation.ViewInject;
import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 类似于xUtils的运行时注解
 *  (ButterKnife是编译时注解)
 */
public class ViewInjectUtils {
    private static final String METHOD_SET_CONTENT_VIEW = "setContentView";
    private static final String METHOD_FIND_VIEW_BY_ID = "findViewById";

    public static void inject(Activity activity){
        injectContentView(activity);
        injectViews(activity);
        injectEvents(activity);
    }

    /**
     * 注入主布局文件
     * 通过传入的activity对象，获得它的Class类型，判断是否写了ContentView这个注解，如果写了，读取它的value，然后得到setContentView这个方法，使用invoke进行调用；
     * @param activity
     */
    private static void injectContentView(Activity activity){
        LogUtils.e("java控制反转，调用Activity的 setContentView() 方法");
        Class<? extends Activity> clazz = activity.getClass();
        // 查询类上是否存在ContentView注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null){
            int contentViewLayoutId = contentView.value();
            try {
                Method method = clazz.getMethod(METHOD_SET_CONTENT_VIEW, int.class);
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
    private static void injectViews(Activity activity){
        LogUtils.e("java控制反转，调用Activity的 findViewById() 方法");
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

    /**
     * 注入事件
     * 遍历所有的方法，拿到该方法省的OnClick注解，然后再拿到该注解上的EventBase注解，得到事件监听的需要调用的方法名，类型，和需要调用的方法的名称；通过Proxy和InvocationHandler得到监听器的代理对象，显示设置了方法，最后通过反射设置监听器。
     * @param activity
     */
    private static void injectEvents(Activity activity){
        LogUtils.e("java控制反转，调用View的 setOnClickListener() 方法");
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getMethods();//这里获取的是所有public方法
        //遍历所有的方法
        for (Method method:methods){
            //获取方法上的所有注解
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation:annotations){
//          //获取方法上的OnClick注解 TODO 这里不这么做是因为我们还可能会添加 长按事件 的注册
//          OnClick annotation = method.getAnnotation(OnClick.class);
//          if (annotation != null) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                //拿到注解上的注解
                EventBase eventBaseAnnotation = annotationType.getAnnotation(EventBase.class);
                //如果设置为EventBase
                if (eventBaseAnnotation != null){
                    //取出设置监听器的类型，监听器的名称，调用的方法名
                    Class<?> listenerType = eventBaseAnnotation.listenerType();
                    String listenerSetter = eventBaseAnnotation.listenerSetter();
                    String methodName = eventBaseAnnotation.methodName();

                    try {
                        //拿到Onclick注解中的value方法
                        Method aMethod = annotationType.getDeclaredMethod("value");
                        //取出所有的viewId
                        int[] viewIds = (int[]) aMethod.invoke(annotation);
                        //通过InvocationHandler设置代理
                        DynamicHandler handler = new DynamicHandler(activity);
                        handler.addMethod(methodName, method);
                        Object listener = Proxy.newProxyInstance(
                                listenerType.getClassLoader(),
                                new Class<?>[] { listenerType }, handler);
                        //遍历所有的View，设置事件
                        for (int viewId : viewIds) {
                            View view = activity.findViewById(viewId);
                            Method setEventListenerMethod = view.getClass().getMethod(listenerSetter, listenerType);
                            setEventListenerMethod.invoke(view, listener);
                        }
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
