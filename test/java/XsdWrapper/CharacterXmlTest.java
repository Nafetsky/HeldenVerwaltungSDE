package XsdWrapper;

import api.Advantage;
import api.Event;
import api.base.Character;
import api.CombatTechnique;
import api.Disadvantage;
import api.IAttributes;
import api.history.SkillChange;
import api.skills.BaseSkills;
import api.skills.Skill;
import api.skills.SkillGroup;
import api.skills.SkillLevler;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.TestPreparer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


class CharacterXmlTest {

	private static final String EVENT_DESCRIPTION = "Krit beim Zechen";

	private Character barundar;

	@BeforeEach
	void load() throws Exception {
		TestPreparer preparer = new TestPreparer();
		barundar = preparer.getBarundar();
	}

	@Test
	void loadAttributes(){

		IAttributes attributes = barundar.getAttributes();

		assertThat(attributes.getStrength(), is(16));
		assertThat(attributes.getCharisma(), is(9));
	}

	@Test
	void loadSkills(){
		List<Skill> skills = barundar.getSkills(SkillGroup.Base);

		Optional<Skill> flySkill = skills.stream()
										.filter(skill -> StringUtils.equals(skill.getName(), "Fliegen"))
										.findFirst();
		assertThat(flySkill.isPresent(), is(true));
		assertThat(flySkill.get().getLevel(), is(1));
	}

	@Test
	void loadMeta(){
		String name = barundar.getMetaData()
							  .getName();
		assertThat(name, containsString("Barundar"));
	}

	@Test
	void loadAdvantages(){
		Optional<Advantage> loadedAdvantage = barundar.getAdvantages()
												 .stream()
												 .filter(advantage -> StringUtils.contains(advantage.getName(), "Zäher Hund"))
												 .findFirst();
		assertThat(loadedAdvantage.isPresent(), is(true));
		Advantage toughAsNails = loadedAdvantage.get();
		assertThat(toughAsNails.getCost(), is(20));
	}

	@Test
	void loadDisadvantages(){
		Optional<Disadvantage> loadedDisadvantage = barundar.getDisadvantages()
																   .stream()
																   .filter(disadvantage -> StringUtils.contains(disadvantage.getName(), "Goldgier"))
																   .findFirst();

		assertThat(loadedDisadvantage.isPresent(), is(true));
		Disadvantage toughAsNails = loadedDisadvantage.get();
		assertThat(toughAsNails.getCost(), is(5));
	}

	@Test
	void loadCombatTechniques(){
		Optional<CombatTechnique> optionalCombatTechnique = barundar.getCombatTechniques()
												   .stream()
												   .filter(combatTechnique -> StringUtils.contains(combatTechnique.getName(), "Schild"))
												   .findFirst();

		assertThat(optionalCombatTechnique.isPresent(), is(true));
		CombatTechnique combatTechnique = optionalCombatTechnique.get();
		assertThat(combatTechnique.getLevel(), is(16));
	}

	@Test
	void testLevelSkillByEvent(){
		SkillChange skillChange = new SkillChange(BaseSkills.CAROUSE.getName());
		skillChange.setIncrease(1);
		Event build = Event.builder()
						   .skillChanges(Collections.singletonList(skillChange))
						   .build();

		barundar.increase(build);
		barundar.save(EVENT_DESCRIPTION);

		List<Event> history = barundar.getHistory();
		assertThat(history, hasSize(2));
		Event event = history.get(history.size() - 1);
		assertThat(event.getDescription(), is(EVENT_DESCRIPTION));
		assertThat(event.getSkillChanges(), hasSize(1));
		SkillChange changeFromHistory = event.getSkillChanges()
										.get(0);
		assertThat(changeFromHistory.getName(), is(BaseSkills.CAROUSE.getName()));
		assertThat(changeFromHistory.getIncrease(), is(1));
		assertThat(changeFromHistory.getNewValue(), is(10));
	}

	@Test
	void testLevelSkillDirectly(){
		SkillLevler carouseLevler = barundar.getSkillLevler(BaseSkills.CAROUSE.getName());
		carouseLevler.level();
		barundar.save(EVENT_DESCRIPTION);

		List<Event> history = barundar.getHistory();
		assertThat(history, hasSize(2));
		Event event = history.get(history.size() - 1);
		assertThat(event.getSkillChanges(), hasSize(1));
		SkillChange changeFromHistory = event.getSkillChanges()
											 .get(0);
		assertThat(changeFromHistory.getName(), is(BaseSkills.CAROUSE.getName()));
		assertThat(changeFromHistory.getIncrease(), is(1));
		assertThat(changeFromHistory.getNewValue(), is(10));
	}

	@Test
	void testLevelSkillDirectlyTwice(){
		SkillLevler carouseLevler = barundar.getSkillLevler(BaseSkills.CAROUSE.getName());
		carouseLevler.level();
		carouseLevler.level();
		barundar.save(EVENT_DESCRIPTION);

		List<Event> history = barundar.getHistory();
		assertThat(history, hasSize(2));
		Event event = history.get(history.size() - 1);
		assertThat(event.getSkillChanges(), hasSize(1));
		SkillChange changeFromHistory = event.getSkillChanges()
											 .get(0);
		assertThat(changeFromHistory.getName(), is(BaseSkills.CAROUSE.getName()));
		assertThat(changeFromHistory.getIncrease(), is(2));
		assertThat(changeFromHistory.getNewValue(), is(11));
	}


}