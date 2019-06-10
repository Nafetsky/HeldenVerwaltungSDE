package utils;

import java.util.List;

import api.BaseSkills;
import generated.Basistalent;

class CharakterCleaner {

	final WrappedCharakter activeCharakter;

	CharakterCleaner(WrappedCharakter charakter) {
		this.activeCharakter = charakter;
	}

	/**
	 * sorts all Skill, advantages, disadvantages, feats and so on. should be
	 * used before saving to get a nice clean xml
	 */
	public void cleanUpCharakter() {
		List<Basistalent> skills = activeCharakter.getSkills().getTalent();

		skills.sort((talent1, talent2) -> {
			BaseSkills skill1 = BaseSkills.getSkill(talent1.getName());
			BaseSkills skill2 = BaseSkills.getSkill(talent2.getName());
			return skill1.compareTo(skill2);
		});

	}

}
