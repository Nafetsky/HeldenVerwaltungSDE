package dataBase;

import generated.Attributsk�rzel;

public enum Ability {
	
	VALOR("Mut", Attributsk�rzel.MU),
	INTELLIGENCE("Klugheit", Attributsk�rzel.KL),
	INTUITION("Intuition", Attributsk�rzel.IN),
	CHARISMA("Charisma", Attributsk�rzel.CH),
	DEXTERITY("Fingerfertigkeit", Attributsk�rzel.FF),
	AGILITY("Gewandheit", Attributsk�rzel.GE),
	CONSTITUTION("Konstitution", Attributsk�rzel.KO),
	STRENGTH("K�rperkraft", Attributsk�rzel.KK);
	
	String name;
	Attributsk�rzel acronym;
	
	private Ability(String name, Attributsk�rzel acronym){
		this.name= name;
		this.acronym = acronym;
	}
	
	public String getName(){
		return name;
	}
	
	public Attributsk�rzel getAcronym(){
		return acronym;
	}
	
	public static Ability getAbility(Attributsk�rzel acronym){
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
		case KO:
			return Ability.CONSTITUTION;
		case KK:
			return Ability.STRENGTH;
		default:
			throw new UnsupportedOperationException(acronym.name() + " is no valid Attributsk�rzel");
		}
	}
	
}
