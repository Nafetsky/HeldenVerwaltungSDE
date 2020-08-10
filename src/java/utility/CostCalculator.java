package utility;

import api.BaseAttribute;
import api.CombatTechnique;
import api.Vantage;
import api.base.Character;
import api.IAttributes;
import api.ISpecialAbility;
import api.skills.ImprovementComplexity;
import api.skills.Increasable;
import api.skills.Skill;
import api.skills.SkillGroup;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class CostCalculator {

	private Character character;

	public int calcUsedAP() {
		int usedAp = getAPOnlyRelevantOnCharacter();
		usedAp += calcAllBaseAbilityCosts();
		usedAp += calcAdvantagesCosts();
		usedAp += calcDisadvantagesCosts();
		usedAp += calcAllSkillCosts(character.getSkills());
		usedAp += calcCombatTechniquesCosts(character.getCombatTechniques());
		usedAp += calcFeatsCosts();

		return usedAp;
	}

	private int getAPOnlyRelevantOnCharacter() {
		int sum = character.getMetaData()
						   .getRace()
						   .getCost();
		sum += calcCostSkill(character.getBonusLifePoints(), ImprovementComplexity.D, true);
		sum += calcCostSkill(character.getBonusArcaneEnergy(), ImprovementComplexity.D, true);
		sum += character.getRestoredArcaneEnergy() * 2;
		sum += calcCostSkill(character.getBonusKarmaPoints(), ImprovementComplexity.D, true);
		sum += character.getRestoredKarma() * 2;
		return sum;
	}

	int calcAllBaseAbilityCosts() {
		IAttributes attributes = character.getAttributes();
		return Arrays.stream(BaseAttribute.values())
					 .map(attributes::getValue)
					 .map(CostCalculator::calcCostBaseAbility)
					 .mapToInt(Integer::intValue)
					 .sum();
	}

	private int calcAdvantagesCosts() {
		return character.getAdvantages()
						.stream()
						.mapToInt(Vantage::getCost)
						.sum();
	}

	private int calcDisadvantagesCosts() {
		return character.getDisadvantages()
						.stream()
						.mapToInt(Vantage::getCost)
						.sum() * -1;
	}

	static int calcAllSkillCosts(List<Skill> skills) {
		int sumCosts = 0;
		for (Skill skill : skills) {
			boolean baseSkill = SkillGroup.BASE == skill.getGroup();
			sumCosts += calcCostSkill(skill.getLevel(), skill.getComplexity(), baseSkill);
		}
		return sumCosts;
	}

	private int calcFeatsCosts() {
		List<ISpecialAbility> specialAbilities = character.getSpecialAbilities();
		return specialAbilities.stream()
							   .mapToInt(ISpecialAbility::getCost)
							   .sum();
	}

	private static int calcCombatTechniquesCosts(List<CombatTechnique> combatTechniques) {
		int sum = 0;
		for (CombatTechnique combatTechnique : combatTechniques) {
			sum += calcCostCombat(combatTechnique.getLevel(), combatTechnique.getComplexity());
		}
		return sum;
	}

	private static int calculateIncreasedLevel(int level) {
		return (level * (level + 1)) / 2;
	}

	public static int calcCostSkill(int level, ImprovementComplexity cost, boolean baseSkill) {
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
		return effectiveLevel * cost.getFactor();
	}

	public static int calcCostCombat(int level, ImprovementComplexity cost) {
		if (level < 6) {
			throw new ArithmeticException("Can't calculate costs for combat skill below 6");
		}
		int effectiveLevel;
		if (level < 13) {
			effectiveLevel = level - 6;
		} else {
			effectiveLevel = 5;
			effectiveLevel += calculateIncreasedLevel(level - 11);
		}
		return effectiveLevel * cost.getFactor();
	}

	public static int calcCostForNextLevel(Increasable increasable){
		int currentLevel = increasable.getLevel();
		if(increasable instanceof CombatTechnique && currentLevel < 6){
			throw new ArithmeticException("Can not calculate for combat skills below 6");
		}
		int factor = increasable.getComplexity()
								.getFactor();
		if(currentLevel <= 11){
			return factor;
		}
		return (currentLevel - 10) * factor;
	}

	public static int calcCostForNextLevelAbility(int level){
		return level <= 13 ? 15 : (14 - level) * 15;
	}

	public static int calcCostBaseAbility(int level) {
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
		return effectiveLevel * ImprovementComplexity.Attribute.getFactor();
	}

}
