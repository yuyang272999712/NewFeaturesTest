package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.Arrays;
import java.util.List;

/**
 * 一、LayoutInflate
 *  常用的四种方法：
 *    inflate(layoutId, null )
 *          layoutId的最外层的控件的宽高是没有效果的（也就是layoutId的所有“layout_*”的属性都无效）
 *          不能正确处理宽和高是因为：layout_width,layout_height是相对了父级设置的，
 *          必须与父级的LayoutParams一致。而此temp的getLayoutParams为null
 *    inflate(layoutId, root )
 *          查看源码，其作用和inflate(layoutId, root, true )相同
 *    inflate(layoutId, root, false )
 *          可以正确处理layoutId的宽高，因为temp.setLayoutParams(params);
 *          这个params正是root.generateLayoutParams(attrs);得到的。
 *    inflate(layoutId, root, true )
 *          不仅能够正确的处理layoutId的宽高，而且已经把layoutId这个view加入到了root中，并且返回的是root，
 *          和以上两者返回值有绝对的区别
 */
public class LayoutInflateActivity extends AppCompatActivity {
    private ListView listView1;
    private ListView listView2;
    private ListView listView3;

    private MyAdapter1 adapter1;
    private MyAdapter2 adapter2;
    private MyAdapter3 adapter3;

    private List<String> mDatas = Arrays.asList("Hello", "Java", "Android");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_inflate);

        findViews();
        setViewData();
        setViewAction();
    }

    private void findViews() {
        listView1 = (ListView) findViewById(R.id.layout_inflate_list1);
        listView2 = (ListView) findViewById(R.id.layout_inflate_list2);
        listView3 = (ListView) findViewById(R.id.layout_inflate_list3);
    }

    private void setViewData() {
        adapter1 = new MyAdapter1(this, mDatas);
        adapter2 = new MyAdapter2(this, mDatas);
//        adapter3 = new MyAdapter3(this, mDatas);

        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);
//        listView3.setAdapter(adapter3);
    }

    private void setViewAction() {

    }
}
