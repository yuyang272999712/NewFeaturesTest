package com.yuyang.fitsystemwindowstestdrawer.androidL.behaviorAbout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * BottomSheetBehavior/BottomSheetDialog的使用
 */
public class BottomSheetActivity extends AppCompatActivity {
    private ViewGroup bottomSheetLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_behavior);

        setToolBar();

        bottomSheetLayout = (ViewGroup) findViewById(R.id.bottom_sheet_layout);
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 使用BottomSheetBehavior
     * @param view
     */
    public void showBehavior(View view){
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            //!--yuyang 如果要设置状态为 STATE_HIDDEN（隐藏状态），在xml文件中需要添加app:behavior_hideable="true"属性
            //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    /**
     * 使用BottomSheetDialog
     * @param view
     */
    public void showDialog(View view){
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(recyclerView);
        bottomSheetDialog.show();
        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String text) {
                Toast.makeText(BottomSheetActivity.this, text, Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
    }

    static class Adapter extends RecyclerView.Adapter<Adapter.Holder>{
        private OnItemClickListener mItemClickListener;

        public void setOnItemClickListener(OnItemClickListener li) {
            mItemClickListener = li;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_just_text, parent, false);
            return new Holder(item);
        }

        @Override
        public void onBindViewHolder(final Holder holder, int position) {
            holder.tv.setText("item " + position);
            if(mItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onItemClick(holder.getLayoutPosition(), holder.tv.getText().toString());
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return 50;
        }

        class Holder extends RecyclerView.ViewHolder{
            TextView tv;
            public Holder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.id_info);
            }
        }

        interface OnItemClickListener {
            void onItemClick(int position, String text);
        }
    }
}
