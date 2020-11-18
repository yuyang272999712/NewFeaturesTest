package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.textViewSpannable;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.IconMarginSpan;
import android.text.style.ImageSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TabStopSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.textViewSpannable.mySpan.MySpanActivity;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * TextView SpannableString 使用
 */

public class SpannableStringTestActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable_string);
        findViews();
        initSpanView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("自定义Span");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("自定义Span")){
            startActivity(new Intent(this, MySpanActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initSpanView() {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        TextView backgroundColorText = (TextView) findViewById(R.id.background_color_span);
        BackgroundColorSpan colorSpan = new BackgroundColorSpan(Color.RED);
        builder.append("BackgroundColorSpan-跟背景颜色相关的");
        builder.setSpan(colorSpan, 0, 20, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        backgroundColorText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView clickableText = (TextView) findViewById(R.id.clickable_span);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(SpannableStringTestActivity.this, "点击事件", Toast.LENGTH_SHORT).show();
            }
        };
        builder.append("ClickableSpan-点击事件相关的Span");
        builder.setSpan(clickableSpan, 0, 14, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        clickableText.setMovementMethod(LinkMovementMethod.getInstance());
        clickableText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView urlText = (TextView) findViewById(R.id.url_span);
        URLSpan urlSpan = new URLSpan("http://www.baidu.com");
        builder.append("URLSpan-点击事件相关的Span");
        builder.setSpan(urlSpan, 0, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        urlText.setMovementMethod(LinkMovementMethod.getInstance());
        urlText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView foregroundColorText = (TextView) findViewById(R.id.foreground_color_span);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.BLUE);
        builder.append("ForegroundColorSpan-设置字体颜色");
        builder.setSpan(foregroundColorSpan, 0, 22, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        foregroundColorText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView maskFilterText = (TextView) findViewById(R.id.mask_filter_span);
        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(3, BlurMaskFilter.Blur.OUTER);
        MaskFilterSpan maskFilterSpan1 = new MaskFilterSpan(blurMaskFilter);
        EmbossMaskFilter embossMaskFilter = new EmbossMaskFilter(new float[]{3, 3, 9}, 3.0f, 12, 16);
        MaskFilterSpan maskFilterSpan2 = new MaskFilterSpan(embossMaskFilter);
        builder.append("MaskFilterSpan-文字的装饰效果。分为两种：BlurMaskFilter-模糊效果 和 EmbossMaskFilter-浮雕效果");
        builder.setSpan(maskFilterSpan1, 28, 43, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.setSpan(maskFilterSpan2, 51, 68, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        maskFilterText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView absoluteSizeText = (TextView) findViewById(R.id.absolute_size_span);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(25, true);
        builder.append("AbsoluteSizeSpan-字体大小的");
        builder.setSpan(absoluteSizeSpan, 0, 17, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        absoluteSizeText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView relativeSizeText = (TextView) findViewById(R.id.relative_size_span);
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(2);
        builder.append("RelativeSizeSpan-相对的字体大小");
        builder.setSpan(relativeSizeSpan, 0, 17, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        relativeSizeText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView imageText = (TextView) findViewById(R.id.image_span);
        ImageSpan imageSpan = new ImageSpan(this, R.mipmap.animation_wallet, DynamicDrawableSpan.ALIGN_BOTTOM);
        builder.append("ImageSpan-有关图片的");
        builder.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        imageText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView scaleXText = (TextView) findViewById(R.id.scale_x_span);
        ScaleXSpan scaleXSpan = new ScaleXSpan(4);
        builder.append("ScaleXSpan-横向压缩比例因子");
        builder.setSpan(scaleXSpan, 0, 11, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        scaleXText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView styleText = (TextView) findViewById(R.id.style_span);
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD_ITALIC);
        builder.append("StyleSpan-主要由正常、粗体、斜体和同时加粗倾斜四种样式");
        builder.setSpan(styleSpan, 0, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        styleText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView subscriptText = (TextView) findViewById(R.id.subscript_span);
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        builder.append("SubscriptSpan-脚注样式，比如化学式的常见写法。 SuperscriptSpan：上标样式，比如数学上的次方运算");
        builder.setSpan(subscriptSpan, 9, 13, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.setSpan(superscriptSpan, 42, 46, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        subscriptText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView textAppearanceText = (TextView) findViewById(R.id.text_appearance_span);
        ColorStateList colorStateList = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            colorStateList = this.getColorStateList(R.color.selector_floating_action_button_bg);
        } else {
            try {
                colorStateList = ColorStateList.createFromXml(this.getResources(), this.getResources().getXml(R.color.selector_floating_action_button_bg));
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
        }
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan("sans-serif", Typeface.BOLD_ITALIC, 22, colorStateList, colorStateList);
        builder.append("TextAppearanceSpan-设置文字字体、文字样式");
        builder.setSpan(textAppearanceSpan, 0, 19, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textAppearanceText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView typefaceText = (TextView) findViewById(R.id.typeface_span);
        TypefaceSpan typefaceSpan = new TypefaceSpan("monospace");
        builder.append("TypefaceSpan-字体样式，可以设置不同的字体");
        builder.setSpan(typefaceSpan, 0, 12, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        typefaceText.setText(builder);

        builder.clear();
        builder.clearSpans();

        /*TextView rasterizerText = (TextView) findViewById(R.id.rasterizer_span);
        RasterizerSpan rasterizerSpan = new RasterizerSpan(new Rasterizer());
        builder.append("RasterizerSpan-设置光栅字样");
        builder.setSpan(rasterizerSpan, 0, 14, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        rasterizerText.setText(builder);*/

        builder.clear();
        builder.clearSpans();

        TextView strikeThroughText = (TextView) findViewById(R.id.strike_through_span);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        builder.append("StrikethroughSpan-删除线");
        builder.setSpan(strikethroughSpan, 0, 17, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        strikeThroughText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView underlineText = (TextView) findViewById(R.id.underline_span);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        builder.append("UnderlineSpan-下划线");
        builder.setSpan(underlineSpan, 0, 13, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        underlineText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView tabStopText = (TextView) findViewById(R.id.tab_stop_span);
        TabStopSpan tabStopSpan = new TabStopSpan.Standard(200);
        builder.append("\tTabStopSpan-每行的MarginLeft的偏移量（跟 \\\\t 和 \\\\n 有关系）");
        builder.setSpan(tabStopSpan, 0, builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tabStopText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView quoteText = (TextView) findViewById(R.id.quote_span);
        QuoteSpan quoteSpan = new QuoteSpan(getResources().getColor(R.color.state_1));
        builder.append("QuoteSpan-设置文字左侧显示引用样式（一条竖线）");
        builder.setSpan(quoteSpan, 0, builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        quoteText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView bulletText = (TextView) findViewById(R.id.bullet_span);
        BulletSpan bulletSpan = new BulletSpan(66, getResources().getColor(R.color.state_1));
        builder.append("BulletSpan-类似于HTML中的 li 标签的圆点效果。gapWidth：圆点与文本的间距。color：圆点颜色。");
        builder.setSpan(bulletSpan, 0, builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        bulletText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView leadingMarginText = (TextView) findViewById(R.id.leading_margin_span);
        LeadingMarginSpan leadingMarginSpan = new LeadingMarginSpan.Standard(96, 36);
        builder.append("LeadingMarginSpan-设置文本缩进,first：首行的 margin left 偏移量。rest：其他行的 margin left 偏移量。");
        builder.setSpan(leadingMarginSpan, 0, builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        leadingMarginText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView iconMarginText = (TextView) findViewById(R.id.icon_margin_span);
        IconMarginSpan iconMarginSpan = new IconMarginSpan(BitmapFactory.decodeResource(getResources(), R.mipmap.animation_wallet), 60);
        builder.append("IconMarginSpan-文本插入图片+Margin");
        builder.setSpan(iconMarginSpan, 13, 15, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        iconMarginText.setText(builder);
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("SpannableString的使用");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
