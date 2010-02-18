package citybus.datamanager.ruleunits;

import java.util.ArrayList;

import android.util.Log;
import citybus.datamanager.BusStopGeoInfo;
import citybus.datamanager.BusStopGeoTimeInfo;
import citybus.datamanager.DBConstants;
import citybus.datamanager.Time;

/**
 * Bus Schedule Rules
 * 
 * @author zhang42
 * 
 */
public class RuleUnit {

	// time interval in minutes between each stop
	protected int[] stopGap;
	// time interval in minutes between each bus at starting stop
	protected int[][] busGap;
	protected Time[] busGapCutoffs;
	// blocks that should be empty, from row,col to row,col
	protected ArrayList<Block> emptyBlocks;
	// first bus stop arrive time (always 2nd row, 1st column if special !=null,
	// else 1st row,cool)
	protected Time startTime;
	// last bus's first stop arrive time (always last row,1st column)
	protected Time endTime;
	// special times, always 1st row or null
	protected Time[] specialTimes;
	// total trip time in minutes
	protected int totalTripTime;
	// number of stops
	protected int stopCount;
	// routine name indices
	protected int[] routineIndex;
	// number of rows for each busGapPattern
	protected int[] rowCounts;

	public RuleUnit() {
		stopGap = null;
		emptyBlocks = null;
		specialTimes = null;
	}

	protected void initFields() {
		stopCount = stopGap.length;
		for (int i = 0; i < stopCount; i++) {
			totalTripTime += stopGap[i];
		}
		int start = startTime.toValue();
		int end = endTime.toValue();
		int[] gap;
		int gapInterval = 0;
		int interval = 0;
		if (endTime.toValue() < start) {
			end += 24 * 60;
		}
		if (busGapCutoffs == null) {
			rowCounts = new int[1];
			interval = end - start;
			gap = busGap[0];
			for (int i = 0; i < gap.length; i++) {
				gapInterval += gap[i];
			}
			rowCounts[0] = (interval / gapInterval) * gap.length;
			interval %= gapInterval;
			for (int value : gap) {
				if (interval >= value) {
					interval -= value;
					rowCounts[0]++;
				}
			}
		} else {
			rowCounts = new int[busGap.length];
			for (int i = 0; i < rowCounts.length; i++) {
				if (i == 0) {
					interval = busGapCutoffs[0].toValue() - start;
				} else if (i == rowCounts.length - 1) {
					interval = end - busGapCutoffs[i - 1].toValue();
				} else {
					interval = busGapCutoffs[i].toValue()
							- busGapCutoffs[i - 1].toValue();
				}
				gap = busGap[i];
				gapInterval = 0;
				for (int j = 0; j < gap.length; j++) {
					gapInterval += gap[j];
				}
				rowCounts[i] = (interval / gapInterval) * gap.length;
				interval %= gapInterval;
				for (int value : gap) {
					if (interval >= value) {
						interval -= value;
						rowCounts[i]++;
					}
				}
			}
		}
		for (int i = 0; i < rowCounts.length; i++) {
			Log.d("citybus", "row pattern " + i + ": count: " + rowCounts[i]);
		}
	}

	public ArrayList<BusStopGeoTimeInfo> getCompleteRoutineInfo(Time time) {
		// make sure time is before start or between start/end
		if (endTime.toValue() < startTime.toValue()) {
			endTime.hour += 24;
		}
		if (time.toValue() < startTime.toValue()) {
			time.hour += 24;
			if (time.toValue() > endTime.toValue())
				time.hour -= 24;
		}
		// find which busGap pattern to use & corresponding start(cutoff) time
		int timeValue = time.toValue();
		int[] busGapPattern = null;
		Time patternStart = startTime;
		int cutoffPoint = -1;
		if (busGapCutoffs != null) {
			int cutoffCnt = busGapCutoffs.length;
			for (cutoffPoint = 0; cutoffPoint < cutoffCnt; cutoffPoint++) {
				if (timeValue < busGapCutoffs[cutoffPoint].toValue()) {
					break;
				} else {
					patternStart = busGapCutoffs[cutoffPoint];
				}
			}
			busGapPattern = busGap[cutoffPoint];
		} else {
			busGapPattern = busGap[0];
		}
		// find which row on the bus schedule table it shoud return
		int totalBusPatternCycleTime = 0;
		int busPatternCnt = busGapPattern.length;
		for (int i = 0; i < busPatternCnt; i++) {
			totalBusPatternCycleTime += busGapPattern[i];
		}
		int interval = timeValue - patternStart.toValue();
		int row = 0;
		if (interval >= 0) {
			if (cutoffPoint != -1 && cutoffPoint != 0) {
				for (int i = 0; i < cutoffPoint; i++) {
					int[] gap = busGap[i];
					int cycleTime = 0;
					int len = gap.length;
					for (int j = 0; j < len; j++) {
						cycleTime += gap[j];
					}
					int gapInterval = (i == 0 ? (busGapCutoffs[i].toValue() - startTime
							.toValue())
							: (busGapCutoffs[i].toValue() - busGapCutoffs[i - 1]
									.toValue()));
					row += (gapInterval / cycleTime) * len;
					row = Math.max(0, row);
					gapInterval %= cycleTime;
					for (int value : gap) {
						if (gapInterval >= value) {
							row++;
							gapInterval -= value;
						} else {
							break;
						}
					}
				}
			}
			row += (interval / totalBusPatternCycleTime) * (busPatternCnt);
			row = Math.max(0, row);
			interval %= totalBusPatternCycleTime;
			for (int value : busGapPattern) {
				if (interval >= value) {
					row++;
					interval -= value;
				} else {
					break;
				}
			}
			if (interval != 0)
				row++;
		}
		// generates routine info
		if (emptyBlocks != null) {
			while (true) {
				boolean allEmpty = true;
				for (Block b : emptyBlocks) {
					for (int i = 0; i < busPatternCnt; i++) {
						if (!b.isEmpty(row, i)) {
							allEmpty = false;
							break;
						}
					}
					if (!allEmpty) {
						break;
					}
				}
				if (!allEmpty)
					break;
				row++;
			}
		}
		int fixedRow = row;
		int usePattern = 0;
		for (usePattern = 0; usePattern < rowCounts.length; usePattern++) {
			if (row >= rowCounts[usePattern]) {
				row -= rowCounts[usePattern];
			} else {
				break;
			}
		}
		if (usePattern >= busGap.length) {
			usePattern--;
		}
		int patternRowCount = busGap[usePattern].length;
		int totalTime = 0;
		for (int i = 0; i < patternRowCount; i++) {
			totalTime += busGap[usePattern][i];
		}
		int start = usePattern == 0 ? startTime.toValue()
				: busGapCutoffs[usePattern - 1].toValue();
		start += (row / patternRowCount) * totalTime;
		row %= patternRowCount;
		for (int i = 0; i < busGap[usePattern].length; i++) {
			if (row > 0) {
				row--;
				start += busGap[usePattern][i];
			} else {
				break;
			}
		}
		ArrayList<BusStopGeoTimeInfo> result = new ArrayList<BusStopGeoTimeInfo>();
		for (int i = 0; i < routineIndex.length; i++) {
			boolean empty = false;
			for (Block b : emptyBlocks) {
				if (b.isEmpty(fixedRow, i)) {
					empty = true;
					break;
				}
			}
			Time timeInfo;
			if (i == 0) {
				timeInfo = new Time(start);
			} else {
				start += stopGap[i - 1];
				timeInfo = new Time(start);
			}
			if (!empty) {
				BusStopGeoTimeInfo info = new BusStopGeoTimeInfo();
				BusStopGeoInfo geo = new BusStopGeoInfo(
						DBConstants.BusGpsInfo[routineIndex[i]][0],
						DBConstants.BusGpsInfo[routineIndex[i]][1],
						DBConstants.BusGpsInfo[routineIndex[i]][2],
						routineIndex[i]);
				info.geoInfo = geo;
				info.timeInfo = timeInfo;
				result.add(info);
			}
		}
		return result;
	}

	protected class Block {
		public int fromRow, fromColumn, toRow, toColumn;

		public Block(int fromRow, int fromColumn, int toRow, int toColumn) {
			this.fromColumn = fromColumn;
			this.fromRow = fromRow;
			this.toColumn = toColumn;
			this.toRow = toRow;
		}

		public boolean isEmpty(int row, int column) {
			return row >= fromRow && row <= toRow && column >= fromColumn
					&& column <= toColumn;
		}
	}
}
