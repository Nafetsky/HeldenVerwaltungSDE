package api;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Event {

	private final LocalDateTime date;
	private final String description;

	private int adventurePoints;
	private List<Advantage> advantages;
	private List<Disadvantage> disadvantages;
	private List<Skill> learnedSkills;
	private List<SkillChange> skillChanges;
	private List<SpecialAbility> abilities;

}
