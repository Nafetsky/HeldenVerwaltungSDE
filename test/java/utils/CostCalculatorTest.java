package utils;


import java.util.ArrayList;
import java.util.List;


import database.CostCategory;
import generated.Attribut;
import generated.Attributskürzel;
import generated.Basistalent;
import generated.Charakter;
import generated.Eigenschaftswerte;
import generated.Fertigkeit;
import generated.MerkmalProfan;
import generated.ObjectFactory;
import generated.Steigerungskategorie;
import generated.Talente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class CostCalculatorTest {
	
	ObjectFactory factory;
	
	@BeforeEach
	void init(){
		factory = new ObjectFactory();
	}

	@Test
	void testCostCalculatorComabtforB() {
		assertThat(CostCalculator.calcCostCombat(7, CostCategory.B), is(2));
		assertThat(CostCalculator.calcCostCombat(12, CostCategory.B), is(12));
		assertThat( CostCalculator.calcCostCombat(14, CostCategory.B), is(22));
		assertThat(CostCalculator.calcCostCombat(16, CostCategory.B), is(40));
	}

	@Test
	void testCostCalculatorComabtforC() {
		assertThat(CostCalculator.calcCostCombat(7, CostCategory.C), is(3));
		assertThat(CostCalculator.calcCostCombat(12, CostCategory.C), is(18));
		assertThat(CostCalculator.calcCostCombat(14, CostCategory.C), is(33));
		assertThat(CostCalculator.calcCostCombat(16, CostCategory.C), is(60));
	}

	@Test
	void testSkillCostCalculator() {
		assertThat(CostCalculator.calcCostSkill(0, CostCategory.D, true), is(0));
		assertThat(CostCalculator.calcCostSkill(0, CostCategory.D, false), is(4));
		assertThat(CostCalculator.calcCostSkill(1, CostCategory.A, false), is(2));
		assertThat(CostCalculator.calcCostSkill(10, CostCategory.A, false), is(11));
		assertThat(CostCalculator.calcCostSkill(13, CostCategory.A, false), is(15));
		assertThat(CostCalculator.calcCostSkill(15, CostCategory.A, false), is(22));

		assertThat(CostCalculator.calcCostSkill(18, CostCategory.D, true), is(156));
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
	void testCompleteBaseAbilityCosts(){
		Eigenschaftswerte values = generateBarundarsBaseAbilitys();
		assertThat(CostCalculator.calcAllBaseAbilityCosts(values), is(795));
	}

	private Eigenschaftswerte generateBarundarsBaseAbilitys() {
		Eigenschaftswerte values = factory.createEigenschaftswerte();
		Attribut attribute = factory.createAttribut();
		attribute.setKürzel(Attributskürzel.MU);
		attribute.setAttributswert(16);
		values.setMut(attribute);
		
		attribute = factory.createAttribut();
		attribute.setKürzel(Attributskürzel.KL);
		attribute.setAttributswert(14);
		values.setKlugheit(attribute);
		
		attribute = factory.createAttribut();
		attribute.setKürzel(Attributskürzel.IN);
		attribute.setAttributswert(14);
		values.setIntuition(attribute);
		
		attribute = factory.createAttribut();
		attribute.setKürzel(Attributskürzel.CH);
		attribute.setAttributswert(9);
		values.setCharisma(attribute);
		
		attribute = factory.createAttribut();
		attribute.setKürzel(Attributskürzel.FF);
		attribute.setAttributswert(9);
		values.setFingerfertigkeit(attribute);
		
		attribute = factory.createAttribut();
		attribute.setKürzel(Attributskürzel.GE);
		attribute.setAttributswert(14);
		values.setGewandheit(attribute);
		
		attribute = factory.createAttribut();
		attribute.setKürzel(Attributskürzel.KK);
		attribute.setAttributswert(16);
		values.setKörperkraft(attribute);
		
		attribute = factory.createAttribut();
		attribute.setKürzel(Attributskürzel.KO);
		attribute.setAttributswert(16);
		values.setKonstitution(attribute);
		
		return values;
	}
	
	@Test
	void testBaseSkillCostCalculation(){
		Talente talente = makeExampleSkillsFor88AP();
		int cost = CostCalculator.calcAllBaseSkillCosts(talente);
		assertThat(cost, is(88));
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
	void testSpecialCostCalculation(){
		List<Fertigkeit> skills = new ArrayList<>();
		assertThat(CostCalculator.calcAllSpecificSkillCosts(skills), is(0));
		Fertigkeit skill = new Fertigkeit();
		skill.setFertigkeitswert(1);
		skill.setSteigerungskosten(Steigerungskategorie.A);
		skills.add(skill);
		assertThat(CostCalculator.calcAllSpecificSkillCosts(skills), is(2));
	}
	
	
	@Test
	void testCompleteTest() throws Exception{
		Charakter barundar;
		TestPreparer preparer = new TestPreparer();
		barundar = preparer.getBarundar();
		
		int cost = CostCalculator.calcUsedAP(WrappedCharakter.getWrappedCharakter(barundar));
		assertThat(cost, is(1725));
	}

}
