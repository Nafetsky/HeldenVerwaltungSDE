package utils;

import org.apache.commons.lang3.StringUtils;

import database.CostCategory;
import database.SpecialSkillGroup;
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
		int newCost = CostCalculatorOld.calcCostSkill(currentSkillLevel + 1, category, false);
		int currentCost = CostCalculatorOld.calcCostSkill(currentSkillLevel, category, false);
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

	@Override
	public CostCategory getCostCategory() {
		return CostCategory.getCostCategory(skill.getSteigerungskosten());
	}
	
	private static final String REPRESENTATION = "REPR�SENTATION_";
	private static final String TRADIDTION = "TRADIDTION_";
	public String getAtributes(){
		String prefix ="";
		String attributes = "";
		for(String attribute:skill.getMerkmal()){
			if(StringUtils.startsWith(attribute, REPRESENTATION) || StringUtils.startsWith(attribute, TRADIDTION)){
				prefix =attribute.replace(REPRESENTATION, "").replace(TRADIDTION,"");
			}
			else{
				attributes += attribute + ", ";
			}
		}
		return prefix+" "+attributes.substring(0, attributes.length()-2);
	}

}
