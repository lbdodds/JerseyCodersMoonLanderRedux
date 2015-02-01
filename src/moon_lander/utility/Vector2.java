package moon_lander.utility;

/**
 * A utility class for dealing with vectors in two-dimensional space
 * @author Liam Dodds
 *
 */
public class Vector2 {
	
	private double x = 0.0;
	private double y = 0.0	;
	
	public Vector2() {}
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Sets the x and y attributes
	 * @param x
	 * @param y
	 */
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Adds the passed parameters to the x and y values
	 * @param x
	 * @param y
	 */
	public void add(double x, double y) {
		this.x += x;
		this.y += y;
	}
	
	/**
	 * Adds the passed vector to this vector
	 * @param vector
	 */
	public void add(Vector2 vector) {
		this.x += vector.getX();
		this.y += vector.getY();
	}
	
	/**
	 * Scales this vector by the passed scalar
	 * @param scalar
	 */
	public void scale(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
	}
	
	/**
	 * Scales this vector by the passed vector
	 * @param vector
	 */
	public void scale(Vector2 vector) {
		this.x *= vector.getX();
		this.y *= vector.getY();
	}
}
