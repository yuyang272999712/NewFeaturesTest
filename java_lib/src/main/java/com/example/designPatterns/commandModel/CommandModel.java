package com.example.designPatterns.commandModel;

/**
 * 命令模式
 */
public class CommandModel {
    public static void main(String[] args){
        /**
         * 具体的实现类
         */
        Light light = new Light();
        Door door = new Door();
        /**
         * 一个控制器，假设是我们的app主界面
         */
        SimpleRemoteControl controlPanel = new SimpleRemoteControl();
        // 为每个按钮设置功能
        controlPanel.setCommand(0, new LightOnCommand(light));
        controlPanel.setCommand(1, new DoorOpenCommand(door));

        // 模拟点击
        controlPanel.buttonWasPressed(0);
        controlPanel.buttonWasPressed(1);
        controlPanel.buttonWasPressed(2);// 这个没有指定，但是不会出任何问题，我们的NoCommand的功劳
    }
}
