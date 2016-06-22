package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.handlerAndMessage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.lang.ref.WeakReference;

/**
 * TODO yuyang Handler、Message、MessageQueue、Looper、HandlerThread源码解析
 *  以下是handler简单使用
 *
 * 具体解析详见：http://blog.csdn.net/yanbober/article/details/45936145
 *
 * 一、实例化过程
 * （＊＊＊子线程在实例化Handler之前需要先调运Looper.prepare()，下面有解释）
 *      Handler的所有实例化方法最终都会掉用Handler(Callback callback, boolean async)构造方法，
 *  该方法会通过Looper.myLooper()静态方法获取当前线程中的Looper对象，再通过mQueue = mLooper.mQueue;获取当先线程中的消息队列等信息。
 *      Looper.myLooper()方法通过sThreadLocal.get()获取当前线程中的Looper对象。
 *  Looper在使用前一定会调用其prepare(boolean quitAllowed)方法，该方法会sThreadLocal.set(new Looper(quitAllowed));向当前线程中实例一个Looper对象；
 *  （UI线程中的Looper会在UI线程启动时自动在ActivityThread类的main方法中调用Looper.prepare()方法）
 *  Looper对象管理当前线程中的唯一消息队列：mQueue = new MessageQueue(quitAllowed)，并持有当前线程：mThread = Thread.currentThread();
 *
 *  Handler与Looper实例化总结，具体如下：
 *   1、在主线程中可以直接创建Handler对象，而在子线程中需要先调用Looper.prepare()才能创建Handler对象，否则运行抛出”Can’t create handler inside thread that has not called Looper.prepare()”异常信息。
 *   2、每个线程中最多只能有一个Looper对象，否则抛出异常。
 *   3、可以通过Looper.myLooper()获取当前线程的Looper实例，通过Looper.getMainLooper()获取主（UI）线程的Looper实例。
 *   4、一个Looper只能对应了一个MessageQueue。
 *   5、一个线程中只有一个Looper实例，一个MessageQueue实例，可以有多个Handler实例。
 *
 * 二、Handler消息收发机制
 *  1、通过Handler发消息到消息队列
 *      通过分析Handler源码发现Handler中提供的很多个发送消息方法中除了 sendMessageAtFrontOfQueue() 方法之外，
 *  其它的发送消息方法最终都调用了 sendMessageAtTime(Message msg, long uptimeMillis) 方法。
 *      sendMessageAtTime()方法的第二行可以看到queue = mQueue，而mQueue是在Handler实例化时构造函数中实例化的。
 *  在Handler的构造函数中可以看见mQueue = mLooper.mQueue;而Looper的mQueue对象上面分析过了，是在Looper的构造函数中创建的一个MessageQueue。
 *      再放入消息队列前，消息Message的target属性设置为当前Handler对象（进行关联msg.target = this）；
 *  接着将msg与uptimeMillis这两个参数都传递到MessageQueue（消息队列）的enqueueMessage()方法中。
 *  2、通过Handler接收发送的消息
 *      Looper.loop()方法首先得到了Looper所在线程的Looper对象me，接着通过当前Looper对象得到与Looper对象一一对应的
 *  MessageQueue消息队列（也就类似上面发送消息部分，Handler通过myLoop方法得到Looper对象，然后获取Looper的MessageQueue消息队列对象），
 *  然后进入一个死循环不断调用MessageQueue的next()方法，取出Handler发送进来的msg。
 *      loop()方法中取出的msg继续通过msg.target.dispatchMessage(msg)，将它传递到msg.target的dispatchMessage()方法中；
 *  其中这个msg.target其实就是上面分析Handler发送消息代码部分Handler的enqueueMessage方法中的msg.target = this;语句。
 *      Handler类的dispatchMessage()方法逻辑比较简单，具体就是如果mCallback不为空，则调用mCallback的handleMessage()方法，
 *  否则直接调用Handler的handleMessage()方法，并将消息对象作为参数传递过去。
 *  3、结束MessageQueue消息队列阻塞死循环
 *      Looper类的quit方法，通过判断标记mQuitAllowed来决定该消息队列是否可以退出，
 *  然而当mQuitAllowed为false时抛出的异常竟然是”Main thread not allowed to quit.”，
 *  Main Thread：所以可以说明Main Thread关联的Looper一一对应的MessageQueue消息队列是不能通过该方法退出的。
 *
 * 三、Handler其它方法
 *  1、除了sendEmptyMessage(int what)方法，其实Handler有如下一些发送方式：
 *      sendMessage(Message msg);
 *      sendEmptyMessage(int what);
 *      sendEmptyMessageDelayed(int what, long delayMillis);
 *      sendEmptyMessageAtTime(int what, long uptimeMillis);
 *      sendMessageDelayed(Message msg, long delayMillis);
 *      sendMessageAtTime(Message msg, long uptimeMillis);
 *      sendMessageAtFrontOfQueue(Message msg);
 *      这些方法不再做过多解释，用法雷同最终都会掉用sendMessageAtTime()方法，生成一个Message决定啥时发送到target去。
 *  2、post(Runnable r);
 *     postDelayed(Runnable r, long delayMillis);等post系列方法。
 *      该方法实质源码其实就是如下：
 *      public final boolean postDelayed(Runnable r, long delayMillis){
 *          return sendMessageDelayed(getPostMessage(r), delayMillis);
 *      }
 *  3、View.post(Runnable)和View.postDelayed(Runnable action, long delayMillis);
 *      View的postDelayed方法源码实质上还是声称消息，让后将消息传递至主线程消息队列中
 *
 * 四、关于Handler发送消息的一点优化
 *  通过调用Handler的 obtainMessage 方法获取Message对象就能避免创建对象，从而减少内存的开
 *
 * 五、关于Handler导致内存泄露的分析与解决方法
 *      当使用内部类（包括匿名类）来创建Handler的时候，Handler对象会隐式地持有一个外部类对象（通常是一个Activity）的引用。
 *  而Handler通常会伴随着一个耗时的后台线程一起出现，这个后台线程在任务执行完毕之后，通过消息机制通知Handler，
 *  然后Handler把消息发送到UI线程。然而，如果用户在耗时线程执行过程中关闭了Activity（正常情况下Activity不再被使用，
 *  它就有可能在GC检查时被回收掉），由于这时线程尚未执行完，而该线程持有Handler的引用，这个Handler又持有Activity的引用，
 *  就导致该Activity暂时无法被回收（即内存泄露）。
 *    Handler内存泄漏解决方案呢？
 *      方案一：通过程序逻辑来进行保护
 *          1、在关闭Activity的时候停掉你的后台线程。线程停掉了，就相当于切断了Handler和外部连接的线，Activity自然会在合适的时候被回收。
 *          2、如果你的Handler是被delay的Message持有了引用，那么使用相应的Handler的removeCallbacks()方法，把消息对象从消息队列移除就行了（如上面的例子部分的onStop中代码）。
 *      方案二：将Handler声明为静态类
 *          例如下面代码的注释部分。
 *
 * 六、HandlerThread源码分析（详见HandlerTreadSimpleActivity）
 *      其实HandlerThread就是Thread、Looper和Handler的组合实现，Android系统这么封装体现了Android系统组件的思想，
 *  同时也方便了开发者开发。
 *      通过源码可以看到，HandlerThread主要是对Looper进行初始化，并提供一个Looper对象给新创建的Handler对象，
 *  使得Handler处理消息事件在子线程中处理。这样就发挥了Handler的优势，同时又可以很好的和线程结合到一起。
 *
 */
public class HandlerSimpleActivity extends Activity {
    private TextView textView;
    private Button button;

    private int mCount = 0;
    //独立线程中的handler
    private Handler mHandlerThread = null;
    //UI线程中的handler
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("--yuyang--", "mHandler--handleMessage--msg.what="+msg.what);
            textView.setText("mCount="+mCount);
            //handler接收到独立线程发送来的信息后，向mHandlerThread发送msg 1
            mHandlerThread.sendEmptyMessage(1);
            mCount++;
            if (mCount >= 30){
                //由于mHandlerThr是在Child Thread创建，Looper手动死循环阻塞，所以需要quit。
                mHandlerThread.getLooper().quit();
            }
        }
    };

    /*static class TestHandler extends Handler {
        WeakReference<Activity > mActivityReference;

        TestHandler(Activity activity) {
            mActivityReference= new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.i("--yuyang--", "mHandler--handleMessage--msg.what="+msg.what);
            final Activity activity = mActivityReference.get();
            if (activity != null) {
                HandlerSimpleActivity handlerActivity = (HandlerSimpleActivity) activity;
                handlerActivity.textView.setText("mCount=" + handlerActivity.mCount);
                //handler接收到独立线程发送来的信息后，向mHandlerThread发送msg 1
                handlerActivity.mHandlerThread.sendEmptyMessage(1);
                handlerActivity.mCount++;
                Log.i("--yuyang--", "mHandler--handleMessage--mCount="+handlerActivity.mCount);
                *//*if (handlerActivity.mCount >= 30) {
                    //由于mHandlerThr是在Child Thread创建，Looper手动死循环阻塞，所以需要quit。
                    handlerActivity.mHandlerThread.getLooper().quit();
                }*//*
            }
        }
    }
    private Handler mHandler = new TestHandler(this);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_simple_use);

        textView = (TextView) findViewById(R.id.handler_text);
        button = (Button) findViewById(R.id.handler_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(null, ">>>>>>>>>>>>>Child# begin start send msg!!!");
                //向mHandlerThread发送msg 1
                mHandlerThread.sendEmptyMessage(1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startThread();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //TODO yuyang 移除所有消失以免内存泄露，因为mHandler中持有TextView对象
        mHandler.removeCallbacksAndMessages(null);
        mHandlerThread.getLooper().quit();//结束MessageQueue消息队列阻塞死循环,结束线程
    }

    private void startThread() {
        Log.d("--yuyang--", "begin start thread!!!");
        new Thread(){
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                mHandlerThread = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Log.i("--yuyang--", "mHandlerThreads--handleMessage--msg.what=" + msg.what);
                        //接收发送到子线程的消息，然后向UI线程中的Handler发送msg 0。
                        mHandler.sendEmptyMessageDelayed(0, 500);
                    }
                };
                //不能在这个后面添加代码，程序是无法运行到这行之后的，除非quit消息队列
                Looper.loop();// TODO yuyang 该方法会进入一个死循环，不断便利Looper消息队列中的消息
            }
        }.start();
    }
}
