package com.example.designPatterns.decoratorModel;

/**
 * 装饰品的接口
 */
public interface IEquipDecorator extends IEquip {
    /**
     *  需要等级的描述
     *
     * @return
     */
    public String needLevel();
}
