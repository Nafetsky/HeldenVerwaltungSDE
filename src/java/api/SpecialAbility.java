package api;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SpecialAbility implements ISpecialAbility {

	private final String name;
	private final int cost;
	private final AbilityGroup group;
	@Builder.Default
	private List<Descriptor> descriptors = new ArrayList<>();


}
