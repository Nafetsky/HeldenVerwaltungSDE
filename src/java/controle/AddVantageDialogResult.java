package controle;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
public class AddVantageDialogResult implements AddDialogResult {

	private final String name;
	private final int cost;
	private final boolean advantage;

	@Override
	public boolean isComplete() {
		return StringUtils.isNotEmpty(name) && cost > -1;
	}


}
