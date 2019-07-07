package api.base;

import api.Advantage;
import api.CombatTechniqueImpl;
import api.Disadvantage;
import api.IAttributes;
import api.ISpecialAbility;
import api.skills.Skill;

import java.util.List;

public interface Profession {

	IAttributes getAttributes();

	List<Advantage> getAdvantages();

	List<Disadvantage> getDisadvantages();

	List<Skill> getSkills();

	List<CombatTechniqueImpl> getCombatTechniques();

	List<ISpecialAbility> getSpecialAbilities();
}
