package dataBase;

import org.apache.commons.lang3.StringUtils;

public enum Species {

	HUMAN("Mensch", 5, -5, -5, 8, 0),
	ELF("Elf", 2, -4, -6, 8, 18),
	HALF_ELF("Halb-Elf", 5, -4, -6, 8, 0),
	DWARF("Zwerg", 8, -4, -4, 6, 61);

	public String name;
	public int baseLife;
	public int spiritualToughnes;
	public int physicalToughness;
	public int speed;
	public int cost;

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
			if(StringUtils.equals(species.name, name)){
				return species;
			}
		}
		throw new IllegalArgumentException(name +" is no valid species name");
	}

}
