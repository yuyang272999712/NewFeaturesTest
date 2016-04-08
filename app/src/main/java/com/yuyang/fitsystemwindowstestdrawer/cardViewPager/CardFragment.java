package com.yuyang.fitsystemwindowstestdrawer.cardViewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/3/11.
 */
public class CardFragment extends Fragment {

    private CardPager cardPager;

    private ImageView imageView;
    private TextView titleView;
    private TextView desView;

    public static CardFragment getInstance(CardPager cardPager){
        CardFragment cardFragment = new CardFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("cardPager", cardPager);
        cardFragment.setArguments(bundle);
        return  cardFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.cardPager = (CardPager) getArguments().getSerializable("cardPager");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_fragment, null);
        imageView = (ImageView) view.findViewById(R.id.image_cover);
        titleView = (TextView) view.findViewById(R.id.text_title);
        desView = (TextView) view.findViewById(R.id.text_author);

        imageView.setImageResource(cardPager.getImgId());
        titleView.setText(cardPager.getTitle());
        desView.setText(cardPager.getDescribe());

        return view;
    }
}
