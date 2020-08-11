package api;

import api.skills.ImprovementComplexity;
import lombok.Data;

@Data
public class CombatTechniqueImpl implements CombatTechnique {

	private final String name;
	private final BaseAttribute attribute;
	private final ImprovementComplexity complexity;

	private int level = 6;
}
