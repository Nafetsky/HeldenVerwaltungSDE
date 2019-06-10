package XsdWrapper;

import api.AbilityGroup;
import api.Advantage;
import api.Character;
import api.CombatTechnique;
import api.Disadvantage;
import api.Event;
import api.IAttributes;
import api.IMetaData;
import api.ISpecialAbility;
import api.Skill;
import api.SkillGroup;
import api.SpecialAbility;
import data.Attributes;
import data.Metadata;
import generated.Charakter;
import generated.Eigenschaftswerte;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CharacterXml implements Character {

	private final Charakter wrapped;
	private final Translator translator;


	@Override
	public IMetaData getMetaData() {
		return Metadata.builder()
					   .name(wrapped.getName())
					   .race(translator.translate(wrapped.getSpezies()))
					   .culture(wrapped.getKultur())
					   .profession(wrapped.getKultur())
					   .sex(translator.translate(wrapped.getGeschlecht()))
					   .age(10)
					   .birthday(LocalDate.of(1000, 1, 1))
					   .build();
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
						 .courage(eigenschaftswerte.getMut()
												   .getAttributswert())
						 .sagacity(eigenschaftswerte.getKlugheit()
													.getAttributswert())
						 .intuition(eigenschaftswerte.getIntuition()
													 .getAttributswert())
						 .charisma(eigenschaftswerte.getCharisma()
													.getAttributswert())
						 .dexterity(eigenschaftswerte.getFingerfertigkeit()
													 .getAttributswert())
						 .agility(eigenschaftswerte.getGewandheit()
												   .getAttributswert())
						 .constitution(eigenschaftswerte.getKonstitution()
														.getAttributswert())
						 .strength(eigenschaftswerte.getKörperkraft()
													.getAttributswert())
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
					  .map(translator::translate)
					  .collect(Collectors.toList());
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
					  .map(translator::translate)
					  .collect(Collectors.toList());
	}

	@Override
	public List<ISpecialAbility> getSpecialAbilities() {
		List<ISpecialAbility> specialAbilities = wrapped.getSonderfertigkeiten()
														.getSonderfertigkeit()
														.stream()
														.map(translator::translate)
														.collect(Collectors.toCollection(ArrayList::new));
		List<SpecialAbility> scripts = wrapped.getKommunikatives()
											  .getSchriften()
											  .stream()
											  .map(translator::translate)
											  .collect(Collectors.toList());
		specialAbilities.addAll(scripts);
		List<ISpecialAbility> languages = wrapped.getKommunikatives()
												 .getSprachen()
												 .stream()
												 .map(translator::translate)
												 .collect(Collectors.toList());
		specialAbilities.addAll(languages);
		SkillSpecialisationTranslator translator = new SkillSpecialisationTranslator(wrapped);
		List<ISpecialAbility> specialisations = translator.translate(wrapped.getSonderfertigkeiten()
																			.getTalentspezialisierung());
		specialAbilities.addAll(specialisations);


		return specialAbilities;
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
		EventParser parser = new EventParser();
		return wrapped.getSteigerungshistorie()
			   .getEreignis()
			   .stream()
			   .map(parser::parse)
			   .collect(Collectors.toList());
	}
}
