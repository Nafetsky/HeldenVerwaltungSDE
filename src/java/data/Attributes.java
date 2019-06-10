package data;

import api.BaseAttribute;
import api.IAttributes;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Attributes implements IAttributes {

	private final int courage;
	private final int sagacity;
	private final int intuition;
	private final int charisma;
	private final int dexterity;
	private final int agility;
	private final int constitution;
	private final int strength;


	@Override
	public int getValue(BaseAttribute attribute) {
		switch(attribute){
			case Courage:
				return getCourage();
			case Sagacity:
				return getSagacity();
			case Intuition:
				return getIntuition();
			case Charisma:
				return getCharisma();
			case Dexterity:
				return getDexterity();
			case Agility:
				return getAgility();
			case Constitution:
				return getConstitution();
			case Strength:
				return getStrength();
		}
		return 0;
	}
}
