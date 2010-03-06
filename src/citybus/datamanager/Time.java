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

	public void decrementDay() {
		switch (day) {
		case Calendar.SUNDAY:
			day = Calendar.SATURDAY;
			break;
		case Calendar.MONDAY:
			day = Calendar.SUNDAY;
			break;
		case Calendar.TUESDAY:
			day = Calendar.MONDAY;
			break;
		case Calendar.WEDNESDAY:
			day = Calendar.TUESDAY;
			break;
		case Calendar.THURSDAY:
			day = Calendar.WEDNESDAY;
			break;
		case Calendar.FRIDAY:
			day = Calendar.THURSDAY;
			break;
		case Calendar.SATURDAY:
			day = Calendar.FRIDAY;
			break;
		}
	}

	public void incrementDay() {
		switch (day) {
		case Calendar.SUNDAY:
			day = Calendar.MONDAY;
			break;
		case Calendar.MONDAY:
			day = Calendar.TUESDAY;
			break;
		case Calendar.TUESDAY:
			day = Calendar.WEDNESDAY;
			break;
		case Calendar.WEDNESDAY:
			day = Calendar.THURSDAY;
			break;
		case Calendar.THURSDAY:
			day = Calendar.FRIDAY;
			break;
		case Calendar.FRIDAY:
			day = Calendar.SATURDAY;
			break;
		case Calendar.SATURDAY:
			day = Calendar.SUNDAY;
			break;
		}
	}

	public String toString() {
		String dayOfWeek = null;
		switch (day) {
		case Calendar.SUNDAY:
			dayOfWeek = "SUNDAY";
			break;
		case Calendar.MONDAY:
			dayOfWeek = "MONDAY";
			break;
		case Calendar.TUESDAY:
			dayOfWeek = "TUESDAY";
			break;
		case Calendar.WEDNESDAY:
			dayOfWeek = "WEDNESDAY";
			break;
		case Calendar.THURSDAY:
			dayOfWeek = "THURSDAY";
			break;
		case Calendar.FRIDAY:
			dayOfWeek = "FRIDAY";
			break;
		case Calendar.SATURDAY:
			dayOfWeek = "SATURDAY";
			break;
		}

		return "day: " + dayOfWeek + "," + hour + ":" + minute;
	}

	public int toValue() {
		return hour * 60 + minute;
	}
}
