package citybus.application;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import citybus.datamanager.BusNextComingInfo;
import citybus.datamanager.DBConstants;
import citybus.datamanager.DataManager;
import citybus.datamanager.Time;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class busStopItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	public busStopItemizedOverlay(Drawable defaultMarker, Context context) {
		  super(boundCenterBottom(defaultMarker));
		  mContext = context;
		  mOverlays = new ArrayList<OverlayItem>();
	}
	public busStopItemizedOverlay(Drawable defaultMarker) {
		  super(boundCenterBottom(defaultMarker));
		  mOverlays = new ArrayList<OverlayItem>();
	}
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i);
	}
	@Override
	public int size() {
	  return mOverlays.size();
	}
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  Calendar now = Calendar.getInstance();
	  SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
	  String message = "";
	  int id = -1;
	  boolean firstPrint = true;
	  
	  ArrayList<BusNextComingInfo> info = DataManager.getNextComingBusInfoByLocation(//Date might be weird, check with Fan TODO
				mContext, stopIdByName(item.getTitle()), new Time(now.get(Calendar.DAY_OF_WEEK), 
						now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE)), 4);
	  //Log.d("citybus debug", now.get(Calendar.HOUR_OF_DAY)+":"+ now.get(Calendar.MINUTE));
	  for (BusNextComingInfo i : info) {
		  if(!checkPreferences(i.routeId, prefs))//check if they put it in the preferences
			  continue;
		  if(id != i.routeId) {
			  if(firstPrint)
				  firstPrint = false;
			  else
				  message += "\n";
			  message += loopById(i.routeId) + ":\n";
			  message += "\t[" + formatTime(i) + "]\n";
			  id = i.routeId;
		  }
		  else {
			  message += "\t[" + formatTime(i) + "]\n";
		  }
			  
	  }
	  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(message);
	  dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
              dialog.cancel();
         }
	  });
	  dialog.setNeutralButton("More Detail", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
        	  Intent list = new Intent(mContext, List2.class);
        	  mContext.startActivity(list);
         }
	  });
	  dialog.show();
	  return true;
	}
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
	private String loopById(int i) {		
		switch(i) {
		case 0: 
			return "(12) Gold Loop";
		case 1: 
			return "(13) Silver Loop";
		case 2: 
			return "(14) Black Loop";
		case 3: 
			return "(15) Tower Acres";
		case 4: 
			return "(16) Bronze Loop";
		case 5: 
			return "(17) Ross Ade";
		case 6: 
			return "(18) Night Rider";
		case 7: 
			return "(19) South Campus/AvTech";
		}
		return "";		
	}
	private int stopIdByName(String s) {
		int i=0;
		for(i=0; i<25; i++) {
			if(s.equals(DBConstants.BusStopNames[i]))
				return i;
		}
		return i;
	}
	private String formatTime(BusNextComingInfo i) {
		String message = "";
		if(i.geoTimeInfo.timeInfo.hour == 0) //am but we don't want it to say 0:23am, we want 12:23am
			  message += "12" + String.format(":%02d", i.geoTimeInfo.timeInfo.minute) + " am";
		else if(i.geoTimeInfo.timeInfo.hour == 12) //pm but we don't want to subtract 12
			  message += "12" + String.format(":%02d", i.geoTimeInfo.timeInfo.minute) + " pm";
		else if(i.geoTimeInfo.timeInfo.hour > 12) //pm (between the hours of 13 and 23)
			  message += String.format("%02d", i.geoTimeInfo.timeInfo.hour-12) + String.format(":%02d", i.geoTimeInfo.timeInfo.minute) + " pm";
		else								//am (between the hours of 1 and 11)
			  message += String.format("%02d", i.geoTimeInfo.timeInfo.hour) + String.format(":%02d", i.geoTimeInfo.timeInfo.minute) + " am"; 
		return message;
	}
	@Override
	public void draw(Canvas canvas, MapView arg1, boolean arg2) {
		super.draw(canvas, arg1, arg2);
	}
	
}
