package database;

import api.base.Character;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
@Getter
public enum BaseResourceData {

	LIFE("", "LeP"){

		@Override
		public int getBoughtPoints(Character charakter) {
			return charakter.getBonusLifePoints();
		}

	},
	ARCANE_ENERGY("Zauberer", "AsP") {
		@Override
		public int getBoughtPoints(Character charakter) {
			return charakter.getBonusArcaneEnergy();
		}
	},
	KARMA("Geweihter", "KaP"){
		@Override
		public int getBoughtPoints(Character charakter) {
			return charakter.getBonusKarmaPoints();
		}
	};

	public static final String BOUGHT_PREFIX = "Zugekaufte ";
	public static final String BUY_PREFIX = "Kaufe ";
	public static final String LOST_PREFIX = "Verlorene ";
	public static final String LOOSE_PREFIX = "Verliere ";
	public static final String RESTORED_PREFIX = "Zurückgekaufte ";
	public static final String RESTORE_PREFIX = "Heile ";
	private final String requiredAdvantage;
	private final String name;


	public boolean isRelevant(Character charakter) {
		if (StringUtils.isEmpty(this.requiredAdvantage)) {
			return true;
		}

		return charakter.getAdvantages()
						.stream()
						.anyMatch(advantage -> StringUtils.equalsIgnoreCase(advantage.getName(), this.requiredAdvantage));
	}

	public abstract int getBoughtPoints(Character charakter);

}
