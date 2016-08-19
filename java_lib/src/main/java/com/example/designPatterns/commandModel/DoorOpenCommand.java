package com.example.designPatterns.commandModel;

/**
 * 具体的命令实现, 即把具体类的函数, 封装入命令接口
 */
public class DoorOpenCommand implements ICommand {
    private Door door;

    public DoorOpenCommand(Door door){
        this.door = door;
    }

    @Override
    public void execute() {
        door.open();
    }
}
