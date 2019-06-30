package api.skills;

import api.AbilityGroup;
import api.DescribesSkill;
import api.ISpecialAbility;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class SkillSpecialisation implements ISpecialAbility {

	private final String skill;
	private final String name;
	private final int cost;
	private final AbilityGroup group = AbilityGroup.SPECIALISATION;

	@Override
	public List<Descriptor> getDescriptors() {
		return Collections.singletonList(new DescribesSkill(skill));
	}
}
