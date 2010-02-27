package citybus.datamanager.ruleunits;

import java.util.ArrayList;

import citybus.datamanager.DBConstants;
import citybus.datamanager.Time;

public class TowerAcresRuleUnit extends RuleUnit {

	public TowerAcresRuleUnit() {
		routineIndex = DBConstants.RoutineIndex[3];
		stopGap = new int[] { 10, 5, 10 };
		busGap = new int[][] { { 10, 20, 10, 15 }, { 5, 10, 15 }, { 30 } };
		busGapCutoffs = new Time[] { new Time(8, 0), new Time(23, 45) };
		startTime = new Time(7, 5);
		endTime = new Time(0, 15);
		emptyBlocks = new ArrayList<Block>();
		emptyBlocks.add(new Block(0, 3, 2, 3));
		emptyBlocks.add(new Block(102, 1, 1000, 3));
		initFields();
	}
}
