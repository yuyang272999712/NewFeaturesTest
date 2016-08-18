package com.example.designPatterns.decoratorModel;

/**
 * 武器装备
 */
public class ArmsEquip implements IEquip {
    @Override
    public int caculateAttack() {
        return 20;
    }

    @Override
    public String description() {
        return "武器";
    }
}
