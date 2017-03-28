package dataBase;

import org.apache.commons.lang3.StringUtils;

public enum Species {

	HUMAN("Mensch", 5, -5, -5, 8, 0),
	ELF("Elf", 2, -4, -6, 8, 18),
	HALF_ELF("Halb-Elf", 5, -4, -6, 8, 0),
	DWARF("Zwerg", 8, -4, -4, 6, 61);

	private final String name;
	private final int baseLife;
	private final int spiritualToughnes;
	private final int physicalToughness;
	private final int speed;
	private final int cost;

	private Species(String name, int baseLife, int spiritualToughnes, int physicalToughness, int speed, int cost) {
		this.name = name;
		this.baseLife = baseLife;
		this.spiritualToughnes = spiritualToughnes;
		this.physicalToughness = physicalToughness;
		this.speed = speed;
		this.cost = cost;
	}
	
	public static Species getEnumToName(String name){
		for(Species species:Species.values()){
			if(StringUtils.equals(species.getName(), name)){
				return species;
			}
		}
		throw new IllegalArgumentException(name +" is no valid species name");
	}

	public String getName() {
		return name;
	}

	public int getBaseLife() {
		return baseLife;
	}

	public int getSpiritualToughnes() {
		return spiritualToughnes;
	}

	public int getPhysicalToughness() {
		return physicalToughness;
	}

	public int getSpeed() {
		return speed;
	}

	public int getCost() {
		return cost;
	}

}
