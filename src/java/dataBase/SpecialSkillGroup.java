package dataBase;

import generated.Fertigkeitskategorie;

public enum SpecialSkillGroup {

	ABILITY("Eigenschaft"), BASE("Basis"), SPELL("Zauber"), RITUAL("Ritual"), LITURGY("Liturgie"), ZEREMONY(
			"Zeremonie"), COMBAT("Kampftechnik");

	private String groupName;

	private SpecialSkillGroup(String name) {
		groupName = name;
	}

	public final String getName() {
		return groupName;
	}

	public static SpecialSkillGroup getFromFertigkeitskategorie(Fertigkeitskategorie fertigkeitskategorie) {
		switch (fertigkeitskategorie) {
		case ZAUBER:
			return SpecialSkillGroup.SPELL;
		case RITUAL:
			return RITUAL;
		case LITURGIE:
			return LITURGY;
		case ZEREMONIE:
			return ZEREMONY;
		default:
			return null;
		}
	}

}
