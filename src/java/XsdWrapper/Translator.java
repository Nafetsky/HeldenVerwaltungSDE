package XsdWrapper;

import api.AbilityGroup;
import api.BaseAttribute;
import api.BaseDescriptors;
import api.BaseSkills;
import api.CombatTechnique;
import api.Descriptor;
import api.ImprovementComplexity;
import api.Language;
import api.Race;
import api.Sex;
import api.Skill;
import api.SkillGroup;
import api.SpecialAbility;
import generated.Attributskürzel;
import generated.Basistalent;
import generated.Geschlecht;
import generated.Kampftechnik;
import generated.MerkmalProfan;
import generated.Schrift;
import generated.Sonderfertigkeit;
import generated.Spezies;
import generated.Sprache;
import generated.Steigerungskategorie;

public class Translator {


	public Race translate(Spezies spezies) {
		return new Race() {
			@Override
			public String getName() {
				return spezies.getName();
			}

			@Override
			public int getCost() {
				return spezies.getKosten();
			}
		};
	}

	public Sex translate(Geschlecht sex) {
		switch (sex) {
			case WEIBLICH:
				return Sex.Female;
			case MÄNNLICH:
				return Sex.Male;
		}
		return Sex.Unspecified;
	}

	public Skill translate(Basistalent talent) {
		MerkmalProfan merkmal = talent.getMerkmal();
		BaseSkills skill1 = BaseSkills.getSkill(talent.getName());
		Skill skill = new Skill(talent.getName(), SkillGroup.Base, translate(merkmal), skill1.getCategory());
		skill.setLevel(talent.getFertigkeitswert());
		return skill;
	}

	private Descriptor[] translate(MerkmalProfan merkmal) {
		switch (merkmal) {
			case KÖRPER:
				return new Descriptor[]{BaseDescriptors.Physical};
			case NATUR:
				return new Descriptor[]{BaseDescriptors.Nature};
			case GESELLSCHAFT:
				return new Descriptor[]{BaseDescriptors.Social};
			case WISSEN:
				return new Descriptor[]{BaseDescriptors.Knowledge};
			case HANDWERK:
				return new Descriptor[]{BaseDescriptors.Craft};
		}
		throw new IllegalArgumentException("Something went haywire");
	}

	public CombatTechnique translate(Kampftechnik kampftechnik) {
		String name = kampftechnik.getName();
		BaseAttribute baseAttribute = translate(kampftechnik.getLeiteigenschaft());
		ImprovementComplexity complexity = translate(kampftechnik.getSteigerungskosten());
		CombatTechnique combatTechnique = new CombatTechnique(name, baseAttribute, complexity);
		combatTechnique.setLevel(kampftechnik.getKampftechnikwert());
		return combatTechnique;
	}

	private ImprovementComplexity translate(Steigerungskategorie steigerungskosten) {
		switch (steigerungskosten) {
			case A:
				return ImprovementComplexity.A;
			case B:
				return ImprovementComplexity.B;
			case C:
				return ImprovementComplexity.C;
			case D:
				return ImprovementComplexity.D;
		}
		return null;
	}

	private BaseAttribute translate(Attributskürzel acronym) {
		switch (acronym) {
			case MU:
				return BaseAttribute.Courage;
			case KL:
				return BaseAttribute.Sagacity;
			case IN:
				return BaseAttribute.Intuition;
			case CH:
				return BaseAttribute.Charisma;
			case FF:
				return BaseAttribute.Dexterity;
			case GE:
				return BaseAttribute.Agility;
			case KO:
				return BaseAttribute.Constitution;
			case KK:
				return BaseAttribute.Strength;
		}
		return null;
	}

	public Language translate(Sprache languageXml) {
		Language language = new Language(languageXml.getName());
		language.setLevel(languageXml.getStufe());
		return language;
	}

	public SpecialAbility translate(Schrift script) {
		int complexity = script.getKomplexität()
							   .ordinal() + 1;
		return SpecialAbility.builder()
							 .name(script.getName())
							 .cost(complexity * 2)
							 .group(AbilityGroup.MUNDANE)
							 .build();
	}

	public SpecialAbility translate(Sonderfertigkeit sonderfertigkeit) {
		AbilityGroup abilityGroup = translateToAbilityGroup(sonderfertigkeit.getKategorie());
		return SpecialAbility.builder()
							 .name(sonderfertigkeit.getName())
							 .cost(sonderfertigkeit.getKosten())
							 .group(abilityGroup)
							 .build();
	}

	public AbilityGroup translateToAbilityGroup(String kategorie) {
		switch (kategorie) {
			case ("Kampf"):
				return AbilityGroup.COMBAT;
			case ("Allgemein"):
				return AbilityGroup.MUNDANE;
		}
		return AbilityGroup.valueOf(kategorie);
	}
}
