package citybus.datamanager.ruleunits;

import java.util.ArrayList;

import citybus.datamanager.DBConstants;
import citybus.datamanager.Time;

public class BlackLoopSundayRuleUnit extends RuleUnit {

	public BlackLoopSundayRuleUnit() {
		routineIndex = DBConstants.RoutineIndex[2];
		stopGap = new int[] { 5, 5, 5, 5 };
		busGap = new int[][] { { 30 } };
		busGapCutoffs = null;
		startTime = new Time(17, 15);
		endTime = new Time(0, 15);
		emptyBlocks = new ArrayList<Block>();
		emptyBlocks.add(new Block(14, 1, 14, 4));
		initFields();
	}
}
