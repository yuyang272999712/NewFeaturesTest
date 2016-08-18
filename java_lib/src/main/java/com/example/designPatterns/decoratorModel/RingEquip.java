package com.example.designPatterns.decoratorModel;

/**
 * 戒指装备
 */
public class RingEquip implements IEquip {
    @Override
    public int caculateAttack() {
        return 15;
    }

    @Override
    public String description() {
        return "戒指";
    }
}
