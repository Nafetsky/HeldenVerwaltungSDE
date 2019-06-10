package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import database.Ability;
import api.BaseSkills;
import database.FeatGroup;
import database.SpecialSkillGroup;
import generated.Attribut;
import generated.Attributskürzel;
import generated.Basistalent;
import generated.Eigenschaftswerte;
import generated.Fertigkeit;
import generated.Kampftechnik;
import generated.ObjectFactory;
import generated.Sonderfertigkeit;
import generated.Talentspezialisierung;

public class SkillFinder {

	WrappedCharakter charakter;
	
	ObjectFactory factory;
	
	private static final Logger LOGGER = Logger.getLogger(SkillFinder.class);

	public SkillFinder(WrappedCharakter charakter) {
		this.charakter = charakter;
		factory = new ObjectFactory();
	}

	public Skill findSkill(String name) {
		Skill skill;
		skill = findAbilty(name);
		if (skill != null) {
			return skill;
		}
		Optional<Skill> optional = findBaseSkill(name);
		if (optional.isPresent()) {
			return optional.get();
		}
		optional = findSpecialSkill(name);
		if (optional.isPresent()) {
			return optional.get();
		}

		optional = findCombatSkill(name);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new IllegalArgumentException(
				name + " is no existent skill for the current character " + charakter.getName());
	}

	private Skill findAbilty(String name) {
		for (Ability ability : Ability.values()) {
			if (StringUtils.equals(name, ability.getName())) {
				return new SkillAbility(getAbilty(ability.getAcronym()));
			}
		}
		return null;
	}

	private Optional<Skill> findBaseSkill(String name) {
		try {
			BaseSkills skillData = BaseSkills.getSkill(name);
			List<Basistalent> baseSkillsXml = charakter.getTalente().getTalent();
			for (Basistalent baseSkillXml : baseSkillsXml) {
				if (StringUtils.equals(baseSkillXml.getName(), name)) {
					return Optional.of(new SkillBase(baseSkillXml));
				}
			}
			Basistalent baseSkillXml = factory.createBasistalent();
			baseSkillXml.setFertigkeitswert(0);
			baseSkillXml.setName(name);
//			baseSkillXml.setMerkmal(skillData.getMerkmal());
			charakter.getTalente().getTalent().add(baseSkillXml);
			return Optional.of(new SkillBase(baseSkillXml));

		} catch (IllegalArgumentException e) {
			LOGGER.info("There is no skill " + name, e);
			return Optional.empty();
		}
	}

	private Optional<Skill> findSpecialSkill(String name) {
		Skill skill = null;
		for (SpecialSkillGroup group : SpecialSkillGroup.values()) {
			skill = findSpecialSkill(name, group.getName());
			if (skill != null) {
				return Optional.of(skill);
			}
		}

		return Optional.empty();
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

	private Optional<Skill> findCombatSkill(String name) {
		for (Kampftechnik combatSkill : charakter.getKampftechniken().getKampftechnik()) {
			if (StringUtils.equals(name, combatSkill.getName())) {
				return Optional.of(new SkillCombat(combatSkill));
			}
		}
		return Optional.empty();
	}

	public Attribut getAbilty(Attributskürzel acronym) {
		Eigenschaftswerte abilitys = charakter.getEigenschaftswerte();
		switch (acronym) {
		case MU:
			return abilitys.getMut();
		case KL:
			return abilitys.getKlugheit();
		case IN:
			return abilitys.getIntuition();
		case CH:
			return abilitys.getCharisma();
		case FF:
			return abilitys.getFingerfertigkeit();
		case GE:
			return abilitys.getGewandheit();
		case KK:
			return abilitys.getKörperkraft();
		case KO:
			return abilitys.getKonstitution();
		default:
			throw new UnsupportedOperationException(acronym.name() + " is no valid Attributsk�rzel");
		}
	}
	
	public List<Sonderfertigkeit> getAllFeatsByGroup(FeatGroup group){
		List<Sonderfertigkeit> list = new ArrayList<>();
		for(Sonderfertigkeit feat:charakter.getSonderfertigkeiten().getSonderfertigkeit()){
			if(StringUtils.equals(feat.getKategorie(), group.getName())){
				list.add(feat);
			}
		}
		return list;
	}

	public String getSkillSpecialisations(String name) {
		StringBuilder result = new StringBuilder();
		for(Talentspezialisierung feat:charakter.getSonderfertigkeiten().getTalentspezialisierung()){
			if(StringUtils.equals(feat.getFertigkeit(), name)){
				if(StringUtils.isNotEmpty(result)){
					result.append(", ");
				}
				result.append(feat.getName());
			}
		}
		return result.toString();
	}

}
