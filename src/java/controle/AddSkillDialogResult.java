package controle;

import api.skills.ImprovementComplexity;
import api.skills.SkillGroup;
import org.apache.commons.lang3.StringUtils;

import database.SpecialSkillGroup;
import generated.Attributskürzel;

public class AddSkillDialogResult implements AddDialogResult {
	public final String name;
	public final Attributskürzel[] abilities;
	public final ImprovementComplexity costCategory;
	public final String[] attributes;
	private SkillGroup group;

	public AddSkillDialogResult(String name, ImprovementComplexity costCategory, Attributskürzel[] abilities,
			String[] descriptorNames) {
		this.name = name;
		this.costCategory = costCategory;
		if (abilities == null || abilities.length != 3) {
			throw new IndexOutOfBoundsException(abilities + " does not have three values");
		}
		this.abilities = abilities;
		this.attributes = descriptorNames;
	}

	public SkillGroup getGroup() {
		return group;
	}

	public void setGroup(SkillGroup group) {
		if(group != null){
			throw new UnsupportedOperationException("Can't set group, if it is already set");
		}
		this.group = group;
	}

	@Override
	public boolean isComplete() {
		if (StringUtils.isEmpty(name)) {
			return false;
		}

		if (abilities.length != 3 || abilities[0] == null || abilities[1] == null || abilities[2] == null) {
			return false;
		}

		if (costCategory == null) {
			return false;
		}

		if (getGroup() == null) {
			return false;
		}

		return true;
	}
}
