package citybus.datamanager.ruleunits;

import java.util.Calendar;

import citybus.datamanager.DataManager;

/**
 * gets schedule rule for a routine in a specific day
 * 
 * @author zhang42
 * 
 */
public class RuleParser {

	public static RuleUnit getRoutineRule(int routine, int dayInWeek) {
		switch (routine) {
		case DataManager.GOLD_LOOP:
			return new GoldLoopRuleUnit();
		case DataManager.SILVER_LOOP:
			return new SilverLoopRuleUnit();
		case DataManager.BRONZE_LOOP:
			return new BronzeLoopRuleUnit();
		case DataManager.BLACK_LOOP:
			if (dayInWeek == Calendar.SUNDAY) {
				return new BlackLoopSundayRuleUnit();
			} else {
				return new BlackLoopSaturdayRuleUnit();
			}
		case DataManager.TOWER_ACRES:
			return new TowerAcresRuleUnit();
		case DataManager.ROSS_ADE:
			return new RossAdeRuleUnit();
		case DataManager.NIGHT_RIDER:
			if (dayInWeek == Calendar.SATURDAY) {
				return new NightRiderSatRuleUnit();
			} else {
				return new NightRiderFridayRuleUnit();
			}
		case DataManager.SOUTH_CAMPUS_AV_TECH:
			return new SouthCampusAVTechRuleUnit();
		default:
			return null;
		}
	}
}
