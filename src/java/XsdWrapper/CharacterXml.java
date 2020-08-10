package XsdWrapper;

import api.AbilityGroup;
import api.Advantage;
import api.BaseAttribute;
import api.BaseValueChanges;
import api.CombatTechnique;
import api.Disadvantage;
import api.Event;
import api.IAttributes;
import api.ILanguage;
import api.IMetaData;
import api.ISpecialAbility;
import api.Language;
import api.Vantage;
import api.base.Character;
import api.history.AttributeChange;
import api.history.SkillChange;
import api.skills.Increasable;
import api.skills.Skill;
import api.skills.SkillGroup;
import api.skills.SkillLevler;
import data.Attributes;
import data.Metadata;
import generated.Charakter;
import generated.Eigenschaftswerte;
import generated.Fertigkeit;
import generated.Nachteil;
import generated.Schrift;
import generated.Sonderfertigkeit;
import generated.Sprache;
import generated.Talentspezialisierung;
import generated.Vorteil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import utility.CostCalculator;

import javax.xml.bind.JAXBException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
public class CharacterXml implements Character {

	private final Charakter wrapped;
	private final Translator translator = new Translator();
	private Event currentChanges = Event.builder()
										.build();


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
		CostCalculator calculator = new CostCalculator(this);
		return calculator.calcUsedAP();
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
	public List<Vantage> getAdvantages() {
		return wrapped.getVorteile()
					  .getVorteil()
					  .stream()
					  .map(advantage -> new Advantage(advantage.getName(), advantage.getKosten()))
					  .collect(Collectors.toList());
	}

	@Override
	public List<Vantage> getDisadvantages() {
		return wrapped.getNachteile()
					  .getNachteil()
					  .stream()
					  .map(disadvantage -> new Disadvantage(disadvantage.getName(), disadvantage.getKosten()))
					  .collect(Collectors.toList());
	}

	@Override
	public int getBonusLifePoints() {
		return Optional.ofNullable(wrapped.getLeP()).orElse(0);
	}

	@Override
	public int getBonusArcaneEnergy() {
		return Optional.ofNullable(wrapped.getAsP()).orElse(0);
	}

	@Override
	public int getLostArcaneEnergy() {
		return 0;
	}

	@Override
	public int getBonusKarmaPoints() {
		return Optional.ofNullable(wrapped.getKaP()).orElse(0);
	}

	@Override
	public List<Skill> getSkills() {
		List<Skill> skills = wrapped.getTalente()
									.getTalent()
									.stream()
									.map(translator::translate)
									.collect(Collectors.toList());
		skills.addAll(wrapped.getZauber()
							 .getZauber()
							 .stream()
							 .map(translator::translate)
							 .collect(Collectors.toList()));
		skills.addAll(wrapped.getRituale()
							 .getRitual()
							 .stream()
							 .map(translator::translate)
							 .collect(Collectors.toList()));
		skills.addAll(wrapped.getLiturgien()
							 .getLiturgie()
							 .stream()
							 .map(translator::translate)
							 .collect(Collectors.toList()));
		skills.addAll(wrapped.getZeremonien()
							 .getZeremonie()
							 .stream()
							 .map(translator::translate)
							 .collect(Collectors.toList()));

		return skills;
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
		List<ISpecialAbility> scripts = getScriptures();

		specialAbilities.addAll(scripts);
		List<ILanguage> languages = getLanguages();
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
	public List<ILanguage> getLanguages() {
		return wrapped.getKommunikatives()
					  .getSprachen()
					  .stream()
					  .map(translator::translate)
					  .collect(Collectors.toList());
	}

	@Override
	public List<ISpecialAbility> getScriptures() {
		return wrapped.getKommunikatives()
					  .getSchriften()
					  .stream()
					  .map(translator::translate)
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

	@Override
	public void increase(Event event) {
		increaseAbilities(event);
		getNewVantages(event);
		applyBaseChanges(event);
		learnNewSkills(event);
		learnNewCombatTechniques(event);
		applySkillChanges(event);
		learnNewSpecialAbilities(event);

	}

	private void applyBaseChanges(Event event) {
		BaseValueChanges baseValueChanges = event.getBaseValueChanges();
		int boughtHitPoints = baseValueChanges.getBoughtHitPoints();
		if (boughtHitPoints > 0) {
			wrapped.setLeP(wrapped.getLeP() == null ? boughtHitPoints : wrapped.getLeP() + boughtHitPoints);
		}
		int boughtAstralPoints = baseValueChanges.getBoughtAstralPoints();
		if (boughtAstralPoints > 0) {
			wrapped.setAsP(wrapped.getLeP() == null ? boughtAstralPoints : wrapped.getLeP() + boughtAstralPoints);
		}
		int boughtKarmaPoints = baseValueChanges.getBoughtKarmaPoints();
		if (boughtKarmaPoints > 0) {
			wrapped.setKaP(wrapped.getLeP() == null ? boughtKarmaPoints : wrapped.getLeP() + boughtKarmaPoints);
		}
		currentChanges.getBaseValueChanges()
					  .merge(baseValueChanges);
	}

	private void getNewVantages(Event event) {
		List<Vorteil> advantages = event.getAdvantages()
									 .stream()
									 .map(translator::translate)
									 .collect(Collectors.toList());
		wrapped.getVorteile()
			   .getVorteil()
			   .addAll(advantages);

		List<Nachteil> disadvantages = event.getDisadvantages()
									  .stream()
									  .map(translator::translate)
									  .collect(Collectors.toList());
		wrapped.getNachteile()
			   .getNachteil()
			   .addAll(disadvantages);


	}

	private void increaseAbilities(Event event) {
		event.getAttributeChanges()
			 .forEach(this::increaseAbility);
	}

	private void increaseAbility(AttributeChange increase) {
		SkillLevler skillLevler = getSkillLevler(increase.getAttribute());
		for (int i = 0; i < increase.getChange(); ++i) {
			skillLevler.level();
		}
	}

	private void learnNewSkills(Event event) {
		for (Skill learnedSkill : event.getLearnedSkills()) {
			Fertigkeit newSkill = translator.translate(learnedSkill);
			List<Fertigkeit> skillsToAddTo = findSkillsToAddTo(learnedSkill);
			skillsToAddTo.add(newSkill);
			currentChanges.getLearnedSkills()
						  .add(learnedSkill);
			findOrBuildSkillChange(learnedSkill.getName());
		}
	}

	private void learnNewCombatTechniques(Event event) {
		for (CombatTechnique combatTechnique : event.getLearnedCombatTechniques()) {
			wrapped.getKampftechniken()
				   .getKampftechnik()
				   .add(translator.translate(combatTechnique));
			currentChanges.getLearnedCombatTechniques()
						  .add(combatTechnique);
		}
	}

	private List<Fertigkeit> findSkillsToAddTo(Skill learnedSkill) {
		switch (learnedSkill.getGroup()) {
			case SPELL:
				return wrapped.getZauber()
							  .getZauber();
			case RITUAL:
				return wrapped.getRituale()
							  .getRitual();
			case LITURGICAL_CHANT:
				return wrapped.getLiturgien()
							  .getLiturgie();
			case CEREMONY:
				return wrapped.getZeremonien()
							  .getZeremonie();
		}
		throw new IllegalStateException("Something went haywire or is not yet supported. Trying to learn skill " + learnedSkill);
	}

	@Override
	public SkillLevler getSkillLevler(String name) {
		Optional<Skill> foundSkill = getSkills().stream()
												.filter(skill -> StringUtils.equals(skill.getName(), name))
												.findFirst();
		if (foundSkill.isPresent()) {
			return levelSkill(name, foundSkill.get());
		}

		CombatTechnique combatTechnique = getCombatTechniques().stream()
															   .filter(skill -> StringUtils.equals(skill.getName(), name))
															   .findFirst()
															   .orElseThrow(() -> new UnsupportedOperationException("The Character " + wrapped.getName() + " has no skill " + name));
		return levelSkill(name, combatTechnique);
	}

	@Override
	public SkillLevler getSkillLevler(BaseAttribute name) {
		return () -> {
			int currentValue = this.getAttributes()
								   .getValue(name);
			Eigenschaftswerte attributes = wrapped.getEigenschaftswerte();
			Optional<AttributeChange> first = currentChanges.getAttributeChanges()
															.stream()
															.filter(change -> change.getAttribute() == name)
															.findFirst();
			AttributeChange attributeChange;
			if (first.isPresent()) {
				attributeChange = first.get();
			} else {
				AttributeChange newAttributeChange = AttributeChange.builder()
																	.attribute(name)
																	.newValue(currentValue)
																	.change(0)
																	.build();
				currentChanges.getAttributeChanges()
							  .add(newAttributeChange);
				attributeChange = newAttributeChange;
			}
			switch (name) {
				case Courage:
					attributes.getMut()
							  .setAttributswert(currentValue + 1);
					break;
				case Sagacity:
					attributes.getKlugheit()
							  .setAttributswert(currentValue + 1);
					break;
				case Intuition:
					attributes.getIntuition()
							  .setAttributswert(currentValue + 1);
					break;
				case Charisma:
					attributes.getCharisma()
							  .setAttributswert(currentValue + 1);
					break;
				case Agility:
					attributes.getGewandheit()
							  .setAttributswert(currentValue + 1);
					break;
				case Dexterity:
					attributes.getFingerfertigkeit()
							  .setAttributswert(currentValue + 1);
					break;
				case Constitution:
					attributes.getKonstitution()
							  .setAttributswert(currentValue + 1);
					break;
				case Strength:
					attributes.getKörperkraft()
							  .setAttributswert(currentValue + 1);
					break;
			}
			attributeChange.setChange(attributeChange.getChange() + 1);
			attributeChange.setNewValue(currentValue + 1);
		};
	}


	private SkillLevler levelSkill(String name, Increasable foundSkill) {
		return () -> {
			int level = foundSkill.getLevel();
			foundSkill.setLevel(level + 1);


			SkillChange skillChange = findOrBuildSkillChange(name);
			skillChange.setIncrease(skillChange.getIncrease() + 1);
			skillChange.setNewValue(foundSkill.getLevel());
		};
	}


	private void learnNewSpecialAbilities(Event event) {
		currentChanges.getAbilities()
					  .addAll(event.getAbilities());
		List<Sonderfertigkeit> sonderfertigkeiten = event.getAbilities()
														 .stream()
														 .filter(sA -> sA.getGroup() != AbilityGroup.SPECIALISATION)
														 .filter(sA -> sA.getGroup() != AbilityGroup.SCRIPTURE)
														 .filter(sA -> sA.getGroup() != AbilityGroup.LANGUAGE)
														 .map(translator::translate)
														 .filter(Optional::isPresent)
														 .map(Optional::get)
														 .collect(Collectors.toList());
		wrapped.getSonderfertigkeiten()
			   .getSonderfertigkeit()
			   .addAll(sonderfertigkeiten);

		List<Talentspezialisierung> specs = event.getAbilities()
												 .stream()
												 .filter(sA -> sA.getGroup() == AbilityGroup.SPECIALISATION)
												 .map(translator::translateToSpecialisation)
												 .collect(Collectors.toList());
		wrapped.getSonderfertigkeiten()
			   .getTalentspezialisierung()
			   .addAll(specs);


		List<Sprache> languages = event.getAbilities()
									   .stream()
									   .filter(sA -> sA instanceof ILanguage)
									   .map(sA -> (ILanguage) sA)
									   .map(translator::translateLanguage)
									   .collect(Collectors.toList());
		increaseLanguages(languages);

		List<Schrift> scriptures = event.getAbilities()
										.stream()
										.filter(sA -> sA.getGroup() == AbilityGroup.SCRIPTURE)
										.map(translator::translateScripture)
										.collect(Collectors.toList());
		wrapped.getKommunikatives()
			   .getSchriften()
			   .addAll(scriptures);


	}

	private void increaseLanguages(List<Sprache> languagesToAdd) {
		List<Sprache> sprachen = wrapped.getKommunikatives()
										.getSprachen();
		for (Sprache languageToAdd : languagesToAdd) {
			Optional<Sprache> languageToIncrease = sprachen.stream()
														   .filter(l -> StringUtils.equals(l.getName(), languageToAdd.getName()))
														   .findFirst();
			if (languageToIncrease.isPresent()) {
				Sprache match = languageToIncrease.get();
				int value = match.getStufe() + languageToAdd.getStufe();
				match.setStufe(Math.min(value, 4));
			} else {
				sprachen.add(languageToAdd);
			}
		}
	}

	private SkillChange findOrBuildSkillChange(String name) {
		Optional<SkillChange> first = currentChanges.getSkillChanges()
													.stream()
													.filter(s -> StringUtils.equals(s.getName(), name))
													.findFirst();
		if (first.isPresent()) {
			return first.get();
		}

		SkillChange newChange = new SkillChange(name);
		currentChanges.getSkillChanges()
					  .add(newChange);
		return newChange;
	}

	private void applySkillChanges(Event event) {
		List<SkillChange> skillChanges = event.getSkillChanges();
		for (SkillChange skillChange : skillChanges) {
			SkillLevler skillLevler = getSkillLevler(skillChange.getName());
			for (int i = 0; i < skillChange.getIncrease(); i++) {
				skillLevler.level();
			}

		}
	}


	@Override
	public void save(String message) {
		EventParser parser = new EventParser();

		Event eventToSave = currentChanges.toBuilder()
										  .description(message)
										  .date(LocalDateTime.now())
										  .build();
		wrapped.getSteigerungshistorie()
			   .getEreignis()
			   .add(parser.parse(eventToSave));
		currentChanges = Event.builder()
							  .build();
	}

	@Override
	public String getContent() {
		try {
			MarshallingHelper helper = new MarshallingHelper();
			return helper.marshall(wrapped);
		} catch (JAXBException e) {
			LOGGER.error("could not marhsal", e);
			return "";
		}
	}
}
