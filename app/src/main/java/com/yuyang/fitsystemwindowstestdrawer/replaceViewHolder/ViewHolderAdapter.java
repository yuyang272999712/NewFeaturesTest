package com.yuyang.fitsystemwindowstestdrawer.replaceViewHolder;

import android.util.SparseArray;
import android.view.View;
import android.widget.BaseAdapter;

/**
 * 通用viewHolder
 *
 */
public abstract class ViewHolderAdapter extends BaseAdapter {

	@SuppressWarnings("unchecked")
	public <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}
	
	
}
