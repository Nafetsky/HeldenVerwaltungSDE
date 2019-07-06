package XsdWrapper;

import api.AbilityGroup;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
public enum SpecialAbilityKeys {

	COMBAT(AbilityGroup.COMBAT, "Kampf"),
	MUNDANE(AbilityGroup.MUNDANE, "Allgemein");

	final private AbilityGroup group;
	final private String name;


	SpecialAbilityKeys(AbilityGroup group, String xmlName) {
		this.group = group;
		name = xmlName;

	}

	public static AbilityGroup parse(String name) {
		return Arrays.stream(values())
					 .filter(key -> StringUtils.equals(key.name, name))
					 .findFirst()
					 .orElseThrow(() -> new IllegalArgumentException(""))
					 .getGroup();
	}

	public static String parse(AbilityGroup group) {
		return Arrays.stream(values())
					 .filter(key -> key.group == group)
					 .findFirst()
					 .orElseThrow(() -> new IllegalArgumentException(""))
					 .getName();
	}


}
