package citybus.datamanager;

import java.util.ArrayList;

import android.content.Context;
import citybus.datamanager.ruleunits.RuleParser;
import citybus.datamanager.ruleunits.RuleUnit;

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

	private static final int[] allRoutes = new int[] { GOLD_LOOP, SILVER_LOOP,
			BLACK_LOOP, TOWER_ACRES, BRONZE_LOOP, ROSS_ADE, NIGHT_RIDER,
			SOUTH_CAMPUS_AV_TECH };
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
	 * @param nextComings
	 *            Specify how many next coming schedules you need. 1 will give
	 *            you the immediate upcoming bus, 2 will give you the next
	 *            coming 2 bus information. etc.
	 * @author zhang42
	 * @return
	 */
	public static ArrayList<BusNextComingInfo> getNextComingBusInfoByLocation(
			Context ctx, int busStopId, Time currentTime, int nextComings) {
		if (ctx == null || currentTime == null || busStopId < 0
				|| busStopId >= DBConstants.BusStopNames.length)
			return null;
		if (nextComings <= 0)
			return null;
		if (loops == null) {
			initRoutines(ctx);
		}
		ArrayList<BusNextComingInfo> result = new ArrayList<BusNextComingInfo>();
		for (int routeId : allRoutes) {
			int[] busStops = DBConstants.RoutineIndex[routeId];
			boolean passBusStop = false;
			for (int busStop : busStops) {
				if (busStop == busStopId) {
					passBusStop = true;
					break;
				}
			}
			if (passBusStop) {
				RuleUnit unit = RuleParser.getRoutineRule(routeId,
						currentTime.day);
				int needNextComings = nextComings;
				Time time = new Time(currentTime);
				while (needNextComings > 0) {
					Time startTime = unit.getBusStopOffsetTime(time, busStopId);
					ArrayList<BusStopGeoTimeInfo> routeResult = unit
							.getCompleteRoutineInfo(startTime);
					for (BusStopGeoTimeInfo routeStop : routeResult) {
						if (routeStop.geoInfo.index == busStopId) {
							BusNextComingInfo info = new BusNextComingInfo();
							info.routeId = routeId;
							info.geoTimeInfo = routeStop;
							int timeValue = routeStop.timeInfo.toValue();
							timeValue++;
							if (timeValue >= 24 * 60) {
								timeValue -= 24 * 60;
								time = new Time(timeValue);
								time.day = routeStop.timeInfo.day;
								time.incrementDay();
							} else {
								time = new Time(timeValue);
								time.day = routeStop.timeInfo.day;
							}
							result.add(info);
							break;
						}
					}
					needNextComings--;
				}
			}
		}
		return result;
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
