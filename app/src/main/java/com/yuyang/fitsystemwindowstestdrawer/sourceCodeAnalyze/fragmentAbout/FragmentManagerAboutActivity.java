package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.fragmentAbout;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * TODO yuyang
 * *****这尼玛，如果Fragment中的EditText没有ID，那么Fragment在remove后被销毁，重新添加EditText的内容就会没有了，
 * *****但是如果又ID，内容就能保存下来
 *
 Fragment家族常用的API
    Fragment常用的三个类：
         android.app.Fragment 主要用于定义Fragment
         android.app.FragmentManager 主要用于在Activity中操作Fragment
         android.app.FragmentTransaction 保证一些列Fragment操作的原子性，熟悉事务这个词，一定能明白~
    a、获取FragmentManage的方式：
        getFragmentManager() // v4中，getSupportFragmentManager
    b、主要的操作都是FragmentTransaction的方法
        1、FragmentTransaction transaction = fm.beginTransaction();//开启一个事务
        2、transaction.add()
        往Activity中添加一个Fragment
        3、transaction.remove()
        从Activity中移除一个Fragment，如果被移除的Fragment没有添加到回退栈（回退栈后面会详细说），这个Fragment实例将会被销毁。
        4、transaction.replace()
        使用另一个Fragment替换当前的，实际上就是remove()然后add()的合体~
        5、transaction.hide()
        隐藏当前的Fragment，仅仅是设为不可见，并不会销毁
        6、transaction.show()
        显示之前隐藏的Fragment
        7、detach()
        会将view从UI中移除,和remove()不同,此时fragment的状态依然由FragmentManager维护。
        8、attach()
        重建view视图，附加到UI上并显示。
        9、transaction.commit()//提交一个事务
    注意：常用Fragment的哥们，可能会经常遇到这样Activity状态不一致：State loss这样的错误。主要是因为：commit方法一定要在Activity.onSaveInstance()之前调用。
  上述，基本是操作Fragment的所有的方式了，在一个事务开启到提交可以进行多个的添加、移除、替换等操作。
    值得注意的是：如果你喜欢使用Fragment，一定要清楚这些方法，哪个会销毁视图，哪个会销毁实例，哪个仅仅只是隐藏，这样才能更好的使用它们。
     a、比如：我在FragmentA中的EditText填了一些数据，当切换到FragmentB时，如果希望会到A还能看到数据，则适合你的就是hide和show；也就是说，希望保留用户操作的面板，你可以使用hide和show，当然了不要使劲在那new实例，进行下非null判断。
     b、再比如：我不希望保留用户操作，你可以使用remove()，然后add()；或者使用replace()这个和remove,add是相同的效果。
     c、remove和detach有一点细微的区别，在不考虑回退栈的情况下，remove会销毁整个Fragment实例，而detach则只是销毁其视图结构，实例并不会被销毁。那么二者怎么取舍使用呢？如果你的当前Activity一直存在，那么在不希望保留用户操作的时候，你可以优先使用detach。

 ！！！对于销毁这种说法很蛋疼啊，Activity总是持有Fragment的，即使Activity执行了remove()方法，可是Activity类中仍然持有Fragment的实例，所有Fragment实例还是不会被销毁
 */
public class FragmentManagerAboutActivity extends Activity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_manager_about);

        fragmentManager = getFragmentManager();
    }

    public void add1(View view){
        /* TODO yuyang 这样添加的Fragment，Activity不持有Fragment对象，所以remove后Fragment实例会销毁
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_content_1, ManagerFragmentTest.getInstance("第0个Fragment"));
        transaction.commit();*/
        ManagerFragmentTest fragmentTest1 = (ManagerFragmentTest) fragmentManager.findFragmentByTag("第一个Fragment");
        if (fragmentTest1 == null) {
            fragmentTest1 = ManagerFragmentTest.getInstance("第一个Fragment");
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!fragmentTest1.isAdded()) {
            transaction.add(R.id.fragment_content_1, fragmentTest1, "第一个Fragment");
            transaction.commit();
        }else {
            transaction.show(fragmentTest1);
        }
    }

    public void remove1(View view){
        ManagerFragmentTest fragmentTest1 = (ManagerFragmentTest) fragmentManager.findFragmentByTag("第一个Fragment");
        if (fragmentTest1 != null && fragmentTest1.isAdded()){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(fragmentTest1);
            transaction.commit();
        }
    }

    public void replace1(View view){
        ManagerFragmentTest fragmentTest2 = (ManagerFragmentTest) fragmentManager.findFragmentByTag("第二个Fragment");
        if (fragmentTest2 == null) {
            fragmentTest2 = ManagerFragmentTest.getInstance("第二个Fragment");
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_content_1, fragmentTest2, "第二个Fragment");
        transaction.commit();
    }

    public void show1(View view){
        ManagerFragmentTest fragmentTest3 = (ManagerFragmentTest) fragmentManager.findFragmentByTag("第三个Fragment");
        if (fragmentTest3 == null){
            fragmentTest3 = ManagerFragmentTest.getInstance("第三个Fragment");
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!fragmentTest3.isAdded()){
            transaction.add(R.id.fragment_content_1, fragmentTest3, "第三个Fragment");
        }else {
            transaction.show(fragmentTest3);
        }
        transaction.commit();
    }

    public void hide1(View view){
        ManagerFragmentTest fragmentTest3 = (ManagerFragmentTest) fragmentManager.findFragmentByTag("第三个Fragment");
        if (fragmentTest3 != null && fragmentTest3.isAdded()){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(fragmentTest3);
            transaction.commit();
        }
    }

    public void detach1(View view){
        ManagerFragmentTest fragmentTest2 = (ManagerFragmentTest) fragmentManager.findFragmentByTag("第二个Fragment");
        if (fragmentTest2 != null && !fragmentTest2.isDetached()){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.detach(fragmentTest2);
            transaction.commit();
        }
    }

    public void attach1(View view){
        ManagerFragmentTest fragmentTest2 = (ManagerFragmentTest) fragmentManager.findFragmentByTag("第二个Fragment");
        if (fragmentTest2 != null && fragmentTest2.isDetached()){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.attach(fragmentTest2);
            transaction.commit();
        }
    }

}
