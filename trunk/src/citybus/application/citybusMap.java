package citybus.application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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
		addBusStops();
		
	}
	public void addBusStops() {
		
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.busstop);
		busStopItemizedOverlay itemizedoverlay = new busStopItemizedOverlay(drawable, this);
		
		ArrayList<BusStopGeoTimeInfo> info = DataManager.getRoutineInfoById(
				this, DataManager.GOLD_LOOP, new Time(Calendar.SUNDAY, 0, 29));
		for (BusStopGeoTimeInfo i : info) {
			GeoPoint point = new GeoPoint((int)(i.geoInfo.latitude*Math.pow(10, 6)), (int)(i.geoInfo.longitude*Math.pow(10, 6)));
			OverlayItem overlayitem = new OverlayItem(point, i.geoInfo.name, i.timeInfo.toString());
			itemizedoverlay.addOverlay(overlayitem);
			Log.d("citybus", "lat: " + point.getLatitudeE6() + ", long: "
					+ point.getLongitudeE6());
		}
		
		mapOverlays.add(itemizedoverlay);		
	}
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
