package controle;

import org.apache.commons.lang3.StringUtils;

import database.CostCategory;
import database.SpecialSkillGroup;
import generated.Attributskürzel;

public class AddSkillDialogResult implements AddDialogResult {
	public final String name;
	public final Attributskürzel[] abilitys;
	public final CostCategory costCategory;
	public final String[] attributes;
	private SpecialSkillGroup group;

	public AddSkillDialogResult(String name, CostCategory costCategory, Attributskürzel[] abilitys,
			String[] attributes) {
		this.name = name;
		this.costCategory = costCategory;
		if (abilitys == null || abilitys.length != 3) {
			throw new IndexOutOfBoundsException(abilitys + " does not have three values");
		}
		this.abilitys = abilitys;
		this.attributes = attributes;
	}

	public SpecialSkillGroup getGroup() {
		return group;
	}

	public void setGroup(SpecialSkillGroup group) {
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

		if (abilitys.length != 3 || abilitys[0] == null || abilitys[1] == null || abilitys[2] == null) {
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
