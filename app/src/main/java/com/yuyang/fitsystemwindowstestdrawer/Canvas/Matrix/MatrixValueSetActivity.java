package com.yuyang.fitsystemwindowstestdrawer.Canvas.Matrix;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Matrix 直接修改矩阵的各项数值来修改图片的变换
 */
public class MatrixValueSetActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private MatrixView matrixView;
    private GridLayout mGroup;
    private int mEtWidth, mEtHeight;
    private EditText[] mEts = new EditText[9];//保存矩阵上的EditText
    private float[] mMatrix = new float[9];//保存矩阵的值

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_value_set);
        findViews();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mGroup = (GridLayout) findViewById(R.id.group);
        matrixView = (MatrixView) findViewById(R.id.matrixView);

        mToolbar.setTitle("Matrix值修改");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mGroup.post(new Runnable() {
            @Override
            public void run() {
                mEtHeight = mGroup.getHeight()/3;
                mEtWidth = mGroup.getWidth()/3;
                addEts();
                initMatrix();
            }
        });
    }

    //作用矩阵效果
    public void btnChange(View view) {
        getMatrix();
        setImageMatrix();
    }

    //重置矩阵效果
    public void btnReset(View view) {
        initMatrix();
        getMatrix();
        setImageMatrix();
    }

    /**
     * 初始化所有EditText的值，即初始化矩阵的值
     */
    private void initMatrix() {
        for (int i=0; i<9; i++){
            if (i%4 == 0){
                mEts[i].setText(String.valueOf(1));
            }else {
                mEts[i].setText(String.valueOf(0));
            }
        }
    }

    /**
     * 向GridLayout中添加EditText
     */
    private void addEts() {
        for (int i=0; i<9; i++){
            EditText editText = new EditText(this);
            mEts[i] = editText;
            mGroup.addView(editText, mEtWidth, mEtHeight);
        }
    }

    /**
     * 获取矩阵的值
     */
    private void getMatrix(){
        for (int i=0; i<9; i++){
            mMatrix[i] = Float.valueOf(mEts[i].getText().toString());
        }
    }

    /**
     * 将矩阵值设置到图像
     */
    private void setImageMatrix(){
        matrixView.setMatrix(mMatrix);
    }

}