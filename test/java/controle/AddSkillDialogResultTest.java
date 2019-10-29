package controle;

import api.BaseAttribute;
import api.skills.ImprovementComplexity;
import api.skills.SkillGroup;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class AddSkillDialogResultTest {

	@Test
	void setGroup() {
		AddSkillDialogResult dasd = new AddSkillDialogResult("dasd", ImprovementComplexity.A, new BaseAttribute[]{BaseAttribute.Courage, BaseAttribute.Charisma, BaseAttribute.Strength}, new String[]{"Antimagie", "REPRÄSENTATION_MAGIER"});

		dasd.setGroup(SkillGroup.SPELL);

		assertThat(dasd.getGroup(), is(SkillGroup.SPELL));
	}
}