package api.skills;

import generated.MerkmalProfan;
import org.apache.commons.lang3.StringUtils;

public enum BaseSkills {
	FLY("Fliegen", BaseDescriptors.Physical, ImprovementComplexity.B),
	JUGGLERY("Gaukeleien", BaseDescriptors.Physical, ImprovementComplexity.A),
	CLIMB("Klettern", BaseDescriptors.Physical, ImprovementComplexity.B),
	BODY_CONTROL("Körperbeherrschung", BaseDescriptors.Physical, ImprovementComplexity.D),
	STRENGHT_SURGE("Kraftakt", BaseDescriptors.Physical, ImprovementComplexity.B),
	RIDE("Reiten", BaseDescriptors.Physical, ImprovementComplexity.B),
	SWIM("Schwimmen", BaseDescriptors.Physical, ImprovementComplexity.B),
	COMPOSURE("Selbstbeherrschung", BaseDescriptors.Physical, ImprovementComplexity.D),
	SING("Singen", BaseDescriptors.Physical, ImprovementComplexity.A),
	PERCEPTION("Sinnesschärfe", BaseDescriptors.Physical, ImprovementComplexity.D),
	DANCE("Tanzen", BaseDescriptors.Physical, ImprovementComplexity.A),
	PICKPOCKETING("Taschendiebstahl", BaseDescriptors.Physical, ImprovementComplexity.B),
	STEALTH("Verbergen", BaseDescriptors.Physical, ImprovementComplexity.C),
	CAROUSE("Zechen", BaseDescriptors.Physical, ImprovementComplexity.A),
	
	CONVINCE("Bekehren& Überzeugen", BaseDescriptors.Social, ImprovementComplexity.B),
	COZEN("Betören", BaseDescriptors.Social, ImprovementComplexity.B),
	INTIMIDATE("Einschüchtern", BaseDescriptors.Social, ImprovementComplexity.B),
	ETIQUETTE("Etikette", BaseDescriptors.Social, ImprovementComplexity.B),
	STREETSMARTS("Gassenwissen", BaseDescriptors.Social, ImprovementComplexity.C),
	SENSE_MOTIVE("Menschenkenntnis", BaseDescriptors.Social, ImprovementComplexity.C),
	PERSUADE("Überreden", BaseDescriptors.Social, ImprovementComplexity.C),
	DISGUISE("Verkleiden", BaseDescriptors.Social, ImprovementComplexity.B),
	WILLPOWER("Willenskraft", BaseDescriptors.Social, ImprovementComplexity.D),
	
	TRACK("Fährtensuchen", BaseDescriptors.Nature, ImprovementComplexity.C),
	BINDING("Fesseln", BaseDescriptors.Nature, ImprovementComplexity.A),
	FISHING("Fischen& Angeln", BaseDescriptors.Nature, ImprovementComplexity.A),
	PATHFINDING("Orientierung", BaseDescriptors.Nature, ImprovementComplexity.B),
	BOTANY("Pflanzenkunde", BaseDescriptors.Nature, ImprovementComplexity.C),
	ZOOLOGY("Tierkunde", BaseDescriptors.Nature, ImprovementComplexity.C),
	SURVIVAL("Wildnisleben", BaseDescriptors.Nature, ImprovementComplexity.C),
	
	GAMBLE("Brett-& Glückspiele", BaseDescriptors.Knowledge, ImprovementComplexity.A),
	GEOGRAPHY("Geographie", BaseDescriptors.Knowledge, ImprovementComplexity.B),
	HISTORY("Geschichtswissen", BaseDescriptors.Knowledge, ImprovementComplexity.B),
	RELIGION("Götter& Kulte", BaseDescriptors.Knowledge, ImprovementComplexity.B),
	WARCRAFT("Kriegskunst", BaseDescriptors.Knowledge, ImprovementComplexity.B),
	SPELLCRAFT("Magiekunde", BaseDescriptors.Knowledge, ImprovementComplexity.C),
	MECHANIC("Mechanik", BaseDescriptors.Knowledge, ImprovementComplexity.B),
	MATH("Rechnen", BaseDescriptors.Knowledge, ImprovementComplexity.A),
	LAW("Rechtskunde", BaseDescriptors.Knowledge, ImprovementComplexity.A),
	LORE("Sagen& Legenden", BaseDescriptors.Knowledge, ImprovementComplexity.B),
	SPHEROLOGY("Sphärenkunde", BaseDescriptors.Knowledge, ImprovementComplexity.B),
	ASTROLOGY("Sternenkunde", BaseDescriptors.Knowledge, ImprovementComplexity.A),
	
	ALCHEMY("Alchemie", BaseDescriptors.Craft, ImprovementComplexity.C),
	SEAFARING("Boote& Schiffe", BaseDescriptors.Craft, ImprovementComplexity.B),
	VEHICLES("Fahrzeuge", BaseDescriptors.Craft, ImprovementComplexity.A),
	TRADE("Handel", BaseDescriptors.Craft, ImprovementComplexity.B),
	TREAT_POISION("Heilkunde Gift", BaseDescriptors.Craft, ImprovementComplexity.B),
	TREAT_DISEASE("Heilkunde Krankheit", BaseDescriptors.Craft, ImprovementComplexity.B),
	TREAT_SOUL("Heilkunde Seele", BaseDescriptors.Craft, ImprovementComplexity.B),
	TREAT_WOUNDS("Heilkunde Wunde", BaseDescriptors.Craft, ImprovementComplexity.D),
	WOODCRAFTING("Holzbearbeitung", BaseDescriptors.Craft, ImprovementComplexity.B),
	COOKING("Lebensmittelbearbeitung", BaseDescriptors.Craft, ImprovementComplexity.A),
	LETHERWORKING("Lederbearbeitung", BaseDescriptors.Craft, ImprovementComplexity.B),
	DRAWING("Malen& Zeichnen", BaseDescriptors.Craft, ImprovementComplexity.A),
	METALCRAFT("Metallbearbeitung", BaseDescriptors.Craft, ImprovementComplexity.C),
	MUSIC_MAKING("Musizieren", BaseDescriptors.Craft, ImprovementComplexity.A),
	LOCKPICKING("Schlösserknacken", BaseDescriptors.Craft, ImprovementComplexity.C),
	STONECRAFT("Steinbearbeitung", BaseDescriptors.Craft, ImprovementComplexity.A),
	TAILORING("Stoffbearbeitung", BaseDescriptors.Craft, ImprovementComplexity.A);
	
	String name;
	Descriptor descriptor;
	ImprovementComplexity category;
	
	BaseSkills(String name, Descriptor merkmal, ImprovementComplexity category){
		this.name = name;
		this.descriptor = merkmal;
		this.category = category;
	}
	
	public String getName(){
		return name;
	}
	
	public Descriptor getMerkmal(){
		return descriptor;
	}
	
	public ImprovementComplexity getCategory(){
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
		case KÖRPER:
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
