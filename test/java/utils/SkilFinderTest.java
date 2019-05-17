package utils;


import java.util.List;

import database.FeatGroup;
import generated.Charakter;
import generated.Sonderfertigkeit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class SkilFinderTest {

	SkillFinder finder;
	Charakter barundar;

	@BeforeEach
	void init() throws Exception {
		TestPreparer test = new TestPreparer();
		barundar = test.getBarundar();
		finder = new SkillFinder(WrappedCharakter.getWrappedCharakter(barundar));
	}
	
	@Test
	void testgetCostCombatSkillIncreaseShields() {
		Skill skill = finder.findSkill("Schilde");
		assertThat(skill.getCostForNextLevel(), is(18));
	}

	@Test
	void testgetCostBaseSkillIncreaseLackingCozen() {
		Skill skill = finder.findSkill("Betören");
		assertThat(skill.getCostForNextLevel(), is(2));
	}

	@Test
	void testgetCostBaseSkillIncreaseClimb() {
		Skill skill = finder.findSkill("Klettern");
		assertThat(skill.getCostForNextLevel(), is(2));
	}

	@Test
	void testGetAllFeatsByGroup() {
		List<Sonderfertigkeit> feats = finder.getAllFeatsByGroup(FeatGroup.COMMON);
		assertThat(feats.size(), is(4));
		feats = finder.getAllFeatsByGroup(FeatGroup.COMBAT);
		assertThat(feats.size(), is(5));
	}


}
