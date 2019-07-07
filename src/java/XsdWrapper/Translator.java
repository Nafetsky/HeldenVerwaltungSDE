package XsdWrapper;

import api.AbilityGroup;
import api.Advantage;
import api.ISpecialAbility;
import api.history.AttributeChange;
import api.BaseAttribute;
import api.skills.BaseDescriptors;
import api.CombatTechnique;
import api.skills.Descriptor;
import api.Disadvantage;
import api.skills.ImprovementComplexity;
import api.Language;
import api.Race;
import api.Sex;
import api.skills.Skill;
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
import java.util.Arrays;
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

	public ImprovementComplexity translate(Steigerungskategorie steigerungskosten) {
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

	public BaseAttribute translate(Attributskürzel acronym) {
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

	public Attributskürzel translate(BaseAttribute attribute) {
		switch (attribute) {
			case Courage:
				return Attributskürzel.MU;
			case Sagacity:
				return Attributskürzel.KL;
			case Intuition:
				return Attributskürzel.IN;
			case Charisma:
				return Attributskürzel.CH;
			case Dexterity:
				return Attributskürzel.FF;
			case Agility:
				return Attributskürzel.GE;
			case Constitution:
				return Attributskürzel.KO;
			case Strength:
				return Attributskürzel.KK;
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

	public Skill translate(Basistalent talent) {
		return new DatabackedBaseSkill(talent);
	}

	public Skill translate(Fertigkeit fertigkeit) {
		return new DatabackedSkill(fertigkeit);
	}

	public SkillGroup translate(Fertigkeitskategorie kategorie) {
		switch(kategorie){
			case ZAUBER:
				return SkillGroup.SPELL;
			case RITUAL:
				return SkillGroup.RITUAL;
			case LITURGIE:
				return SkillGroup.LITURGICAL_CHANT;
			case ZEREMONIE:
				return SkillGroup.CEREMONY;
			case PROFAN:
				return SkillGroup.BASE;
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

	public Fertigkeit translate(Skill learnedSkill) {
		Fertigkeit fertigkeit = factory.createFertigkeit();
		fertigkeit.setName(learnedSkill.getName());
		BaseAttribute[] baseAttributes = learnedSkill.getAttributes()
													 .orElseThrow(IllegalStateException::new);
		fertigkeit.setAttribut1(translate(baseAttributes[0]));
		fertigkeit.setAttribut2(translate(baseAttributes[1]));
		fertigkeit.setAttribut3(translate(baseAttributes[2]));
		fertigkeit.setKategorie(translate(learnedSkill.getGroup()));
		fertigkeit.setSteigerungskosten(translate(learnedSkill.getComplexity()));
		DescriptorTranslator descriptorTranslator = new DescriptorTranslator();
		List<String> collect = Arrays.stream(learnedSkill.getDescriptors())
									 .map(descriptorTranslator::translate)
									 .collect(Collectors.toList());
		fertigkeit.getMerkmal().addAll(collect);

		return fertigkeit;
	}

	private Fertigkeitskategorie translate(SkillGroup group) {
		switch (group){
			case SPELL:
				return Fertigkeitskategorie.ZAUBER;
			case RITUAL:
				return Fertigkeitskategorie.RITUAL;
			case LITURGICAL_CHANT:
				return Fertigkeitskategorie.LITURGIE;
		}
		throw new UnsupportedOperationException("Translation not yet implemented");
	}

	private Steigerungskategorie translate(ImprovementComplexity complexity) {
		switch (complexity){
			case A:
				return Steigerungskategorie.A;
			case B:
				return Steigerungskategorie.B;
			case C:
				return Steigerungskategorie.C;
			case D:
				return Steigerungskategorie.D;
			case Attribute:
		}
		throw new IllegalStateException("Something went haywire while parsing,...");
	}
}
