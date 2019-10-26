package XsdWrapper;

import api.AbilityGroup;
import api.Event;
import api.ISpecialAbility;
import api.Language;
import api.SpecialAbility;
import api.history.SkillChange;
import generated.Eigenschaftssteigerung;
import generated.Ereignis;
import generated.Fertigkeitsmodifikation;
import generated.Kommunikatives;
import generated.ObjectFactory;
import generated.Sonderfertigkeit;
import generated.Talentspezialisierung;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EventParser {

	private Translator translator = new Translator();

	public Event parse(Ereignis ereignis) {
		return Event.builder()
					.date(parseDate(ereignis))
					.description(ereignis.getGrund())
					.adventurePoints(translator.translate(ereignis.getAP()))
					.baseValueChanges(translator.extractBaseValueChanges(ereignis))
					.advantages(translator.translateAdvantages(ereignis.getVorteil()))
					.disadvantages(translator.translateDisadvantages(ereignis.getNachteil()))
					.attributeChanges(translator.translateAttributeChanges(ereignis.getEigenschaftssteigerung()))
					.learnedSkills(translator.translateToNewSkills(ereignis.getFertigkeitsänderung()))
					.learnedCombatTechniques(translator.translateToNewCombatTechniques(ereignis.getFertigkeitsänderung()))
					.skillChanges(collectAllSkillChanges(ereignis))
					.abilities(collectAllLearnedSpecialAbilities(ereignis))
					.build();
	}

	private List<SkillChange> collectAllSkillChanges(Ereignis ereignis) {
		List<SkillChange> skillChanges = translator.translateSkillChanges(ereignis.getFertigkeitsänderung());
		List<SkillChange> combatTechniqueChanges = translator.translateSkillChanges(ereignis.getKampftechnikänderung());
		skillChanges.addAll(combatTechniqueChanges);

		return skillChanges;
	}

	private List<ISpecialAbility> collectAllLearnedSpecialAbilities(Ereignis ereignis) {
		List<ISpecialAbility> specialAbilities = translator.translateToSpecialAbility(ereignis.getSonderfertigkeitshinzugewinn());

		List<ISpecialAbility> specialisations = translator.translate(ereignis.getTalentspezialisierungshinzugewinn());
		specialAbilities.addAll(specialisations);


		Kommunikatives kommunikatives = ereignis.getKommunikatives();
		if (null == kommunikatives) {
			return specialAbilities;
		}
		List<Language> languages = kommunikatives.getSprachen()
												 .stream()
												 .map(translator::translate)
												 .collect(Collectors.toList());
		specialAbilities.addAll(languages);
		List<SpecialAbility> scriptures = kommunikatives.getSchriften()
														.stream()
														.map(translator::translate)
														.collect(Collectors.toList());
		specialAbilities.addAll(scriptures);
		return specialAbilities;
	}

	private LocalDateTime parseDate(Ereignis ereignis) {
		XMLGregorianCalendar datum = ereignis.getDatum();
		int hour = Math.max(datum.getHour(), 0);
		int minute = Math.max(datum.getMinute(), 0);
		return LocalDateTime.of(datum.getYear(), datum.getMonth(), datum.getDay(), hour, minute);
	}

	public Ereignis parse(Event eventToSave) {
		ObjectFactory factory = new ObjectFactory();

		Ereignis ereignis = factory.createEreignis();
		ereignis.setGrund(eventToSave.getDescription());
		ereignis.setDatum(translator.translate(eventToSave.getDate()));

		List<Eigenschaftssteigerung> abilityChanges = eventToSave.getAttributeChanges()
																 .stream()
																 .map(translator::translate)
																 .collect(Collectors.toList());
		insertBaseChanges(eventToSave, ereignis);
		ereignis.getEigenschaftssteigerung()
				.addAll(abilityChanges);
		List<Fertigkeitsmodifikation> skillChangesWithNewSkills = buildSkillChangesIncludingNewSkills(eventToSave);
		ereignis.getFertigkeitsänderung()
				.addAll(skillChangesWithNewSkills);

		Collection<Sonderfertigkeit> sonderfertigkeiten = eventToSave.getAbilities()
																	 .stream()
																	 .filter(Objects::nonNull)
																	 .map(translator::translate)
																	 .collect(Collectors.toList());
		ereignis.getSonderfertigkeitshinzugewinn()
				.addAll(sonderfertigkeiten);
		List<Talentspezialisierung> specialisations = eventToSave.getAbilities()
																 .stream()
																 .filter(Objects::nonNull)
																 .filter(feat -> feat.getGroup() == AbilityGroup.SPECIALISATION)
																 .map(translator::translateToSpecialisation)
																 .collect(Collectors.toList());
		ereignis.getTalentspezialisierungshinzugewinn()
				.addAll(specialisations);

		return ereignis;

	}

	private void insertBaseChanges(Event eventToSave, Ereignis ereignis) {
		ereignis.setLePGekauft(eventToSave.getBaseValueChanges()
										  .getBoughtHitPoints());
		ereignis.setAsPGekauft(eventToSave.getBaseValueChanges()
										  .getBoughtAstralPoints());
		ereignis.setKaPGekauft(eventToSave.getBaseValueChanges()
										  .getBoughtKarmaPoints());
	}

	private List<Fertigkeitsmodifikation> buildSkillChangesIncludingNewSkills(Event eventToSave) {
		List<Fertigkeitsmodifikation> skillChanges = eventToSave.getSkillChanges()
																.stream()
																.map(translator::translate)
																.collect(Collectors.toList());

		List<Fertigkeitsmodifikation> collect = eventToSave.getLearnedCombatTechniques()
														   .stream()
														   .map(translator::translate)
														   .map(translator::buildModificationFromCombatTechnique)
														   .collect(Collectors.toList());
		skillChanges.addAll(collect);


		for (Fertigkeitsmodifikation change : skillChanges) {
			eventToSave.getLearnedSkills()
					   .stream()
					   .filter(newSkill -> StringUtils.equals(newSkill.getName(), change.getName()))
					   .map(translator::translate)
					   .forEach(change::setNeueFertigkeit);
		}

		return skillChanges;


	}

}
