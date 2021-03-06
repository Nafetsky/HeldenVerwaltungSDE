package api;


import api.skills.Descriptor;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class Language implements ILanguage {

	private final String name;
	private final AbilityGroup group = AbilityGroup.LANGUAGE;

	private int level;


	@Override
	public int getCost() {
		return level < 4 ? level * 2 : 0;
	}

	@Override
	public List<Descriptor> getDescriptors() {
		return Collections.emptyList();
	}

}
