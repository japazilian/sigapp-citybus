package citybus.application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openintents.intents.AbstractWikitudeARIntent;
import org.openintents.intents.WikitudeARIntent;
import org.openintents.intents.WikitudePOI;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import citybus.datamanager.BusStopGeoTimeInfo;
import citybus.datamanager.DataManager;
import citybus.datamanager.Time;

public class citybus extends Activity implements OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// test data manager
		ArrayList<BusStopGeoTimeInfo> info = DataManager.getRoutineInfoById(
				this, DataManager.GOLD_LOOP, new Time(Calendar.SUNDAY, 0, 29));
		for (BusStopGeoTimeInfo i : info) {
			Log.d("citybus", "time: " + i.timeInfo.toString() + ", name: "
					+ i.geoInfo.name);
		}
		// Test AR button setup, TODO change once we have real GUI
		Button b = (Button) findViewById(R.id.btn_startar);
		b.setOnClickListener(this);
	}

	/**
	 * startAR opens up the activity for the augmented reality
	 */
	public void startAR() {
		WikitudeARIntent intent = new WikitudeARIntent(this.getApplication(),
				"F606E0CFD83ACC9801914AFF3F7B159C", "Purdue ACM SigApp");
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

	/**
	 * Method createPOIList creates the list of points of interest
	 * 
	 * TODO-I am a bit worried about dynamic refreshing of information, like
	 * changing the time of the next bus
	 * 
	 * @return an array list of the points of interest to be displayed to the
	 *         user in the AR
	 */
	public List<WikitudePOI> createPOIList() {
		List<WikitudePOI> pois = new ArrayList<WikitudePOI>();
		WikitudePOI poi1 = new WikitudePOI(35.683333, 139.766667, 36, "Tokyo",
				"Tokyo is the capital of Japan.");
		poi1.setLink("http://www.tourism.metro.tokyo.jp/");
		WikitudePOI poi2 = new WikitudePOI(
				41.9,
				12.5,
				14,
				"Rome",
				"Rome is the capital of Italy and the country's largest and most populous city, with over 2.7 million residents.");
		WikitudePOI poi3 = new WikitudePOI(
				40.716667,
				-74,
				1,
				"New York",
				"New York is the most populous city in the United States, and the center of the New York metropolitan area.");
		WikitudePOI poi4 = new WikitudePOI(48.208333, 16.373056, 220, "Vienna",
				"Vienna is the capital of the Republic of Austria.");
		pois.add(poi1);
		pois.add(poi2);
		pois.add(poi3);
		pois.add(poi4);
		return pois;
	}
 
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Intent i = new Intent(this, LargeImageScroller.class);
		startActivity(i);
	}
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.options_menu, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.map:
        	Intent i = new Intent(this, LargeImageScroller.class);
    		startActivity(i);
            return true;
        case R.id.pref:
        	Intent k = new Intent(this, Preferences.class);
			startActivity(k);
			return true;

        }
        return false;
    }
    
    

}