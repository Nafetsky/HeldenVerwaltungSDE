package controle;

import org.apache.commons.lang3.StringUtils;

public class AddFeatDialogResult implements AddDialogResult{
	
	String name;
	int cost;
	
	public AddFeatDialogResult(String name, int cost){
		this.name = name;
		this.cost = cost;
	}

	@Override
	public boolean isComplete(){
		return StringUtils.isNoneEmpty(name)&&cost>-1;
	}

}
