package utils;

import dataBase.CostCategory;
import dataBase.SpecialSkillGroup;
import generated.Kampftechnik;

public class SkillCombat implements Skill {

	Kampftechnik combatSkill;

	public SkillCombat(Kampftechnik combatSkill) {
		this.combatSkill = combatSkill;
	}

	@Override
	public int getCostForNextLevel() {
		CostCategory category = CostCategory.getCostCategory(combatSkill.getSteigerungskosten());
		int skillLevel = combatSkill.getKampftechnikwert();
		boolean isBaseSkill = true;
		if (combatSkill.isBasistechnik() != null && !combatSkill.isBasistechnik()) {
			isBaseSkill = false;
		}
		int newCost = CostCalculator.calcCostCombat(skillLevel + 1, category, isBaseSkill);
		int currentCost = CostCalculator.calcCostCombat(skillLevel, category, isBaseSkill);

		return newCost - currentCost;
	}

	@Override
	public void increaseByOne() {
		combatSkill.setKampftechnikwert(combatSkill.getKampftechnikwert() + 1);

	}

	@Override
	public SpecialSkillGroup getGroup() {
		return SpecialSkillGroup.COMBAT;
	}

	@Override
	public String getName() {
		return combatSkill.getName();
	}

	@Override
	public int getCurrentValue() {
		return combatSkill.getKampftechnikwert();
	}

}
