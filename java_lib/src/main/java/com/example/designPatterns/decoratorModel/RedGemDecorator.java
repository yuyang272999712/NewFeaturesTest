package com.example.designPatterns.decoratorModel;

/**
 * 红宝石装饰品
 */
public class RedGemDecorator implements IEquipDecorator {
    /**
     * 每个装饰品维护一个装备
     */
    private IEquip equip;

    public RedGemDecorator(IEquip equip){
        this.equip = equip;
    }

    @Override
    public String needLevel() {
        return "需要等级20";
    }

    @Override
    public int caculateAttack() {
        return equip.caculateAttack() + 10;
    }

    @Override
    public String description() {
        return equip.description() + " ＋红宝石";
    }
}
