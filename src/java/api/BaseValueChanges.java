package api;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BaseValueChanges {

	int boughtHitPoints = 0;
	int lostHitPoints = 0;
	int boughtAstralPoints = 0;
	int lostAstralPoints = 0;
	int restoredAstralPoints = 0;
	int boughtKarmaPoints = 0;
	int lostKarmaPoints = 0;
	int restoredKarmaPoints = 0;


	public void merge(BaseValueChanges other) {
		boughtHitPoints += other.getBoughtHitPoints();
		lostHitPoints += other.getLostHitPoints();
		boughtAstralPoints += other.boughtAstralPoints;
		lostAstralPoints += other.lostAstralPoints;
		restoredAstralPoints += other.restoredAstralPoints;
		boughtKarmaPoints += other.boughtKarmaPoints;
		lostKarmaPoints += other.lostKarmaPoints;
		restoredKarmaPoints += other.restoredKarmaPoints;
	}
}
