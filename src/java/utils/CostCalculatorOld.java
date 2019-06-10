package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.BaseSkills;
import database.CostCategory;
import generated.Basistalent;
import generated.Eigenschaftswerte;
import generated.Fertigkeit;
import generated.Kampftechnik;
import generated.Kampftechniken;
import generated.Kommunikatives;
import generated.Nachteil;
import generated.Nachteile;
import generated.Schrift;
import generated.Sonderfertigkeit;
import generated.Sonderfertigkeiten;
import generated.Sprache;
import generated.Talente;
import generated.Talentspezialisierung;
import generated.Vorteil;
import generated.Vorteile;

@Deprecated
public class CostCalculatorOld {

	private CostCalculatorOld() {
		// no op
	}

	public static int calcUsedAP(WrappedCharakter charakterW) {
		int usedAp = 0;
		if(charakterW.isCharakter()){
			usedAp += getAPOnlyRelevantOnCharakter(charakterW);
		}
		if(charakterW.isProfession()){
			usedAp += getAPOnlyRelevantOnProfession(charakterW);
		}
		usedAp += calcAllBaseAbilityCosts(charakterW.getEigenschaftswerte());
		usedAp += calcAdvantagesCosts(charakterW.getVorteile());
		usedAp += calcDisadvantagesCosts(charakterW.getNachteile());
		usedAp += calcKommunicatiesCosts(charakterW.getKommunikatives());
//		usedAp += calcAllBaseSkillCosts(charakterW.getTalente());
		usedAp += calcCombatSkillsCosts(charakterW.getKampftechniken());
		usedAp += calcAllSpecificSkillCosts(charakterW.getZauber().getZauber());
		usedAp += calcAllSpecificSkillCosts(charakterW.getRituale().getRitual());
		usedAp += calcAllSpecificSkillCosts(charakterW.getLiturgien().getLiturgie());
		usedAp += calcAllSpecificSkillCosts(charakterW.getZeremonien().getZeremonie());

		return usedAp;
	}

	private static int getAPOnlyRelevantOnCharakter(WrappedCharakter charakter) {
		int sum = charakter.charakter.getSpezies().getKosten();
		sum += calcFeatsCosts(charakter);
		return sum; 
	}
	private static int getAPOnlyRelevantOnProfession(WrappedCharakter charakter) {
		return calcFeatsCosts(charakter);
	}

	static int calcAllBaseAbilityCosts(Eigenschaftswerte values) {
		int abilityCosts = 0;
		abilityCosts += calcCostBaseAbility(values.getMut().getAttributswert());
		abilityCosts += calcCostBaseAbility(values.getKlugheit().getAttributswert());
		abilityCosts += calcCostBaseAbility(values.getIntuition().getAttributswert());
		abilityCosts += calcCostBaseAbility(values.getCharisma().getAttributswert());
		abilityCosts += calcCostBaseAbility(values.getFingerfertigkeit().getAttributswert());
		abilityCosts += calcCostBaseAbility(values.getGewandheit().getAttributswert());
		abilityCosts += calcCostBaseAbility(values.getKonstitution().getAttributswert());
		abilityCosts += calcCostBaseAbility(values.getKörperkraft().getAttributswert());
		return abilityCosts;
	}

	private static int calcAdvantagesCosts(Vorteile vorteile) {
		int sum = 0;
		for (Vorteil advant : vorteile.getVorteil()) {
			sum += advant.getKosten();
		}
		return sum;
	}

	private static int calcDisadvantagesCosts(Nachteile nachteile) {
		int sum = 0;
		for (Nachteil flaw : nachteile.getNachteil()) {
			sum += flaw.getKosten();
		}
		return -sum;
	}

	private static int calcKommunicatiesCosts(Kommunikatives kommunikatives) {
		int sum = 0;
		for (Sprache lang : kommunikatives.getSprachen()) {
			if (lang.getStufe() < 4) {
				sum += lang.getStufe() * 2;
			}
		}
		for (Schrift script : kommunikatives.getSchriften()) {
			sum += CostCategory.getCostCategory(script.getKomplexität()).getMultiplier() * 2;
		}

		return sum;
	}

//	static int calcAllBaseSkillCosts(Talente talente) {
//		int sumCosts = 0;
//		for (Basistalent talent : talente.getTalent()) {
//			BaseSkills skill = BaseSkills.getSkill(talent.getName());
//			sumCosts += calcCostSkill(talent.getFertigkeitswert(), skill.getCategory(), true);
//		}
//		return sumCosts;
//	}

	static int calcAllSpecificSkillCosts(List<Fertigkeit> specialSkills) {
		int sumCosts = 0;
		for (Fertigkeit special : specialSkills) {
			sumCosts += calcCostSkill(special.getFertigkeitswert(),
					CostCategory.getCostCategory(special.getSteigerungskosten()), false);
		}
		return sumCosts;
	}

	private static int calcFeatsCosts(WrappedCharakter charakter) {
		Sonderfertigkeiten sonderfertigkeiten = charakter.getSonderfertigkeiten();
		int sum = 0;
		for (Sonderfertigkeit feat : sonderfertigkeiten.getSonderfertigkeit()) {
			sum += feat.getKosten();
		}
		Map<String, Integer> specialsMap = new HashMap<>();
		SkillFinder finder = new SkillFinder(charakter);
		for (Talentspezialisierung special : sonderfertigkeiten.getTalentspezialisierung()) {
			int multiplier;
			if (specialsMap.containsKey(special.getFertigkeit())) {
				multiplier = specialsMap.get(special.getFertigkeit()) + 1;
			} else {
				multiplier = 1;
			}
			sum += multiplier * finder.findSkill(special.getFertigkeit()).getCostCategory().getMultiplier();
			specialsMap.put(special.getFertigkeit(), multiplier);
		}

		return sum;
	}

	private static int calcCombatSkillsCosts(Kampftechniken kampftechniken) {
		int sum = 0;
		for (Kampftechnik combatSkill : kampftechniken.getKampftechnik()) {
			sum += calcCostCombat(combatSkill.getKampftechnikwert(),
					CostCategory.getCostCategory(combatSkill.getSteigerungskosten()));
		}
		return sum;
	}

	private static int calculateIncreasedLevel(int level) {
		return (level * (level + 1)) / 2;
	}

	public static int calcCostSkill(int level, CostCategory cost, boolean baseSkill) {
		if (level < 0) {
			throw new ArithmeticException("Can't calculate costs for negative value");
		}
		int effectiveLevel = baseSkill ? 0 : 1;
		if (level < 13) {
			effectiveLevel += level;
		} else {
			effectiveLevel += 11;
			effectiveLevel += calculateIncreasedLevel(level - 11);
		}
		return effectiveLevel * cost.getMultiplier();
	}

	public static final int calcCostCombat(int level, CostCategory cost) {
		if (level < 6) {
			throw new ArithmeticException("Can't calculate costs for comabt skill below 6");
		}
		int effectiveLevel;
		if (level < 13) {
			effectiveLevel = level - 6;
		} else {
			effectiveLevel = 5;
			effectiveLevel += calculateIncreasedLevel(level - 11);
		}
		return effectiveLevel * cost.getMultiplier();
	}

	public static int calcCostBaseAbility(int level) {
		return calcCostBaseAbility(level, CostCategory.E);
	}

	public static int calcCostBaseAbility(int level, CostCategory cost) {
		if (level < 8) {
			throw new ArithmeticException("Can't calculate costs for attribute below 8");
		}
		int effectiveLevel = 0;
		if (level < 15) {
			effectiveLevel += level - 8;
		} else {
			effectiveLevel += 5;
			effectiveLevel += calculateIncreasedLevel(level - 13);
		}
		return effectiveLevel * cost.getMultiplier();
	}

}
