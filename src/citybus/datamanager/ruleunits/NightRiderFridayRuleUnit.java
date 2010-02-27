package citybus.datamanager.ruleunits;

import java.util.ArrayList;

import citybus.datamanager.DBConstants;
import citybus.datamanager.Time;

public class NightRiderFridayRuleUnit extends RuleUnit {

	public NightRiderFridayRuleUnit() {
		routineIndex = DBConstants.RoutineIndex[6];
		stopGap = new int[] { 8, 7, 5, 9, 3, 3 };
		busGap = new int[][] { { 40 }, { 20 } };
		busGapCutoffs = new Time[] { new Time(20, 15) };
		startTime = new Time(18, 15);
		endTime = new Time(3, 35);
		emptyBlocks = new ArrayList<Block>();
		emptyBlocks.add(new Block(4, 0, 7, 2));
		emptyBlocks.add(new Block(22, 4, 22, 6));
		emptyBlocks.add(new Block(24, 1, 24, 6));
		initFields();
	}
}
