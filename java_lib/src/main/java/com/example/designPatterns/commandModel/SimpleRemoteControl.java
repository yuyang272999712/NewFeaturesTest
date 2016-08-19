package com.example.designPatterns.commandModel;

/**
 * 接受者, 执行请求, 传入命令参数, 统一执行命令:
 */
public class SimpleRemoteControl {
    private static final int CONTROL_SIZE = 9;
    private ICommand[] commands;

    public SimpleRemoteControl(){
        commands = new ICommand[CONTROL_SIZE];
        /**
         * 初始化所有按钮指向空对象
         */
        for (int i = 0; i < CONTROL_SIZE; i++) {
            commands[i] = new NoCommand();
        }
    }

    public void setCommand(int index, ICommand command) {
        commands[index] = command;
    }

    public void buttonWasPressed(int index) {
        commands[index].execute();
    }
}
