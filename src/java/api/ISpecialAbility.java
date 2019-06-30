package api;

import api.skills.Descriptor;

import java.util.List;

public interface ISpecialAbility {

	String getName();

	int getCost();

	AbilityGroup getGroup();

	List<Descriptor> getDescriptors();
}
