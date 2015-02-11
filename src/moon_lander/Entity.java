package moon_lander;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import moon_lander.utility.Angle;
import moon_lander.utility.Box2;
import moon_lander.utility.Vector2;

public class Entity {
	/**
	 * A random number generator used for anything an entity has need for
	 */
	protected Random random;
	
	/**
	 * The coordinates of the entity on the map
	 */
	protected Point position;
	
	/**
	 * The velocity of the entity in two-dimensional space
	 */
	protected Vector2 velocity;
	
	/**
	 * The width and height of the entity
	 */
	protected Dimension dimensions;
	
	/**
	 * The Bounding Box of the entity
	 */
	protected Box2 boundingBox;
	
	/**
	 * The rotation angle of the entity
	 */
	protected Angle angle;
	
	/**
	 * The speed of the entity, hurtling through space
	 */
	protected double acceleration;
	
	/**
	 * The product to decrease the acceleration by
	 */
	protected double decceleration;
	
	/**
	 * The entity to be drawn to the screen
	 */
	protected BufferedImage image;
	
	/**
	 * The URL of the entity's image
	 */
	protected String imageURL;
	
	public Entity() {
		random = new Random();
		position = new Point(0,0);
		velocity = new Vector2();
		dimensions = new Dimension();
		boundingBox = new Box2(position, dimensions);
		angle = new Angle();
		acceleration = 0.0;
		decceleration = 0.5;
	}
	public Entity(String imageURL) {
		this();
		this.imageURL = imageURL;
		this.LoadContent();
	}
	/**
	 * Loading the entity content
	 */
	private void LoadContent() {
		try {
			if(imageURL != null) {
				URL url = this.getClass().getResource(imageURL);
				image = ImageIO.read(url);
				dimensions.setSize(image.getWidth(), image.getHeight());
			}
		} catch(IOException ex) {
			Logger.getLogger(PlayerRocket.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	/**
	 * The entity logic method
	 */
	public void Update() {

		if(this.boundingBox.left() > Framework.frameWidth) {
			this.position.setLocation(0 - this.boundingBox.width, this.position.y);
		}
		
		if(this.boundingBox.top() > Framework.frameHeight) {
			this.position.setLocation(this.position.x, 0 - this.boundingBox.height);
		}
		
		if(this.boundingBox.right() < 0) {
			this.position.setLocation(Framework.frameWidth, this.position.y);
		}
		
		if(this.boundingBox.bottom() < 0) {
			this.position.setLocation(this.position.x, Framework.frameHeight);
		}
		
		velocity.add(
    		(acceleration * angle.sin()), 
    		-(acceleration * angle.cos())
		);
		
		// Reduce speed
		acceleration *= decceleration;
		
		position.translate((int)Math.round(velocity.getX()), (int)Math.round(velocity.getY()));
        boundingBox.setLocation(position);
        boundingBox.setSize(dimensions);
	}
	
	/**
	 * Drawing the entity to the screen
	 * @param g2d
	 */
	public void Draw(Graphics2D g2d) {
		AffineTransform old = g2d.getTransform();
		g2d.rotate(angle.toRadians(), boundingBox.getCenterX(), boundingBox.getCenterY());
		g2d.drawImage(image, position.x, position.y, null);
		g2d.setTransform(old);
	}
	
	public Boolean intersects(Entity entity) {
		return entity.boundingBox.intersects(this.boundingBox);
	}
	
	public Point getPosition() {
		return position;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public Dimension getDimensions() {
		return dimensions;
	}
	
	public Box2 getBoundingBox() {
		return boundingBox;
	}
	
	public Angle getAngle() {
		return angle;
	}
}
