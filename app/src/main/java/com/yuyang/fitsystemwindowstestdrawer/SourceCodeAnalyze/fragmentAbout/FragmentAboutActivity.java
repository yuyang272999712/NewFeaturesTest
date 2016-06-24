package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.fragmentAbout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Fragment常用知识以及填坑
 *
 * 1、管理Fragment回退栈(在配置发生变化后有点混乱)
 * 2、Fragment与Activity通信
 *  详见：BackStackFragment1.java，定义一个接口，让Activity实现这个接口
 * 3、处理运行时配置发生变化
 *    当屏幕发生旋转，Activity发生重新启动，默认的Activity中的Fragment也会跟着Activity重新创建；这样造成当旋转的时候，
 *  本身存在的Fragment会重新启动，然后当执行Activity的onCreate时，又会再次实例化一个新的Fragment。
 *  其实通过检查onCreate的参数Bundle savedInstanceState就可以判断，当前是否发生Activity的重新创建
 * 4、Fragment与ActionBar和MenuItem集成
 *  详见：BackStackFragment1.java中标注的 TODO。
 * 5、没有布局的Fragment的作用
 *  google推荐的Activity运行时配置改变保存数据的最佳方式；
 *  详见：ConfigChangesActivity.java
 * 6、使用Fragment创建对话框
 *  这是Google推荐的方式
 *  详见：DialogActivity.java
 * 7、fragment能够从Activity中接收返回结果，但是其自设无法产生返回结果，只有Activity拥有返回结果。
 *  调用getActivity().setResult(ListTitleFragment.REQUEST_DETAIL, intent);进行设置返回的数据；
 *  最后在Fragment.onActivityResult（）拿到返回的数据进行回显
 * 8、TODO yuyang FragmentPagerAdapter与FragmentStatePagerAdapter
 *  主要区别就在与对于fragment是否销毁，下面细说：
 *    FragmentPagerAdapter：对于不再需要的fragment，选择调用detach方法，仅销毁视图，并不会销毁fragment实例。
 *    FragmentStatePagerAdapter：会销毁不再需要的fragment，当当前事务提交以后，会彻底的将fragment从当前
 *  Activity的FragmentManager中移除，state标明，销毁时，会将其onSaveInstanceState(Bundle outState)
 *  中的bundle信息保存下来，当用户切换回来，可以通过该bundle恢复生成新的fragment，也就是说，你可以在
 *  onSaveInstanceState(Bundle outState)方法中保存一些数据，在onCreate中进行恢复创建。
 *    如上所说，使用FragmentStatePagerAdapter当然更省内存，但是销毁新建也是需要时间的。一般情况下，如果你是制作
 *  主页面，就3、4个Tab，那么可以选择使用FragmentPagerAdapter，如果你是用于ViewPager展示数量特别多的条目时，
 *  那么建议使用FragmentStatePagerAdapter。
 * 9、TODO yuyang Fragment间的数据传递（当前fragment由别的fragment启动）
 *  通过Fragment.setTargetFragment()方法设置
 */
public class FragmentAboutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_about);
    }

    public void gotoFragmentManager(View view){
        Intent intent = new Intent(this, FragmentManagerAboutActivity.class);
        startActivity(intent);
    }

    public void gotoFragmentBackStack(View view){
        Intent intent = new Intent(this, FragmentBackStackActivity.class);
        startActivity(intent);
    }
}
