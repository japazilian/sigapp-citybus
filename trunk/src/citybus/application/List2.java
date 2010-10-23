package citybus.application;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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
        
        TextView tv_route = (TextView)findViewById(R.id.tasks_title);
        TextView tv_stop = (TextView)findViewById(R.id.tasks_title1);
        
        tv_route.setText(loopById(getIntent().getIntExtra("route", 1)));
        tv_stop.setText(DBConstants.BusStopNames[getIntent().getIntExtra("stop", 1)]);
        
        int r = getIntent().getIntExtra("route", 1);
        int s = getIntent().getIntExtra("stop", 1);
        
        ArrayList<BusNextComingInfo> info =
            DataManager.getAllTimeForBus(this,
            r, 
            s, 
            Calendar.MONDAY);
        
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
}
