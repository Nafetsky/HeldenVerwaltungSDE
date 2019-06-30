package api;

import api.history.AttributeChange;
import api.history.SkillChange;
import api.skills.Skill;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class Event {

	@Builder.Default
	private final LocalDateTime date = LocalDateTime.now();
	@Builder.Default
	private final String description = "";

	private int adventurePoints;
	@Builder.Default
	private List<Advantage> advantages = new ArrayList<>();
	@Builder.Default
	private List<Disadvantage> disadvantages = new ArrayList<>();
	@Builder.Default
	private List<AttributeChange> attributeChanges = new ArrayList<>();
	@Builder.Default
	private List<Skill> learnedSkills = new ArrayList<>();
	@Builder.Default
	private List<SkillChange> skillChanges = new ArrayList<>();
	@Builder.Default
	private List<ISpecialAbility> abilities = new ArrayList<>();

}
