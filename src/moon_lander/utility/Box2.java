package moon_lander.utility;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * A utility class that has methods to get the relevent position of the rectangle's sides
 * @author Liam Dodds
 *
 */
public class Box2 extends Rectangle {
	
	/**
	 * Constructor to pass position and dimensions to the Rectangle constructor 
	 * @param position
	 * @param dimensions
	 */
	public Box2(Point position, Dimension dimensions) {
		super(position, dimensions);
	}
	/**
	 * Returns the left-most x coordinate of the rectangle
	 * @return left-most x coordinate of the rectangle
	 */
	public int left() { 
		return this.x; 
	}
	
	/**
	 * Returns the right-most x coordinate of the rectangle
	 * @return right-most x coordinate of the rectangle
	 */
	public int right() { 
		return this.x + this.width; 
	}
	
	/**
	 * Returns the top-most y coordinate of the rectangle
	 * @return top-most y coordinate of the rectangle
	 */
	public int top() {
		return this.y; 
	}
	
	/**
	 * Returns the bottom-most y coordinate of the rectangle
	 * @return bottom-most y coordinate of the rectangle
	 */
	public int bottom() { 
		return this.y + this.height; 
	}
	
	/**
	 * Returns the center point of the rectangle
	 * @return the center point of the rectangle
	 */
	public Point center() { 
		return new Point((int)this.getCenterX(), (int)this.getCenterY()); 
	}
}
