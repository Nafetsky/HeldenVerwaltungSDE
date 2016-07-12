package utils;

import dataBase.CostCategory;
import dataBase.SpecialSkillGroup;
import generated.Fertigkeit;

public class SkillSpecial implements Skill {

	Fertigkeit skill;
	SpecialSkillGroup group;

	public SkillSpecial(Fertigkeit skill, SpecialSkillGroup group) {
		this.skill = skill;
		this.group = group;
	}

	@Override
	public int getCostForNextLevel() {
		CostCategory category = CostCategory.getCostCategory(skill.getSteigerungskosten());
		int currentSkillLevel = skill.getFertigkeitswert();
		int newCost = CostCalculator.calcCostSkill(currentSkillLevel + 1, category, false);
		int currentCost = CostCalculator.calcCostSkill(currentSkillLevel, category, false);
		return newCost - currentCost;
	}

	@Override
	public void increaseByOne() {
		skill.setFertigkeitswert(skill.getFertigkeitswert() + 1);
	}

	@Override
	public SpecialSkillGroup getGroup() {
		return group;
	}

	@Override
	public String getName() {
		return skill.getName();
	}

	@Override
	public int getCurrentValue() {
		return skill.getFertigkeitswert();
	}

}
