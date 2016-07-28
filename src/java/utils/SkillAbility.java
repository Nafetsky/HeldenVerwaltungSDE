package utils;

import dataBase.Ability;
import dataBase.CostCategory;
import dataBase.SpecialSkillGroup;
import generated.Attribut;

public class SkillAbility implements Skill {

	Attribut ability;

	public SkillAbility(Attribut ability) {
		this.ability = ability;
	}

	@Override
	public int getCostForNextLevel() {
		return CostCalculator.calcCostBaseAbility(ability.getAttributswert() + 1)
				- CostCalculator.calcCostBaseAbility(ability.getAttributswert());
	}

	@Override
	public void increaseByOne() {
		ability.setAttributswert(ability.getAttributswert()+1);
	}

	@Override
	public SpecialSkillGroup getGroup() {
		return SpecialSkillGroup.ABILITY;
	}

	@Override
	public String getName() {
		switch(ability.getKürzel()){
		case MU:
			return Ability.VALOR.getName();
		case KL:
			return Ability.INTELLIGENCE.getName();
		case IN:
			return Ability.INTUITION.getName();
		case CH:
			return Ability.CHARISMA.getName();
		case FF:
			return Ability.DEXTERITY.getName();
		case GE:
			return Ability.AGILITY.getName();
		case KK:
			return Ability.STRENGTH.getName();
		case KO:
			return Ability.CONSTITUTION.getName();
		default:
			throw new UnsupportedOperationException(ability.getKürzel() + " is no valid Attributskürzel");
		}
			
	}

	@Override
	public int getCurrentValue() {
		return ability.getAttributswert();
	}
	
	public Attribut getAbility(){
		return ability;
	}

	@Override
	public CostCategory getCostCategory() {
		return CostCategory.E;
	}

}
