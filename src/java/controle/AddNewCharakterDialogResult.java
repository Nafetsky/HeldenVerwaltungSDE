package controle;

import api.Sex;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
public class AddNewCharakterDialogResult implements AddDialogResult {
	private final String name;
	private final String species;
	private final String culture;
	private final String profession;
	private final Sex sex;

	@Override
	public boolean isComplete() {
		return StringUtils.isNotEmpty(name) && null != species;
	}

}
