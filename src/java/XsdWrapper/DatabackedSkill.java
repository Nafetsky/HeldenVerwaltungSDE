package XsdWrapper;

import api.BaseAttribute;
import api.skills.Descriptor;
import api.skills.ImprovementComplexity;
import api.skills.Skill;
import api.skills.SkillGroup;
import api.skills.SkillImpl;
import generated.Fertigkeit;

import java.util.List;
import java.util.Optional;

public class DatabackedSkill implements Skill {

	private Skill skill;
	private Fertigkeit fertigkeit;



	public DatabackedSkill(Fertigkeit fertigkeit) {
		this.fertigkeit = fertigkeit;
		Translator translator = new Translator();

		String name = fertigkeit.getName();
		SkillGroup skillGroup = translator.translate(fertigkeit.getKategorie());
		BaseAttribute firstAttribute = translator.translate(fertigkeit.getAttribut1());
		BaseAttribute secondAttribute = translator.translate(fertigkeit.getAttribut2());
		BaseAttribute thirdAttribute = translator.translate(fertigkeit.getAttribut3());
		BaseAttribute[] attributes = {firstAttribute, secondAttribute, thirdAttribute};
		List<String> merkmals = fertigkeit.getMerkmal();
		Descriptor[] descriptors = new DescriptorTranslator().translateToDescriptors(merkmals);

		ImprovementComplexity complexity = translator.translate(fertigkeit.getSteigerungskosten());
		skill =  new SkillImpl(name, skillGroup, attributes, descriptors, complexity);
	}

	@Override
	public Optional<BaseAttribute[]> getAttributes() {
		return skill.getAttributes();
	}

	@Override
	public void setLevel(int level) {
		fertigkeit.setFertigkeitswert(level);
	}

	@Override
	public String getName() {
		return skill.getName();
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
		return fertigkeit.getFertigkeitswert();
	}
}
