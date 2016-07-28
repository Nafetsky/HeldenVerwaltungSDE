package utils;

import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;

import controle.AddSkillDialogResult;
import dataBase.Ability;
import dataBase.FeatGroup;
import dataBase.SpecialSkillGroup;
import generated.Charakter;
import generated.Eigenschaftssteigerung;
import generated.Ereignis;
import generated.Fertigkeit;
import generated.Fertigkeitskategorie;
import generated.Fertigkeitsmodifikation;
import generated.Nachteil;
import generated.ObjectFactory;
import generated.Sonderfertigkeit;
import generated.Steigerungskategorie;
import generated.Talentspezialisierung;
import generated.Vorteil;

public class CharacterModifier {
	Charakter charakter;
	ObjectFactory factory;
	Ereignis changes;
	SkillFinder finder;

	public CharacterModifier(Charakter charakter) {
		this.charakter = charakter;
		finder = new SkillFinder(this.charakter);
		factory = new ObjectFactory();
		clearChanges();
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
			e.printStackTrace();
			throw new UnsupportedOperationException("Something went totally wrong", e);
		}
	}

	public void saveChanges() {
		charakter.getSteigerungshistorie().getEreignis().add(changes);
		CharakterCleaner cleaner = new CharakterCleaner(charakter);
		cleaner.cleanUpCharakter();
		clearChanges();
	}

	public void changeApBy(int apDifference) {
		charakter.setAP(charakter.getAP() + apDifference);
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
		if(result == null){
			return null;
		}
		Fertigkeit skill = factory.createFertigkeit();
		
		skill.setName(result.name);
		
		skill.setAttribut1(result.abilitys[0]);
		skill.setAttribut2(result.abilitys[1]);
		skill.setAttribut3(result.abilitys[2]);
		
		skill.setFertigkeitswert(0);
		skill.setKategorie(Fertigkeitskategorie.fromValue(result.group.getName()));
		
		skill.setSteigerungskosten(Steigerungskategorie.fromValue(result.costCategory.name()));
		
		skill.getMerkmal().add(result.attributes[0]);
		skill.getMerkmal().add(result.attributes[1]);
		
		addSkill(skill);
		return new SkillSpecial(skill, SpecialSkillGroup.getFromFertigkeitskategorie(skill.getKategorie()));
	}

	public void addSkill(Fertigkeit fertigkeit) {
		Fertigkeitsmodifikation change = null;
		switch (fertigkeit.getKategorie()) {
		case PROFAN:
			throw new UnsupportedOperationException("Can't add BaseSKills to a charakter");
		case ZAUBER:
			charakter.getZauber().getZauber().add(fertigkeit);
			change = makeNewSkillChangeEntry(new SkillSpecial(fertigkeit, SpecialSkillGroup.SPELL), fertigkeit);
			break;
		case RITUAL:
			charakter.getRituale().getRitual().add(fertigkeit);
			change = makeNewSkillChangeEntry(new SkillSpecial(fertigkeit, SpecialSkillGroup.RITUAL), fertigkeit);
			break;
		case LITURGIE:
			charakter.getLiturgien().getLiturgie().add(fertigkeit);
			change = makeNewSkillChangeEntry(new SkillSpecial(fertigkeit, SpecialSkillGroup.LITURGY), fertigkeit);
			break;
		case ZEREMONIE:
			charakter.getZeremonien().getZeremonie().add(fertigkeit);
			change = makeNewSkillChangeEntry(new SkillSpecial(fertigkeit, SpecialSkillGroup.ZEREMONY), fertigkeit);
			break;
		}
		changes.getFertigkeitsänderung().add(change);
	}

	public void increaseSkillByOne(String name) {
		Skill skillable = finder.findSkill(name);
		if (skillable instanceof SkillBase && skillable.getCurrentValue() == 0) {
			charakter.getTalente().getTalent().add(((SkillBase) skillable).getBasistalent());
		}
		skillable.increaseByOne();
		addSkillChangeToHistory(skillable);
	}

	private void addSkillChangeToHistory(Skill skill) {

		boolean handledAsExistingChange = handleExistingChange(skill);

		if (!handledAsExistingChange) {
			handleAsNewChange(skill);
		}
	}

	public void addSkillSpecialisation(String name, String skillName){
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
	
	public Sonderfertigkeit addFeat(String name, int cost, FeatGroup group){
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
			List<Eigenschaftssteigerung> abilityChanges = changes.getEigenschaftssteigerung();
			for (Eigenschaftssteigerung newChange : abilityChanges) {
				if (StringUtils.equals(Ability.getAbility(newChange.getEigenschaft()).getName(), skill.getName())) {
					newChange.setSteigerung(newChange.getSteigerung() + 1);
					newChange.setNeuerWert(newChange.getNeuerWert() + 1);
					return true;
				}
			}
		case BASE:
		case SPELL:
		case RITUAL:
		case LITURGY:
		case ZEREMONY:
			List<Fertigkeitsmodifikation> skillChanges = changes.getFertigkeitsänderung();
			for (Fertigkeitsmodifikation newChange : skillChanges) {
				if (StringUtils.equals(newChange.getName(), skill.getName())) {
					newChange.setÄnderung(newChange.getÄnderung() + 1);
					newChange.setNeuerWert(newChange.getNeuerWert() + 1);
					return true;
				}
			}
		case COMBAT:
			List<Fertigkeitsmodifikation> comabtChanges = changes.getKampftechnikänderung();
			for (Fertigkeitsmodifikation newChange : comabtChanges) {
				if (StringUtils.equals(newChange.getName(), skill.getName())) {
					newChange.setÄnderung(newChange.getÄnderung() + 1);
					newChange.setNeuerWert(newChange.getNeuerWert() + 1);
					return true;
				}
			}
		default:
			break;
		}
		return false;
	}

	private void handleAsNewChange(Skill skill) {
		switch (skill.getGroup()) {
		case ABILITY:
			Eigenschaftssteigerung abilityChange = makeNewAbilityCahngeEntry((SkillAbility)skill);
			changes.getEigenschaftssteigerung().add(abilityChange);
			break;
		case BASE:
		case SPELL:
		case RITUAL:
		case LITURGY:
		case ZEREMONY:
			Fertigkeitsmodifikation change = makeNewSkillChangeEntry(skill, null);
			changes.getFertigkeitsänderung().add(change);
			break;
		case COMBAT:
			change = makeNewSkillChangeEntry(skill, null);
			changes.getKampftechnikänderung().add(change);
			break;
		default:
			break;
		}
	}

	private Eigenschaftssteigerung makeNewAbilityCahngeEntry(SkillAbility skill) {
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

	private int handleNullInteger(Integer reference, int modifier) {
		if (reference == null) {
			reference = 0;
		}
		return reference + modifier;
	}

}
