package citybus.application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import citybus.datamanager.BusStopGeoTimeInfo;
import citybus.datamanager.DataManager;
import citybus.datamanager.Time;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


public class citybusMap extends MapActivity {
	private MapView mapView;
	
	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.mapview);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.getController().setZoom(16); //16
		GeoPoint point = new GeoPoint(40430060, -86918760);
		mapView.getController().setCenter(point);
		//mapView.getLatitudeSpan();
		addBusRoutes();
		addBusStops();
	}
	
	/**
	 * Adds bus routes, we're using one overlay and painting all routes on that one overlay
	 */
	private void addBusRoutes() {
		busRouteOverlay routeOverlay = new busRouteOverlay(getBaseContext());
		mapView.getOverlays().add(routeOverlay);
	}
	
	/**
	 * Adds correct bus stops that need to be drawn then adds the array list to mapview
	 */
	private void addBusStops() {
		
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.busstop);
		busStopItemizedOverlay itemizedoverlay = new busStopItemizedOverlay(drawable, this);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		ArrayList<String> addedStops = new ArrayList<String>();//used to check if I already added that stop (no point in printing again)
		Calendar now = Calendar.getInstance();
		
		for(int j=0; j<8; j++) { //Better way to write this? TODO
			if(!checkPreferences(j, prefs))//check if they put it in the preferences
				continue;
			ArrayList<BusStopGeoTimeInfo> info = DataManager.getRoutineInfoById(//Date might be wierd, check with Fan TODO
					this, j, new Time(now.get(Calendar.DAY_OF_WEEK), now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE)));
			for (BusStopGeoTimeInfo i : info) {
				if(addedStops.contains(i.geoInfo.name))
					continue;
				else
					addedStops.add(i.geoInfo.name);
				
				GeoPoint point = new GeoPoint((int)(i.geoInfo.latitude*Math.pow(10, 6)), (int)(i.geoInfo.longitude*Math.pow(10, 6)));
				OverlayItem overlayitem = new OverlayItem(point, i.geoInfo.name, i.timeInfo.toString());
				itemizedoverlay.addOverlay(overlayitem);
			}
		}
		//Log.d("citybus", "Day: " + now.get(Calendar.DAY_OF_WEEK) + ", Hour: " + now.get(Calendar.HOUR_OF_DAY) + ", Minute: "+now.get(Calendar.MINUTE));
		mapOverlays.add(itemizedoverlay);		
	}
	
	/**
	 * Check to see if it is checked in preferences, better way? TODO
	 */
	private boolean checkPreferences(int i, SharedPreferences prefs) {		
		switch(i) {
		case 0: 
			return prefs.getBoolean("goldloop", false);
		case 1: 
			return prefs.getBoolean("silverloop", false);
		case 2: 
			return prefs.getBoolean("blackloop", false);
		case 3: 
			return prefs.getBoolean("toweracres", false);
		case 4: 
			return prefs.getBoolean("bronzeloop", false);
		case 5: 
			return prefs.getBoolean("rossade", false);
		case 6: 
			return prefs.getBoolean("nightrider", false);
		case 7: 
			return prefs.getBoolean("southcampus", false);
		}
		return false;		
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
