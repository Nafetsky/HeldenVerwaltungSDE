package controle;

import org.apache.commons.lang3.StringUtils;

public class AddVantageDialogResult implements AddDialogResult{
	
	private String name;
	private int cost;
	private boolean isAdvantage;
	
	public AddVantageDialogResult(String name, int cost){
		this.name = name;
		this.cost = cost;
	}
	
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public boolean isComplete(){
		return StringUtils.isNotEmpty(name)&&cost>-1;
	}

	public boolean isAdvantage() {
		return isAdvantage;
	}

	public void setAdvantage(boolean isAdvantage) {
		this.isAdvantage = isAdvantage;
	}

}
