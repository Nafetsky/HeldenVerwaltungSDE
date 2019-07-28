package XsdWrapper;

import api.AbilityGroup;
import api.Advantage;
import api.BaseAttribute;
import api.CombatTechnique;
import api.CombatTechniqueImpl;
import api.Disadvantage;
import api.Event;
import api.IAttributes;
import api.ISpecialAbility;
import api.SpecialAbility;
import api.base.Character;
import api.history.SkillChange;
import api.skills.BaseSkills;
import api.skills.Descriptor;
import api.skills.ImprovementComplexity;
import api.skills.KarmicDescriptor;
import api.skills.MagicDescriptors;
import api.skills.Skill;
import api.skills.SkillImpl;
import api.skills.SkillGroup;
import api.skills.SkillLevler;
import api.skills.TraditionDescriptors;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.TestPreparer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
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
	void loadAttributes() {

		IAttributes attributes = barundar.getAttributes();

		assertThat(attributes.getStrength(), is(16));
		assertThat(attributes.getCharisma(), is(9));
	}

	@Test
	void loadSkills() {
		List<Skill> skills = barundar.getSkills(SkillGroup.BASE);

		Optional<Skill> flySkill = skills.stream()
										 .filter(skill -> StringUtils.equals(skill.getName(), "Fliegen"))
										 .findFirst();
		assertThat(flySkill.isPresent(), is(true));
		assertThat(flySkill.get()
						   .getLevel(), is(1));
	}

	@Test
	void loadMeta() {
		String name = barundar.getMetaData()
							  .getName();
		assertThat(name, containsString("Barundar"));
	}

	@Test
	void loadAdvantages() {
		Optional<Advantage> loadedAdvantage = barundar.getAdvantages()
													  .stream()
													  .filter(advantage -> StringUtils.contains(advantage.getName(), "Zäher Hund"))
													  .findFirst();
		assertThat(loadedAdvantage.isPresent(), is(true));
		Advantage toughAsNails = loadedAdvantage.get();
		assertThat(toughAsNails.getCost(), is(20));
	}

	@Test
	void loadDisadvantages() {
		Optional<Disadvantage> loadedDisadvantage = barundar.getDisadvantages()
															.stream()
															.filter(disadvantage -> StringUtils.contains(disadvantage.getName(), "Goldgier"))
															.findFirst();

		assertThat(loadedDisadvantage.isPresent(), is(true));
		Disadvantage toughAsNails = loadedDisadvantage.get();
		assertThat(toughAsNails.getCost(), is(5));
	}

	@Test
	void loadCombatTechniques() {
		Optional<CombatTechnique> optionalCombatTechnique = barundar.getCombatTechniques()
																	.stream()
																	.filter(combatTechnique -> StringUtils.contains(combatTechnique.getName(), "Schild"))
																	.findFirst();

		assertThat(optionalCombatTechnique.isPresent(), is(true));
		CombatTechnique combatTechnique = optionalCombatTechnique.get();
		assertThat(combatTechnique.getLevel(), is(16));
	}

	@Test
	void testLevelSkillByEvent() {
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
	void testLevelSkillDirectly() {
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
	void testLevelSkillDirectlyTwice() {
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
		assertThat(barundar.getSkills()
						   .get(8)
						   .getLevel(), is(11));
	}

	@Test
	void testLearnCombatSpecialAbility() {
		String specialAbilityName = "Finte II";
		ISpecialAbility feintLevel2 = SpecialAbility.builder()
													.name(specialAbilityName)
													.group(AbilityGroup.COMBAT)
													.cost(25)
													.build();
		Event build = Event.builder()
						   .abilities(Collections.singletonList(feintLevel2))
						   .build();

		barundar.increase(build);
		barundar.save("Learn Stuff");

		List<Event> history = barundar.getHistory();
		assertThat(history, hasSize(2));
		Event event = history.get(history.size() - 1);
		assertThat(event.getAbilities(), hasSize(1));
		ISpecialAbility historySpecialAbility = event.getAbilities()
													 .get(0);
		assertThat(historySpecialAbility.getName(), is(specialAbilityName));
		assertThat(historySpecialAbility.getCost(), is(25));
		assertThat(historySpecialAbility.getGroup(), is(AbilityGroup.COMBAT));

		ISpecialAbility ownSpecialAbility1 = barundar.getSpecialAbilities()
													 .stream()
													 .filter(sA -> StringUtils.equals(sA.getName(), specialAbilityName))
													 .findFirst()
													 .orElseThrow(() -> new IllegalStateException("Test failed. specialability missing"));
		assertThat(ownSpecialAbility1.getName(), is(specialAbilityName));
		assertThat(ownSpecialAbility1.getCost(), is(25));
		assertThat(ownSpecialAbility1.getGroup(), is(AbilityGroup.COMBAT));

	}

	@Test
	void testLearnMundaneSpecialAbility() {
		ISpecialAbility feintLevel2 = SpecialAbility.builder()
													.name("Freiklettern")
													.group(AbilityGroup.MUNDANE)
													.cost(8)
													.build();
		Event build = Event.builder()
						   .abilities(Collections.singletonList(feintLevel2))
						   .build();

		barundar.increase(build);
		barundar.save("Learn Stuff");

		List<Event> history = barundar.getHistory();
		assertThat(history, hasSize(2));
		Event event = history.get(history.size() - 1);
		assertThat(event.getAbilities(), hasSize(1));
		ISpecialAbility iSpecialAbility = event.getAbilities()
											   .get(0);
		assertThat(iSpecialAbility.getName(), is("Freiklettern"));
		assertThat(iSpecialAbility.getCost(), is(8));
		assertThat(iSpecialAbility.getGroup(), is(AbilityGroup.MUNDANE));
	}

	@Test
	void testLearnNewSpell() {
		Descriptor[] descriptors = new Descriptor[]{TraditionDescriptors.GUILD_MAGE, MagicDescriptors.ANTI_MAGIC};
		BaseAttribute[] attributes = new BaseAttribute[]{BaseAttribute.Courage, BaseAttribute.Sagacity, BaseAttribute.Charisma};
		String spellname = "Gardianum";
		Skill gardianum = new SkillImpl(spellname, SkillGroup.SPELL, attributes, descriptors, ImprovementComplexity.B);
		SkillChange learendGardianum = new SkillChange(spellname);
		learendGardianum.setNewValue(0);
		learendGardianum.setIncrease(1);
		Event build = Event.builder()
						   .learnedSkills(Collections.singletonList(gardianum))
						   .skillChanges(Collections.singletonList(learendGardianum))
						   .build();


		barundar.increase(build);
		barundar.save("Learn Magic");


		List<Event> history = barundar.getHistory();
		List<Skill> knownSpells = barundar.getSkills(SkillGroup.SPELL);
		assertThat(knownSpells, hasSize(1));
		Skill spell = knownSpells.get(0);
		assertThat(spell.getName(), is(spellname));
		assertThat(spell.getLevel(), is(1));
		assertThat(Arrays.asList(spell.getAttributes()
									  .get()), contains(BaseAttribute.Courage, BaseAttribute.Sagacity, BaseAttribute.Charisma));
		assertThat(spell.getComplexity(), is(ImprovementComplexity.B));
		assertThat(Arrays.asList(spell.getDescriptors()), contains(TraditionDescriptors.GUILD_MAGE, MagicDescriptors.ANTI_MAGIC));

		assertThat(history, hasSize(2));
		Event event = history.get(history.size() - 1);
		assertThat(event.getLearnedSkills(), hasSize(1));
	}

	@Test
	void testLearnNewRitual() {
		Descriptor[] descriptors = new Descriptor[]{TraditionDescriptors.GUILD_MAGE, MagicDescriptors.ANTI_MAGIC};
		BaseAttribute[] attributes = new BaseAttribute[]{BaseAttribute.Courage, BaseAttribute.Sagacity, BaseAttribute.Charisma};
		String ritualName = "Bannkreis";
		Skill gardianum = new SkillImpl(ritualName, SkillGroup.RITUAL, attributes, descriptors, ImprovementComplexity.B);
		SkillChange learendGardianum = new SkillChange(ritualName);
		learendGardianum.setNewValue(0);
		learendGardianum.setIncrease(1);
		Event build = Event.builder()
						   .learnedSkills(Collections.singletonList(gardianum))
						   .skillChanges(Collections.singletonList(learendGardianum))
						   .build();


		barundar.increase(build);
		barundar.save("Learn Magic");


		List<Event> history = barundar.getHistory();
		List<Skill> knownRituals = barundar.getSkills(SkillGroup.RITUAL);
		assertThat(knownRituals, hasSize(1));
		Skill ritual = knownRituals.get(0);
		assertThat(ritual.getName(), is(ritualName));
		assertThat(ritual.getLevel(), is(1));
		assertThat(Arrays.asList(ritual.getAttributes()
									   .get()), contains(BaseAttribute.Courage, BaseAttribute.Sagacity, BaseAttribute.Charisma));
		assertThat(ritual.getComplexity(), is(ImprovementComplexity.B));
		assertThat(Arrays.asList(ritual.getDescriptors()), contains(TraditionDescriptors.GUILD_MAGE, MagicDescriptors.ANTI_MAGIC));

		assertThat(history, hasSize(2));
		Event event = history.get(history.size() - 1);
		assertThat(event.getLearnedSkills(), hasSize(1));
	}

	@Test
	void testLearnNewLiturgy() {
		Descriptor[] descriptors = new Descriptor[]{TraditionDescriptors.PRAIOS, KarmicDescriptor.PRAIOS_ANTI_MAGIC};
		BaseAttribute[] attributes = new BaseAttribute[]{BaseAttribute.Courage, BaseAttribute.Intuition, BaseAttribute.Charisma};
		String liturgyName = "kleiner Bannstrahl";
		Skill liturgy = new SkillImpl(liturgyName, SkillGroup.LITURGICAL_CHANT, attributes, descriptors, ImprovementComplexity.B);
		SkillChange learnedLiturgy = new SkillChange(liturgyName);
		learnedLiturgy.setNewValue(0);
		learnedLiturgy.setIncrease(0);
		Event build = Event.builder()
						   .learnedSkills(Collections.singletonList(liturgy))
						   .skillChanges(Collections.singletonList(learnedLiturgy))
						   .build();


		barundar.increase(build);
		barundar.save("Learn Chanting");


		List<Event> history = barundar.getHistory();
		List<Skill> knownLiturgies = barundar.getSkills(SkillGroup.LITURGICAL_CHANT);
		assertThat(knownLiturgies, hasSize(1));
		Skill spell = knownLiturgies.get(0);
		assertThat(spell.getName(), is(liturgyName));
		assertThat(spell.getLevel(), is(0));
		assertThat(Arrays.asList(spell.getAttributes()
									  .get()), contains(BaseAttribute.Courage, BaseAttribute.Intuition, BaseAttribute.Charisma));
		assertThat(spell.getComplexity(), is(ImprovementComplexity.B));
		assertThat(Arrays.asList(spell.getDescriptors()), contains(TraditionDescriptors.PRAIOS, KarmicDescriptor.PRAIOS_ANTI_MAGIC));

		assertThat(history, hasSize(2));
		Event event = history.get(history.size() - 1);
		assertThat(event.getLearnedSkills(), hasSize(1));
		Skill skill = event.getLearnedSkills()
						   .get(0);
		assertThat(skill.getName(), is(liturgyName));
	}

	@Test
	void testLearnNewCeremony() {
		Descriptor[] descriptors = new Descriptor[]{TraditionDescriptors.PRAIOS, KarmicDescriptor.PRAIOS_ORDER};
		BaseAttribute[] attributes = new BaseAttribute[]{BaseAttribute.Courage, BaseAttribute.Intuition, BaseAttribute.Charisma};
		String liturgyName = "Greifenruf";
		Skill liturgy = new SkillImpl(liturgyName, SkillGroup.CEREMONY, attributes, descriptors, ImprovementComplexity.C);
		SkillChange learnedLiturgy = new SkillChange(liturgyName);
		learnedLiturgy.setNewValue(0);
		learnedLiturgy.setIncrease(2);
		Event build = Event.builder()
						   .learnedSkills(Collections.singletonList(liturgy))
						   .skillChanges(Collections.singletonList(learnedLiturgy))
						   .build();


		barundar.increase(build);
		barundar.save("Learn Chanting");


		List<Event> history = barundar.getHistory();
		List<Skill> knownLiturgies = barundar.getSkills(SkillGroup.CEREMONY);
		assertThat(knownLiturgies, hasSize(1));
		Skill spell = knownLiturgies.get(0);
		assertThat(spell.getName(), is(liturgyName));
		assertThat(spell.getLevel(), is(2));
		assertThat(Arrays.asList(spell.getAttributes()
									  .get()), contains(BaseAttribute.Courage, BaseAttribute.Intuition, BaseAttribute.Charisma));
		assertThat(spell.getComplexity(), is(ImprovementComplexity.C));
		assertThat(Arrays.asList(spell.getDescriptors()), contains(TraditionDescriptors.PRAIOS, KarmicDescriptor.PRAIOS_ORDER));

		assertThat(history, hasSize(2));
		Event event = history.get(history.size() - 1);
		assertThat(event.getLearnedSkills(), hasSize(1));
		Skill skill = event.getLearnedSkills()
						   .get(0);
		assertThat(skill.getName(), is(liturgyName));
	}

	@Test
	void testLearnCombatTechnique() {

		String skillName = "Kettenwaffen";
		CombatTechniqueImpl technique = new CombatTechniqueImpl(skillName, BaseAttribute.Strength, ImprovementComplexity.D);
		Event build = Event.builder()
						   .learnedCombatTechniques(Collections.singletonList(technique))
						   .build();

		barundar.increase(build);
		barundar.save("Learn flailing");

		List<Event> history = barundar.getHistory();
		assertThat(history, hasSize(2));
		Event event = history.get(history.size() - 1);
		List<CombatTechnique> learnedCombatTechniques = event.getLearnedCombatTechniques();
		assertThat(learnedCombatTechniques, hasSize(1));
		CombatTechnique combatTechnique = learnedCombatTechniques.get(0);
		assertThat(combatTechnique.getName(), is(skillName));
	}

	@Test
	void testIncreaseCombatTechnique() {
		String brawlingName = "Raufen";
		SkillLevler brawlIncreaser = barundar.getSkillLevler(brawlingName);

		brawlIncreaser.level();
		brawlIncreaser.level();
		barundar.save("Train boxing");

		CombatTechnique brawling = barundar.getCombatTechniques()
										   .stream()
										   .filter(skill -> StringUtils.equals(skill.getName(), brawlingName))
										   .findFirst()
										   .get();
		assertThat(brawling.getLevel(), is(14));
		List<Event> history = barundar.getHistory();
		assertThat(history, hasSize(2));
		Event event = history.get(1);
		assertThat(event.getLearnedCombatTechniques(), hasSize(0));
		assertThat(event.getSkillChanges(), hasSize(1));
		SkillChange skillChange = event.getSkillChanges()
									   .get(0);
		assertThat(skillChange.getName(), is(brawlingName));
		assertThat(skillChange.getIncrease(), is(2));
		assertThat(skillChange.getNewValue(), is(14));
	}

	@Test
	void testAlreadyKnownPowerAttack() {
		List<ISpecialAbility> specialAbilities = barundar.getSpecialAbilities(AbilityGroup.COMBAT);

		Optional<ISpecialAbility> powerAttack = specialAbilities.stream()
																.filter(sA -> StringUtils.contains(sA.getName(), "Wuchtschlag"))
																.findFirst();

		assertThat(powerAttack.isPresent(), is(true));
		ISpecialAbility iSpecialAbility = powerAttack.get();
		assertThat(iSpecialAbility.getCost(), is(15));
	}

}