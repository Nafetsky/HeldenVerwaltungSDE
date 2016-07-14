package dataBase;

import generated.Attributskürzel;

public enum Ability {
	
	VALOR("Mut", Attributskürzel.MU),
	INTELLIGENCE("Klugheit", Attributskürzel.KL),
	INTUITION("Intuition", Attributskürzel.IN),
	CHARISMA("Charisma", Attributskürzel.CH),
	DEXTERITY("Fingerfertigkeit", Attributskürzel.FF),
	AGILITY("Gewandheit", Attributskürzel.GE),
	STRENGTH("Körperkraft", Attributskürzel.KK),
	CONSTITUTION("Konstitution", Attributskürzel.KO);
	
	String name;
	Attributskürzel acronym;
	
	private Ability(String name, Attributskürzel acronym){
		this.name= name;
		this.acronym = acronym;
	}
	
	public String getName(){
		return name;
	}
	
	public Attributskürzel getAcronym(){
		return acronym;
	}
	
	public static Ability getAbility(Attributskürzel acronym){
		switch(acronym){
		case MU:
			return Ability.VALOR;
		case KL:
			return Ability.INTELLIGENCE;
		case IN:
			return Ability.INTUITION;
		case CH:
			return Ability.CHARISMA;
		case FF:
			return Ability.DEXTERITY;
		case GE:
			return Ability.AGILITY;
		case KK:
			return Ability.STRENGTH;
		case KO:
			return Ability.CONSTITUTION;
		default:
			throw new UnsupportedOperationException(acronym.name() + " is no valid Attributskürzel");
		}
	}
	
}
