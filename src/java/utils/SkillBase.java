package utils;

import database.BaseSkills;
import database.CostCategory;
import database.SpecialSkillGroup;
import generated.Basistalent;

public class SkillBase implements Skill {

	Basistalent baseSkill;
	BaseSkills skillData;

	public SkillBase(Basistalent baseSkill) {
		this.baseSkill = baseSkill;
		skillData = BaseSkills.getSkill(baseSkill.getName());
	}

	@Override
	public int getCostForNextLevel() {
		return CostCalculator.calcCostSkill(baseSkill.getFertigkeitswert() + 1, skillData.getCategory(), true)
				- CostCalculator.calcCostSkill(baseSkill.getFertigkeitswert(), skillData.getCategory(), true);
	}

	@Override
	public void increaseByOne() {
		baseSkill.setFertigkeitswert(baseSkill.getFertigkeitswert() + 1);
	}

	@Override
	public SpecialSkillGroup getGroup() {
		return SpecialSkillGroup.BASE;
	}

	@Override
	public String getName() {
		return baseSkill.getName();
	}

	@Override
	public int getCurrentValue() {
		return baseSkill.getFertigkeitswert();
	}
	
	public Basistalent getBasistalent(){
		return baseSkill;
	}

	@Override
	public CostCategory getCostCategory() {
		return skillData.getCategory();
	}

}
