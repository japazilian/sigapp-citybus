package citybus.datamanager;

/**
 * DataBase related constants
 * 
 * @author zhang42
 * 
 */
public class DBConstants {

	public final static String dbfile = "CityBus.db";
	public final static int dbversion = 1;

	public final static String BusStopTable = "BusStopGeoInfo";
	public final static String BusRoutineId = "RoutineId";
	public final static String LATITUDE = "Latitude";
	public final static String LONGITUDE = "Longitude";
	public final static String ALTITUDE = "Altitude";
	public final static String STOPNAME = "StopName";
	public final static int[][] loopsIndex = new int[][] {
	// gold
			{ 0, 1, 2, 3, 1, 4 },
			// silver
			{ 5, 6, 7, 8 },
			// black
			{ 9, 10, 11, 12, 13 },
			// tower arces
			{ 14, 15, 1, 16 },
			// bronze
			{ 17, 18, 3, 10 },
			// ross ade
			{ 19, 15, 20 },
			// night
			{ 9, 21, 22, 23, 22, 2, 1 },
			// south
			{ 24, 12, 1 } };

	// do not reorder
	public final static String[] BusStopNames = new String[] {
			"Nimitz & Marshall", "Third & University", "Northwestern & State",
			"University Hall", "Discovery Park Lot", "1st & MacArthur",
			"Intramural & Stadium", "EE", "Class of 1950", "Tower Acres",
			"Physics", "STEW", "South Lot", "McCutcheon at Purdue West",
			"Cul-de-sac on David Ross Rd.", "PMU", "Tower Dr. & Hilltop Dr.",
			"Canal & 9th", "4th & Brown", "Ross Ade", "Beering Hall",
			"Lilly Hall", "Wabash Landing", "7th & Main", "Airport" };

	// TODO fill in this, order should match BusStopNames, keep number type in
	// float, double might break database
	public final static float[][] BusGpsInfo = new float[][] {
			{ 0.1f, 0.2f, 0.3f }, { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 },
			{ 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 },
			{ 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 },
			{ 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 },
			{ 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 },
			{ 0, 0, 0 } };
}
