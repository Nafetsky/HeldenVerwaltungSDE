package controle;

import api.AbilityGroup;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class AddFeatDialogResult implements AddDialogResult {

	private String name;
	private int cost;
	private AbilityGroup group;

	public AddFeatDialogResult(String name, int cost, AbilityGroup group) {
		this.name = name;
		this.cost = cost;
		this.group = group;
	}

	@Override
	public boolean isComplete() {
		return StringUtils.isNotEmpty(name) && cost > -1;
	}

}
