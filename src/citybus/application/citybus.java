package citybus.application;

import java.util.ArrayList;
import java.util.List;

import org.openintents.intents.AbstractWikitudeARIntent;
import org.openintents.intents.WikitudeARIntent;
import org.openintents.intents.WikitudePOI;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class citybus extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Test AR button setup, TODO change once we have real GUI
        Button b = (Button) findViewById(R.id.btn_startar);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startAR();
            }
        });
    }
    /**
     * startAR opens up the activity for the augmented reality 
     */
    public void startAR() {
    	WikitudeARIntent intent = new WikitudeARIntent(this.getApplication(), 
    			"F606E0CFD83ACC9801914AFF3F7B159C", "Purdue ACM SigApp"); //create new intent, needs a key that you get after registering with them
        intent.addPOIs(createPOIList()); //Adds the array list to the intent
        try {
        	intent.startIntent(this); //try starting the new intent (could fail if they don't have wikitude, which is dumb...)
        }
        catch(ActivityNotFoundException e) {
        	 AbstractWikitudeARIntent.handleWikitudeNotFound(this);//tells them to install wikitude
        }
    }
    /**
     * Method createPOIList creates the list of points of interest
     * 
     * TODO-I am a bit worried about dynamic refreshing of information, like changing the time of the next bus
     * @return 
     * an array list of the points of interest to be displayed to the user in the AR
     */
    public List<WikitudePOI> createPOIList() {
    	List<WikitudePOI> pois = new ArrayList<WikitudePOI>();
    	WikitudePOI poi1 = new WikitudePOI(35.683333, 139.766667, 36, "Tokyo", "Tokyo is the capital of Japan.");
        poi1.setLink("http://www.tourism.metro.tokyo.jp/");
        WikitudePOI poi2 = new WikitudePOI(41.9, 12.5, 14, "Rome",
                "Rome is the capital of Italy and the country's largest and most populous city, with over 2.7 million residents.");
        WikitudePOI poi3 = new WikitudePOI(40.716667, -74, 1, "New York",
                "New York is the most populous city in the United States, and the center of the New York metropolitan area.");
        WikitudePOI poi4 = new WikitudePOI(48.208333, 16.373056, 220, "Vienna",
                "Vienna is the capital of the Republic of Austria.");
        pois.add(poi1);
        pois.add(poi2);
        pois.add(poi3);
        pois.add(poi4);
        return pois;
    }
   
}
