package utils;

import database.CostCategory;
import database.SpecialSkillGroup;

public interface Skill {
	
	int getCostForNextLevel();
	
	CostCategory getCostCategory();
	
	void increaseByOne();
	
	SpecialSkillGroup getGroup();
	
	String getName();

	int getCurrentValue();

}
