package moon_lander.utility;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class Box2 extends Rectangle {
	public Box2(Point position, Dimension dimensions) {
		super(position, dimensions);
	}
	public int left() { return this.x; }
	public int right() { return this.x + this.width; }
	public int top() { return this.y; }
	public int bottom() { return this.y + this.height; }
	public Point center() { return new Point((int)this.getCenterX(), (int)this.getCenterY()); }
}
