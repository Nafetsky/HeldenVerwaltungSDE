package utils;

import database.CostCategory;
import database.SpecialSkillGroup;
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
		int newCost = CostCalculatorOld.calcCostCombat(skillLevel + 1, category);
		int currentCost = CostCalculatorOld.calcCostCombat(skillLevel, category);

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

	@Override
	public CostCategory getCostCategory() {
		return CostCategory.getCostCategory(combatSkill.getSteigerungskosten());
	}

}
