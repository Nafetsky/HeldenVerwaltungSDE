package api.skills;

import api.BaseAttribute;

import java.util.Optional;

public interface Skill {
	Optional<BaseAttribute[]> getAttributes();

	void setLevel(int level);

	String getName();

	SkillGroup getGroup();

	Descriptor[] getDescriptors();

	ImprovementComplexity getComplexity();

	int getLevel();
}
