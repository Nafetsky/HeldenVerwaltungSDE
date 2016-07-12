package utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import dataBase.BaseSkills;
import dataBase.SpecialSkillGroup;
import generated.Basistalent;
import generated.Charakter;
import generated.Fertigkeit;
import generated.Kampftechnik;
import generated.ObjectFactory;

public class SkillFinder {

	Charakter charakter;
	ObjectFactory factory;

	public SkillFinder(Charakter charakter) {
		this.charakter = charakter;
		factory = new ObjectFactory();
	}

	public Skill findSkill(String name) {
		Skill skill = null;
		skill = findBaseSkill(name);
		if (skill != null) {
			return skill;
		}
		skill = findSpecialSkill(name);
		if (skill != null) {
			return skill;
		}

		skill = findCombatSkill(name);
		if (skill != null) {
			return skill;
		}
		throw new IllegalArgumentException(
				name + " is no existent skill for the current character " + charakter.getName());
	}

	private Skill findBaseSkill(String name) {
		try {
			BaseSkills skillData = BaseSkills.getSkill(name);
			List<Basistalent> baseSkillsXml = charakter.getTalente().getTalent();
			for (Basistalent baseSkillXml : baseSkillsXml) {
				if (StringUtils.equals(baseSkillXml.getName(), name)) {
					return new SkillBase(baseSkillXml);
				}
			}
			Basistalent baseSkillXml = factory.createBasistalent();
			baseSkillXml.setFertigkeitswert(0);
			baseSkillXml.setName(name);
			baseSkillXml.setMerkmal(skillData.getMerkmal());
			return new SkillBase(baseSkillXml);

		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	private Skill findSpecialSkill(String name) {
		Skill skill = null;
		for (SpecialSkillGroup group : SpecialSkillGroup.values()) {
			skill = findSpecialSkill(name, group.getName());
			if (skill != null) {
				return skill;
			}
		}

		return skill;
	}

	private Skill findSpecialSkill(String name, String context) {
		Skill skill = null;
		if (StringUtils.equals(context, SpecialSkillGroup.SPELL.getName())) {
			return findSpecialSkillInList(name, charakter.getZauber().getZauber(), SpecialSkillGroup.SPELL);
		} else if (StringUtils.equals(context, SpecialSkillGroup.RITUAL.getName())) {
			return findSpecialSkillInList(name, charakter.getRituale().getRitual(), SpecialSkillGroup.RITUAL);
		} else if (StringUtils.equals(context, SpecialSkillGroup.LITURGY.getName())) {
			return findSpecialSkillInList(name, charakter.getLiturgien().getLiturgie(), SpecialSkillGroup.LITURGY);
		} else if (StringUtils.equals(context, SpecialSkillGroup.ZEREMONY.getName())) {
			return findSpecialSkillInList(name, charakter.getZeremonien().getZeremonie(), SpecialSkillGroup.ZEREMONY);
		}
		return skill;
	}

	private Skill findSpecialSkillInList(String name, List<Fertigkeit> list, SpecialSkillGroup group) {
		for (Fertigkeit skill : list) {
			if (StringUtils.equals(skill.getName(), name)) {
				return new SkillSpecial(skill, group);
			}
		}
		return null;
	}

	private Skill findCombatSkill(String name) {
		for (Kampftechnik combatSkill : charakter.getKampftechniken().getKampftechnik()) {
			if (StringUtils.equals(name, combatSkill.getName())) {
				return new SkillCombat(combatSkill);
			}
		}
		return null;
	}

}
