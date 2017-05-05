package database;

import org.apache.commons.lang3.StringUtils;

public enum FeatGroup {
	
	COMMON("Allgemein"),
	COMBAT("Kampf"),
	MAGIC("Magisch"),
	ORDAINED("Geweiht"),
	SKILL_SPECIALISATION("Fertigeitsspezialisierung");
	
	String name;
	
	private FeatGroup(String name){
		this.name= name;
	}
	
	public String getName(){
		return name;
	}
	
	public FeatGroup getFeatGroupByName(String name){
		for(FeatGroup group:FeatGroup.values()){
			if(StringUtils.equals(group.getName(), name)){
				return group;
			}
		}
		throw new UnsupportedOperationException(name +" is no valid FeatGroup name");
	}

}
