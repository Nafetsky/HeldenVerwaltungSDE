package api.base;

import api.ISpecialAbility;
import api.skills.Skill;

import java.util.List;

public interface Culture {

	List<Skill> getSkills();

	List<ISpecialAbility> getSpecialAbilities();
}
