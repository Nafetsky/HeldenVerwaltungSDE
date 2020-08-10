package api.base;

import api.AbilityGroup;
import api.BaseAttribute;
import api.CombatTechnique;
import api.Event;
import api.IAttributes;
import api.ILanguage;
import api.IMetaData;
import api.ISpecialAbility;
import api.Vantage;
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

	List<Vantage> getAdvantages();

	List<Vantage> getDisadvantages();

	int getBonusLifePoints();

	int getBonusArcaneEnergy();

	int getLostArcaneEnergy();

	int getRestoredArcaneEnergy();

	int getBonusKarmaPoints();


	List<Skill> getSkills();

	List<Skill> getSkills(SkillGroup group);

	List<CombatTechnique> getCombatTechniques();

	List<ISpecialAbility> getSpecialAbilities();

	List<ISpecialAbility> getSpecialAbilities(AbilityGroup group);

	List<ILanguage> getLanguages();

	List<ISpecialAbility> getScriptures();

	List<Event> getHistory();

	SkillLevler getSkillLevler(String name);

	SkillLevler getSkillLevler(BaseAttribute attribute);

	void increase(Event event);

	void save(String message);

	String getContent();


}
