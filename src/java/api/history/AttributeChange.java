package api.history;

import api.BaseAttribute;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttributeChange {

	private final BaseAttribute attribute;
	private int change;
	private int newValue;

}
