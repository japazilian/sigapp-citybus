package citybus.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.preference.PreferenceManager;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class busRouteOverlay extends Overlay {
	private SharedPreferences prefs;
	@SuppressWarnings("unused")
	private Context ctx;
	private boolean preferences[];
	private Paint PaintStyles[];

	public busRouteOverlay(Context ctx) {
		super();
		this.ctx = ctx;
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		preferences = new boolean[8];
		PaintStyles = new Paint[8];
		for(int i=0; i<8; i++) {
				preferences[i] = checkPreferences(i);
				Paint p = new Paint();
				setPaintProperties(p, i);
				PaintStyles[i] = p;			
		}
	}

	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
			long when) {

		Projection projection = mapView.getProjection();
		// TODO this go out as part of final Paint[].
		Paint paint = new Paint();
		paint.setAntiAlias(true);

		for (int j = 0; j < 8; j++) {
			// initialize for each loop
			if (!preferences[j])
				continue;
			int[][] currentLoop = getCurLoop(j);
			// TODO use final Paint[] instead of function. More Paint takes more
			// memory, but better than slow performance.
			paint = PaintStyles[j];
			// draw all of the dots for the loop
			for (int i = 0; i < currentLoop.length - 1; i++) {
				GeoPoint geopoint = new GeoPoint((currentLoop[i][1]),
						(currentLoop[i][0]));
				Point pointA = projection.toPixels(geopoint, null);
				geopoint = new GeoPoint((currentLoop[i + 1][1]),
						(currentLoop[i + 1][0]));
				Point pointB = projection.toPixels(geopoint, null);
				if (checkInBounds(pointA, pointB, mapView)) {
					canvas.drawLine(pointA.x, pointA.y, pointB.x, pointB.y,
							paint);
				}
			}
		}
		return super.draw(canvas, mapView, shadow, when);
	}

	private void setPaintProperties(Paint paint, int i) {
		paint.setStrokeWidth(5);
		switch (i) {
		case 0:
			paint.setARGB(100, 199, 182, 0);
			paint.setPathEffect(new DashPathEffect(new float[] { 5, 5 }, 1));
			break;
		case 1:
			paint.setARGB(100, 138, 138, 138);
			paint.setPathEffect(new DashPathEffect(new float[] { 5, 5 }, 1));
			break;
		case 2:
			paint.setARGB(100, 0, 0, 0);
			paint.setPathEffect(new DashPathEffect(new float[] { 5, 5 }, 1));
			break;
		case 3:
			paint.setARGB(100, 15, 179, 0);
			paint.setPathEffect(new DashPathEffect(new float[] { 5, 5 }, 1));
			break;
		case 4:
			paint.setARGB(100, 82, 102, 0);
			paint.setPathEffect(new DashPathEffect(new float[] { 5, 5 }, 1));
			break;
		case 5:
			paint.setARGB(100, 82, 71, 163);
			paint.setPathEffect(new DashPathEffect(new float[] { 5, 5 }, 1));
			break;
		case 6:
			paint.setARGB(100, 255, 179, 0);
			paint.setPathEffect(new DashPathEffect(new float[] { 5, 5 }, 1));
			break;
		case 7:
			paint.setARGB(100, 255, 0, 247);
			paint.setPathEffect(new DashPathEffect(new float[] { 5, 5 }, 1));
			break;
		}

	}

	private int[][] getCurLoop(int i) {
		switch (i) {
		case 0:
			return goldloop;
		case 1:
			return silverloop;
		case 2:
			return blackloop;
		case 3:
			return toweracres;
		case 4:
			return bronzeloop;
		case 5:
			return rossade;
		case 6:
			return nightrider;
		case 7:
			return southcampus;
		}
		return null;
	}

	private boolean checkPreferences(int i) {
		switch (i) {
		case 0:
			return prefs.getBoolean("goldloop", false);
		case 1:
			return prefs.getBoolean("silverloop", false);
		case 2:
			return prefs.getBoolean("blackloop", false);
		case 3:
			return prefs.getBoolean("toweracres", false);
		case 4:
			return prefs.getBoolean("bronzeloop", false);
		case 5:
			return prefs.getBoolean("rossade", false);
		case 6:
			return prefs.getBoolean("nightrider", false);
		case 7:
			return prefs.getBoolean("southcampus", false);
		}
		return false;
	}

	public boolean checkInBounds(Point a, Point b, MapView MV) {
		int h = MV.getHeight();
		int w = MV.getWidth();
		if ((h < a.y) && (h < b.y))
			return false;
		if ((w < a.x) && (w < b.x))
			return false;
		if (0 > a.x && 0 > b.x)
			return false;
		if (0 > a.y && 0 > b.y)
			return false;
		return true;
	}

	public final int[][] goldloop = new int[][] {
			{ -86930496, 40422611, 0000000 }, { -86930496, 40422329, 0000000 },
			{ -86930496, 40422329, 0000000 }, { -86928322, 40422321, 0000000 },
			{ -86927856, 40422150, 0000000 }, { -86926117, 40421028, 0000000 },
			{ -86925781, 40420910, 0000000 }, { -86925400, 40420860, 0000000 },
			{ -86924622, 40420860, 0000000 }, { -86924622, 40420860, 0000000 },
			{ -86924667, 40419338, 0000000 }, { -86924797, 40419159, 0000000 },
			{ -86925163, 40418991, 0000000 }, { -86927063, 40418949, 0000000 },
			{ -86927437, 40419029, 0000000 }, { -86927612, 40419140, 0000000 },
			{ -86927727, 40419350, 0000000 }, { -86927757, 40419739, 0000000 },
			{ -86927971, 40420071, 0000000 }, { -86927971, 40420071, 0000000 },
			{ -86928421, 40419960, 0000000 }, { -86930481, 40419941, 0000000 },
			{ -86930481, 40419941, 0000000 }, { -86930527, 40424259, 0000000 },
			{ -86930527, 40424259, 0000000 }, { -86928940, 40424271, 0000000 },
			{ -86928940, 40424271, 0000000 }, { -86928947, 40427399, 0000000 },
			{ -86928947, 40427399, 0000000 }, { -86924133, 40427410, 0000000 },
			{ -86916641, 40427250, 0000000 }, { -86916641, 40427250, 0000000 },
			{ -86916679, 40431358, 0000000 }, { -86916679, 40431358, 0000000 },
			{ -86914139, 40431381, 0000000 }, { -86914139, 40431381, 0000000 },
			{ -86912407, 40429741, 0000000 }, { -86908157, 40425919, 0000000 },
			{ -86907967, 40425629, 0000000 }, { -86907959, 40424850, 0000000 },
			{ -86908020, 40423988, 0000000 }, { -86908020, 40423988, 0000000 },
			{ -86908188, 40423931, 0000000 }, { -86908409, 40423969, 0000000 },
			{ -86913773, 40424149, 0000000 }, { -86913773, 40424149, 0000000 },
			{ -86913750, 40424709, 0000000 }, { -86913681, 40424839, 0000000 },
			{ -86913673, 40425121, 0000000 }, { -86913681, 40425369, 0000000 },
			{ -86913750, 40425468, 0000000 }, { -86913773, 40425652, 0000000 },
			{ -86913757, 40425991, 0000000 }, { -86913757, 40425991, 0000000 },
			{ -86914574, 40425941, 0000000 }, { -86914726, 40425869, 0000000 },
			{ -86914848, 40425709, 0000000 }, { -86914886, 40425571, 0000000 },
			{ -86914848, 40424171, 0000000 }, { -86914848, 40424171, 0000000 },
			{ -86916641, 40424191, 0000000 }, { -86916641, 40424191, 0000000 },
			{ -86916641, 40427582, 0000000 } };
	public final int[][] silverloop = new int[][] {
			{ -86925758, 40427399, 0000000 }, { -86924133, 40427410, 0000000 },
			{ -86921997, 40427341, 0000000 }, { -86921997, 40427341, 0000000 },
			{ -86921753, 40427792, 0000000 }, { -86921692, 40428028, 0000000 },
			{ -86921532, 40432968, 0000000 }, { -86921532, 40432968, 0000000 },
			{ -86919212, 40432941, 0000000 }, { -86919212, 40432941, 0000000 },
			{ -86919167, 40431400, 0000000 }, { -86919167, 40431400, 0000000 },
			{ -86916679, 40431358, 0000000 }, { -86914139, 40431381, 0000000 },
			{ -86914139, 40431381, 0000000 }, { -86912407, 40429741, 0000000 },
			{ -86908157, 40425919, 0000000 }, { -86907967, 40425629, 0000000 },
			{ -86907959, 40424850, 0000000 }, { -86908020, 40423988, 0000000 },
			{ -86908020, 40423988, 0000000 }, { -86908188, 40423931, 0000000 },
			{ -86908409, 40423969, 0000000 }, { -86913773, 40424149, 0000000 },
			{ -86913773, 40424149, 0000000 }, { -86913750, 40424709, 0000000 },
			{ -86913681, 40424839, 0000000 }, { -86913673, 40425121, 0000000 },
			{ -86913681, 40425369, 0000000 }, { -86913750, 40425468, 0000000 },
			{ -86913773, 40425652, 0000000 }, { -86913757, 40425991, 0000000 },
			{ -86913757, 40425991, 0000000 }, { -86914574, 40425941, 0000000 },
			{ -86914726, 40425869, 0000000 }, { -86914848, 40425709, 0000000 },
			{ -86914886, 40425571, 0000000 }, { -86914848, 40424171, 0000000 },
			{ -86914848, 40424171, 0000000 }, { -86918190, 40424221, 0000000 },
			{ -86918297, 40424179, 0000000 }, { -86919098, 40424191, 0000000 },
			{ -86923248, 40424290, 0000000 }, { -86925743, 40424278, 0000000 },
			{ -86925743, 40424278, 0000000 }, { -86925789, 40425659, 0000000 },
			{ -86925781, 40427399, 0000000 } };
	public final int[][] blackloop = new int[][] {
			{ -86924789, 40435490, 0000000 }, { -86925201, 40434830, 0000000 },
			{ -86925583, 40434422, 0000000 }, { -86926903, 40433762, 0000000 },
			{ -86927094, 40433571, 0000000 }, { -86927193, 40433392, 0000000 },
			{ -86927193, 40433392, 0000000 }, { -86926559, 40433239, 0000000 },
			{ -86926208, 40433201, 0000000 }, { -86925903, 40433208, 0000000 },
			{ -86924858, 40433441, 0000000 }, { -86924454, 40433430, 0000000 },
			{ -86923721, 40433361, 0000000 }, { -86923042, 40433182, 0000000 },
			{ -86922218, 40433022, 0000000 }, { -86921532, 40432968, 0000000 },
			{ -86919212, 40432941, 0000000 }, { -86919212, 40432941, 0000000 },
			{ -86919167, 40431400, 0000000 }, { -86919167, 40431400, 0000000 },
			{ -86916679, 40431358, 0000000 }, { -86914139, 40431381, 0000000 },
			{ -86914139, 40431381, 0000000 }, { -86912407, 40429741, 0000000 },
			{ -86908157, 40425919, 0000000 }, { -86907967, 40425629, 0000000 },
			{ -86907959, 40424850, 0000000 }, { -86908020, 40423988, 0000000 },
			{ -86908020, 40423988, 0000000 }, { -86908188, 40423931, 0000000 },
			{ -86908409, 40423969, 0000000 }, { -86913773, 40424149, 0000000 },
			{ -86913773, 40424149, 0000000 }, { -86913750, 40424709, 0000000 },
			{ -86913681, 40424839, 0000000 }, { -86913673, 40425121, 0000000 },
			{ -86913681, 40425369, 0000000 }, { -86913750, 40425468, 0000000 },
			{ -86913773, 40425652, 0000000 }, { -86913757, 40425991, 0000000 },
			{ -86913757, 40425991, 0000000 }, { -86914574, 40425941, 0000000 },
			{ -86914726, 40425869, 0000000 }, { -86914848, 40425709, 0000000 },
			{ -86914886, 40425571, 0000000 }, { -86914848, 40424171, 0000000 },
			{ -86914848, 40424171, 0000000 }, { -86918190, 40424221, 0000000 },
			{ -86918297, 40424179, 0000000 }, { -86921730, 40424252, 0000000 },
			{ -86921730, 40424252, 0000000 }, { -86921822, 40420509, 0000000 },
			{ -86921822, 40420509, 0000000 }, { -86922173, 40420559, 0000000 },
			{ -86922981, 40420860, 0000000 }, { -86925400, 40420860, 0000000 },
			{ -86925781, 40420910, 0000000 }, { -86926117, 40421028, 0000000 },
			{ -86926514, 40421249, 0000000 }, { -86927856, 40422150, 0000000 },
			{ -86928322, 40422321, 0000000 }, { -86930496, 40422329, 0000000 },
			{ -86930496, 40422329, 0000000 }, { -86930527, 40424259, 0000000 },
			{ -86930527, 40424259, 0000000 }, { -86928940, 40424271, 0000000 },
			{ -86928940, 40424271, 0000000 }, { -86928947, 40427399, 0000000 },
			{ -86928947, 40427399, 0000000 }, { -86924133, 40427410, 0000000 },
			{ -86921997, 40427341, 0000000 }, { -86921997, 40427341, 0000000 },
			{ -86921753, 40427792, 0000000 }, { -86921692, 40428028, 0000000 },
			{ -86921532, 40432968, 0000000 }, { -86921532, 40432968, 0000000 },
			{ -86921631, 40432980, 0000000 }

	};
	public final int[][] toweracres = new int[][] {
			{ -86924789, 40435490, 0000000 }, { -86925201, 40434830, 0000000 },
			{ -86925583, 40434422, 0000000 }, { -86926903, 40433762, 0000000 },
			{ -86927094, 40433571, 0000000 }, { -86927193, 40433392, 0000000 },
			{ -86927193, 40433392, 0000000 }, { -86926559, 40433239, 0000000 },
			{ -86926208, 40433201, 0000000 }, { -86925903, 40433208, 0000000 },
			{ -86924858, 40433441, 0000000 }, { -86924454, 40433430, 0000000 },
			{ -86923721, 40433361, 0000000 }, { -86923042, 40433182, 0000000 },
			{ -86922218, 40433022, 0000000 }, { -86921532, 40432968, 0000000 },
			{ -86919212, 40432941, 0000000 }, { -86919212, 40432941, 0000000 },
			{ -86919167, 40431400, 0000000 }, { -86919167, 40431400, 0000000 },
			{ -86916679, 40431358, 0000000 }, { -86914139, 40431381, 0000000 },
			{ -86914139, 40431381, 0000000 }, { -86912407, 40429741, 0000000 },
			{ -86908157, 40425919, 0000000 }, { -86907967, 40425629, 0000000 },
			{ -86907959, 40424850, 0000000 }, { -86908020, 40423988, 0000000 },
			{ -86908020, 40423988, 0000000 }, { -86908188, 40423931, 0000000 },
			{ -86908409, 40423969, 0000000 }, { -86913773, 40424149, 0000000 },
			{ -86916641, 40424191, 0000000 }, { -86916641, 40424191, 0000000 },
			{ -86916672, 40431259, 0000000 }, { -86916702, 40432671, 0000000 },
			{ -86916763, 40432880, 0000000 }, { -86916763, 40432880, 0000000 },
			{ -86919212, 40432941, 0000000 }, { -86919212, 40432941, 0000000 },
			{ -86919212, 40432892, 0000000 } };
	public final int[][] bronzeloop = new int[][] {
			{ -86887459, 40434509, 0000000 }, { -86887299, 40434368, 0000000 },
			{ -86887299, 40434368, 0000000 }, { -86887161, 40434422, 0000000 },
			{ -86886948, 40434399, 0000000 }, { -86886948, 40434399, 0000000 },
			{ -86886963, 40434212, 0000000 }, { -86887192, 40433559, 0000000 },
			{ -86886841, 40421848, 0000000 }, { -86886841, 40421848, 0000000 },
			{ -86886940, 40421879, 0000000 }, { -86889450, 40421860, 0000000 },
			{ -86893082, 40421799, 0000000 }, { -86893082, 40421799, 0000000 },
			{ -86893120, 40424850, 0000000 }, { -86892776, 40425571, 0000000 },
			{ -86892014, 40426739, 0000000 }, { -86892014, 40426739, 0000000 },
			{ -86891617, 40426529, 0000000 }, { -86891510, 40426399, 0000000 },
			{ -86891449, 40426201, 0000000 }, { -86891441, 40425709, 0000000 },
			{ -86891441, 40425709, 0000000 }, { -86892067, 40425652, 0000000 },
			{ -86893227, 40425301, 0000000 }, { -86893639, 40425240, 0000000 },
			{ -86899483, 40425320, 0000000 }, { -86899483, 40425320, 0000000 },
			{ -86899979, 40425362, 0000000 }, { -86900818, 40425499, 0000000 },
			{ -86902443, 40425999, 0000000 }, { -86903664, 40426491, 0000000 },
			{ -86904602, 40426991, 0000000 }, { -86905640, 40427391, 0000000 },
			{ -86906921, 40427551, 0000000 }, { -86909607, 40427608, 0000000 },
			{ -86909859, 40427551, 0000000 }, { -86909859, 40427441, 0000000 },
			{ -86909859, 40427441, 0000000 }, { -86908157, 40425919, 0000000 },
			{ -86908028, 40425758, 0000000 }, { -86907967, 40425629, 0000000 },
			{ -86907959, 40424850, 0000000 }, { -86908020, 40423988, 0000000 },
			{ -86908020, 40423988, 0000000 }, { -86908188, 40423931, 0000000 },
			{ -86908409, 40423969, 0000000 }, { -86913773, 40424149, 0000000 },
			{ -86913773, 40424149, 0000000 }, { -86913750, 40424709, 0000000 },
			{ -86913681, 40424839, 0000000 }, { -86913673, 40425121, 0000000 },
			{ -86913681, 40425369, 0000000 }, { -86913750, 40425468, 0000000 },
			{ -86913773, 40425652, 0000000 }, { -86913757, 40425991, 0000000 },
			{ -86913757, 40425991, 0000000 }, { -86914574, 40425941, 0000000 },
			{ -86914726, 40425869, 0000000 }, { -86914848, 40425709, 0000000 },
			{ -86914886, 40425571, 0000000 }, { -86914848, 40424171, 0000000 },
			{ -86914848, 40424171, 0000000 }, { -86916641, 40424191, 0000000 },
			{ -86916641, 40424191, 0000000 }, { -86916679, 40431358, 0000000 },
			{ -86916679, 40431358, 0000000 }, { -86917969, 40431389, 0000000 },
			{ -86917969, 40431389, 0000000 }, { -86917961, 40430260, 0000000 },
			{ -86917961, 40430260, 0000000 }, { -86916679, 40430222, 0000000 },
			{ -86916679, 40430222, 0000000 }, { -86916679, 40431358, 0000000 },
			{ -86916679, 40431358, 0000000 }, { -86914139, 40431381, 0000000 },
			{ -86914139, 40431381, 0000000 }, { -86912407, 40429741, 0000000 },
			{ -86909073, 40426720, 0000000 }, { -86909073, 40426720, 0000000 },
			{ -86905640, 40426620, 0000000 }, { -86904716, 40426529, 0000000 },
			{ -86903763, 40426311, 0000000 }, { -86900917, 40425461, 0000000 },
			{ -86899422, 40425240, 0000000 }, { -86899422, 40425240, 0000000 },
			{ -86895477, 40425171, 0000000 }, { -86895477, 40425171, 0000000 },
			{ -86894630, 40425030, 0000000 }, { -86893852, 40424831, 0000000 },
			{ -86893852, 40424831, 0000000 }, { -86894478, 40423740, 0000000 },
			{ -86894524, 40423569, 0000000 }, { -86894661, 40423538, 0000000 },
			{ -86894661, 40423538, 0000000 }, { -86894524, 40423569, 0000000 },
			{ -86894478, 40423740, 0000000 }, { -86893478, 40425541, 0000000 },
			{ -86893478, 40425541, 0000000 }, { -86891640, 40428749, 0000000 },
			{ -86891121, 40429531, 0000000 }, { -86890518, 40430302, 0000000 },
			{ -86889267, 40431580, 0000000 }, { -86887756, 40433029, 0000000 },
			{ -86887520, 40433552, 0000000 }, { -86887428, 40434250, 0000000 },
			{ -86887299, 40434368, 0000000 }, { -86887299, 40434368, 0000000 },
			{ -86887459, 40434509, 0000000 } };
	public final int[][] rossade = new int[][] {
			{ -86919472, 40435982, 0000000 }, { -86919456, 40435829, 0000000 },
			{ -86919708, 40435349, 0000000 }, { -86919769, 40435131, 0000000 },
			{ -86919777, 40434380, 0000000 }, { -86919472, 40433472, 0000000 },
			{ -86919243, 40433201, 0000000 }, { -86919167, 40431400, 0000000 },
			{ -86919167, 40431400, 0000000 }, { -86916679, 40431358, 0000000 },
			{ -86914139, 40431381, 0000000 }, { -86914139, 40431381, 0000000 },
			{ -86912407, 40429741, 0000000 }, { -86908157, 40425919, 0000000 },
			{ -86907967, 40425629, 0000000 }, { -86907959, 40424850, 0000000 },
			{ -86908020, 40423988, 0000000 }, { -86908020, 40423988, 0000000 },
			{ -86908188, 40423931, 0000000 }, { -86908409, 40423969, 0000000 },
			{ -86913773, 40424149, 0000000 }, { -86913773, 40424149, 0000000 },
			{ -86913750, 40424709, 0000000 }, { -86913681, 40424839, 0000000 },
			{ -86913673, 40425121, 0000000 }, { -86913681, 40425369, 0000000 },
			{ -86913750, 40425468, 0000000 }, { -86913773, 40425652, 0000000 },
			{ -86913757, 40425991, 0000000 }, { -86913757, 40425991, 0000000 },
			{ -86914574, 40425941, 0000000 }, { -86914726, 40425869, 0000000 },
			{ -86914848, 40425709, 0000000 }, { -86914886, 40425571, 0000000 },
			{ -86914848, 40424171, 0000000 }, { -86914848, 40424171, 0000000 },
			{ -86916641, 40424191, 0000000 }, { -86916641, 40424191, 0000000 },
			{ -86916702, 40432671, 0000000 }, { -86917084, 40433708, 0000000 },
			{ -86917107, 40434231, 0000000 }, { -86917358, 40435059, 0000000 },
			{ -86918030, 40436100, 0000000 }, { -86918030, 40436100, 0000000 },
			{ -86919281, 40435860, 0000000 } };
	public final int[][] nightrider = new int[][] {
			{ -86924789, 40435490, 0000000 }, { -86925201, 40434830, 0000000 },
			{ -86925583, 40434422, 0000000 }, { -86926903, 40433762, 0000000 },
			{ -86927094, 40433571, 0000000 }, { -86927193, 40433392, 0000000 },
			{ -86927193, 40433392, 0000000 }, { -86926559, 40433239, 0000000 },
			{ -86926208, 40433201, 0000000 }, { -86925903, 40433208, 0000000 },
			{ -86924858, 40433441, 0000000 }, { -86923988, 40433399, 0000000 },
			{ -86922218, 40433022, 0000000 }, { -86921532, 40432968, 0000000 },
			{ -86921532, 40432968, 0000000 }, { -86921700, 40427921, 0000000 },
			{ -86922272, 40426819, 0000000 }, { -86922348, 40426140, 0000000 },
			{ -86922302, 40425880, 0000000 }, { -86921768, 40424789, 0000000 },
			{ -86921730, 40424252, 0000000 }, { -86921799, 40421009, 0000000 },
			{ -86921799, 40421009, 0000000 }, { -86919548, 40420979, 0000000 },
			{ -86919548, 40420979, 0000000 }, { -86919533, 40423450, 0000000 },
			{ -86919472, 40423611, 0000000 }, { -86919151, 40423969, 0000000 },
			{ -86919098, 40424191, 0000000 }, { -86919098, 40424191, 0000000 },
			{ -86918297, 40424179, 0000000 }, { -86918198, 40424129, 0000000 },
			{ -86917908, 40424122, 0000000 }, { -86912399, 40424011, 0000000 },
			{ -86912216, 40423939, 0000000 }, { -86912216, 40423939, 0000000 },
			{ -86912209, 40422161, 0000000 }, { -86912209, 40422161, 0000000 },
			{ -86907501, 40422058, 0000000 }, { -86907501, 40422058, 0000000 },
			{ -86907089, 40423019, 0000000 }, { -86906960, 40423130, 0000000 },
			{ -86906792, 40423168, 0000000 }, { -86906433, 40423069, 0000000 },
			{ -86906433, 40423069, 0000000 }, { -86905548, 40422600, 0000000 },
			{ -86904182, 40422050, 0000000 }, { -86904182, 40422050, 0000000 },
			{ -86903893, 40422100, 0000000 }, { -86902229, 40422039, 0000000 },
			{ -86897682, 40422009, 0000000 }, { -86897682, 40422009, 0000000 },
			{ -86899307, 40422020, 0000000 }, { -86899307, 40422020, 0000000 },
			{ -86899323, 40421879, 0000000 }, { -86899139, 40421452, 0000000 },
			{ -86899208, 40421082, 0000000 }, { -86899902, 40420029, 0000000 },
			{ -86900040, 40419891, 0000000 }, { -86900520, 40419628, 0000000 },
			{ -86900520, 40419628, 0000000 }, { -86900253, 40419239, 0000000 },
			{ -86900017, 40419010, 0000000 }, { -86899780, 40418869, 0000000 },
			{ -86897278, 40417679, 0000000 }, { -86896713, 40417450, 0000000 },
			{ -86896332, 40417351, 0000000 }, { -86895470, 40417309, 0000000 },
			{ -86888618, 40417332, 0000000 }, { -86888618, 40417332, 0000000 },
			{ -86888680, 40419121, 0000000 }, { -86888680, 40419121, 0000000 },
			{ -86891861, 40419151, 0000000 }, { -86895470, 40419121, 0000000 },
			{ -86895470, 40419121, 0000000 }, { -86895493, 40418201, 0000000 },
			{ -86895493, 40418201, 0000000 }, { -86896942, 40418190, 0000000 },
			{ -86897591, 40418240, 0000000 }, { -86898041, 40418339, 0000000 },
			{ -86899727, 40418991, 0000000 }, { -86899887, 40419090, 0000000 },
			{ -86900108, 40419331, 0000000 }, { -86900360, 40419708, 0000000 },
			{ -86900360, 40419708, 0000000 }, { -86900139, 40419830, 0000000 },
			{ -86902069, 40422039, 0000000 }, { -86902229, 40422039, 0000000 },
			{ -86902229, 40422039, 0000000 }, { -86902672, 40422531, 0000000 },
			{ -86903297, 40423679, 0000000 }, { -86903297, 40423679, 0000000 },
			{ -86904327, 40422150, 0000000 }, { -86906509, 40423111, 0000000 },
			{ -86907944, 40423820, 0000000 }, { -86908188, 40423931, 0000000 },
			{ -86908409, 40423969, 0000000 }, { -86913773, 40424149, 0000000 },
			{ -86916641, 40424191, 0000000 }, { -86916641, 40424191, 0000000 },
			{ -86916641, 40427250, 0000000 }, { -86916641, 40427250, 0000000 },
			{ -86921997, 40427341, 0000000 }, { -86921997, 40427341, 0000000 },
			{ -86921951, 40427410, 0000000 } };
	public final int[][] southcampus = new int[][] {
			{ -86936760, 40415192, 0000000 }, { -86936302, 40417339, 0000000 },
			{ -86935799, 40417252, 0000000 }, { -86935043, 40417042, 0000000 },
			{ -86933952, 40416908, 0000000 }, { -86931458, 40416790, 0000000 },
			{ -86930672, 40416771, 0000000 }, { -86930481, 40416809, 0000000 },
			{ -86930481, 40416809, 0000000 }, { -86930481, 40419941, 0000000 },
			{ -86930481, 40419941, 0000000 }, { -86928421, 40419960, 0000000 },
			{ -86927971, 40420071, 0000000 }, { -86927971, 40420071, 0000000 },
			{ -86927757, 40419739, 0000000 }, { -86927727, 40419350, 0000000 },
			{ -86927612, 40419140, 0000000 }, { -86927437, 40419029, 0000000 },
			{ -86927063, 40418949, 0000000 }, { -86925163, 40418991, 0000000 },
			{ -86924797, 40419159, 0000000 }, { -86924667, 40419338, 0000000 },
			{ -86924622, 40420860, 0000000 }, { -86924622, 40420860, 0000000 },
			{ -86922981, 40420860, 0000000 }, { -86922173, 40420559, 0000000 },
			{ -86921822, 40420509, 0000000 }, { -86921822, 40420509, 0000000 },
			{ -86913788, 40420330, 0000000 }, { -86913788, 40420330, 0000000 },
			{ -86913773, 40424149, 0000000 }, { -86913773, 40424149, 0000000 },
			{ -86916641, 40424191, 0000000 }, { -86916641, 40424191, 0000000 },
			{ -86916641, 40427250, 0000000 }, { -86916641, 40427250, 0000000 },
			{ -86924133, 40427410, 0000000 }, { -86925781, 40427399, 0000000 },
			{ -86925781, 40427399, 0000000 }, { -86925789, 40425659, 0000000 },
			{ -86925743, 40424278, 0000000 }, { -86925743, 40424278, 0000000 },
			{ -86930527, 40424259, 0000000 }, { -86930527, 40424259, 0000000 },
			{ -86930481, 40419941, 0000000 } };
}
