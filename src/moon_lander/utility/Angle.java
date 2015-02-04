package moon_lander.utility;

/**
 * Utility class to hold an angle and perform operations on it
 * @author Liam Dodds
 *
 */
public class Angle {
	/**
	 * An internal storage for the angle in degrees
	 */
	private double angle;
	
	public Angle() {}
	public Angle(double angle) {
		this.setAngle(angle);
	}
	/**
	 * @return the angle
	 */
	public double getAngle() {
		return angle;
	}
	/**
	 * @param angle the angle to set
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	/**
	 * Increase the angle
	 * @param step
	 */
	public void increment(double step){
		angle += step;
		
		if(angle > 360) {
			angle = angle - 360;
		}
		
		if(angle < 0) {
			angle = 360 + angle;
		}
	}
	
	/**
	 * Returns the trigonometric cosine of the angle
	 * @return the trigonometric cosine of the angle
	 */
	public double cos() {
		return Math.cos(Math.toRadians(angle));
	}
	
	/**
	 * Returns the trigonometric sine of the angle
	 * @return the trigonometric sine of the angle
	 */
	public double sin() {
		return Math.sin(Math.toRadians(angle));
	}
	
	/**
	 * Returns the trigonometric tangent of the angle
	 * @return the trigonometric tangent of the angle
	 */
	public double tan() {
		return Math.tan(Math.toRadians(angle));
	}
	
	/**
	 * Returns the angle as radians
	 * @return the angle as radians
	 */
	public double toRadians() {
		return Math.toRadians(angle);
	}
}
