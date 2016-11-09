package com.yuyang.fitsystemwindowstestdrawer.Canvas.SurfaceView;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 命令模式构造画板
 */
public class DrawBoardActivity extends AppCompatActivity {
    private DrawSurfaceView mCanvas;//画布
    private Button btnUndo,btnRedo;//撤销、重做按钮

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_board);

        mCanvas = (DrawSurfaceView) findViewById(R.id.draw_board);

        btnUndo = (Button) findViewById(R.id.btn_undo);
        btnRedo = (Button) findViewById(R.id.btn_redo);
        btnRedo.setEnabled(false);
        btnUndo.setEnabled(false);

        mCanvas.setInvokerStateChangeListener(new DrawSurfaceView.InvokerStateChangeListener() {
            @Override
            public void stateChanged() {
                if (mCanvas.canRedo()){
                    btnRedo.setEnabled(true);
                }else {
                    btnRedo.setEnabled(false);
                }
                if (mCanvas.canUndo()){
                    btnUndo.setEnabled(true);
                }else {
                    btnUndo.setEnabled(false);
                }
            }
        });
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_red:
                mCanvas.setPaintColor(Color.RED);
                break;
            case R.id.btn_green:
                mCanvas.setPaintColor(Color.GREEN);
                break;
            case R.id.btn_blue:
                mCanvas.setPaintColor(Color.BLUE);
                break;
            case R.id.btn_undo:
                mCanvas.undo();
                break;
            case R.id.btn_redo:
                mCanvas.redo();
                break;
            case R.id.btn_normal:
                mCanvas.setBrush(new NormalBrush());
                break;
            case R.id.btn_circle:
                mCanvas.setBrush(new CircleBrush());
                break;
        }
    }
}
