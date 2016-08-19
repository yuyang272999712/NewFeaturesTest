package com.example.designPatterns.commandModel;

/**
 * 命令接口, 提供所有命令的接口, 具体命令继承接口, 并实现方法:
 */
public interface ICommand {
    public void execute();
}
