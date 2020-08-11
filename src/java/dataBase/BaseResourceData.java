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
		public int getLevel(Character charakter) {
			return charakter.getBonusLifePoints();
		}

		@Override
		public int getLost(Character charakter) {
			throw new UnsupportedOperationException("You do not loose LeP");
		}

		@Override
		public int getRestored(Character charakter) {
			throw new UnsupportedOperationException("You can restore LeP");
		}

	},
	ARCANE_ENERGY("Zauberer", "AsP") {
		@Override
		public int getLevel(Character charakter) {
			return charakter.getBonusArcaneEnergy();
		}

		@Override
		public int getLost(Character charakter) {
			return charakter.getLostArcaneEnergy();
		}

		@Override
		public int getRestored(Character charakter) {
			return charakter.getRestoredArcaneEnergy();
		}
	},
	KARMA("Geweihter", "KaP"){
		@Override
		public int getLevel(Character charakter) {
			return charakter.getBonusKarmaPoints();
		}

		@Override
		public int getLost(Character charakter) {
			return charakter.getLostKarma();
		}
		@Override
		public int getRestored(Character charakter) {
			return charakter.getRestoredKarma();
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

	public abstract int getLevel(Character charakter);

	public abstract int getLost(Character charakter);

	public abstract int getRestored(Character charakter);

}
