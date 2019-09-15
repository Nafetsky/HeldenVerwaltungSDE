package controle;

import api.BaseAttribute;
import api.skills.ImprovementComplexity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;


@Data
public class AddNewCombatSkillDialogResult implements AddDialogResult {
	private final String name;
	private final BaseAttribute ability;
	private final ImprovementComplexity costCategory;

	public AddNewCombatSkillDialogResult(String name, BaseAttribute ability, ImprovementComplexity cost) {
		this.name = name;
		this.ability = ability;
		costCategory = cost;
	}

	public boolean isComplete() {
		if (StringUtils.isEmpty(name)) {
			return false;
		}
		if (null == ability) {
			return false;
		}
		if (null == costCategory) {
			return false;
		}
		return true;
	}
}
