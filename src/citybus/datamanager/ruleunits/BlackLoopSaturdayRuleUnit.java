package citybus.datamanager.ruleunits;

import java.util.ArrayList;

import citybus.datamanager.DBConstants;
import citybus.datamanager.Time;

public class BlackLoopSaturdayRuleUnit extends RuleUnit {

	public BlackLoopSaturdayRuleUnit() {
		routineIndex = DBConstants.RoutineIndex[2];
		stopGap = new int[] { 5, 5, 5, 5 };
		busGap = new int[][] { { 30 } };
		busGapCutoffs = null;
		startTime = new Time(21, 15);
		endTime = new Time(3, 15);
		emptyBlocks = new ArrayList<Block>();
		emptyBlocks.add(new Block(12, 2, 12, 4));
		initFields();
	}
}
