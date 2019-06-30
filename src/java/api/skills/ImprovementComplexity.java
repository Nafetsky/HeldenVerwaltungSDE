package api.skills;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * represents ImprovementCost
 */
@AllArgsConstructor
@Getter
public enum ImprovementComplexity {

	A(1),
	B(2),
	C(3),
	D(4),
	Attribute(15);

	private final int factor;

}
