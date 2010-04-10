package citybus.application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openintents.intents.AbstractWikitudeARIntent;
import org.openintents.intents.WikitudeARIntent;
import org.openintents.intents.WikitudePOI;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
		mapView.getController().setZoom(16); // 16
		GeoPoint point = new GeoPoint(40430060, -86918760);
		mapView.getController().setCenter(point);
		// mapView.getLatitudeSpan();
		addBusRoutes();
		addBusStops();
	}

	/**
	 * Adds bus routes, we're using one overlay and painting all routes on that
	 * one overlay
	 */
	private void addBusRoutes() {
		busRouteOverlay routeOverlay = new busRouteOverlay(getBaseContext());
		mapView.getOverlays().add(routeOverlay);
	}

	/**
	 * Adds correct bus stops that need to be drawn then adds the array list to
	 * mapview
	 */
	private void addBusStops() {

		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.busstop);
		busStopItemizedOverlay itemizedoverlay = new busStopItemizedOverlay(
				drawable, this);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		ArrayList<String> addedStops = new ArrayList<String>();// used to check
		// if I already
		// added that
		// stop (no
		// point in
		// printing
		// again)
		Calendar now = Calendar.getInstance();

		for (int j = 0; j < 8; j++) { // Better way to write this? TODO
			if (!checkPreferences(j, prefs))// check if they put it in the
				// preferences
				continue;
			ArrayList<BusStopGeoTimeInfo> info = DataManager
					.getRoutineInfoById(// Date might be wierd, check with Fan
							// TODO
							this, j, new Time(now.get(Calendar.DAY_OF_WEEK),
									now.get(Calendar.HOUR_OF_DAY), now
											.get(Calendar.MINUTE)));
			for (BusStopGeoTimeInfo i : info) {
				if (addedStops.contains(i.geoInfo.name))
					continue;
				else
					addedStops.add(i.geoInfo.name);

				GeoPoint point = new GeoPoint((int) (i.geoInfo.latitude * Math
						.pow(10, 6)), (int) (i.geoInfo.longitude * Math.pow(10,
						6)));
				OverlayItem overlayitem = new OverlayItem(point,
						i.geoInfo.name, i.timeInfo.toString());
				itemizedoverlay.addOverlay(overlayitem);
			}
		}
		// Log.d("citybus", "Day: " + now.get(Calendar.DAY_OF_WEEK) + ", Hour: "
		// + now.get(Calendar.HOUR_OF_DAY) +
		// ", Minute: "+now.get(Calendar.MINUTE));
		mapOverlays.add(itemizedoverlay);
	}

	/**
	 * Check to see if it is checked in preferences, better way? TODO
	 */
	private boolean checkPreferences(int i, SharedPreferences prefs) {
		switch (i) {
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

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.options_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ar:
			startAR();
			return true;
		case R.id.routes:
			Intent k = new Intent(this, Preferences.class);
			startActivity(k);
			return true;

		}
		return false;
	}

	public void startAR() {
		// WikitudeARIntent intent = new WikitudeARIntent(this.getApplication(),
		// "F606E0CFD83ACC9801914AFF3F7B159C", "Purdue ACM SigApp");
		final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			buildAlertMessageNoGps();
			return;
		}

		WikitudeARIntent intent = new WikitudeARIntent(this.getApplication(),
				null, null);
		// create new intent, needs a key that you get after
		// registering with them
		intent.addPOIs(createPOIList()); // Adds the array list to the intent
		try {
			intent.startIntent(this);
			// try starting the new intent (could fail if they don't have
			// wikitude, which is dumb...)
		} catch (ActivityNotFoundException e) {
			// tells them to install wikitude
			AbstractWikitudeARIntent.handleWikitudeNotFound(this);
		}
	}

	private void buildAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Yout GPS seems to be disabled, do you want to enable it?")
				.setCancelable(false).setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									final int id) {
								launchGPSOptions();
							}
						}).setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									@SuppressWarnings("unused") final int id) {
								dialog.cancel();
							}
						});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	private void launchGPSOptions() {
		final ComponentName toLaunch = new ComponentName(
				"com.android.settings", "com.android.settings.SecuritySettings");
		final Intent intent = new Intent(
				Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setComponent(toLaunch);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivityForResult(intent, 0);
	}

	public List<WikitudePOI> createPOIList() {
		List<WikitudePOI> pois = new ArrayList<WikitudePOI>();
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		ArrayList<String> addedStops = new ArrayList<String>();// used to check
		// if I already
		// added that
		// stop (no
		// point in
		// printing
		// again)
		Calendar now = Calendar.getInstance();
		for (int j = 0; j < 8; j++) { // Better way to write this? TODO
			if (!checkPreferences(j, prefs))// check if they put it in the
				// preferences
				continue;
			ArrayList<BusStopGeoTimeInfo> info = DataManager
					.getRoutineInfoById(// Date might be wierd, check with Fan
							// TODO
							this, j, new Time(now.get(Calendar.DAY_OF_WEEK),
									now.get(Calendar.HOUR_OF_DAY), now
											.get(Calendar.MINUTE)));
			for (BusStopGeoTimeInfo i : info) {
				if (addedStops.contains(i.geoInfo.name))
					continue;
				else
					addedStops.add(i.geoInfo.name);

				pois.add(new WikitudePOI(i.geoInfo.latitude,
						i.geoInfo.longitude, 0, i.geoInfo.name));
			}
		}/*
		 * WikitudePOI poi1 = new WikitudePOI(35.683333, 139.766667, 36,
		 * "Tokyo", "Tokyo is the capital of Japan.");
		 * poi1.setLink("http://www.tourism.metro.tokyo.jp/"); WikitudePOI poi2
		 * = new WikitudePOI( 41.9, 12.5, 14, "Rome",
		 * "Rome is the capital of Italy and the country's largest and most populous city, with over 2.7 million residents."
		 * ); WikitudePOI poi3 = new WikitudePOI( 40.716667, -74, 1, "New York",
		 * "New York is the most populous city in the United States, and the center of the New York metropolitan area."
		 * ); WikitudePOI poi4 = new WikitudePOI(48.208333, 16.373056, 220,
		 * "Vienna", "Vienna is the capital of the Republic of Austria.");
		 * pois.add(poi1); pois.add(poi2); pois.add(poi3); pois.add(poi4);
		 */
		return pois;
	}

}
