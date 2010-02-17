package citybus.datamanager.ruleunits;

import java.util.ArrayList;
import java.util.Date;

/**
 * Bus Schedule Rules
 * 
 * @author zhang42
 * 
 */
public class RuleUnit {

	// time interval in minutes between each stop
	protected int[] stopGap;
	// blocks that should be empty, row
	protected ArrayList<Block> emptyBlocks;
	// first bus stop arrive time (always 2nd row, 1st column if special !=null,
	// else 1st row,col)
	protected Date startTime;
	// last bus's first stop arrive time (always last row,1st column)
	protected Date endTime;
	// special times, always 1st row or null
	protected Date[] specialTimes;

	public RuleUnit() {
		stopGap = null;
		emptyBlocks = null;
	}

	protected class Block {
		public int row, column;

		public Block(int r, int c) {
			row = r;
			column = c;
		}
	}
}
