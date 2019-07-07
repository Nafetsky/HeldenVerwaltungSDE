package XsdWrapper;

import api.BaseAttribute;
import api.CombatTechnique;
import api.skills.ImprovementComplexity;
import generated.Kampftechnik;
import lombok.Getter;

public class DatabackedCombatTechnique implements CombatTechnique {

	private Kampftechnik kampftechnik;
	@Getter
	private final BaseAttribute attribute;
	@Getter
	private final ImprovementComplexity complexity;


	DatabackedCombatTechnique(Kampftechnik kampftechnik) {
		this.kampftechnik = kampftechnik;
		Translator translator = new Translator();
		attribute = translator.translate(kampftechnik.getLeiteigenschaft());
		complexity = translator.translate(kampftechnik.getSteigerungskosten());
	}

	@Override
	public void setLevel(int level) {
		kampftechnik.setKampftechnikwert(level);
	}

	@Override
	public String getName() {
		return kampftechnik.getName();
	}

	@Override
	public int getLevel() {
		return kampftechnik.getKampftechnikwert();
	}
}
