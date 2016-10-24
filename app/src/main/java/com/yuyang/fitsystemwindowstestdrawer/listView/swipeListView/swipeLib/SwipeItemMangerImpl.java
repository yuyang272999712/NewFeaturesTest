package com.yuyang.fitsystemwindowstestdrawer.listView.swipeListView.swipeLib;

import android.view.View;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SwipeItemMangerImpl is a helper class to help all the adapters to maintain
 * open status.
 */
public class SwipeItemMangerImpl implements SwipeItemMangerInterface {

	private Attributes.Mode mode = Attributes.Mode.Single;//默认只可以有一个是滑动状态
	public final static int INVALID_POSITION = -1;

	protected int mOpenPosition = INVALID_POSITION;//如果是单滑动模式，用这个参数记录滑动的item的位置

	protected Set<Integer> mOpenPositions = new HashSet<Integer>();//如果是多滑动模式，用这个参数记录所有滑动的item的位置
	protected Set<SwipeLayout> mShownLayouts = new HashSet<SwipeLayout>();//记录当前现实的item

	protected BaseAdapter xListAdapter;

	public SwipeItemMangerImpl(BaseAdapter xListAdapter) {
		if (xListAdapter == null)
			throw new IllegalArgumentException(
					"SwipeAdapterInterface can not be null");
		this.xListAdapter = xListAdapter;
	}

	public Attributes.Mode getMode() {
		return mode;
	}

	public void setMode(Attributes.Mode mode) {
		this.mode = mode;
		mOpenPositions.clear();
		mShownLayouts.clear();
		mOpenPosition = INVALID_POSITION;
	}

	public void bind(View view, int position) {
		int resId = view.getId();
		SwipeLayout swipeLayout = (SwipeLayout) view;
		if (swipeLayout == null)
			throw new IllegalStateException(
					"can not find SwipeLayout in target view");

		if (swipeLayout.getTag(resId) == null) {
			OnLayoutListener onLayoutListener = new OnLayoutListener(position);
			SwipeMemory swipeMemory = new SwipeMemory(position);
			swipeLayout.addSwipeListener(swipeMemory);
			swipeLayout.addOnLayoutListener(onLayoutListener);
			swipeLayout.setTag(resId, new ValueBox(position, swipeMemory,
					onLayoutListener));
			mShownLayouts.add(swipeLayout);
		} else {
			ValueBox valueBox = (ValueBox) swipeLayout.getTag(resId);
			valueBox.swipeMemory.setPosition(position);
			valueBox.onLayoutListener.setPosition(position);
			valueBox.position = position;
		}
	}

	@Override
	public void openItem(int position) {
		if (mode == Attributes.Mode.Multiple) {
			if (!mOpenPositions.contains(position))
				mOpenPositions.add(position);
		} else {
			mOpenPosition = position;
		}
		xListAdapter.notifyDataSetChanged();
	}

	@Override
	public void closeItem(int position) {
		if (mode == Attributes.Mode.Multiple) {
			mOpenPositions.remove(position);
		} else {
			if (mOpenPosition == position)
				mOpenPosition = INVALID_POSITION;
		}
		xListAdapter.notifyDataSetChanged();
	}

	@Override
	public void closeAllExcept(SwipeLayout layout) {
		for (SwipeLayout s : mShownLayouts) {
			if (s != layout)
				s.close();
		}
	}

	@Override
	public void closeAllItems() {
		if (mode == Attributes.Mode.Multiple) {
			mOpenPositions.clear();
		} else {
			mOpenPosition = INVALID_POSITION;
		}
		for (SwipeLayout s : mShownLayouts) {
			s.close();
		}
	}

	@Override
	public void removeShownLayouts(SwipeLayout layout) {
		mShownLayouts.remove(layout);
	}

	@Override
	public List<Integer> getOpenItems() {
		if (mode == Attributes.Mode.Multiple) {
			return new ArrayList<Integer>(mOpenPositions);
		} else {
			return Collections.singletonList(mOpenPosition);
		}
	}

	@Override
	public List<SwipeLayout> getOpenLayouts() {
		return new ArrayList<SwipeLayout>(mShownLayouts);
	}

	@Override
	public boolean isOpen(int position) {
		if (mode == Attributes.Mode.Multiple) {
			return mOpenPositions.contains(position);
		} else {
			return mOpenPosition == position;
		}
	}

	class ValueBox {
		OnLayoutListener onLayoutListener;
		SwipeMemory swipeMemory;
		int position;

		ValueBox(int position, SwipeMemory swipeMemory,
				OnLayoutListener onLayoutListener) {
			this.swipeMemory = swipeMemory;
			this.onLayoutListener = onLayoutListener;
			this.position = position;
		}
	}

	class OnLayoutListener implements SwipeLayout.OnLayout {

		private int position;

		OnLayoutListener(int position) {
			this.position = position;
		}

		public void setPosition(int position) {
			this.position = position;
		}

		@Override
		public void onLayout(SwipeLayout v) {
			if (isOpen(position)) {
				v.open(false, false);
			} else {
				v.close(false, false);
			}
		}

	}

	class SwipeMemory extends SimpleSwipeListener {

		private int position;

		SwipeMemory(int position) {
			this.position = position;
		}

		@Override
		public void onClose(SwipeLayout layout) {
			if (mode == Attributes.Mode.Multiple) {
				mOpenPositions.remove(position);
			} else {
				mOpenPosition = INVALID_POSITION;
			}
		}

		@Override
		public void onStartOpen(SwipeLayout layout) {
			if (mode == Attributes.Mode.Single) {
				closeAllExcept(layout);
			}
		}

		@Override
		public void onOpen(SwipeLayout layout) {
			if (mode == Attributes.Mode.Multiple)
				mOpenPositions.add(position);
			else {
				closeAllExcept(layout);
				mOpenPosition = position;
			}
		}

		public void setPosition(int position) {
			this.position = position;
		}
	}

}
