package citybus.datamanager.ruleunits;

import java.util.ArrayList;

import citybus.datamanager.DBConstants;
import citybus.datamanager.Time;

public class SouthCampusAVTechRuleUnit extends RuleUnit {

	public SouthCampusAVTechRuleUnit() {
		routineIndex = DBConstants.RoutineIndex[7];
		stopGap = new int[] { 6, 8 };
		busGap = new int[][] { { 20 } };
		busGapCutoffs = null;
		startTime = new Time(7, 10);
		endTime = new Time(17, 50);
		specialTimes = null;
		emptyBlocks = new ArrayList<Block>();
		emptyBlocks.add(new Block(0, 0, 0, 0));
		initFields();
	}
}
