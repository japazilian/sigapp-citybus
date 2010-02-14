package citybus.datamanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	 *            currentTime
	 * @return List of BusStopInfo
	 */
	public static List<BusStopGeoTimeInfo> getRoutineInfoById(Context ctx,
			int routineId, Date currentTime) {
		if (currentTime == null) {
			return null;
		}
		if (loops == null) {
			initRoutines(ctx);
		}
		return null;
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
