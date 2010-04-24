package citybus.application;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import citybus.datamanager.BusNextComingInfo;
import citybus.datamanager.DBConstants;
import citybus.datamanager.DataManager;

public class List2 extends ListActivity {
	
	private String[] Busstop={"3:03","300"};
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        
        Button back = (Button)findViewById(R.id.back_button);
        Button next = (Button)findViewById(R.id.next_button);
        
        ArrayList<BusNextComingInfo> info =
            DataManager.getAllTimeForBus(this,
            DataManager.GOLD_LOOP, DBConstants.THIRD_UNIV, Calendar.MONDAY);
        
        String[] Times = new String[info.size()];
        for(int i=0; i<info.size(); i++) {
        	String postfix = "am";
        	if(info.get(i).geoTimeInfo.timeInfo.hour == 0){
        		info.get(i).geoTimeInfo.timeInfo.hour = 12;
        	}
        	else if(info.get(i).geoTimeInfo.timeInfo.hour > 12){
        		info.get(i).geoTimeInfo.timeInfo.hour = info.get(i).geoTimeInfo.timeInfo.hour-12;
        		postfix = "pm";
        	}
        	else if(info.get(i).geoTimeInfo.timeInfo.hour == 12){
        		postfix = "pm";
        	}
        	Times[i] = "" + info.get(i).geoTimeInfo.timeInfo.hour +":"+ info.get(i).geoTimeInfo.timeInfo.minute+" "+postfix;
        }

        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, Times));
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.day_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
	}
}
