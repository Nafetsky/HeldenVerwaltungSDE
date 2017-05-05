package controle;

import org.apache.commons.lang3.StringUtils;

import database.CostCategory;
import generated.Attributskürzel;

public class AddNewCombatSkillDialogResult implements AddDialogResult{
	public final String name;
	public final Attributskürzel ability;
	public final CostCategory costCategory;
	
	public AddNewCombatSkillDialogResult(String name, Attributskürzel ability, CostCategory cost){
		this.name = name;
		this.ability = ability;
		costCategory = cost;
	}
	
	public boolean isComplete(){
		if(StringUtils.isEmpty(name)){
			return false;
		}
		if(null==ability){
			return false;
		}
		if(null==costCategory){
			return false;
		}
		return true;
	}

	public Attributskürzel getAbility() {
		return ability;
	}
}
