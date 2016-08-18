package com.example.designPatterns.decoratorModel;

/**
 * 蓝宝石装饰品
 */
public class BlueGemDecorator implements IEquipDecorator {
    /**
     * 每个装饰品维护一个装备
     */
    private IEquip equip;

    public BlueGemDecorator(IEquip equip){
        this.equip = equip;
    }

    @Override
    public String needLevel() {
        return "需要等级10";
    }

    @Override
    public int caculateAttack() {
        return equip.caculateAttack() + 20;
    }

    @Override
    public String description() {
        return equip.description() + " ＋蓝宝石";
    }
}
