package citybus.datamanager;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DataBase related functions
 * 
 * @author zhang42
 * 
 */
public class DBManager extends SQLiteOpenHelper {

	private final static String CreateTable_BusStop_GeoInfo = "CREATE TABLE "
			+ DBConstants.BusStopTable + "(" + DBConstants.BusRoutineId
			+ " INTEGER," + DBConstants.LATITUDE + " REAL,"
			+ DBConstants.LONGITUDE + " REAL," + DBConstants.ALTITUDE
			+ " REAL," + DBConstants.STOPNAME + " INTEGER);";

	public DBManager(Context context) {
		super(context, DBConstants.dbfile, null, DBConstants.dbversion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CreateTable_BusStop_GeoInfo);
		populateEntries(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		dropAllTables(db);
		onCreate(db);
	}

	private void dropAllTables(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.BusStopTable);
	}

	private void populateEntries(SQLiteDatabase db) {
		for (int i = 0; i < DataManager.TOTAL_LOOP_COUNT; i++) {
			int[] busRoutine = DBConstants.loopsIndex[i];
			int busRountineStop = busRoutine.length;
			for (int j = 0; j < busRountineStop; j++) {
				ContentValues values = new ContentValues();
				values.put(DBConstants.BusRoutineId, i);
				values.put(DBConstants.STOPNAME, busRoutine[j]);
				values.put(DBConstants.LATITUDE,
						DBConstants.BusGpsInfo[busRoutine[j]][0]);
				values.put(DBConstants.LONGITUDE,
						DBConstants.BusGpsInfo[busRoutine[j]][1]);
				values.put(DBConstants.ALTITUDE,
						DBConstants.BusGpsInfo[busRoutine[j]][2]);
				db.insert(DBConstants.BusStopTable, null, values);
			}
		}
	}

	public ArrayList<BusStopGeoInfo> getBusStopsByRoutine(int routineId) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(DBConstants.BusStopTable, null,
				DBConstants.BusRoutineId + "=" + routineId, null, null, null,
				null);
		ArrayList<BusStopGeoInfo> routine = new ArrayList<BusStopGeoInfo>();
		while (cursor.moveToNext()) {
			float lat = cursor.getFloat(cursor
					.getColumnIndex(DBConstants.LATITUDE));
			float lon = cursor.getFloat(cursor
					.getColumnIndex(DBConstants.LONGITUDE));
			float alt = cursor.getFloat(cursor
					.getColumnIndex(DBConstants.ALTITUDE));
			int nameIndex = cursor.getInt(cursor
					.getColumnIndex(DBConstants.STOPNAME));
			routine.add(new BusStopGeoInfo(lat, lon, alt, nameIndex));
		}
		cursor.close();
		db.close();
		return routine;
	}
}
