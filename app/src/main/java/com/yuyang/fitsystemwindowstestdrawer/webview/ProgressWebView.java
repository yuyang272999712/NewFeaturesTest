package com.yuyang.fitsystemwindowstestdrawer.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.JsPromptResult;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 自带加载进度的webview
 */
@SuppressWarnings("deprecation")
public class ProgressWebView extends WebView {
	private ProgressBar progressbar;
	private static final int PROGRESS_BAR_WIDTH = 4;
	private String htmlTitle;
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public ProgressWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		progressbar = new ProgressBar(context, null,
				android.R.attr.progressBarStyleHorizontal);
		progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				PROGRESS_BAR_WIDTH, 0, 0));
		Drawable drawable = context.getResources().getDrawable(
				R.drawable.progressbar_webview_states);
		progressbar.setProgressDrawable(drawable);
		addView(progressbar);

		WebSettings settings = getSettings();
		settings.setSupportZoom(true); //支持缩放
		settings.setUseWideViewPort(true);//设置屏幕密度为高频，中频，低频自动缩放，禁止用户手动调整缩放
		settings.setBuiltInZoomControls(true);//是否支持缩放
		settings.setDisplayZoomControls(false);//同上方法
        settings.setLoadWithOverviewMode(true);//缩放至屏幕大小
		setWebChromeClient(new WebChromeClient());
	}

	public class WebChromeClient extends android.webkit.WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {

			if (newProgress == 100) {
				progressbar.setVisibility(GONE);
			} else {
				if (progressbar.getVisibility() == GONE)
					progressbar.setVisibility(VISIBLE);
				
				progressbar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}
		
		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			htmlTitle = title;
		}

    }
	
	public String getTitle(){
		if(null == htmlTitle)
			return "";
		return htmlTitle;
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
		lp.x = l;
		lp.y = t;
		progressbar.setLayoutParams(lp);
		super.onScrollChanged(l, t, oldl, oldt);
	}
}
