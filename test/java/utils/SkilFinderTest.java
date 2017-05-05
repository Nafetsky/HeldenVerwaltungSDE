package utils;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import database.FeatGroup;
import generated.Charakter;
import generated.Sonderfertigkeit;

public class SkilFinderTest {
	
	SkillFinder finder;
	Charakter barundar;
	
	@Before
	public void init() throws Exception{
		TestPreparer test = new TestPreparer();
		barundar = test.getBarundar();
		finder = new SkillFinder(WrappedCharakter.getWrappedCharakter(barundar));
	}
	
	@Test
	public void testgetCostCombatSkillIncreaseShields(){
		Skill skill = finder.findSkill("Schilde");
		assertEquals(18, skill.getCostForNextLevel());
	}
	
	@Test
	public void testgetCostBaseSkillIncreaseLackingCozen(){
		Skill skill = finder.findSkill("Betören");
		assertEquals(2, skill.getCostForNextLevel());
	}
	
	@Test
	public void testgetCostBaseSkillIncreaseClimb(){
		Skill skill = finder.findSkill("Klettern");
		assertEquals(2, skill.getCostForNextLevel());
	}
	
	@Test
	public void testGetAllFeatsByGroup(){
		List<Sonderfertigkeit> feats = finder.getAllFeatsByGroup(FeatGroup.COMMON);
		assertEquals(4, feats.size());
		feats = finder.getAllFeatsByGroup(FeatGroup.COMBAT);
		assertEquals(5, feats.size());
	}
	
	

}
