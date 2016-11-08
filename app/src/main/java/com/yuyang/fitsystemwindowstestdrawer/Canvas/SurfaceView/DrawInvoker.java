package com.yuyang.fitsystemwindowstestdrawer.Canvas.SurfaceView;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 绘制请求封装类
 */

public class DrawInvoker {
    /**
     * 绘制列表
     */
    private List<DrawPath> drawList = Collections.synchronizedList(new ArrayList<DrawPath>());
    /**
     * 重做列表
     */
    private List<DrawPath> redoList = Collections.synchronizedList(new ArrayList<DrawPath>());

    /**
     * 增加一个命令
     * @param command
     */
    public void add(DrawPath command){
        redoList.clear();
        drawList.add(command);
    }

    /**
     * 撤销上一步命令
     */
    public void undo(){
        if (drawList.size() > 0){
            DrawPath undo = drawList.get(drawList.size()-1);
            drawList.remove(drawList.size()-1);
            undo.undo();
            redoList.add(undo);
        }
    }

    /**
     * 重做上一步撤销的命令
     */
    public void redo(){
        if (redoList.size()>0){
            DrawPath redoCommand = redoList.get(redoList.size()-1);
            redoList.remove(redoList.size()-1);
            drawList.add(redoCommand);
        }
    }

    /**
     * 执行命令
     * @param canvas
     */
    public void execute(Canvas canvas){
        if (drawList != null){
            for (DrawPath tmp : drawList){
                tmp.draw(canvas);
            }
        }
    }

    /**
     * 是否可以重做
     * @return
     */
    public boolean canRedo(){
        return redoList.size()>0;
    }

    /**
     * 是否可以撤销
     * @return
     */
    public boolean canUndo(){
        return drawList.size()>0;
    }
}
