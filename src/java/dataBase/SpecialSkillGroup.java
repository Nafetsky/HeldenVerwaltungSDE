package dataBase;

public enum SpecialSkillGroup {
	
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
