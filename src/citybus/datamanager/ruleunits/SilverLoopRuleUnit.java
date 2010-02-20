package citybus.datamanager.ruleunits;

import citybus.datamanager.DBConstants;
import citybus.datamanager.Time;

public class SilverLoopRuleUnit extends RuleUnit {

	public SilverLoopRuleUnit() {
		routineIndex = DBConstants.RoutineIndex[1];
		stopGap = new int[] { 2, 2, 10 };
		busGap = new int[][] { { 5, 5, 10 }, { 3, 5, 10, 2 }, { 10 } };
		busGapCutoffs = new Time[] { new Time(11, 27), new Time(17, 55) };
		startTime = new Time(7, 5);
		endTime = new Time(17, 55);
		initFields();
	}
}
