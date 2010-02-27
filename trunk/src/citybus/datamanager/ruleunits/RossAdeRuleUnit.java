package citybus.datamanager.ruleunits;

import java.util.ArrayList;

import citybus.datamanager.DBConstants;
import citybus.datamanager.Time;

public class RossAdeRuleUnit extends RuleUnit {

	public RossAdeRuleUnit() {
		routineIndex = DBConstants.RoutineIndex[5];
		stopGap = new int[] { 5, 10 };
		busGap = new int[][] { { 10 } };
		busGapCutoffs = null;
		startTime = new Time(7, 0);
		endTime = new Time(18, 0);
		specialTimes = new Time[] { new Time(6, 55), null, null };
		emptyBlocks = new ArrayList<Block>();
		emptyBlocks.add(new Block(0, 0, 0, 0));
		emptyBlocks.add(new Block(66, 1, 66, 2));
		initFields();
	}
}
