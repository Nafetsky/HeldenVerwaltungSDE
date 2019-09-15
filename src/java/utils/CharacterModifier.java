package utils;

import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import controle.AddNewCombatSkillDialogResult;
import controle.AddSkillDialogResult;
import database.Ability;
import database.FeatGroup;
import database.SpecialSkillGroup;
import generated.Attribut;
import generated.Basistalent;
import generated.Eigenschaftssteigerung;
import generated.Eigenschaftswerte;
import generated.Ereignis;
import generated.Fertigkeit;
import generated.Fertigkeitskategorie;
import generated.Fertigkeitsmodifikation;
import generated.Kampftechnik;
import generated.Kommunikatives;
import generated.Nachteil;
import generated.ObjectFactory;
import generated.Schrift;
import generated.Sonderfertigkeit;
import generated.Sprache;
import generated.Steigerungskategorie;
import generated.Talentspezialisierung;
import generated.Vorteil;

@Log4j2
public class CharacterModifier {


	private WrappedCharakter charakter;
	private ObjectFactory factory;
	private Ereignis changes;
	private SkillFinder finder;

	public CharacterModifier(WrappedCharakter charakter) {
		this.charakter = charakter;
		finder = new SkillFinder(this.charakter);
		factory = new ObjectFactory();
		clearChanges();
	}

	/**
	 * this function is ONLY meant to be used while charakter creation, it does
	 * not add to the history
	 * 
	 * @param template
	 * @return true if applying was successful
	 */
	public boolean applyTemplate(WrappedCharakter template) {
		if (!charakter.isCharakter()) {
			throw new UnsupportedOperationException("Can't apply templates to non charakters");
		}
		if (null == template) {
			return true;
		}
		if (!(template.isCulture() || template.isProfession())) {
			throw new UnsupportedOperationException("Can't apply a charakter to a charakters");
		}

		for (Basistalent skill : template.getTalente().getTalent()) {
			for (int i = 0; i < skill.getFertigkeitswert(); ++i) {
				increaseSkillByOne(skill.getName());
			}
		}
		if (template.isProfession()) {
			applyProfessionTemplate(template);
		}

		clearChanges();
		if (template.isCulture()) {
			charakter.charakter.setKultur(template.getName());
		}
		if (template.isProfession()) {
			// sets name of charakters profession
		}

		return true;
	}

	private void applyProfessionTemplate(WrappedCharakter template) {
		setAbilitysToMinimumAbilitys(template.getEigenschaftswerte());
		for (Kampftechnik combatSkill : template.getKampftechniken().getKampftechnik()) {
			addCombatSkill(combatSkill);
		}
		addSkills(template.getZauber().getZauber());
		addSkills(template.getRituale().getRitual());
		addSkills(template.getLiturgien().getLiturgie());
		addSkills(template.getZeremonien().getZeremonie());

		for (Sonderfertigkeit feat : template.getSonderfertigkeiten().getSonderfertigkeit()) {
			addFeat(feat);
		}
		for (Talentspezialisierung skillSpecialisation : template.getSonderfertigkeiten().getTalentspezialisierung()) {
			addSkillSpecialisation(skillSpecialisation.getName(), skillSpecialisation.getFertigkeit());
		}
		for (Vorteil advantage : template.getVorteile().getVorteil()) {
			addAdvantage(advantage.getName(), advantage.getKosten());
		}
	}

	private void addSkills(List<Fertigkeit> skills) {
		for (Fertigkeit skill : skills) {
			addSkill(skill);
		}
	}

	private void setAbilitysToMinimumAbilitys(Eigenschaftswerte eigenschaftswerte) {
		Eigenschaftswerte abilitys = charakter.getEigenschaftswerte();
		setAbilityToMinimum(abilitys.getMut(), eigenschaftswerte.getMut().getAttributswert());
		setAbilityToMinimum(abilitys.getKlugheit(), eigenschaftswerte.getKlugheit().getAttributswert());
		setAbilityToMinimum(abilitys.getIntuition(), eigenschaftswerte.getIntuition().getAttributswert());
		setAbilityToMinimum(abilitys.getCharisma(), eigenschaftswerte.getCharisma().getAttributswert());
		setAbilityToMinimum(abilitys.getFingerfertigkeit(), eigenschaftswerte.getFingerfertigkeit().getAttributswert());
		setAbilityToMinimum(abilitys.getGewandheit(), eigenschaftswerte.getGewandheit().getAttributswert());
		setAbilityToMinimum(abilitys.getKonstitution(), eigenschaftswerte.getKonstitution().getAttributswert());
		setAbilityToMinimum(abilitys.getKörperkraft(), eigenschaftswerte.getKörperkraft().getAttributswert());
	}

	private void setAbilityToMinimum(Attribut ability, int minAbilityValue) {
		if (ability.getAttributswert() < minAbilityValue) {
			ability.setAttributswert(minAbilityValue);
		}
	}

	private void clearChanges() {
		changes = factory.createEreignis();
		changes.setDatum(getNow());
	}

	private XMLGregorianCalendar getNow() {
		GregorianCalendar calendar = new GregorianCalendar();
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (DatatypeConfigurationException e) {
			LOGGER.error("Something went wrong working with dates", e);
			throw new UnsupportedOperationException("Something went totally wrong", e);
		}
	}

	public void saveChanges(String reason) {
		changes.setGrund(reason);
		if (charakter.isCharakter()) {
			charakter.getSteigerungshistorie().getEreignis().add(changes);
		}
		CharakterCleaner cleaner = new CharakterCleaner(charakter);
		cleaner.cleanUpCharakter();
		clearChanges();
	}

	public void changeApBy(int apDifference) {
		if (charakter.isCharakter()) {
			charakter.setAP(charakter.getAP() + apDifference);
		}
		changes.setAP(apDifference);
		changes.setNeueAP(charakter.getAP());
	}

	public void buyLifePoint() {
		charakter.setLeP(handleNullInteger(charakter.getLeP(), 1));
		changes.setLePGekauft(handleNullInteger(changes.getLePGekauft(), 1));
	}

	public void buyAstralPoint() {
		charakter.setAsP(handleNullInteger(charakter.getAsP(), 1));
		changes.setAsPGekauft(handleNullInteger(changes.getAsPGekauft(), 1));
	}

	public void buyKarmaPoint() {
		charakter.setKaP(handleNullInteger(charakter.getKaP(), 1));
		changes.setKaPGekauft(handleNullInteger(changes.getKaPGekauft(), 1));
	}

	public void addAdvantage(String name, int cost) {
		Vorteil advantage = factory.createVorteil();
		advantage.setName(name);
		advantage.setKosten(cost);
		changes.getVorteil().add(advantage);
		charakter.getVorteile().getVorteil().add(advantage);
	}

	public void removeAdvantage(String name) {
		for (Vorteil advantage : charakter.getVorteile().getVorteil()) {
			if (StringUtils.equals(advantage.getName(), name)) {
				advantage.setKosten(advantage.getKosten() * -1);
				changes.getVorteil().add(advantage);
				charakter.getVorteile().getVorteil().remove(advantage);
				return;
			}
		}
	}

	public void addDisadvantage(String name, int cost) {
		Nachteil disadvantage = factory.createNachteil();
		disadvantage.setName(name);
		disadvantage.setKosten(cost);
		changes.getNachteil().add(disadvantage);
		charakter.getNachteile().getNachteil().add(disadvantage);
	}

	public void removeDisadvantage(String name) {
		for (Nachteil disadvantage : charakter.getNachteile().getNachteil()) {
			if (StringUtils.equals(disadvantage.getName(), name)) {
				disadvantage.setKosten(disadvantage.getKosten() * -1);
				changes.getNachteil().add(disadvantage);
				charakter.getNachteile().getNachteil().remove(disadvantage);
				return;
			}
		}
	}

	public int getCostForSkillIncreasment(String name) {
		Skill skillable = finder.findSkill(name);
		return skillable.getCostForNextLevel();
	}

	public Skill addSkill(AddSkillDialogResult result) {
		if (result == null) {
			return null;
		}
		Fertigkeit skill = factory.createFertigkeit();

		skill.setName(result.name);

		skill.setAttribut1(result.abilities[0]);
		skill.setAttribut2(result.abilities[1]);
		skill.setAttribut3(result.abilities[2]);

		skill.setFertigkeitswert(0);
		skill.setKategorie(Fertigkeitskategorie.fromValue(result.getGroup().getName()));

		skill.setSteigerungskosten(Steigerungskategorie.fromValue(result.costCategory.name()));

		skill.getMerkmal().add(result.attributes[0]);
		skill.getMerkmal().add(result.attributes[1]);

		addSkill(skill);
		return new SkillSpecial(skill, SpecialSkillGroup.getFromFertigkeitskategorie(skill.getKategorie()));
	}

	public Skill addSkill(AddNewCombatSkillDialogResult result) {
		if (result == null) {
			return null;
		}
		Kampftechnik combatSkill = factory.createKampftechnik();
		combatSkill.setName(result.name);
		combatSkill.setLeiteigenschaft(result.getAbility());
		combatSkill.setSteigerungskosten(Steigerungskategorie.fromValue(result.costCategory.name()));
		combatSkill.setKampftechnikwert(6);

		return addCombatSkill(combatSkill);
	}

	private Skill addCombatSkill(Kampftechnik skill) {
		Skill wrappedSkill = new SkillCombat(skill);
		Fertigkeitsmodifikation change = makeNewCombatSkillChangeEntry(wrappedSkill, skill);
		charakter.getKampftechniken().getKampftechnik().add(skill);
		changes.getFertigkeitsänderung().add(change);
		return wrappedSkill;

	}

	public void addSkill(Fertigkeit fertigkeit) {
		Fertigkeit skillCopy = makeCopy(fertigkeit);
		Fertigkeitsmodifikation change;
		switch (skillCopy.getKategorie()) {
		case PROFAN:
			throw new UnsupportedOperationException("Can't add BaseSKills to a charakter");
		case ZAUBER:
			charakter.getZauber().getZauber().add(skillCopy);
			change = makeNewSkillChangeEntry(new SkillSpecial(skillCopy, SpecialSkillGroup.SPELL), skillCopy);
			break;
		case RITUAL:
			charakter.getRituale().getRitual().add(skillCopy);
			change = makeNewSkillChangeEntry(new SkillSpecial(skillCopy, SpecialSkillGroup.RITUAL), skillCopy);
			break;
		case LITURGIE:
			charakter.getLiturgien().getLiturgie().add(skillCopy);
			change = makeNewSkillChangeEntry(new SkillSpecial(skillCopy, SpecialSkillGroup.LITURGY), skillCopy);
			break;
		case ZEREMONIE:
			charakter.getZeremonien().getZeremonie().add(skillCopy);
			change = makeNewSkillChangeEntry(new SkillSpecial(skillCopy, SpecialSkillGroup.ZEREMONY), skillCopy);
			break;
		default:
			throw new UnsupportedOperationException(
					skillCopy.getKategorie().toString() + " is not yet supported to be added");

		}
		changes.getFertigkeitsänderung().add(change);
	}

	private Fertigkeit makeCopy(Fertigkeit original) {
		Fertigkeit copy = factory.createFertigkeit();
		copy.setAttribut1(original.getAttribut1());
		copy.setAttribut2(original.getAttribut2());
		copy.setAttribut3(original.getAttribut3());
		copy.setFertigkeitswert(original.getFertigkeitswert());
		copy.setKategorie(original.getKategorie());
		copy.setName(original.getName());
		copy.setSteigerungskosten(original.getSteigerungskosten());
		copy.getMerkmal().addAll(original.getMerkmal());
		return copy;
	}

	public void increaseSkillByOne(String name) {
		Skill skillable = finder.findSkill(name);
		skillable.increaseByOne();
		addSkillChangeToHistory(skillable);
	}

	public void increaseLanguage(String language) {
		if (charakter.isCharakter() || charakter.isCulture()) {
			Kommunikatives comm = charakter.getKommunikatives();
			Sprache lang = getdLanguage(language, comm);

			int currentLevel = lang.getStufe();
			if (currentLevel < 4) {
				lang.setStufe(currentLevel + 1);
			} else {
				return;
			}

			Kommunikatives history = changes.getKommunikatives();
			if (history == null) {
				history = factory.createKommunikatives();
				changes.setKommunikatives(history);
			}
			Sprache languageInHistory = null;
			for (Sprache iteraor : history.getSprachen()) {
				if (StringUtils.equals(language, iteraor.getName())) {
					languageInHistory = iteraor;
					break;
				}
			}
			if (null == languageInHistory) {
				history.getSprachen().add(lang);
			} else {
				languageInHistory.setStufe(lang.getStufe());
			}

		}
	}

	private Sprache getdLanguage(String language, Kommunikatives comm) {
		Sprache lang = null;
		for (Sprache currentLang : comm.getSprachen()) {
			if (StringUtils.equals(currentLang.getName(), language)) {
				lang = currentLang;
				break;
			}
		}
		if (lang == null) {
			lang = factory.createSprache();
			lang.setName(language);
			lang.setStufe(0);
			comm.getSprachen().add(lang);
		}
		comm.getSprachen().stream().filter((Sprache currentLang) -> StringUtils.equals(currentLang.getName(), language))
				.findFirst();
		return lang;
	}

	private void addSkillChangeToHistory(Skill skill) {

		boolean handledAsExistingChange = handleExistingChange(skill);

		if (!handledAsExistingChange) {
			handleAsNewChange(skill);
		}
	}

	public void addSkillSpecialisation(String name, String skillName) {
		Talentspezialisierung feat = factory.createTalentspezialisierung();
		feat.setName(name);
		feat.setFertigkeit(skillName);
		List<Talentspezialisierung> list = charakter.getSonderfertigkeiten().getTalentspezialisierung();
		for (Talentspezialisierung oldFeat : list) {
			if (StringUtils.equals(oldFeat.getName(), feat.getName())) {
				return;
			}
		}
		list.add(feat);
		changes.getTalentspezialisierungshinzugewinn().add(feat);
	}

	public void addScript(String name, Steigerungskategorie costCategorie) {
		Schrift script = factory.createSchrift();
		script.setName(name);
		script.setKomplexität(costCategorie);
		addScript(script);
	}

	private void addScript(Schrift script) {
		List<Schrift> list = charakter.getKommunikatives().getSchriften();
		for (Schrift oldScript : list) {
			if (StringUtils.equals(script.getName(), oldScript.getName())) {
				return;
			}
		}
		list.add(script);
		if (null == changes.getKommunikatives()) {
			changes.setKommunikatives(factory.createKommunikatives());
		}
		changes.getKommunikatives().getSchriften().add(script);
	}

	public Sonderfertigkeit addFeat(String name, int cost, FeatGroup group) {
		Sonderfertigkeit feat = factory.createSonderfertigkeit();
		feat.setName(name);
		feat.setKosten(cost);
		feat.setKategorie(group.getName());
		addFeat(feat);
		return feat;
	}

	public void addFeat(Sonderfertigkeit feat) {
		List<Sonderfertigkeit> list = charakter.getSonderfertigkeiten().getSonderfertigkeit();
		for (Sonderfertigkeit oldFeat : list) {
			if (StringUtils.equals(oldFeat.getName(), feat.getName())) {
				return;
			}
		}
		list.add(feat);
		changes.getSonderfertigkeitshinzugewinn().add(feat);
	}

	private boolean handleExistingChange(Skill skill) {
		switch (skill.getGroup()) {
		case ABILITY:
			return handleExistingAbilityChange(skill);
		case BASE:
		case SPELL:
		case RITUAL:
		case LITURGY:
		case ZEREMONY:
			return handleExistingSkillChange(skill);
		case COMBAT:
			return handleExistingCombatChange(skill);
		default:
			break;
		}
		return false;
	}

	private boolean handleExistingAbilityChange(Skill skill) {
		List<Eigenschaftssteigerung> abilityChanges = changes.getEigenschaftssteigerung();
		for (Eigenschaftssteigerung newChange : abilityChanges) {
			if (StringUtils.equals(Ability.getAbility(newChange.getEigenschaft()).getName(), skill.getName())) {
				newChange.setSteigerung(newChange.getSteigerung() + 1);
				newChange.setNeuerWert(newChange.getNeuerWert() + 1);
				return true;
			}
		}
		return false;
	}

	private boolean handleExistingSkillChange(Skill skill) {
		List<Fertigkeitsmodifikation> skillChanges = changes.getFertigkeitsänderung();
		for (Fertigkeitsmodifikation newChange : skillChanges) {
			if (StringUtils.equals(newChange.getName(), skill.getName())) {
				newChange.setÄnderung(newChange.getÄnderung() + 1);
				newChange.setNeuerWert(newChange.getNeuerWert() + 1);
				return true;
			}
		}
		return false;
	}

	private boolean handleExistingCombatChange(Skill skill) {
		List<Fertigkeitsmodifikation> comabtChanges = changes.getKampftechnikänderung();
		for (Fertigkeitsmodifikation newChange : comabtChanges) {
			if (StringUtils.equals(newChange.getName(), skill.getName())) {
				newChange.setÄnderung(newChange.getÄnderung() + 1);
				newChange.setNeuerWert(newChange.getNeuerWert() + 1);
				return true;
			}
		}
		return false;
	}

	private void handleAsNewChange(Skill skill) {
		switch (skill.getGroup()) {
		case ABILITY:
			Eigenschaftssteigerung abilityChange = makeNewAbilityChangeEntry((SkillAbility) skill);
			changes.getEigenschaftssteigerung().add(abilityChange);
			break;
		case BASE:
		case SPELL:
		case RITUAL:
		case LITURGY:
		case ZEREMONY:
			Fertigkeitsmodifikation change = makeNewSkillChangeEntry(skill, (Fertigkeit) null);
			changes.getFertigkeitsänderung().add(change);
			break;
		case COMBAT:
			change = makeNewCombatSkillChangeEntry(skill, (Kampftechnik) null);
			changes.getKampftechnikänderung().add(change);
			break;
		default:
			break;
		}
	}

	private Eigenschaftssteigerung makeNewAbilityChangeEntry(SkillAbility skill) {
		Eigenschaftssteigerung change = factory.createEigenschaftssteigerung();
		change.setEigenschaft(skill.getAbility().getKürzel());
		change.setNeuerWert(skill.getCurrentValue());
		change.setSteigerung(1);
		return change;
	}

	private Fertigkeitsmodifikation makeNewSkillChangeEntry(Skill skill, Fertigkeit newSkill) {
		Fertigkeitsmodifikation change = factory.createFertigkeitsmodifikation();
		change.setName(skill.getName());
		change.setÄnderung(1);
		change.setNeuerWert(skill.getCurrentValue());
		change.setNeueFertigkeit(newSkill);
		return change;
	}

	private Fertigkeitsmodifikation makeNewCombatSkillChangeEntry(Skill skill, Kampftechnik newSkill) {
		Fertigkeitsmodifikation change = factory.createFertigkeitsmodifikation();
		change.setName(skill.getName());
		change.setÄnderung(null == newSkill ? 1 : 0);
		change.setNeuerWert(skill.getCurrentValue());
		change.setNeueKampftechnik(newSkill);
		return change;
	}

	private int handleNullInteger(Integer reference, int modifier) {
		int input;
		if (reference == null) {
			input = 0;
		} else {
			input = reference;
		}
		return input + modifier;
	}

}
