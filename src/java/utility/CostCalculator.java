package utility;

import api.Advantage;
import api.BaseAttribute;
import api.Character;
import api.CombatTechnique;
import api.Disadvantage;
import api.IAttributes;
import api.ISpecialAbility;
import api.ImprovementComplexity;
import api.Skill;
import api.SkillGroup;
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
						.mapToInt(Advantage::getCost)
						.sum();
	}

	private int calcDisadvantagesCosts() {
		return character.getDisadvantages()
						.stream()
						.mapToInt(Disadvantage::getCost)
						.sum() * -1;
	}

	static int calcAllSkillCosts(List<Skill> skills) {
		int sumCosts = 0;
		for (Skill skill : skills) {
			boolean baseSkill = SkillGroup.Base == skill.getGroup();
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
			sum += calcCostCombat(combatTechnique.getLevel(), combatTechnique.getImprovementComplexity());
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

	public static final int calcCostCombat(int level, ImprovementComplexity cost) {
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

	static int calcCostBaseAbility(int level) {
		return calcCostBaseAbility(level, ImprovementComplexity.Attribute);
	}

	public static int calcCostBaseAbility(int level, ImprovementComplexity complexity) {
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
		return effectiveLevel * complexity.getFactor();
	}

}
