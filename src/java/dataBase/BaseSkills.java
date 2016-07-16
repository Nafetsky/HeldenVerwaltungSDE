package dataBase;

import org.apache.commons.lang3.StringUtils;

import generated.MerkmalProfan;

public enum BaseSkills {
	FLY("Fliegen", MerkmalProfan.K�RPER, CostCategory.B),
	JUGGLERY("Gaukeleien", MerkmalProfan.K�RPER, CostCategory.A),
	CLIMB("Klettern", MerkmalProfan.K�RPER, CostCategory.B),
	BODY_CONTROL("K�rperbeherrschung", MerkmalProfan.K�RPER, CostCategory.D),
	STRENGHT_SURGE("Kraftakt", MerkmalProfan.K�RPER, CostCategory.B),
	RIDE("Reiten", MerkmalProfan.K�RPER, CostCategory.B),
	SWIM("Schwimmen", MerkmalProfan.K�RPER, CostCategory.B),
	COMPOSURE("Selbstbeherrschung", MerkmalProfan.K�RPER, CostCategory.D),
	SING("Singen", MerkmalProfan.K�RPER, CostCategory.A),
	PERCEPTION("Sinnessch�rfe", MerkmalProfan.K�RPER, CostCategory.D),
	DANCE("Tanzen", MerkmalProfan.K�RPER, CostCategory.A),
	PICKPOCKETING("Taschendiebstahl", MerkmalProfan.K�RPER, CostCategory.B),
	STEALTH("Verbergen", MerkmalProfan.K�RPER, CostCategory.C),
	CAROUSE("Zechen", MerkmalProfan.K�RPER, CostCategory.A),
	
	CONVINCE("Bekehren& �berzeugen", MerkmalProfan.GESELLSCHAFT, CostCategory.B),
	COZEN("Bet�ren", MerkmalProfan.GESELLSCHAFT, CostCategory.B),
	INTIMIDATE("Einsch�chtern", MerkmalProfan.GESELLSCHAFT, CostCategory.B),
	ETIQUETTE("Etikette", MerkmalProfan.GESELLSCHAFT, CostCategory.B),
	STREETSMARTS("Gassenwissen", MerkmalProfan.GESELLSCHAFT, CostCategory.C),
	SENSE_MOTIVE("Menschenkenntnis", MerkmalProfan.GESELLSCHAFT, CostCategory.C),
	PERSUADE("�berreden", MerkmalProfan.GESELLSCHAFT, CostCategory.C),
	DISGUISE("Verkleiden", MerkmalProfan.GESELLSCHAFT, CostCategory.B),
	WILLPOWER("Willenskraft", MerkmalProfan.GESELLSCHAFT, CostCategory.D),
	
	TRACK("F�hrtensuchen", MerkmalProfan.NATUR, CostCategory.C),
	BINDING("Fesseln", MerkmalProfan.NATUR, CostCategory.A),
	FISHING("Fischen& Angeln", MerkmalProfan.NATUR, CostCategory.A),
	PATHFINDING("Orientierung", MerkmalProfan.NATUR, CostCategory.B),
	BOTANY("Pflanzenkunde", MerkmalProfan.NATUR, CostCategory.C),
	ZOOLOGY("Tierkunde", MerkmalProfan.NATUR, CostCategory.C),
	SURVIVAL("Wildnisleben", MerkmalProfan.NATUR, CostCategory.C),
	
	GAMBLE("Brett-& Gl�ckspiele", MerkmalProfan.WISSEN, CostCategory.A),
	GEOGRAPHY("Geographie", MerkmalProfan.WISSEN, CostCategory.B),
	HISTORY("Geschichtswissen", MerkmalProfan.WISSEN, CostCategory.B),
	RELIGION("G�tter& Kulte", MerkmalProfan.WISSEN, CostCategory.B),
	WARCRAFT("Kriegskunst", MerkmalProfan.WISSEN, CostCategory.B),
	SPELLCRAFT("Magiekunde", MerkmalProfan.WISSEN, CostCategory.C),
	MECHANIC("Mechanik", MerkmalProfan.WISSEN, CostCategory.B),
	MATH("Rechnen", MerkmalProfan.WISSEN, CostCategory.A),
	LAW("Retchskunde", MerkmalProfan.WISSEN, CostCategory.A),
	LORE("Sagen& Legenden", MerkmalProfan.WISSEN, CostCategory.B),
	SPHEROLOGY("Sph�renkunde", MerkmalProfan.WISSEN, CostCategory.B),
	ASTROLOGY("Sternenkunde", MerkmalProfan.WISSEN, CostCategory.A),
	
	ALCHEMY("Alchemie", MerkmalProfan.HANDWERK, CostCategory.C),
	SEAFARING("Boote& Schiffe", MerkmalProfan.HANDWERK, CostCategory.B),
	VEHICLES("Fahrzeuge", MerkmalProfan.HANDWERK, CostCategory.A),
	TRADE("Handel", MerkmalProfan.HANDWERK, CostCategory.B),
	TREAT_POISION("Heilkunde Gift", MerkmalProfan.HANDWERK, CostCategory.B),
	TREAT_DISEASE("Heilkunde Krankheit", MerkmalProfan.HANDWERK, CostCategory.B),
	TREAT_SOUL("Heilkunde Seele", MerkmalProfan.HANDWERK, CostCategory.B),
	TREAT_WOUNDS("Heilkunde Wunde", MerkmalProfan.HANDWERK, CostCategory.D),
	WOODCRAFTING("Holzbearbeitung", MerkmalProfan.HANDWERK, CostCategory.B),
	COOKING("Lebensmittelverarbeitung", MerkmalProfan.HANDWERK, CostCategory.A),
	LETHERWORKING("Lebensmittelverarbeitung", MerkmalProfan.HANDWERK, CostCategory.B),
	DRAWING("Malen& Zeichnen", MerkmalProfan.HANDWERK, CostCategory.A),
	METALCRAFT("Metallbearbeitung", MerkmalProfan.HANDWERK, CostCategory.C),
	MUSIC_MAKING("Musizieren", MerkmalProfan.HANDWERK, CostCategory.A),
	LOCKPICKING("Schl�sserknacken", MerkmalProfan.HANDWERK, CostCategory.C),
	STONECRAFT("Steinbearbeitung", MerkmalProfan.HANDWERK, CostCategory.A),
	TAILORING("Stoffbearbeitung", MerkmalProfan.HANDWERK, CostCategory.A);
	
	String name;
	MerkmalProfan merkmal;
	CostCategory category;
	
	private BaseSkills(String name, MerkmalProfan merkmal, CostCategory category){
		this.name = name;
		this.merkmal = merkmal;
		this.category = category;
	}
	
	public String getName(){
		return name;
	}
	
	public MerkmalProfan getMerkmal(){
		return merkmal;
	}
	
	public CostCategory getCategory(){
		return category;
	}
	
	public static BaseSkills getSkill(String name){
		for(BaseSkills skill:BaseSkills.values()){
			if(StringUtils.equals(skill.getName(), name)){
				return skill;
			}
		}
		
		throw new IllegalArgumentException(name + " is no valid baseSkill");
	}
	
	public static int getAmountByCategory(MerkmalProfan merkmal){
		switch(merkmal){
		case K�RPER:
			return 14;
		case GESELLSCHAFT:
			return 9;
		case NATUR:
			return 7;
		case WISSEN:
			return 12;
		case HANDWERK:
			return 17;
		default:
			return 0;
		}
	}
	

}
