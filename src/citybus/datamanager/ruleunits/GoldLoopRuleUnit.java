package citybus.datamanager.ruleunits;

import java.util.ArrayList;

import citybus.datamanager.DBConstants;
import citybus.datamanager.Time;

public class GoldLoopRuleUnit extends RuleUnit {

	public GoldLoopRuleUnit() {
		routineIndex = DBConstants.RoutineIndex[0];
		stopGap = new int[] { 5, 5, 5, 5, 7 };
		busGap = new int[][] { { 5, 5, 20, 5, 5, 20 }, { 30 } };
		busGapCutoffs = new Time[] { new Time(17, 35) };
		startTime = new Time(7, 5);
		endTime = new Time(0, 5);
		specialTimes = new Time[] { null, null, null, null, null,
				new Time(7, 3) };
		emptyBlocks = new ArrayList<Block>();
		emptyBlocks.add(new Block(0, 2, 63, 2));
		emptyBlocks.add(new Block(76, 2, 76, 5));
		initFields();
	}
}
