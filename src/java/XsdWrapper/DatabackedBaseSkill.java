package XsdWrapper;

import api.BaseAttribute;
import api.skills.BaseSkills;
import api.skills.Descriptor;
import api.skills.ImprovementComplexity;
import api.skills.Skill;
import api.skills.SkillGroup;
import api.skills.SkillImpl;
import generated.Basistalent;
import generated.MerkmalProfan;

import java.util.Optional;

public class DatabackedBaseSkill implements Skill {

	private Basistalent backedSkill;
	private Skill skill;

	public DatabackedBaseSkill(Basistalent talent) {
		backedSkill = talent;
		Translator translator = new Translator();

		BaseSkills baseSkill = BaseSkills.getSkill(talent.getName());
		MerkmalProfan merkmal = talent.getMerkmal();
		skill = new SkillImpl(talent.getName(), SkillGroup.BASE, translator.translate(merkmal), baseSkill.getCategory());
		skill.setLevel(talent.getFertigkeitswert());
	}

	@Override
	public Optional<BaseAttribute[]> getAttributes() {
		return skill.getAttributes();
	}

	@Override
	public void setLevel(int level) {
		backedSkill.setFertigkeitswert(level);
	}

	@Override
	public String getName() {
		return backedSkill.getName();
	}

	@Override
	public SkillGroup getGroup() {
		return skill.getGroup();
	}

	@Override
	public Descriptor[] getDescriptors() {
		return skill.getDescriptors();
	}

	@Override
	public ImprovementComplexity getComplexity() {
		return skill.getComplexity();
	}

	@Override
	public int getLevel() {
		return backedSkill.getFertigkeitswert();
	}
}
