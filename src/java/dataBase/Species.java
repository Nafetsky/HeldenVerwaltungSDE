package dataBase;

public enum Species {

	HUMAN("Mesnch", 5, -5, -5, 8, 0),
	ELF("Elf", 2, -4, -6, 8, 18),
	HALF_ELF("Halb-Elf", 5, -4, -6, 8, 0),
	DWARF("Zwerg", 8, -4, -4, 6, 61);

	String name;
	int baseLife;
	int spiritualToughnes;
	int physicalToughness;
	int speed;
	int cost;

	private Species(String name, int baseLife, int spiritualToughnes, int physicalToughness, int speed, int cost) {
		this.name = name;
		this.baseLife = baseLife;
		this.spiritualToughnes = spiritualToughnes;
		this.physicalToughness = physicalToughness;
		this.speed = speed;
		this.cost = cost;
	}

}
