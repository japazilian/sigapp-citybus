package citybus.application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openintents.intents.AbstractWikitudeARIntent;
import org.openintents.intents.WikitudeARIntent;
import org.openintents.intents.WikitudePOI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import citybus.datamanager.BusNextComingInfo;
import citybus.datamanager.DBConstants;
import citybus.datamanager.DataManager;
import citybus.datamanager.Time;

public class citybus extends Activity implements OnClickListener, OnTouchListener, OnScrollListener {
	ImageView homeOp;
	ScrollView menuScroll;
	ImageView bgV;
	ImageView labV;
	
	//Scroller scrl;
	
	ImageView vmbutton;
    ImageView plbutton;
    ImageView prbutton;
    ImageView abbutton;
	
	float fy;
	float ly;
	int lab = 1;
	
	Animation push_anim;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// test data manager
		// ArrayList<BusStopGeoTimeInfo> info = DataManager.getRoutineInfoById(
		// this, DataManager.GOLD_LOOP, new Time(Calendar.SUNDAY, 0, 29));
		// for (BusStopGeoTimeInfo i : info) {
		// Log.d("citybus", "time: " + i.timeInfo.toString() + ", name: "
		// + i.geoInfo.name);
		// }

		ArrayList<BusNextComingInfo> info2 = DataManager
				.getNextComingBusInfoByLocation(this, DBConstants.THIRD_UNIV,
						new Time(Calendar.MONDAY, 14, 40), 3);
		for (BusNextComingInfo i : info2) {
			Log.d("citybus", "bus ID=" + i.routeId + ",time="
					+ i.geoTimeInfo.timeInfo.toString());
		}
		
		homeOp = (ImageView)findViewById(R.id.HomeOp);
		menuScroll = (ScrollView)findViewById(R.id.ScrollView01);
		menuScroll.setOnTouchListener(this);
		//menuScroll.setSmoothScrollingEnabled(false);
		bgV = (ImageView)findViewById(R.id.ImageViewBg);
		labV = (ImageView)findViewById(R.id.ImageViewLab);
		//scrl = (Scroller) findViewById(R.id.LinearLayout01);
		
		push_anim = AnimationUtils.loadAnimation(this, R.anim.click_shade);
		
		vmbutton = (ImageView)findViewById(R.id.ViewMapButton);
	    vmbutton.setOnClickListener(this);
	    plbutton = (ImageView)findViewById(R.id.PlanButton);
	    plbutton.setOnClickListener(this);
	    prbutton = (ImageView)findViewById(R.id.PrefButton);
	    prbutton.setOnClickListener(this);
	    abbutton = (ImageView)findViewById(R.id.AboutButton);
	    abbutton.setOnClickListener(this);
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

	public void onScrollStateChanged(AbsListView view, int scrollState){
		return;
	}
	
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		return;
	}
	
	public boolean onTouch(View v, MotionEvent ev) {
		if (ev.getAction() == 0x00000000) {
			fy = ev.getRawY();
		}
		else if (ev.getAction() == 0x00000001) {
			ly = ev.getRawY();
			//menuScroll.abortAnimation();
			//menuScroll.
			
			if(ly > fy) {
				
				bgV.setImageResource(R.drawable.mapuser);
				if (lab == 1) {
					//menuScroll.scrollTo(0, 360);
					abbutton.startAnimation(push_anim);
					lab = 5;
					labV.setBackgroundResource(R.drawable.about_label);
				}
				else if (lab == 2) {
					//menuScroll.scrollTo(0, 0);
					homeOp.startAnimation(push_anim);
					lab = 1;
					labV.setBackgroundResource(R.drawable.home_label);
				}
				else if (lab == 3) {
					//menuScroll.scrollTo(0, 90);
					vmbutton.startAnimation(push_anim);
					lab = 2;
					labV.setBackgroundResource(R.drawable.map_label);
				}
				else if (lab == 4) {
					//menuScroll.scrollTo(0, 180);
					plbutton.startAnimation(push_anim);
					lab = 3;
					labV.setBackgroundResource(R.drawable.plan_label);
				}
				else if (lab == 5) {
					//menuScroll.scrollTo(0, 270);
					prbutton.startAnimation(push_anim);
					lab = 4;
					labV.setBackgroundResource(R.drawable.sche_label);
				}
			}
			else if(ly < fy) {
				
				bgV.setImageResource(R.drawable.cell);
				if (lab == 1) {
					//menuScroll.scrollTo(0, 90);
					vmbutton.startAnimation(push_anim);
					lab = 2;
					labV.setBackgroundResource(R.drawable.map_label);
				}
				else if (lab == 2) {
					//menuScroll.scrollTo(0, 180);
					plbutton.startAnimation(push_anim);
					lab = 3;
					labV.setBackgroundResource(R.drawable.plan_label);
				}
				else if (lab == 3) {
					//menuScroll.scrollTo(0, 270);
					prbutton.startAnimation(push_anim);
					lab = 4;
					labV.setBackgroundResource(R.drawable.sche_label);
				}
				else if (lab == 4) {
					//menuScroll.scrollTo(0, 360);
					abbutton.startAnimation(push_anim);
					lab = 5;
					labV.setBackgroundResource(R.drawable.about_label);
				}
				else if (lab == 5) {
					//menuScroll.scrollTo(0, 0);
					homeOp.startAnimation(push_anim);
					lab = 1;
					labV.setBackgroundResource(R.drawable.home_label);
				}
			}
			
			//focus		
			if (lab == 1) {
				menuScroll.scrollTo(0, 0);
			}
			else if (lab == 2) {
				menuScroll.scrollTo(0, 90);
			}
			else if (lab == 3) {
				menuScroll.scrollTo(0, 180);
			}
			else if (lab == 4) {
				menuScroll.scrollTo(0, 270);
			}
			else if (lab == 5) {
				menuScroll.scrollTo(0, 360);
			}
			
		}
		return false;
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
			case R.id.ViewMapButton:
				Intent i = new Intent(this, citybusMap.class);
				startActivity(i);
				break;
			case R.id.PlanButton: 
				/*
				Builder warning = new AlertDialog.Builder(this); 
				warning.setTitle("Warning"); 
				warning.setIcon(R.drawable.cone); 
				warning.setMessage("Not Implemented.");  
				warning.setNegativeButton("back", null); 
				warning.show(); */
				Intent a = new Intent(this, LargeImageScroller.class);
				startActivity(a);
				break;
			case R.id.PrefButton:
				Intent list = new Intent(this, List2.class);
				startActivity(list);
				break;
			case R.id.AboutButton:
				Builder about = new AlertDialog.Builder(this); 
				about.setTitle("About"); 
				about.setIcon(R.drawable.bulb); 
				about.setMessage("product of SIGAPP.");  
				about.setNegativeButton("back", null); 
				about.show(); 
				break;
			
		
		}
		
	}
	/*
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.options_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ar:
			Intent i = new Intent(this, LargeImageScroller.class);
			startActivity(i);
			return true;
		case R.id.routes:
			Intent k = new Intent(this, Preferences.class);
			startActivity(k);
			return true;

		}
		return false;
	}*/

}
