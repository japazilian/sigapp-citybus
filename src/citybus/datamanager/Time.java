package citybus.datamanager;

import java.util.Calendar;

/**
 * Represents bus time information in 24 hours format
 * 
 * @author zhang42
 * 
 */
public class Time {

	public int hour;
	public int minute;
	public int day;

	public Time(int time) {
		day = Calendar.MONDAY;
		hour = (time / 60) % 24;
		minute = time % 60;
	}

	public Time(int d, int h, int m) {
		this(h, m);
		this.day = d;
	}

	public Time(int h, int m) {
		this.hour = h;
		this.minute = m;
		this.day = Calendar.MONDAY;
	}

	public String toString() {
		return hour + ":" + minute;
	}

	public int toValue() {
		return hour * 60 + minute;
	}
}
