package XsdWrapper;

import api.AbilityGroup;
import api.Advantage;
import api.BaseAttribute;
import api.BaseDescriptors;
import api.BaseSkills;
import api.Character;
import api.CombatTechnique;
import api.Descriptor;
import api.Disadvantage;
import api.Event;
import api.IAttributes;
import api.IMetaData;
import api.ISpecialAbility;
import api.ImprovementComplexity;
import api.Language;
import api.Race;
import api.Sex;
import api.Skill;
import api.SkillGroup;
import api.SpecialAbility;
import data.Attributes;
import data.Metadata;
import generated.Attributskürzel;
import generated.Basistalent;
import generated.Charakter;
import generated.Eigenschaftswerte;
import generated.Geschlecht;
import generated.Kampftechnik;
import generated.MerkmalProfan;
import generated.Schrift;
import generated.Sonderfertigkeit;
import generated.Spezies;
import generated.Sprache;
import generated.Steigerungskategorie;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CharacterXml implements Character {

	private final Charakter wrapped;


	@Override
	public IMetaData getMetaData() {
		return Metadata.builder()
					   .name(wrapped.getName())
					   .race(translate(wrapped.getSpezies()))
					   .culture(wrapped.getKultur())
					   .profession(wrapped.getKultur())
					   .sex(translate(wrapped.getGeschlecht()))
					   .age(10)
					   .birthday(LocalDate.of(1000, 1,1))
					   .build();
	}

	private Race translate(Spezies spezies) {
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

	private Sex translate(Geschlecht sex) {
		switch(sex){
			case WEIBLICH:
				return Sex.Female;
			case MÄNNLICH:
				return Sex.Male;
		}
		return Sex.Unspecified;
	}

	@Override
	public int getUsedAdventurePoints() {
		return 0;
	}

	@Override
	public int getAllAdventurePoints() {
		return wrapped.getAP();
	}

	@Override
	public int getFreeAdventurePoints() {
		return getAllAdventurePoints() - getUsedAdventurePoints();
	}

	@Override
	public IAttributes getAttributes() {
		Eigenschaftswerte eigenschaftswerte = wrapped.getEigenschaftswerte();
		return Attributes.builder()
				  .courage(eigenschaftswerte.getMut().getAttributswert())
				  .sagacity(eigenschaftswerte.getKlugheit().getAttributswert())
				  .intuition(eigenschaftswerte.getIntuition().getAttributswert())
				  .charisma(eigenschaftswerte.getCharisma().getAttributswert())
				  .dexterity(eigenschaftswerte.getFingerfertigkeit().getAttributswert())
				  .agility(eigenschaftswerte.getGewandheit().getAttributswert())
				  .constitution(eigenschaftswerte.getKonstitution().getAttributswert())
				  .strength(eigenschaftswerte.getKörperkraft().getAttributswert())
				  .build();
	}

	@Override
	public List<Advantage> getAdvantages() {
		return wrapped.getVorteile()
					  .getVorteil()
					  .stream()
					  .map(advantage -> new Advantage(advantage.getName(), advantage.getKosten()))
					  .collect(Collectors.toList());
	}

	@Override
	public List<Disadvantage> getDisadvantages() {
		return wrapped.getNachteile()
					  .getNachteil()
					  .stream()
					  .map(disadvantage -> new Disadvantage(disadvantage.getName(), disadvantage.getKosten()))
					  .collect(Collectors.toList());
	}

	@Override
	public int getBonusLifePoints() {
		return wrapped.getLeP();
	}

	@Override
	public int getBonusArcaneEnergy() {
		return wrapped.getAsP();
	}

	@Override
	public int getLostArcaneEnergy() {
		return 0;
	}

	@Override
	public int getBonusKarmaPoints() {
		return wrapped.getKaP();
	}

	@Override
	public List<Skill> getSkills() {
		return wrapped.getTalente()
			   .getTalent()
			   .stream()
			   .map(this::translate)
			   .collect(Collectors.toList());
	}

	private Skill translate(Basistalent talent) {
		MerkmalProfan merkmal = talent.getMerkmal();
		BaseSkills skill1 = BaseSkills.getSkill(talent.getName());
		Skill skill = new Skill(talent.getName(), SkillGroup.Base, translate(merkmal), skill1.getCategory());
		skill.setLevel(talent.getFertigkeitswert());
		return skill;
	}

	private Descriptor[] translate(MerkmalProfan merkmal) {
		switch(merkmal){
			case KÖRPER:
				return new Descriptor[]{BaseDescriptors.Physical};
			case NATUR:
				return  new Descriptor[]{BaseDescriptors.Nature};
			case GESELLSCHAFT:
				return  new Descriptor[]{BaseDescriptors.Social};
			case WISSEN:
				return  new Descriptor[]{BaseDescriptors.Knowledge};
			case HANDWERK:
				return  new Descriptor[]{BaseDescriptors.Craft};
		}
		throw new IllegalArgumentException("Something went haywire");


	}

	@Override
	public List<Skill> getSkills(SkillGroup group) {
		return this.getSkills()
			.stream()
			.filter(skill -> skill.getGroup() == group)
			.collect(Collectors.toList());
	}

	@Override
	public List<CombatTechnique> getCombatTechniques() {
		return wrapped.getKampftechniken()
			   .getKampftechnik()
			   .stream()
			   .map(this::translate)
			   .collect(Collectors.toList());
	}

	private CombatTechnique translate(Kampftechnik kampftechnik) {
		String name = kampftechnik.getName();
		BaseAttribute baseAttribute = translate(kampftechnik.getLeiteigenschaft());
		ImprovementComplexity complexity = translate(kampftechnik.getSteigerungskosten());
		CombatTechnique combatTechnique = new CombatTechnique(name, baseAttribute, complexity);
		combatTechnique.setLevel(kampftechnik.getKampftechnikwert());
		return combatTechnique;
	}

	private ImprovementComplexity translate(Steigerungskategorie steigerungskosten) {
		switch(steigerungskosten){
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

	private BaseAttribute translate(Attributskürzel acronym){
		switch (acronym){
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

	@Override
	public List<ISpecialAbility> getSpecialAbilities() {
		ArrayList<ISpecialAbility> specialAbilities = wrapped.getSonderfertigkeiten()
															.getSonderfertigkeit()
															.stream()
															.map(this::translate)
															.collect(Collectors.toCollection(ArrayList::new));
		List<SpecialAbility> scripts = wrapped.getKommunikatives()
											  .getSchriften()
											  .stream()
											  .map(this::translate)
											  .collect(Collectors.toList());
		specialAbilities.addAll(scripts);
		List<ISpecialAbility> languages = wrapped.getKommunikatives()
											  .getSprachen()
											  .stream()
											  .map(this::translate)
											  .collect(Collectors.toList());
		specialAbilities.addAll(languages);
		SkillSpecialisationTranslator translator = new SkillSpecialisationTranslator(wrapped);
		List<ISpecialAbility> specialisations = translator.translate(wrapped.getSonderfertigkeiten().getTalentspezialisierung());
		specialAbilities.addAll(specialisations);


		return specialAbilities;
	}

	private Language translate(Sprache languageXml) {
		Language language = new Language(languageXml.getName());
		language.setLevel(languageXml.getStufe());
		return language;
	}

	private SpecialAbility translate(Schrift script) {
		int complexity = script.getKomplexität().ordinal() + 1;
		return SpecialAbility.builder()
				.name(script.getName())
				.cost(complexity*2)
				.group(AbilityGroup.MUNDANE)
				.build();
	}

	private SpecialAbility translate(Sonderfertigkeit sonderfertigkeit) {
		AbilityGroup abilityGroup = translateToAbilityGroup(sonderfertigkeit.getKategorie());
		return SpecialAbility.builder()
				.name(sonderfertigkeit.getName())
				.cost(sonderfertigkeit.getKosten())
				.group(abilityGroup)
				.build();
	}

	private AbilityGroup translateToAbilityGroup(String kategorie) {
		switch(kategorie){
			case ("Kampf"):
				return AbilityGroup.COMBAT;
			case ("Allgemein"):
				return AbilityGroup.MUNDANE;
		}
		return AbilityGroup.valueOf(kategorie);
	}

	@Override
	public List<ISpecialAbility> getSpecialAbilities(AbilityGroup group) {
		return this.getSpecialAbilities()
			.stream()
			.filter(ability -> ability.getGroup() == group)
			.collect(Collectors.toList());
	}

	@Override
	public List<Event> getHistory() {
		return null;
	}
}
