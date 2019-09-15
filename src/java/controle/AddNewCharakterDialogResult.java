package controle;

import org.apache.commons.lang3.StringUtils;

public class AddNewCharakterDialogResult implements AddDialogResult {
	private String name;
	private String species;
	private String culture;
	private String profession;

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getName() {
		return name;
	}

	public void setCulture(String culture) {
		this.culture = culture;
	}

	public String getCulture() {
		return culture;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getProfession() {
		return profession;
	}

	@Override
	public boolean isComplete() {
		return StringUtils.isNotEmpty(name) && null != species;
	}

}
