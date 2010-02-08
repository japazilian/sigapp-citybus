package citybus.datamanager;

/**
 * Bus Stop Information.
 * 
 * @author zhang42
 * 
 */
public class BusStop {

	private String name;
	private float x, y;

	public BusStop(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getX() {
		return x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getY() {
		return y;
	}

	// private float gpsX,gpsY,gpsZ;

}
