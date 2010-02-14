package citybus.datamanager;

import org.openintents.intents.WikitudePOI;

/**
 * Bus Stop Geographical Information
 * 
 * @author zhang42
 * 
 */
public class BusStopGeoInfo {

	public float latitude, longitude, altitude;
	public String name;
	public int index;

	/**
	 * Constructor for BusStopGeoInfo
	 * 
	 * @param latitude
	 * @param longitude
	 * @param altitude
	 * @param index
	 *            bus stop name's index, see DBConstants.routineIndex
	 * @author zhang42
	 */
	public BusStopGeoInfo(float latitude, float longitude, float altitude,
			int index) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.index = index;
		this.name = DBConstants.BusStopNames[index];
	}

	/**
	 * Convert this class to Wikitude compatible data structure for AR use
	 * 
	 * @author zhang42
	 * @return WikitudePOI
	 */
	public WikitudePOI convertToWikitudePOI() {
		if (latitude <= 0 || longitude <= 0 || altitude <= 0)
			return null;
		else if (name == null || name.length() <= 0)
			return null;
		else
			return new WikitudePOI(latitude, longitude, altitude, name);
	}
}
