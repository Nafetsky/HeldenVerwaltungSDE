package utils;

import java.util.List;

import dataBase.AbilityCategory;
import dataBase.BaseSkills;
import dataBase.CostCategory;
import generated.Basistalent;
import generated.Charakter;
import generated.Eigenschaftswerte;
import generated.Fertigkeit;
import generated.Talente;

public class CostCalculator {

	
	public static int calcUsedAP(Charakter charakter){
		int usedAp = 0;
		usedAp += charakter.getSpezies().getKosten();
		usedAp += calcAllBaseAbilityCosts(charakter.getEigenschaftswerte());
		usedAp += calcAllBaseSkillCosts(charakter.getTalente());
		usedAp += calcAllSpecificSkillCosts(charakter.getZauber().getZauber());
		usedAp += calcAllSpecificSkillCosts(charakter.getRituale().getRitual());
		usedAp += calcAllSpecificSkillCosts(charakter.getLiturgien().getLiturgie());
		usedAp += calcAllSpecificSkillCosts(charakter.getZeremonien().getZeremonie());
				
		return usedAp;
	}

	static int calcAllBaseAbilityCosts(Eigenschaftswerte values) {
		int abilityCosts = 0;
		abilityCosts += calcCostBaseAbility(values.getMut().getAttributswert());
		abilityCosts += calcCostBaseAbility(values.getKlugheit().getAttributswert());
		abilityCosts += calcCostBaseAbility(values.getIntuition().getAttributswert());
		abilityCosts += calcCostBaseAbility(values.getCharisma().getAttributswert());
		abilityCosts += calcCostBaseAbility(values.getFingerfertigkeit().getAttributswert());
		abilityCosts += calcCostBaseAbility(values.getGewandheit().getAttributswert());
		abilityCosts += calcCostBaseAbility(values.getKonstitution().getAttributswert());
		abilityCosts += calcCostBaseAbility(values.getKörperkraft().getAttributswert());
		return abilityCosts;
	}

	static int calcAllBaseSkillCosts(Talente talente) {
		int sumCosts = 0;
		for(Basistalent talent:talente.getTalent()){
			BaseSkills skill = BaseSkills.getSkill(talent.getName());
			sumCosts += calcCostSkill(talent.getFertigkeitswert(), skill.getCategory(), true);
		}
		return sumCosts;
	}
	
	static int calcAllSpecificSkillCosts(List<Fertigkeit> specialSkills) {
		int sumCosts = 0;
		for(Fertigkeit special:specialSkills){
			sumCosts += calcCostSkill(special.getFertigkeitswert(), CostCategory.getCostCategory(special.getSteigerungskosten()), false);
		}
		return sumCosts;
	}
	
	public static int calcCost(int level, AbilityCategory abil, CostCategory cost, boolean baseSkill) {
		switch (abil) {
		case SKILL:
			return calcCostSkill(level, cost, baseSkill);
		case COMBAT:
			return calcCostCombat(level, cost, baseSkill);
		case ATTRIBUTE:
			return calcCostBaseAbility(level, cost);
		default:
			return 0;
		}
	}

	private static int calculateIncreasedLevel(int level) {
		return (level * (level + 1)) / 2;
	}

	public static int calcCostSkill(int level, CostCategory cost, boolean baseSkill) {
		if (level < 0) {
			throw new ArithmeticException("Can't calculate costs for negative value");
		}
		int effectiveLevel = baseSkill ? 0 : 1;
		if (level < 13) {
			effectiveLevel += level;
		} else {
			effectiveLevel += 11;
			effectiveLevel += calculateIncreasedLevel(level - 11);
		}
		return effectiveLevel * cost.getMultiplier();
	}

	public static final int calcCostCombat(int level, CostCategory cost, boolean baseSkill) {
		if (level < 6) {
			throw new ArithmeticException("Can't calculate costs for comabt skill below 6");
		}
		int effectiveLevel = baseSkill ? 0 : 1;
		if (level < 13) {
			effectiveLevel += level - 6;
		} else {
			effectiveLevel += 5;
			effectiveLevel += calculateIncreasedLevel(level - 11);
		}
		return effectiveLevel * cost.getMultiplier();
	}

	public static int calcCostBaseAbility(int level) {
		return calcCostBaseAbility(level, CostCategory.E);
	}

	public static int calcCostBaseAbility(int level, CostCategory cost) {
		if (level < 8) {
			throw new ArithmeticException("Can't calculate costs for attribute below 8");
		}
		int effectiveLevel = 0;
		if (level < 15) {
			effectiveLevel += level - 8;
		} else {
			effectiveLevel += 5;
			effectiveLevel += calculateIncreasedLevel(level - 13);
		}
		return effectiveLevel * cost.getMultiplier();
	}

}
