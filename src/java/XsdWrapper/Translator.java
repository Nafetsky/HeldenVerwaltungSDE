package XsdWrapper;

import api.AbilityGroup;
import api.Advantage;
import api.ISpecialAbility;
import api.history.AttributeChange;
import api.BaseAttribute;
import api.skills.BaseDescriptors;
import api.skills.BaseSkills;
import api.CombatTechnique;
import api.skills.Descriptor;
import api.Disadvantage;
import api.skills.ImprovementComplexity;
import api.Language;
import api.Race;
import api.Sex;
import api.skills.Skill;
import api.skills.SkillImpl;
import api.history.SkillChange;
import api.skills.SkillGroup;
import api.SpecialAbility;
import generated.Attributskürzel;
import generated.Basistalent;
import generated.Eigenschaftssteigerung;
import generated.Fertigkeit;
import generated.Fertigkeitskategorie;
import generated.Fertigkeitsmodifikation;
import generated.Geschlecht;
import generated.Kampftechnik;
import generated.MerkmalProfan;
import generated.Nachteil;
import generated.ObjectFactory;
import generated.Schrift;
import generated.Sonderfertigkeit;
import generated.Spezies;
import generated.Sprache;
import generated.Steigerungskategorie;
import generated.Vorteil;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * tested in EventParserTest
 */
public class Translator {

	private DescriptorTranslator descriptorTranslator = new DescriptorTranslator();
	private ObjectFactory factory = new ObjectFactory();

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
		return new DatabackedSkill(talent);
	}

	public Descriptor[] translate(MerkmalProfan merkmal) {
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

	public List<ISpecialAbility> translateToSpecialAbility(List<Sonderfertigkeit> sonderfertigkeiten) {
		return sonderfertigkeiten.stream()
				.map(this::translate)
				.collect(Collectors.toList());
	}

	public ISpecialAbility translate(Sonderfertigkeit sonderfertigkeit) {
		AbilityGroup abilityGroup = translateToAbilityGroup(sonderfertigkeit.getKategorie());
		return SpecialAbility.builder()
							 .name(sonderfertigkeit.getName())
							 .cost(sonderfertigkeit.getKosten())
							 .group(abilityGroup)
							 .build();
	}

	public AbilityGroup translateToAbilityGroup(String kategorie) {
		return SpecialAbilityKeys.parse(kategorie);
	}

	public Advantage translate(Vorteil advantage) {
		return new Advantage(advantage.getName(), advantage.getKosten());
	}

	public List<Advantage> translateAdvantages(List<Vorteil> vorteile) {
		if (null == vorteile) {
			return new ArrayList<>();
		}
		return vorteile
				.stream()
				.map(this::translate)
				.collect(Collectors.toList());
	}

	public Disadvantage translate(Nachteil disadvantage) {
		return new Disadvantage(disadvantage.getName(), disadvantage.getKosten());
	}

	public List<Disadvantage> translateDisadvantages(List<Nachteil> nachteil) {
		if (null == nachteil) {
			return new ArrayList<>();
		}
		return nachteil
				.stream()
				.map(this::translate)
				.collect(Collectors.toList());
	}

	public int translate(Integer input) {
		if (null == input) {
			return 0;
		}
		return input;
	}

	public List<AttributeChange> translateAttributeChanges(List<Eigenschaftssteigerung> eigenschaftssteigerung) {
		return eigenschaftssteigerung.stream()
									 .map(this::translate)
									 .collect(Collectors.toList());
	}

	private AttributeChange translate(Eigenschaftssteigerung eigenschaftssteigerung) {
		return AttributeChange.builder()
							  .attribute(translate(eigenschaftssteigerung.getEigenschaft()))
							  .change(eigenschaftssteigerung.getSteigerung())
							  .newValue(eigenschaftssteigerung.getNeuerWert())
							  .build();
	}

	public List<Skill> translateToNewSkills(List<Fertigkeitsmodifikation> fertigkeitsänderung){
		return fertigkeitsänderung.stream()
				.map(this::translateToNewSkill)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());

	}

	private Optional<Skill> translateToNewSkill(Fertigkeitsmodifikation fertigkeitsmodifikation) {
		Fertigkeit neueFertigkeit = fertigkeitsmodifikation.getNeueFertigkeit();
		if(null == neueFertigkeit){
			return Optional.empty();
		}
		return Optional.of(translate(neueFertigkeit));
	}

	private Skill translate(Fertigkeit fertigkeit) {
		String name = fertigkeit.getName();
		SkillGroup skillGroup = translate(fertigkeit.getKategorie());
		BaseAttribute firstAttribute = translate(fertigkeit.getAttribut1());
		BaseAttribute secondAttribute = translate(fertigkeit.getAttribut2());
		BaseAttribute thirdAttribute = translate(fertigkeit.getAttribut3());
		BaseAttribute[] attributes = {firstAttribute, secondAttribute, thirdAttribute};
		List<String> merkmals = fertigkeit.getMerkmal();
		Descriptor[] descriptors = descriptorTranslator.translateToDescriptors(merkmals);

		ImprovementComplexity complexity = translate(fertigkeit.getSteigerungskosten());
		return new SkillImpl(name, skillGroup, attributes, descriptors, complexity);

	}

	private SkillGroup translate(Fertigkeitskategorie kategorie) {
		switch(kategorie){
			case ZAUBER:
				return SkillGroup.Spell;
			case RITUAL:
				return SkillGroup.Ritual;
			case LITURGIE:
				return SkillGroup.LiturgicalChant;
			case ZEREMONIE:
				return SkillGroup.Ceremony;
			case PROFAN:
				return SkillGroup.Base;
		}
		throw new IllegalArgumentException("The category " + kategorie.value() + " does not exist") ;
	}

	public List<SkillChange> translateSkillChanges(List<Fertigkeitsmodifikation> fertigkeitsänderung) {
		return fertigkeitsänderung.stream()
								  .map(this::translate)
								  .collect(Collectors.toList());

	}

	private SkillChange translate(Fertigkeitsmodifikation skill) {
		SkillChange skillChange = new SkillChange(skill.getName());
		skillChange.setIncrease(skill.getÄnderung());
		skillChange.setNewValue(skill.getNeuerWert());
		return skillChange;
	}

	public Fertigkeitsmodifikation translate(SkillChange skillChange) {
		Fertigkeitsmodifikation fertigkeitsmodifikation = factory.createFertigkeitsmodifikation();

		fertigkeitsmodifikation.setName(skillChange.getName());
		fertigkeitsmodifikation.setÄnderung(skillChange.getIncrease());
		fertigkeitsmodifikation.setNeuerWert(skillChange.getNewValue());

		return fertigkeitsmodifikation;
	}

	public XMLGregorianCalendar translate(LocalDateTime date) {
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());
		} catch (DatatypeConfigurationException e) {
			throw new UnsupportedOperationException("Can't parse " + date.toString());
		}
	}

	public Sonderfertigkeit translate(ISpecialAbility specialAbilityToParse) {
		AbilityGroup group = specialAbilityToParse.getGroup();
		switch(group){
			case COMBAT:
				return buildCombatSpecialAbility(specialAbilityToParse);
			case MUNDANE:
				return buildMundaneSpecialAbility(specialAbilityToParse);
		}
		return null;
	}

	private Sonderfertigkeit buildCombatSpecialAbility(ISpecialAbility specialAbilityToParse) {
		Sonderfertigkeit sonderfertigkeit = factory.createSonderfertigkeit();
		sonderfertigkeit.setName(specialAbilityToParse.getName());
		sonderfertigkeit.setKosten(specialAbilityToParse.getCost());
		sonderfertigkeit.setKategorie(SpecialAbilityKeys.COMBAT.getName());
		return sonderfertigkeit;
	}

	private Sonderfertigkeit buildMundaneSpecialAbility(ISpecialAbility specialAbilityToParse) {
		Sonderfertigkeit sonderfertigkeit = factory.createSonderfertigkeit();
		sonderfertigkeit.setName(specialAbilityToParse.getName());
		sonderfertigkeit.setKosten(specialAbilityToParse.getCost());
		sonderfertigkeit.setKategorie(SpecialAbilityKeys.MUNDANE.getName());
		return sonderfertigkeit;
	}
}
