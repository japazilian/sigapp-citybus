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

	public final static int NIMITZ_MARSHAL = 0;
	public final static int THIRD_UNIV = 1;
	public final static int NORTHWEST_STATE = 2;
	public final static int UNIV_HALL = 3;
	public final static int DISCOVER_PARKLOT = 4;
	public final static int FIRST_MACARTHUR = 5;
	public final static int INTRAMURAL_STATDIUM = 6;
	public final static int EE = 7;
	public final static int CL50 = 8;
	public final static int TOWER_ACRES = 9;
	public final static int PHYS = 10;
	public final static int STEW = 11;
	public final static int SOUTH_LOT = 12;
	public final static int MCCUTCHEON = 13;
	public final static int CUL_DE_SAC = 14;
	public final static int PMU = 15;
	public final static int TOWER_HILLTOP = 16;
	public final static int CANAL_NINE = 17;
	public final static int BROWN_FOUR = 18;
	public final static int ROSS_ADE = 19;
	public final static int BEERING = 20;
	public final static int LILLY = 21;
	public final static int WABASH_LANDING = 22;
	public final static int MAIN_SEVEN = 23;
	public final static int AIRPORT = 24;

	public final static int[][] RoutineIndex = new int[][] {
	// gold
			{ NIMITZ_MARSHAL, THIRD_UNIV, NORTHWEST_STATE, UNIV_HALL,
					THIRD_UNIV, DISCOVER_PARKLOT },
			// silver
			{ FIRST_MACARTHUR, INTRAMURAL_STATDIUM, EE, CL50 },
			// black
			{ TOWER_ACRES, PHYS, STEW, SOUTH_LOT, MCCUTCHEON },
			// tower arces
			{ CUL_DE_SAC, PMU, THIRD_UNIV, TOWER_HILLTOP },
			// bronze
			{ CANAL_NINE, BROWN_FOUR, UNIV_HALL, PHYS },
			// ross ade
			{ ROSS_ADE, PMU, BEERING },
			// night
			{ TOWER_ACRES, LILLY, WABASH_LANDING, MAIN_SEVEN, WABASH_LANDING,
					NORTHWEST_STATE, THIRD_UNIV },
			// south
			{ AIRPORT, SOUTH_LOT, THIRD_UNIV } };

	// do not reorder, make sure no duplicate
	public final static String[] BusStopNames = new String[] {
			"Nimitz & Marshall", "3rd & University", "Northwestern & State",
			"University Hall", "Discovery Park Lot", "1st & MacArthur",
			"Intramural & Stadium", "EE", "Class of 1950", "Tower Acres",
			"Physics", "STEW", "South Lot", "McCutcheon at Purdue West",
			"Cul-de-sac on David Ross Rd.", "PMU", "Tower Dr. & Hilltop Dr.",
			"Canal & 9th", "4th & Brown", "Ross Ade", "Beering Hall",
			"Lilly Hall", "Wabash Landing", "7th & Main", "Airport" };

	// fill in this, order should match BusStopNames, keep number type in
	// float, double might break database
	public final static float[][] BusGpsInfo = new float[][] {
			{ 40.421860f, -86.927363f, 0 }, { 40.427263f, -86.916651f, 0 },
			{ 40.423998f, -86.908022f, 0 }, { 40.425287f, -86.914880f, 0 },
			{ 40.419212f, -86.924743f, 0 }, { 40.425325f, -86.925773f, 0 },
			{ 40.431465f, -86.921554f, 0 }, { 40.428758f, -86.911350f, 0 },
			{ 40.425903f, -86.914700f, 0 }, { 40.434923f, -86.925159f, 0 },
			{ 40.430342f, -86.913030f, 0 }, { 40.425091f, -86.913679f, 0 },
			{ 40.420858f, -86.923257f, 0 }, { 40.424833f, -86.928914f, 0 },
			{ 40.435487f, -86.924783f, 0 }, { 40.424086f, -86.911098f, 0 },
			{ 40.433276f, -86.923268f, 0 }, { 40.434393f, -86.886948f, 0 },
			{ 40.421811f, -86.893090f, 0 }, { 40.435948f, -86.918869f, 0 },
			{ 40.425560f, -86.916616f, 0 }, { 40.424047f, -86.917353f, 0 },
			{ 40.422032f, -86.900981f, 0 }, { 40.419108f, -86.888686f, 0 },
			{ 40.416714f, -86.930485f, 0 } };
}
