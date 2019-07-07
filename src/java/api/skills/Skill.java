package api.skills;

import api.BaseAttribute;

import java.util.Optional;

public interface Skill extends Increasable {
	Optional<BaseAttribute[]> getAttributes();

	SkillGroup getGroup();

	Descriptor[] getDescriptors();

}
