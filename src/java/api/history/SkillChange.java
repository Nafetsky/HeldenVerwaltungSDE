package api.history;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SkillChange {

	private final String name;
	private int increase = 0;
	private int newValue;

}
