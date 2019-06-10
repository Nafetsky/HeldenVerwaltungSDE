package localisation;

import java.util.HashMap;
import java.util.Map;


public class German implements ILanguage{

	private final static Map<LanguageKeys, String> translations = buildMap();

	private static Map<LanguageKeys, String> buildMap() {
		Map<LanguageKeys, String> map = new HashMap<>();
		map.put(LanguageKeys.attributesCourageAcronym, "MU");
		map.put(LanguageKeys.attributesSagacityAcronym, "KL");
		map.put(LanguageKeys.attributesIntuitionAcronym, "IN");
		map.put(LanguageKeys.attributesCharismaAcronym, "CH");
		map.put(LanguageKeys.attributesDexterityAcronym, "FF");
		map.put(LanguageKeys.attributesAgilityAcronym, "GE");
		map.put(LanguageKeys.attributesConstitutionAcronym, "KO");
		map.put(LanguageKeys.attributesStrengthAcronym, "KK");

		return map;
	}


	@Override
	public String get(LanguageKeys key) {
		if(translations.containsKey(key)){
			return translations.get(key);
		}
		throw new UnsupportedOperationException("You have no translation defined for key " + key);

	}
}
