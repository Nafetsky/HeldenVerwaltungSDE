package api;

import api.skills.ImprovementComplexity;
import api.skills.Increasable;
import lombok.Data;

@Data
public class BaseResource implements Increasable {

	private final String name;
	private final ImprovementComplexity complexity = ImprovementComplexity.D;

	private int level;
}
