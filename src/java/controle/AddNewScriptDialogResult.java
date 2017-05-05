package controle;

import generated.Steigerungskategorie;

public class AddNewScriptDialogResult {
	private final String name;
	private final Steigerungskategorie costCategorie;
	
	public AddNewScriptDialogResult(String name, Steigerungskategorie cost){
		this.name = name;
		costCategorie = cost;
	}
	
	public Steigerungskategorie getCostCategorie() {
		return costCategorie;
	}
	
	public String getName() {
		return name;
	}

}
