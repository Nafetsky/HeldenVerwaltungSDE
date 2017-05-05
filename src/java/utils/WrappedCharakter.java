package utils;

import generated.Angabe;
import generated.Charakter;
import generated.Eigenschaftswerte;
import generated.Historie;
import generated.Kampftechniken;
import generated.Kommunikatives;
import generated.Liturgien;
import generated.Nachteile;
import generated.ObjectFactory;
import generated.Rituale;
import generated.Schablone;
import generated.Sonderfertigkeiten;
import generated.Talente;
import generated.Vorteile;
import generated.Zauber;
import generated.Zeremonien;;

public class WrappedCharakter {
	public static final int CHARAKTER = 1;
	public static final int CULTURE = 2;
	public static final int PROFESSION = 3;

	final Charakter charakter;
	final Schablone template;
	private final int contentType;
	private ObjectFactory factory;

	private WrappedCharakter(Charakter charakter) {
		this.charakter = charakter;
		contentType = 1;
		template = null;
		factory = new ObjectFactory();
	}

	private WrappedCharakter(Schablone template, int content) {
		this.charakter = null;
		contentType = content;
		this.template = template;
		factory = new ObjectFactory();
	}

	public static WrappedCharakter getWrappedCharakter(Charakter inputCharakter) {
		return new WrappedCharakter(inputCharakter);
	}

	public static WrappedCharakter getWrappedCulture(Schablone inputTemplate) {
		return new WrappedCharakter(inputTemplate, CULTURE);
	}

	public static WrappedCharakter getWrappedProfession(Schablone inputTemplate) {
		return new WrappedCharakter(inputTemplate, PROFESSION);
	}

	public boolean isCharakter() {
		return contentType == CHARAKTER;
	}

	public boolean isCulture() {
		return contentType == CULTURE;
	}

	public boolean isProfession() {
		return contentType == PROFESSION;
	}

	public Talente getSkills() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getTalente();
		case (CULTURE):
			return template.getTalente();
		case (PROFESSION):
			return template.getTalente();
		}
		return null;
	}

	public int getAP() {
		if (isCharakter()) {
			return charakter.getAP();
		}
		throw new UnsupportedOperationException("This WrappedCaharkter does not have getAp()");
	}

	public Kampftechniken getKampftechniken() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getKampftechniken();
		case (CULTURE):
		case (PROFESSION):
			return template.getKampftechniken();
		}

		return null;
	}

	public Zauber getZauber() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getZauber();
		case (CULTURE):
		case (PROFESSION):
			return template.getZauber();
		}
		return null;
	}

	public Rituale getRituale() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getRituale();
		case (CULTURE):
		case (PROFESSION):
			return template.getRituale();
		}
		return null;
	}

	public Liturgien getLiturgien() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getLiturgien();
		case (CULTURE):
		case (PROFESSION):
			return template.getLiturgien();
		}
		return null;
	}

	public Zeremonien getZeremonien() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getZeremonien();
		case (CULTURE):
		case (PROFESSION):
			return template.getZeremonien();
		}
		return null;
	}

	public String getName() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getName();
		case (CULTURE):
		case (PROFESSION):
			return template.getName();
		}
		return null;
	}

	public Talente getTalente() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getTalente();
		case (CULTURE):
		case (PROFESSION):
			return template.getTalente();
		}
		return null;
	}

	public Eigenschaftswerte getEigenschaftswerte() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getEigenschaftswerte();
		case (CULTURE):
			throw new UnsupportedOperationException("Cultures do not have required ability values");
		case (PROFESSION):
			return template.getEigenschaftswerte();
		}
		return null;
	}

	public Sonderfertigkeiten getSonderfertigkeiten() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getSonderfertigkeiten();
		case (CULTURE):
			throw new UnsupportedOperationException("Cultures do not have feats included");
		case (PROFESSION):
			return template.getSonderfertigkeiten();
		}
		return null;
	}

	public Historie getSteigerungshistorie() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getSteigerungshistorie();
		case (CULTURE):
		case (PROFESSION):
			throw new UnsupportedOperationException("Cultures and Professions do not have a history");
		}
		return null;
	}

	public void setAP(int differnce) {
		switch (contentType) {
		case (CHARAKTER):
			charakter.setAP(differnce);
			break;
		case (CULTURE):
		case (PROFESSION):
			throw new UnsupportedOperationException("Cultures and Professions do not have AP");
		}

	}

	public Integer getLeP() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getLeP();
		case (CULTURE):
		case (PROFESSION):
			throw new UnsupportedOperationException("Cultures and Professions do not have LeP");
		}
		return null;
	}

	public void setLeP(int differnce) {
		switch (contentType) {
		case (CHARAKTER):
			charakter.setLeP(differnce);
			break;
		case (CULTURE):
		case (PROFESSION):
			throw new UnsupportedOperationException("Cultures and Professions do not have LeP");
		}
	}

	public Integer getAsP() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getAsP();
		case (CULTURE):
		case (PROFESSION):
			throw new UnsupportedOperationException("Cultures and Professions do not have AsP");
		}
		return null;
	}

	public void setAsP(int differnce) {
		switch (contentType) {
		case (CHARAKTER):
			charakter.setAsP(differnce);
			break;
		case (CULTURE):
		case (PROFESSION):
			throw new UnsupportedOperationException("Cultures and Professions do not have AsP");
		}
	}

	public Integer getKaP() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getKaP();
		case (CULTURE):
		case (PROFESSION):
			throw new UnsupportedOperationException("Cultures and Professions do not have KaP");
		}
		return null;
	}

	public void setKaP(int differnce) {
		switch (contentType) {
		case (CHARAKTER):
			charakter.setKaP(differnce);
			break;
		case (CULTURE):
		case (PROFESSION):
			throw new UnsupportedOperationException("Cultures and Professions do not have LeP");
		}
	}

	public Vorteile getVorteile() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getVorteile();
		case (PROFESSION):
			return template.getVorteile();
		case (CULTURE):
			throw new UnsupportedOperationException("Cultures and Professions do not contain Advanteges");
		}
		return null;
	}

	public Nachteile getNachteile() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getNachteile();
		case (CULTURE):
		case (PROFESSION):
			throw new UnsupportedOperationException("Cultures and Professions do not contain Disadvanteges");
		}
		return null;
	}

	public int getType() {
		return contentType;
	}

	public Kommunikatives getKommunikatives() {
		switch (contentType) {
		case (CHARAKTER):
			if (null == charakter.getKommunikatives()) {
				charakter.setKommunikatives(new Kommunikatives());
			}
			return charakter.getKommunikatives();
		case (CULTURE):
			if (null == template.getKommunikatives()) {
				template.setKommunikatives(new Kommunikatives());
			}
			return template.getKommunikatives();
		case (PROFESSION):
			throw new UnsupportedOperationException("Professions do not contain languages or writing");
		}
		return null;
	}

	public void setName(String value) {
		switch (contentType) {
		case (CHARAKTER):
			charakter.setName(value);
		case (CULTURE):
		case (PROFESSION):
			throw new UnsupportedOperationException("Only Charakters are allowed to change names");
		}

	}

	public String getCulture() {
		switch (contentType) {
		case (CHARAKTER):
			return charakter.getKultur();
		case (CULTURE):
		case (PROFESSION):
		default:
			throw new UnsupportedOperationException("Only Charakters have cultures");
		}
	}

	public void setCulture(String value) {
		switch (contentType) {
		case (CHARAKTER):
			charakter.setKultur(value);
			break;
		case (CULTURE):
		case (PROFESSION):
		default:
			throw new UnsupportedOperationException("Only Charakters have cultures");
		}
	}

	public Angabe getAngaben() {
		switch (contentType) {
		case (CHARAKTER):
			Angabe result = charakter.getAngaben();
			if (null == result) {
				result = factory.createAngabe();
				charakter.setAngaben(result);
			}
			return result;
		case (CULTURE):
		case (PROFESSION):
		default:
			throw new UnsupportedOperationException("Only Charakters have cultures");
		}
	}

}
