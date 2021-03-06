package database;

import generated.Steigerungskategorie;

public enum CostCategory {
	A(1), B(2), C(3), D(4), E(15);

	int multiplier;

	private CostCategory(int mult) {
		multiplier = mult;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public static CostCategory getCostCategory(String category) {
		if (category.length() != 1) {
			throw new IllegalArgumentException(category + " is no valid CostCategory");
		}
		return getCostCategory(category.toCharArray()[0]);
	}

	public static CostCategory getCostCategory(Steigerungskategorie category) {
		return getCostCategory(category.toString());
	}

	public static CostCategory getCostCategory(char category) {
		switch (category) {
		case ('A'):
			return CostCategory.A;
		case ('B'):
			return CostCategory.B;
		case ('C'):
			return CostCategory.C;
		case ('D'):
			return CostCategory.D;
		case ('E'):
			return CostCategory.E;
		default:
			throw new IllegalArgumentException(category + " is no valid CostCategory");
		}
	}

	@Override
	public String toString() {
		switch (multiplier) {
		case 1:
			return "A";
		case 2:
			return "B";
		case 3:
			return "C";
		case 4:
			return "D";
		case 15:
			return "E";
		default:
			throw new UnsupportedOperationException("Somethind wrent wrong by writing of CostCategory");
		}
			
	}

}
