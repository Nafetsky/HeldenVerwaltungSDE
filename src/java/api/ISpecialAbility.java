package api;

import java.util.List;

public interface ISpecialAbility {

	String getName();

	int getCost();

	AbilityGroup getGroup();

	List<Descriptor> getDescriptors();
}
