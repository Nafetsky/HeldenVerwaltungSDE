package utils;

import dataBase.SpecialSkillGroup;

public interface Skill {
	
	int getCostForNextLevel();
	
	void increaseByOne();
	
	SpecialSkillGroup getGroup();
	
	String getName();

	int getCurrentValue();

}
