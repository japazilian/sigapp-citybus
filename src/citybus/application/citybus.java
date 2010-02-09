package citybus.application;

import java.util.ArrayList;
import java.util.List;

import org.openintents.intents.AbstractWikitudeARIntent;
import org.openintents.intents.WikitudeARIntent;
import org.openintents.intents.WikitudePOI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class citybus extends Activity {
	
	/** the callback-intent after pressing any buttons */
    private static final String CALLBACK_INTENT = "wikitudeapi.mycallbackactivity";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button b = (Button) findViewById(R.id.btn_startar);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                citybus.this.startARViewWithIcons();
            }
        });
    }
    
    /**
     * does the same as the basic AR view above, but adds custom icons to the POIs
     */
    void startARViewWithIcons() {

        // Create the basic intent
        WikitudeARIntent intent = prepareIntent();

        // Optionally add a title
        intent.addTitleText("AR app with custom icons");
        intent.setPrintMarkerSubText(false);
        
        // Optionally: Add icons
        //addIcons(intent);

        // And launch the intent
        try {
            intent.startIntent(this);
        } catch (ActivityNotFoundException e) {
            AbstractWikitudeARIntent.handleWikitudeNotFound(this);
        }
    }
    /**
     * prepares a Wikitude AR Intent (e.g. adds the POIs to the view)
     * 
     * @return the prepared intent
     */
    private WikitudeARIntent prepareIntent() {
        // create the intent
        WikitudeARIntent intent = new WikitudeARIntent(this.getApplication(), null, null);
        // add the POIs
        this.addPois(intent);
        // add one menu item
        intent.setMenuItem1("My menu item", citybus.CALLBACK_INTENT);
        intent.setPrintMarkerSubText(true);
        return intent;
    }
    
    /**
     * adds hard-coded POIs to the intent
     * 
     * @param intent
     *            the intent
     */
    private void addPois(WikitudeARIntent intent) {
        WikitudePOI poi1 = new WikitudePOI(35.683333, 139.766667, 36, "Tokyo", "Tokyo is the capital of Japan.");
        poi1.setLink("http://www.tourism.metro.tokyo.jp/");
        poi1.setDetailAction(citybus.CALLBACK_INTENT);
        WikitudePOI poi2 = new WikitudePOI(41.9, 12.5, 14, "Rome",
                "Rome is the capital of Italy and the country's largest and most populous city, with over 2.7 million residents.");
        poi2.setDetailAction(citybus.CALLBACK_INTENT);
        WikitudePOI poi3 = new WikitudePOI(40.716667, -74, 1, "New York",
                "New York is the most populous city in the United States, and the center of the New York metropolitan area.");
        poi3.setDetailAction(citybus.CALLBACK_INTENT);
        WikitudePOI poi4 = new WikitudePOI(48.208333, 16.373056, 220, "Vienna",
                "Vienna is the capital of the Republic of Austria.");
        poi4.setDetailAction(citybus.CALLBACK_INTENT);
        List<WikitudePOI> pois = new ArrayList<WikitudePOI>();

        pois.add(poi1);
        pois.add(poi2);
        pois.add(poi3);
        pois.add(poi4);
        intent.addPOIs(pois);

        ((citybusApplication) this.getApplication()).setPois(pois);
    }

    /**
     * helper-method to add icons to the intent.
     * 
     * @param intent
     *            the intent
     */
    private void addIcons(WikitudeARIntent intent) {
        ArrayList<WikitudePOI> pois = intent.getPOIs();

        Resources res = getResources();
        //pois.get(0).setIconresource(res.getResourceName(R.drawable.flag_japan));
        //pois.get(1).setIconresource(res.getResourceName(R.drawable.flag_italy));
        //pois.get(2).setIconresource(res.getResourceName(R.drawable.flag_usa));
        //pois.get(3).setIconresource(res.getResourceName(R.drawable.flag_austria));
        // to use this, make sure you have the file present on the sdcard
        // pois.get(3).setIconuri("content://com.IconCP/sdcard/flag_austria.png");
    }

    /**
     * {@inheritDoc}
     */
    protected Dialog onCreateDialog(int dialogId) {
        //String message = this.getString(R.string.modelfile_not_found, modelFileName);
        return new AlertDialog.Builder(this).setTitle("File not found").setMessage("model not found")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // do nothing, just dismiss the dialog
                    }
                }).create();
    }
}