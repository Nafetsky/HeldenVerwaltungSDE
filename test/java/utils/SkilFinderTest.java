package utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import generated.Charakter;

public class SkilFinderTest {
	
	SkillFinder finder;
	Charakter barundar;
	
	@Before
	public void init() throws Exception{
		TestPreparer test = new TestPreparer();
		barundar = test.getBarundar();
		finder = new SkillFinder(barundar);
	}
	
	@Test
	public void testgetCostCombatSkillIncreaseShields(){
		Skill skill = finder.findSkill("Schilde");
		assertEquals(18, skill.getCostForNextLevel());
	}
	
	@Test
	public void testgetCostBaseSkillIncreaseLaqckingCozen(){
		Skill skill = finder.findSkill("Betören");
		assertEquals(2, skill.getCostForNextLevel());
	}
	
	@Test
	public void testgetCostBaseSkillIncreaseClimb(){
		Skill skill = finder.findSkill("Klettern");
		assertEquals(2, skill.getCostForNextLevel());
	}
	
	

}
