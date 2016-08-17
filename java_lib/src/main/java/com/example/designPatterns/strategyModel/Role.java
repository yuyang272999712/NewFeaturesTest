package com.example.designPatterns.strategyModel;

/**
 * 人物
 */
public abstract class Role {
    public String name;
    public ISkill skill;

    public void setSkill(ISkill skill) {
        this.skill = skill;
    }

    public String getSkill() {
        return skill.displaySkill();
    }
}
