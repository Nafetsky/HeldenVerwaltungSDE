package dataBase;

public enum SpecialSkillGroup {
	
	ABILITY("Eigenschaft"),
	BASE("Basis"),
	SPELL("Zauber"),
	RITUAL("Ritual"),
	LITURGY("Liturgie"),
	ZEREMONY("Zeremonie"),
	COMBAT("Kampftechnik")
	;
	
	private String groupName;
	
	private SpecialSkillGroup(String name){
		groupName = name;
	}
	
	public final String getName(){
		return groupName;
	}

}
