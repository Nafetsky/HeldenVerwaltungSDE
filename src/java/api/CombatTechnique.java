package api;

import lombok.Data;

@Data
public class CombatTechnique {

	private final String name;
	private final BaseAttribute attribute;
	private final ImprovementComplexity improvementComplexity;

	private int level = 6;
}
