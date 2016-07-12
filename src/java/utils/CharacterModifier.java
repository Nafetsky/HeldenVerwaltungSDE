package utils;

import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;

import dataBase.SpecialSkillGroup;
import generated.Charakter;
import generated.Ereignis;
import generated.Fertigkeit;
import generated.Fertigkeitsmodifikation;
import generated.Nachteil;
import generated.ObjectFactory;
import generated.Sonderfertigkeit;
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
		clearChanges();
	}
	
	public void changeApBy(int apDifference){
		charakter.setAP(charakter.getAP()+apDifference);
		changes.setAP(apDifference);
		changes.setNeueAP(charakter.getAP());
	}

	public void addAdvantage(String name, int cost) {
		Vorteil advantage = factory.createVorteil();
		advantage.setName(name);
		advantage.setKosten(cost);
		changes.getVorteil().add(advantage);
		charakter.getVorteile().getVorteil().add(advantage);
	}

	public void addDisadvantage(String name, int cost) {
		Nachteil disadvantage = factory.createNachteil();
		disadvantage.setName(name);
		disadvantage.setKosten(cost);
		changes.getNachteil().add(disadvantage);
		charakter.getNachteile().getNachteil().add(disadvantage);
	}

	public int getCostForSkillIncreasment(String name) {
		Skill skillable = finder.findSkill(name);
		return skillable.getCostForNextLevel();
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
	
	public void addFeat(Sonderfertigkeit feat){
		List<Sonderfertigkeit> list = charakter.getSonderfertigkeiten().getSonderfertigkeit();
		for(Sonderfertigkeit oldFeat:list){
			if(StringUtils.equals(oldFeat.getName(),feat.getName())){
				return;
			}
		}
		list.add(feat);
		changes.getSonderfertigkeitshinzugewinn().add(feat);
	}

	private boolean handleExistingChange(Skill skill) {
		switch (skill.getGroup()) {
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
		}
		return false;
	}

	private void handleAsNewChange(Skill skill) {
		Fertigkeitsmodifikation change = makeNewSkillChangeEntry(skill, null);
		switch (skill.getGroup()) {
		case BASE:
		case SPELL:
		case RITUAL:
		case LITURGY:
		case ZEREMONY:
			changes.getFertigkeitsänderung().add(change);
			break;
		case COMBAT:
			changes.getKampftechnikänderung().add(change);
			break;
		}
	}

	private Fertigkeitsmodifikation makeNewSkillChangeEntry(Skill skill, Fertigkeit newSkill) {
		Fertigkeitsmodifikation change = factory.createFertigkeitsmodifikation();
		change.setName(skill.getName());
		change.setÄnderung(1);
		change.setNeuerWert(skill.getCurrentValue());
		change.setNeueFertigkeit(newSkill);
		return change;
	}

}
