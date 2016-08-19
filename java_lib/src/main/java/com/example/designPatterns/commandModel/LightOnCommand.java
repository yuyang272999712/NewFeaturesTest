package com.example.designPatterns.commandModel;

/**
 * 命令接口, 提供所有命令的接口, 具体命令继承接口, 并实现方法:
 */
public class LightOnCommand implements ICommand {
    private Light light;

    public LightOnCommand(Light light){
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }
}
