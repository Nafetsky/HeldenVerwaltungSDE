package utils;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dataBase.CostCategory;
import generated.Attribut;
import generated.Attributskürzel;
import generated.Basistalent;
import generated.Eigenschaftswerte;
import generated.MerkmalProfan;
import generated.ObjectFactory;
import generated.Talente;

public class CostCalculatorTest {
	
	ObjectFactory factory;
	
	@Before
	public void init(){
		factory = new ObjectFactory();
	}

	@Test
	public void testCostCalculatorComabtforB() {
		assertEquals(2, CostCalculator.calcCostCombat(7, CostCategory.B, true));
		assertEquals(12, CostCalculator.calcCostCombat(12, CostCategory.B, true));
		assertEquals(22, CostCalculator.calcCostCombat(14, CostCategory.B, true));
		assertEquals(40, CostCalculator.calcCostCombat(16, CostCategory.B, true));
	}

	@Test
	public void testCostCalculatorComabtforC() {
		assertEquals(3, CostCalculator.calcCostCombat(7, CostCategory.C, true));
		assertEquals(18, CostCalculator.calcCostCombat(12, CostCategory.C, true));
		assertEquals(33, CostCalculator.calcCostCombat(14, CostCategory.C, true));
		assertEquals(60, CostCalculator.calcCostCombat(16, CostCategory.C, true));
	}

	@Test
	public void testSkillCostCalculator() {
		assertEquals(0, CostCalculator.calcCostSkill(0, CostCategory.D, true));
		assertEquals(4, CostCalculator.calcCostSkill(0, CostCategory.D, false));
		assertEquals(2, CostCalculator.calcCostSkill(1, CostCategory.A, false));
		assertEquals(11, CostCalculator.calcCostSkill(10, CostCategory.A, false));
		assertEquals(15, CostCalculator.calcCostSkill(13, CostCategory.A, false));
		assertEquals(22, CostCalculator.calcCostSkill(15, CostCategory.A, false));

		assertEquals(156, CostCalculator.calcCostSkill(18, CostCategory.D, true));
	}

	@Test
	public void testAttributeCosts() {
		assertEquals(0, CostCalculator.calcCostBaseAbility(8));
		assertEquals(60, CostCalculator.calcCostBaseAbility(12));
		assertEquals(90, CostCalculator.calcCostBaseAbility(14));
		assertEquals(120, CostCalculator.calcCostBaseAbility(15));
		assertEquals(165, CostCalculator.calcCostBaseAbility(16));
		assertEquals(225, CostCalculator.calcCostBaseAbility(17));
	}
	
	@Test
	public void testCompleteBaseAbilityCosts(){
		Eigenschaftswerte values = generateBarundarsBaseAbilitys();
		assertEquals(795, CostCalculator.calcAllBaseAbilityCosts(values));
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
	public void testBaseSkillCostCalculation(){
		Talente talente = makeExampleSkillsFor88AP();
		int cost = CostCalculator.calcAllBaseSkillCosts(talente);
		assertEquals(88, cost);
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

}
