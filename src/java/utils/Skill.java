package utils;

import dataBase.CostCategory;
import dataBase.SpecialSkillGroup;

public interface Skill {
	
	int getCostForNextLevel();
	
	CostCategory getCostCategory();
	
	void increaseByOne();
	
	SpecialSkillGroup getGroup();
	
	String getName();

	int getCurrentValue();

}
