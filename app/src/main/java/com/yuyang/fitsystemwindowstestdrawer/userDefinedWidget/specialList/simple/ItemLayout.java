package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.specialList.simple;

import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.specialList.ChangeBigHeadLayout;

/**
 * Created by yuyang on 2017/10/11.
 */

public class ItemLayout extends RelativeLayout implements View.OnClickListener,ChangeBigHeadLayout.OnScrollChangedListener {
    private static final int[] defaultFilterColors = new int[]{
            0xE92B9783, 0xE9C94342, 0xE9E47953, 0xE9D4AA36, 0xE956365D, 0xE947A2BF
    };

    private final ColorEvaluator colorEvaluator = new ColorEvaluator();
    private int filterColor;
    private float currPercent;
    private int expandedHeight;
    private int normalHeight;
    private int pos;
    private DataInfoBean d;

    private TextView tTitle;
    private TextView tSubtitle;
    private ImageView iBackground;
    private ImageView iIcon;
    private ImageView iFilter;
    private ImageView iShadow;

    public ItemLayout(Context context) {
        super(context);
        LayoutInflater.from(getContext()).inflate(R.layout.item_change_big_head, this);
        tTitle = (TextView) findViewById(R.id.t_title);
        tSubtitle = (TextView) findViewById(R.id.t_subtitle);
        iBackground = (ImageView) findViewById(R.id.i_background);
        iIcon = (ImageView) findViewById(R.id.i_icon);
        iFilter = (ImageView) findViewById(R.id.i_filter);
        iShadow = (ImageView) findViewById(R.id.i_shadow);

        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext().getApplicationContext(), "" + (d != null ? d.title : "null"), Toast.LENGTH_SHORT).
                show();
    }

    @Override
    public void onScroll(int currIndex, float percent) {
        if (pos == currIndex + 1) {
            animate(currPercent = percent);
        } else if (pos > currIndex + 1 && currPercent > 0) {
            animate(currPercent = 0);
        } else if (pos < currIndex + 1 && currPercent < 1) {
            animate(currPercent = 1);
        }
    }

    private void animate(float percent) {
        iShadow.setAlpha(percent);
        tSubtitle.setAlpha(percent);

        MarginLayoutParams lpIcon = (MarginLayoutParams) iIcon.getLayoutParams();
        lpIcon.height = (int) (normalHeight * 0.6f + normalHeight * 0.2f * percent);
        lpIcon.width = lpIcon.height;
        lpIcon.bottomMargin = (int) (normalHeight * 0.2f - normalHeight * 0.05f * percent);
        lpIcon.leftMargin = (int) (lpIcon.bottomMargin * (1 - 0.1f * percent));

        tTitle.setTranslationY(-percent * tTitle.getHeight() / 2);
        tSubtitle.setTranslationY(-percent * tSubtitle.getHeight() / 2);

        int tmpColor = (int) colorEvaluator.evaluate(percent, filterColor, Color.TRANSPARENT);
        iFilter.setBackgroundColor(tmpColor);
        tTitle.setTextSize(20 + 5 * percent);
        tSubtitle.setTextSize(14 + 4 * percent);

        getLayoutParams().height = (int) (normalHeight + (expandedHeight - normalHeight) * percent);
        requestLayout();
    }

    public void setData(int position, DataInfoBean data, int childExpandedHeight, int childNormalHeight){
        d = data;
        pos = position;
        expandedHeight = childExpandedHeight;
        normalHeight = childNormalHeight;

        LayoutParams lpContainer = new LayoutParams(LayoutParams.MATCH_PARENT, pos == 0 ? expandedHeight : normalHeight);
        lpContainer.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        setLayoutParams(lpContainer);

        filterColor = defaultFilterColors[position % defaultFilterColors.length];

        MarginLayoutParams lpIcon = (MarginLayoutParams) iIcon.getLayoutParams();
        if (position == 0) {

            iFilter.setBackgroundColor(Color.TRANSPARENT);
            iShadow.setAlpha(1f);

            lpIcon.height = (int) (normalHeight * 0.6f + normalHeight * 0.2f * 1);
            lpIcon.width = lpIcon.height;
            lpIcon.bottomMargin = (int) (normalHeight * 0.2f - normalHeight * 0.05f * 1);
            lpIcon.leftMargin = (int) (lpIcon.bottomMargin * (1 - 0.1f * 1));
        } else {
            iFilter.setBackgroundColor(filterColor);
            iShadow.setAlpha(0f);

            lpIcon.height = (int) (normalHeight * 0.6f + normalHeight * 0.2f * 0);
            lpIcon.width = lpIcon.height;
            lpIcon.bottomMargin = (int) (normalHeight * 0.2f - normalHeight * 0.05f * 0);
            lpIcon.leftMargin = (int) (lpIcon.bottomMargin * (1 - 0.1f * 0));
        }

        iShadow.getLayoutParams().height = (int) (expandedHeight * 0.35f);
        iBackground.getLayoutParams().height = expandedHeight;

        MarginLayoutParams lpTitle = (MarginLayoutParams) tTitle.getLayoutParams();
        lpTitle.bottomMargin = (int) ((normalHeight - tTitle.getTextSize()) * 0.43f);

        MarginLayoutParams lpSubtitle = (MarginLayoutParams) tSubtitle.getLayoutParams();
        lpSubtitle.bottomMargin = (int) ((normalHeight - tSubtitle.getTextSize()) * 0.15f);
        tSubtitle.setAlpha(0f);

        tTitle.setText(data.title);
        tSubtitle.setText(data.subTitle);
        iBackground.setImageResource(data.cover);
        iIcon.setImageResource(data.logo);
    }

    private class ColorEvaluator implements TypeEvaluator {
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            int startColor = (int) startValue;
            int endColor = (int) endValue;

            int alpha = (int) (Color.alpha(startColor) + fraction *
                    (Color.alpha(endColor) - Color.alpha(startColor)));
            int red = (int) (Color.red(startColor) + fraction *
                    (Color.red(endColor) - Color.red(startColor)));
            int green = (int) (Color.green(startColor) + fraction *
                    (Color.green(endColor) - Color.green(startColor)));
            int blue = (int) (Color.blue(startColor) + fraction *
                    (Color.blue(endColor) - Color.blue(startColor)));
            return Color.argb(alpha, red, green, blue);
        }
    }
}
