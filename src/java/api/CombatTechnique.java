package api;

import api.skills.Increasable;

public interface CombatTechnique extends Increasable {
	BaseAttribute getAttribute();
}
