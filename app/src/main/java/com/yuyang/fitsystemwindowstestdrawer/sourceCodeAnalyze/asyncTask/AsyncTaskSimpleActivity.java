package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.asyncTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * AsyncTask简单使用
 *
 * 源码解析详见：http://blog.csdn.net/yanbober/article/details/46117397
 * java的线程池技术详见：http://willsunforjava.iteye.com/blog/1631353
 *
 *  当使用线程和Handler组合实现异步处理时，当每次执行耗时操作都创建一条新线程进行处理，性能开销会比较大。
 * 为了提高性能我们使用AsyncTask实现异步处理（其实也是线程和handler组合实现），因为其内部使用了java提供的线程池技术，
 * 有效的降低了线程创建数量及限定了同时运行的线程数，还有一些针对性的对池的优化操作。
 *  所以说AsyncTask是Android为我们提供的方便编写异步任务的工具类。
 *
 * 一、AsyncTask的execute方法分析
 *     首先判断AsyncTask异步任务的状态，当处于RUNNING和FINISHED时就报IllegalStateException非法状态异常。
 * 由此可以看见一个AsyncTask的execute方法只能被调运一次。
 *     exec.execute(mFuture);代码。exec就是形参出入的上面定义的static SerialExecutor对象（SerialExecutor实现了Executor接口），
 * 所以execute就是SerialExecutor静态内部类的方法喽，在执行execute方法时还传入了AsyncTask构造函数中实例化的第二个成员变量mFuture。
 * ＊＊＊SerialExecutor在AsyncTask中是以常量的形式被使用的，所以在整个应用程序中的所有AsyncTask实例都会共用同一个SerialExecutor对象。＊＊＊
 *     SerialExecutor是使用ArrayDeque这个队列来管理Runnable对象的，如果我们一次性启动了很多个任务，首先在第一次运行execute()方法的
 * 时候会调用ArrayDeque的offer()方法将传入的Runnable对象添加到队列的最后，然后判断mActive对象是不是等于null，第一次运行是null，
 * 然后调用scheduleNext()方法，在这个方法中会从队列的头部取值，并赋值给mActive对象，然后调用＊THREAD_POOL_EXECUTOR＊去执行取出的取出的
 * Runnable对象。之后如果再有新的任务被执行时就等待上一个任务执行完毕后才会得到执行，所以说同一时刻只会有一个线程正在执行，其余的均处于等待状态，
 * 这就是SerialExecutor类的核心作用。
 *     这个THREAD_POOL_EXECUTOR线程池是一个常量，也就是说整个App中不论有多少AsyncTask都只有这一个线程池。也就是说上面SerialExecutor
 * 类中execute()方法的所有逻辑就是在子线程中执行，注意SerialExecutor的execute方法有一个Runnable参数，这个参数就是mFuture对象；
 *     mFuture对象中的c = callable;其实就是AsyncTask构造函数中实例化FutureTask对象时传入的参数mWorker。
 * result = c.call();其实就是调运WorkerRunnable类的call方法。
 *     mWorker中在postResult()方法的参数里面，我们可以看见doInBackground()方法。所以这验证了我们上面例子中使用的AsyncTask，
 * 首先在主线程执行onPreExecute方法，接着在子线程执行doInBackground方法，所以这也就是为什么我们可以在doInBackground()方法中去处理
 * 耗时操作的原因了，接着等待doInBackground方法耗时操作执行完毕以后将返回值传递给了postResult()方法。
 *     postResult()方法中有个getHandler()调用，去掉用的就是UI Handler，这样就把子线程中的操作结果发送到了UI线程中。
 *
 * 二、AsyncTask的排队问题
 *  在Android 3.0之前是并没有SerialExecutor这个类的（上面有分析）。那些版本的代码是直接创建了指定大小的线程池常量来执行task的。
 *  其中MAXIMUM_POOL_SIZE = 128;，所以那时候如果我们应用中一个界面需要同时创建的AsyncTask线程大于128（批量获取数据，
 *  譬如照片浏览瀑布流一次加载）程序直接就挂了。
 *  现在是顺序执行的。而且更劲爆的是现在的AsyncTask还直接提供了客户化实现Executor接口功能，使用如下方法执行AsyncTask即可使用自定义Executor，如下：
 *      public final AsyncTask<Params, Progress, Result> executeOnExecutor(Executor exec,Params... params)
 *
 * 三、AsyncTask与Handler异步机制对比
 *  1、AsyncTask是对Handler与Thread的封装。
 *  2、AsyncTask在代码上比Handler要轻量级别，但实际上比Handler更耗资源，因为AsyncTask底层是一个线程池，
 *    而Handler仅仅就是发送了一个消息队列。但是，如果异步任务的数据特别庞大，AsyncTask线程池比Handler节省开销，
 *    因为Handler需要不停的new Thread执行。
 *  3、AsyncTask的实例化只能在主线程，Handler可以随意，只和Looper有关系。
 */
public class AsyncTaskSimpleActivity extends AppCompatActivity {
    private Button button;

    private MyAsyncTask task;
    private ProgressDialog mDialog = null;
    private int mCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_simple);

        mDialog = new ProgressDialog(this);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setMax(100);

        task = new MyAsyncTask();

        button = (Button) findViewById(R.id.async_task_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO yuyang AsyncTask内维护了一个全局的(贯穿整个APP生命周期的)Runnable队列，只有当上一个Runnable执行完成后才会执行下一个任务，
                // 所以这里执行execute()方法后可能会等待上一个任务完成后才会执行当前任务
                //TODO yuyang AsyncTask提供了executeOnExecutor(Executor exec,Params... params),可以自己提供Executor线程池，
                // 这样就跳过了AsyncTask内的Runnable队列，直接进入我们提供的线程池内运行当前任务。例如BackgroundService类中的AsyncTask
                task.execute();
            }
        });

        /*TODO yuyang AsyncTask只能在主线程中使用！
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                MyAsyncTask task1 = new MyAsyncTask();
                task1.execute();
                Looper.loop();
            }
        };
        thread.start();*/
    }

    //TODO yuyang 三个泛型参数含义
    //1、在执行AsyncTask时需要传入的参数，可以在后台任务中使用
    //2、在后台任务执行时，如果需要在界面上现实当前进度，则使用这个
    //3、当后台任务结束后，如果需要对结果进行返回，则使用这个
    class MyAsyncTask extends AsyncTask<Void, Integer, Boolean>{

        //后台任务执行之前调用，用于界面的初始化操作
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.show();
        }

        //这个方法的所有代码都会在子线程中执行，在这里处理所有的耗时任务
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e("--yuyang--","咋没执行到这？请注意当前app中有一个BackgroundService在后台跑了一个AsyncTask，这里对service中的task做了修改");
            while (mCount <= 100){
                publishProgress(mCount);
                mCount += 10;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

        //当在后台任务中调用了publishProgress(Progress...)方法后，这个方法就很快会被调用
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mDialog.setProgress(values[0]);
        }

        //当后台任务执行完毕并通过return语句进行返回时，这个方法就很快会被调用
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean && mDialog.isShowing()){
                mDialog.dismiss();
            }
        }
    }
}
