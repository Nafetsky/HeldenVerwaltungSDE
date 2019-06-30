package api;

import api.skills.Descriptor;
import lombok.Data;

@Data
public class DescribesSkill implements Descriptor {

	private final String skillName;
}
