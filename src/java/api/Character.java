package api;

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


}
