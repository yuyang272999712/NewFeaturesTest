package com.yuyang.fitsystemwindowstestdrawer.Game2048;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 2048的布局
 */
public class Game2048Layout extends RelativeLayout {
    /**
     * 运动方向的枚举
     */
    private enum ACTION {
        LEFT, RIGHT, UP, DOWN
    }
    //默认4x4的矩阵
    private int mColumn = 4;
    //保存所有的Item
    private Game2048Item[] mGame2048Items;
    //item之间的间距
    private int mMargin = 10;
    //面板的padding
    private int mPadding;
    //检测用户的手势
    private GestureDetector mGestureDetector;
    //用于判断是否需要生成新的值
    private boolean isMargeHappen = true;
    private boolean isMoveHappen = true;
    //记录分数
    private int mScore;
    //防止重复绘制
    private boolean once = false;

    private OnGame2048Listener mGame2048Listener;

    public interface OnGame2048Listener {
        void onScoreChange(int score);
        void onGameOver();

    }
    public void setOnGame2048Listener(OnGame2048Listener mGame2048Listener) {
        this.mGame2048Listener = mGame2048Listener;
    }

    public Game2048Layout(Context context) {
        this(context, null);
    }
    public Game2048Layout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public Game2048Layout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMargin = DensityUtils.dp2px(context, mMargin);
        mPadding = Math.min(getPaddingLeft(),Math.min(getPaddingTop(),Math.min(getPaddingRight(),getPaddingBottom())));
        mGestureDetector = new GestureDetector(context, new MyGestureDetector());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int length = Math.min(getMeasuredWidth(),getMeasuredHeight());
        //item宽
        int childWidth = (length - mPadding*2 - mMargin*(mColumn-1))/mColumn;
        if (!once){
            if (mGame2048Items == null) {
                mGame2048Items = new Game2048Item[mColumn * mColumn];
            }
            for (int i=0; i<mGame2048Items.length; i++){
                Game2048Item item = new Game2048Item(getContext());
                mGame2048Items[i] = item;
                item.setId(i+1);
                LayoutParams lp = new LayoutParams(childWidth, childWidth);
                //设置横向间距及布局(不是第一列)
                if (i%mColumn != 0){
                    lp.leftMargin = mMargin;
                    lp.addRule(RIGHT_OF, mGame2048Items[i-1].getId());
                }
                //设置纵向间距及布局(不是第一行)
                if ((i+1) > mColumn){
                    lp.topMargin = mMargin;
                    lp.addRule(BELOW, mGame2048Items[i-mColumn].getId());
                }
                addView(item,lp);
            }
            generateNum();
        }
        once = true;
        setMeasuredDimension(length, length);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    /**
     * 产生一个数字
     */
    private void generateNum() {
        if (checkOver()) {
            Log.e("TAG", "GAME OVER");
            if (mGame2048Listener != null) {
                mGame2048Listener.onGameOver();
            }
            return;
        }
        if (!isFull()){
            if (isMoveHappen || isMargeHappen){
                Random random = new Random();
                int next = random.nextInt(16);
                Game2048Item item = mGame2048Items[next];
                while (item.getmNumber() != 0) {
                    next = random.nextInt(16);
                    item = mGame2048Items[next];
                }
                item.setmNumber(Math.random() > 0.75 ? 4 : 2);
                isMargeHappen = isMoveHappen = false;
            }
        }
    }

    /**
     * 检测当前所有的位置都有数字，且相邻的没有相同的数字
     */
    private boolean checkOver() {
        // 检测是否所有位置都有数字
        if (!isFull()) {
            return false;
        }
        for (int i=0; i<mColumn; i++){
            for (int j=0; j<mColumn; j++){
                int index = i * mColumn + j;
                //当前Item
                Game2048Item item = mGame2048Items[index];
                if ((index + 1)%mColumn != 0){//右边有数字
                    Game2048Item itemRight = mGame2048Items[index+1];
                    if (item.getmNumber() == itemRight.getmNumber()){
                        return false;
                    }
                }
                if (index%mColumn != 0){//左边有数字
                    Game2048Item itemLeft = mGame2048Items[index-1];
                    if (item.getmNumber() == itemLeft.getmNumber()){
                        return false;
                    }
                }
                if (index+1>mColumn){//上边有数字
                    Game2048Item itemTop = mGame2048Items[index-mColumn];
                    if (item.getmNumber() == itemTop.getmNumber()){
                        return false;
                    }
                }
                if (index+mColumn < mColumn*mColumn){//下边有数字
                    Game2048Item itemBottom = mGame2048Items[index+mColumn];
                    if (item.getmNumber() == itemBottom.getmNumber()){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 是否填满数字
     *
     * @return
     */
    private boolean isFull() {
        // 检测是否所有位置都有数字
        for (int i = 0; i < mGame2048Items.length; i++) {
            if (mGame2048Items[i].getmNumber() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据用户手势，整体进行移动合并值
     */
    private void action(ACTION action) {
        //行｜列
        for (int i=0; i<mColumn; i++){
            List<Game2048Item> row_column = new ArrayList<>();
            for (int j=0; j<mColumn; j++){
                // 得到下标
                int index = getIndexByAction(action, i, j);
                Game2048Item item = mGame2048Items[index];
                // 记录不为0的数字
                if (item.getmNumber() != 0){
                    row_column.add(item);
                }
            }
            // 移动
            for (int j=0; j<mColumn && j<row_column.size(); j++){
                int index = getIndexByAction(action, i, j);
                Game2048Item item = mGame2048Items[index];
                //如果原始的数列与取出的不一致，说明在某个位置上有0值，那么就需要移动旁边的数字到这个0值的位置
                if (item.getmNumber() != row_column.get(j).getmNumber()){
                    isMoveHappen = true;
                    break;
                }
            }
            // 合并相同的
            mergeItem(row_column);
            // 设置合并后的值
            for (int j=0; j<mColumn; j++){
                int index = getIndexByAction(action, i, j);
                if (row_column.size() > j){//说明此行｜列不为全为0
                    mGame2048Items[index].setmNumber(row_column.get(j).getmNumber());
                }else {
                    mGame2048Items[index].setmNumber(0);
                }
            }
        }
        generateNum();
    }

    /**
     * 合并相同的Item
     * @param row_column
     */
    private void mergeItem(List<Game2048Item> row_column) {
        if (row_column.size() < 2){
            return;
        }
        for (int i=0; i<row_column.size()-1; i++){
            Game2048Item item1 = row_column.get(i);
            Game2048Item item2 = row_column.get(i+1);
            if (item1.getmNumber() == item2.getmNumber()){
                isMargeHappen = true;
                int val = item1.getmNumber()*2;
                item1.setmNumber(val);
                //加分
                mScore += val;
                if (mGame2048Listener != null){
                    mGame2048Listener.onScoreChange(mScore);
                }
                //list整体移动
                for (int j=i+1; j<row_column.size()-1; j++){
                    row_column.get(j).setmNumber(row_column.get(j+1).getmNumber());
                }
                row_column.get(row_column.size()-1).setmNumber(0);
            }
        }
    }

    /**
     * 根据三个参数获取数组下标
     * 例如：如果是向右滑动，那么就从右向左的顺序获取每一行数字，
     *      这样在计算是否合并相邻数字时就不用判断方向了，即：
     *      直接将row_column中的数字依次相同的相加即可
     * @param action
     * @param i
     * @param j
     * @return
     */
    private int getIndexByAction(ACTION action, int i, int j) {
        int index = -1;
        switch (action){
            case LEFT:
                index = i * mColumn + j;
                break;
            case RIGHT:
                index = i * mColumn + mColumn - j - 1;
                break;
            case UP:
                index = i + j * mColumn;
                break;
            case DOWN:
                index = i + (mColumn - j - 1) * mColumn;
                break;

        }
        return index;
    }

    /**
     * 这接受滑动手势
     */
    private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        final int FLING_MIN_DISTANCE = 50;
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float dx = e2.getX() - e1.getX();
            float dy = e2.getY() - e1.getY();
            //横向滑动
            if (Math.abs(velocityX) > Math.abs(velocityY)){
                if (dx > FLING_MIN_DISTANCE){//右滑
                    action(ACTION.RIGHT);
                }else if (dx < -FLING_MIN_DISTANCE){
                    action(ACTION.LEFT);
                }
            }else {
                if (dy > FLING_MIN_DISTANCE){//下滑
                    action(ACTION.DOWN);
                }else if (dy < -FLING_MIN_DISTANCE){
                    action(ACTION.UP);
                }
            }
            return true;
        }
    }

    /**
     * 重新开始游戏
     */
    public void restart() {
        for (Game2048Item item : mGame2048Items) {
            item.setmNumber(0);
        }
        mScore = 0;
        if (mGame2048Listener != null) {
            mGame2048Listener.onScoreChange(mScore);
        }
        isMoveHappen = isMargeHappen = true;
        generateNum();
    }

}
