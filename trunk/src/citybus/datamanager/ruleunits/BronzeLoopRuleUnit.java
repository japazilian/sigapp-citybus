package citybus.datamanager.ruleunits;

import java.util.ArrayList;

import citybus.datamanager.DBConstants;
import citybus.datamanager.Time;

public class BronzeLoopRuleUnit extends RuleUnit {

	public BronzeLoopRuleUnit() {
		routineIndex = DBConstants.RoutineIndex[4];
		stopGap = new int[] { 5, 12, 5 };
		busGap = new int[][] { { 30 } };
		busGapCutoffs = null;
		startTime = new Time(7, 5);
		endTime = new Time(18, 5);
		emptyBlocks = new ArrayList<Block>();
		emptyBlocks.add(new Block(22, 2, 22, 3));
		initFields();
	}
}
