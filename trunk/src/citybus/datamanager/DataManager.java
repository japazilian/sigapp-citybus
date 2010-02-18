package citybus.datamanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import citybus.datamanager.ruleunits.RuleParser;
import citybus.datamanager.ruleunits.RuleUnit;

import android.content.Context;

/**
 * Data Manager. This is central data system for bus schedules and location
 * information
 * 
 * @author zhang42
 * 
 */
public class DataManager {

	public static final int TOTAL_LOOP_COUNT = 8;

	public static final int GOLD_LOOP = 0;
	public static final int SILVER_LOOP = 1;
	public static final int BLACK_LOOP = 2;
	public static final int TOWER_ACRES = 3;
	public static final int BRONZE_LOOP = 4;
	public static final int ROSS_ADE = 5;
	public static final int NIGHT_RIDER = 6;
	public static final int SOUTH_CAMPUS_AV_TECH = 7;
	private static ArrayList<ArrayList<BusStopGeoInfo>> loops = null;

	/**
	 * Gets all bus stop geographical information and the next arriving time
	 * after <b>currentTime</b>
	 * 
	 * @author zhang42
	 * @param ctx
	 *            Context
	 * @param routineId
	 *            Routine's ID
	 * @param currentTime
	 *            specify time, all bus comes after this point
	 * @return ArrayList of BusStopInfo
	 */
	public static ArrayList<BusStopGeoTimeInfo> getRoutineInfoById(Context ctx,
			int routineId, Time currentTime) {
		if (currentTime == null || routineId >= TOTAL_LOOP_COUNT) {
			return null;
		}
		if (loops == null) {
			initRoutines(ctx);
		}
		RuleUnit rule = RuleParser.getRoutineRule(routineId, currentTime.day);
		return rule.getCompleteRoutineInfo(currentTime);
	}

	/**
	 * Gets all immediate incoming buses at specified bus stop after specified
	 * time
	 * 
	 * @param ctx
	 *            Context
	 * @param busStopId
	 *            Bus Stop's Id, as defined in DBConstants
	 * @param currentTime
	 *            specify time, all buses comes after this point
	 * @author zhang42
	 * @return
	 */
	public static ArrayList<BusNextComingInfo> getNextComingBusInfoByLocation(
			Context ctx, int busStopId, Date currentTime) throws Exception {
		if (ctx == null || currentTime == null || busStopId < 0
				|| busStopId >= DBConstants.BusStopNames.length)
			return null;
		if (loops == null) {
			initRoutines(ctx);
		}
		throw new Exception("Not Implemented yet.");
	}

	/**
	 * Initialize static lists
	 * 
	 * @author zhang42
	 */
	private static void initRoutines(Context ctx) {
		loops = new ArrayList<ArrayList<BusStopGeoInfo>>();
		DBManager dbManager = new DBManager(ctx);
		for (int i = 0; i < TOTAL_LOOP_COUNT; i++) {
			loops.add(dbManager.getBusStopsByRoutine(i));
		}
	}
}
