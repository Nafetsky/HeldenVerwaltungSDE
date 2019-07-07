package api.base;

import api.AbilityGroup;
import api.Advantage;
import api.CombatTechnique;
import api.CombatTechniqueImpl;
import api.Disadvantage;
import api.Event;
import api.IAttributes;
import api.IMetaData;
import api.ISpecialAbility;
import api.skills.Skill;
import api.skills.SkillGroup;
import api.skills.SkillLevler;

import java.util.List;

public interface Character {

	IMetaData getMetaData();

	int getUsedAdventurePoints();
	int getAllAdventurePoints();
	int getFreeAdventurePoints();

	IAttributes getAttributes();

	List<Advantage> getAdvantages();

	List<Disadvantage> getDisadvantages();

	int getBonusLifePoints();

	int getBonusArcaneEnergy();

	int getLostArcaneEnergy();

	int getBonusKarmaPoints();


	List<Skill> getSkills();

	List<Skill> getSkills(SkillGroup group);

	List<CombatTechnique> getCombatTechniques();

	List<ISpecialAbility> getSpecialAbilities();

	List<ISpecialAbility> getSpecialAbilities(AbilityGroup group);

	List<Event> getHistory();

	SkillLevler getSkillLevler(String name);

	void increase(Event event);

	void save(String message);


}
