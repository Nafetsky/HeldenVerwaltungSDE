package utility;


import api.BaseAttribute;
import api.BaseValueChanges;
import api.CombatTechniqueImpl;
import api.Event;
import api.base.Character;
import api.skills.Descriptor;
import api.skills.ImprovementComplexity;
import api.skills.MagicDescriptors;
import api.skills.Skill;
import api.skills.SkillImpl;
import api.skills.SkillGroup;
import data.Attributes;
import generated.Basistalent;
import generated.MerkmalProfan;
import generated.ObjectFactory;
import generated.Talente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CostCalculatorTest {

	private ObjectFactory factory;

	@BeforeEach
	void init() {
		factory = new ObjectFactory();
	}

	@Test
	void testCostCalculatorCombatForB() {
		assertThat(CostCalculator.calcCostCombat(7, ImprovementComplexity.B), is(2));
		assertThat(CostCalculator.calcCostCombat(12, ImprovementComplexity.B), is(12));
		assertThat(CostCalculator.calcCostCombat(14, ImprovementComplexity.B), is(22));
		assertThat(CostCalculator.calcCostCombat(16, ImprovementComplexity.B), is(40));
	}

	@Test
	void testCostCalculatorComabtforC() {
		assertThat(CostCalculator.calcCostCombat(7, ImprovementComplexity.C), is(3));
		assertThat(CostCalculator.calcCostCombat(12, ImprovementComplexity.C), is(18));
		assertThat(CostCalculator.calcCostCombat(14, ImprovementComplexity.C), is(33));
		assertThat(CostCalculator.calcCostCombat(16, ImprovementComplexity.C), is(60));
	}

	@Test
	void testSkillCostCalculator() {
		assertThat(CostCalculator.calcCostSkill(0, ImprovementComplexity.D, true), is(0));
		assertThat(CostCalculator.calcCostSkill(0, ImprovementComplexity.D, false), is(4));
		assertThat(CostCalculator.calcCostSkill(1, ImprovementComplexity.A, false), is(2));
		assertThat(CostCalculator.calcCostSkill(10, ImprovementComplexity.A, false), is(11));
		assertThat(CostCalculator.calcCostSkill(13, ImprovementComplexity.A, false), is(15));
		assertThat(CostCalculator.calcCostSkill(15, ImprovementComplexity.A, false), is(22));

		assertThat(CostCalculator.calcCostSkill(18, ImprovementComplexity.D, true), is(156));
	}

	@Test
	void testAttributeCosts() {
		assertThat(CostCalculator.calcCostBaseAbility(8), is(0));
		assertThat(CostCalculator.calcCostBaseAbility(12), is(60));
		assertThat(CostCalculator.calcCostBaseAbility(14), is(90));
		assertThat(CostCalculator.calcCostBaseAbility(15), is(120));
		assertThat(CostCalculator.calcCostBaseAbility(16), is(165));
		assertThat(CostCalculator.calcCostBaseAbility(17), is(225));
	}


	@Test
	void testCompleteBaseAbilityCosts() {
		Character character = mock(Character.class);
		Attributes attributes = Attributes.builder()
										  .courage(16)
										  .sagacity(14)
										  .intuition(14)
										  .charisma(9)
										  .dexterity(9)
										  .agility(14)
										  .constitution(16)
										  .strength(16)
										  .build();
		when(character.getAttributes()).thenReturn(attributes);

		CostCalculator costCalculator = new CostCalculator(character);
		assertThat(costCalculator.calcAllBaseAbilityCosts(), is(795));
	}


	@Test
	void testBaseSkillCostCalculation() {
//		Talente talente = makeExampleSkillsFor88AP();
//		int cost = CostCalculator.calcAllBaseSkillCosts(talente);
//		assertThat(cost, is(88));
	}

	private Talente makeExampleSkillsFor88AP() {
		Talente talente = factory.createTalente();
		List<Basistalent> list = talente.getTalent();
		Basistalent basis = factory.createBasistalent();
		basis.setFertigkeitswert(8);
		basis.setMerkmal(MerkmalProfan.KÖRPER);
		basis.setName("Zechen");
		list.add(basis);
		basis = factory.createBasistalent();
		basis.setFertigkeitswert(14);
		basis.setMerkmal(MerkmalProfan.GESELLSCHAFT);
		basis.setName("Willenskraft");
		list.add(basis);
		basis = factory.createBasistalent();
		basis.setFertigkeitswert(12);
		basis.setMerkmal(MerkmalProfan.HANDWERK);
		basis.setName("Fahrzeuge");
		list.add(basis);
		return talente;
	}


	@Test
	void testSpecialCostCalculation() {
		List<Skill> skills = new ArrayList<>();
		assertThat(CostCalculator.calcAllSkillCosts(skills), is(0));
		Skill skill = new SkillImpl("", SkillGroup.SPELL, new Descriptor[]{MagicDescriptors.DEMONIC}, ImprovementComplexity.A);
		skill.setLevel(1);
		skills.add(skill);
		assertThat(CostCalculator.calcAllSkillCosts(skills), is(2));
	}


	@Test
	void testCompleteTest() throws Exception {
		Character barundar;
		TestPreparer preparer = new TestPreparer();
		barundar = preparer.getBarundar();
		CostCalculator costCalculator = new CostCalculator(barundar);

		int cost = costCalculator.calcUsedAP();

		assertThat(cost, is(1725));
	}

	@Test
	void testLostAspDoNotGainPoints() throws Exception {
		TestPreparer preparer = new TestPreparer();
		Character barundar = preparer.getBarundar();
		CostCalculator costCalculator = new CostCalculator(barundar);

		BaseValueChanges build = BaseValueChanges.builder()
												 .lostAstralPoints(2)
												 .build();
		Event changeEvent = Event.builder()
								 .baseValueChanges(build)
								 .build();
		barundar.increase(changeEvent);
		int cost = costCalculator.calcUsedAP();

		assertThat(cost, is(1725));
	}

	@Test
	void testRestoreLostAsp() throws Exception {
		Character barundar;
		TestPreparer preparer = new TestPreparer();
		barundar = preparer.getBarundar();
		CostCalculator costCalculator = new CostCalculator(barundar);

		BaseValueChanges build = BaseValueChanges.builder()
												 .lostAstralPoints(2)
												 .restoredAstralPoints(2)
												 .build();
		Event changeEvent = Event.builder()
								 .baseValueChanges(build)
								 .build();
		barundar.increase(changeEvent);
		int cost = costCalculator.calcUsedAP();

		assertThat(cost, is(1729));
	}

	@Test
	void testRestoreLostKap() throws Exception {
		Character barundar;
		TestPreparer preparer = new TestPreparer();
		barundar = preparer.getBarundar();
		CostCalculator costCalculator = new CostCalculator(barundar);

		BaseValueChanges karmaChange = BaseValueChanges.builder()
												 .lostKarmaPoints(2)
												 .restoredKarmaPoints(2)
												 .build();
		Event changeEvent = Event.builder()
								 .baseValueChanges(karmaChange)
								 .build();
		barundar.increase(changeEvent);
		int cost = costCalculator.calcUsedAP();

		assertThat(cost, is(1729));
	}

	@Test
	void testCostForNextLevel() {
		CombatTechniqueImpl daggers = new CombatTechniqueImpl("Dolche", BaseAttribute.Agility, ImprovementComplexity.B);

		daggers.setLevel(5);
		assertThrows(ArithmeticException.class, () -> CostCalculator.calcCostForNextLevel(daggers));

		daggers.setLevel(7);
		assertThat(CostCalculator.calcCostForNextLevel(daggers), is(2));

		daggers.setLevel(11);
		assertThat(CostCalculator.calcCostForNextLevel(daggers), is(2));

		daggers.setLevel(12);
		assertThat(CostCalculator.calcCostForNextLevel(daggers), is(4));
	}

}
