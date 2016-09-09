package com.yuyang.fitsystemwindowstestdrawer.animationAbout.shoppingCartAnimator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.eventBus.EventBusBaseActivity;
import com.yuyang.fitsystemwindowstestdrawer.eventBus.myEventBus.MyEventBus;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.CommonViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 商品抛入购物车效果
 */
public class ShoppingCartActivity extends EventBusBaseActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ImageView cart;
    private TextView goodCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        findViews();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Adapter());
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("商品抛入购物车效果");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cart = (ImageView) findViewById(R.id.cart);
        goodCount = (TextView) findViewById(R.id.good_count);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addGood(Object object){
        String current = goodCount.getText().toString();
        goodCount.setText(Integer.parseInt(current)+1+"");
    }

    class Adapter extends RecyclerView.Adapter<CommonViewHolder>{

        @Override
        public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return CommonViewHolder.getViewHolder(parent, R.layout.item_good_list);
        }

        @Override
        public void onBindViewHolder(CommonViewHolder holder, int position) {
            final ImageView goodImg = holder.getViews(R.id.good_image);
            goodImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartAddGoodAnimator.startAnimatorAddCart(goodImg, cart, ShoppingCartActivity.this, new Object());
                }
            });
            TextView goodInfo = holder.getViews(R.id.good_info);
            goodInfo.setText("商品"+position);
        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }
}
