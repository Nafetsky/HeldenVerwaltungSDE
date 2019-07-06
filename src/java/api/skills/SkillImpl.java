package api.skills;

import api.BaseAttribute;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public class SkillImpl implements Skill {

	private final String name;
	private final SkillGroup group;
	private final BaseAttribute[] attributes;
	private final Descriptor[] descriptors;
	private final ImprovementComplexity complexity;
	@Setter
	private int level;

	public SkillImpl(String name, SkillGroup group, Descriptor[] descriptors, ImprovementComplexity complexity) {
		this.name = name;
		this.group = group;
		this.descriptors = descriptors;
		this.attributes = null;
		this.complexity = complexity;
		level = 0;
	}


	public SkillImpl(String name, SkillGroup group, BaseAttribute[] attributes, Descriptor[] descriptors, ImprovementComplexity complexity) {
		this.name = name;
		this.group = group;
		if(Arrays.asList(attributes).size() != 3){
			throw new IllegalArgumentException("Skills are checked with 3 attributes");
		}
		this.attributes = attributes;
		this.descriptors = descriptors;
		this.complexity = complexity;
		level = 0;
	}

	@Override
	public Optional<BaseAttribute[]> getAttributes(){
		if (attributes == null) {
			return Optional.empty();
		}
		return Optional.of(attributes);
	}
}
