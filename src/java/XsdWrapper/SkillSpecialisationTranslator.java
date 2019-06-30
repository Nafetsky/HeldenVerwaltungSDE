package XsdWrapper;

import api.skills.BaseSkills;
import api.ISpecialAbility;
import api.skills.SkillSpecialisation;
import generated.Charakter;
import generated.Fertigkeit;
import generated.Talentspezialisierung;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Log4j2
public class SkillSpecialisationTranslator {

	private final Charakter charakter;
	private Map<String, Integer> complexity = new HashMap<>();


	public List<ISpecialAbility> translate(List<Talentspezialisierung> specs) {
		ArrayList<ISpecialAbility> specialisations = new ArrayList<>();

		Map<String, Integer> howOften = new HashMap<>();


		for(Talentspezialisierung spec:specs){
			String skill = spec.getFertigkeit();
			String name = spec.getName();
			int factor = getComplexity(skill);
			int repetition = getNextRepetition(skill, howOften);

			SkillSpecialisation specialisation = new SkillSpecialisation(skill, name, factor*repetition);
			specialisations.add(specialisation);
		}


		return specialisations;
	}

	private int getNextRepetition(String skill, Map<String, Integer> howOften) {
		if(!howOften.containsKey(skill)){
			howOften.put(skill, 1);
			return 1;
		}
		howOften.put(skill, howOften.get(skill)+1);
		return howOften.get(skill);
	}

	private int getComplexity(String skill) {
		if(complexity.containsKey(skill)){
			return complexity.get(skill);
		}
		int factor = findComplexity(skill);
		complexity.put(skill, factor);
		return factor;
	}

	private int findComplexity(String skillName) {
		try{
			BaseSkills skill = BaseSkills.getSkill(skillName);
			return skill.getCategory().getFactor();
		} catch (IllegalArgumentException e){
			LOGGER.debug(skillName + " is no baseskill, searching learned skills", e);
		}

		Stream<Fertigkeit> allSkills = Stream.of(
				charakter.getZauber()
						 .getZauber(),
				charakter.getRituale()
						 .getRitual(),
				charakter.getLiturgien()
						 .getLiturgie(),
				charakter.getZeremonien()
						 .getZeremonie())
													.flatMap(Collection::stream);
		Optional<Fertigkeit> foundSkill = allSkills
				.filter(skill -> StringUtils.equals(skill.getName(), skillName))
				.findAny();

		if(foundSkill.isPresent()){
			return foundSkill.get().getSteigerungskosten().ordinal()+1;
		}
		LOGGER.error("character " + charakter.getName() + " does have no skill " + skillName + " so can not have specialisations");


		return 1;
	}
}
