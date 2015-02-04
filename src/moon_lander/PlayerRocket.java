package moon_lander;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
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
import moon_lander.utility.ScreenLogger;
import moon_lander.utility.Vector2;

/**
 * The space rocket with which player will have to land.
 * 
 * @author www.gametutorial.net
 */

public class PlayerRocket {
    
    /**
     * We use this to generate a random number for starting x coordinate of the rocket.
     */
    private Random random;
 
    /**
     * The coordinates of the rocket
     */
    Point position;
    
    /**
     * Is rocket landed?
     */
    public boolean landed;
    
    /**
     * Has rocket crashed?
     */
    public boolean crashed;
        
    /**
     * Accelerating speed of the rocket.
     */
    private int speedAccelerating;
    /**
     * Stopping/Falling speed of the rocket. Falling speed because, the gravity pulls the rocket down to the moon.
     */
    private int speedStopping;
    
    /**
     * Maximum speed that rocket can have without having a crash when landing.
     */
    public int topLandingSpeed;
    
    /**
     * The velocity of the rocket in two-dimensional space
     */
    private Vector2 velocity;
            
    /**
     * Image of the rocket in air.
     */
    private BufferedImage rocketImg;
    /**
     * Image of the rocket when landed.
     */
    private BufferedImage rocketLandedImg;
    /**
     * Image of the rocket when crashed.
     */
    private BufferedImage rocketCrashedImg;
    /**
     * Image of the rocket fire.
     */
    private BufferedImage rocketFireImg;
    
    /**
     * The width and height of the Rocket
     */
    private Dimension dimensions;
    
    private Angle angle;
    
    /**
     * The Rocket's speed to be translated into velocity
     */
    private double speed;
    
    /**
     * The box that contains the rocket, useful for doing intersects
     */
    private Box2 boundingBox;
    
    
    public PlayerRocket()
    {
        Initialize();
        LoadContent();
        
        // Now that we have rocketImgWidth we set starting x coordinate.
        position.x = random.nextInt(Framework.frameWidth - (int)dimensions.getWidth());
        boundingBox.setLocation(position);
        boundingBox.setSize(dimensions);
    }
    
    
    private void Initialize()
    {
        random = new Random();
        speedAccelerating = 2;
        speedStopping = 1;
        
        topLandingSpeed = 5;
        angle = new Angle();
        position = new Point(0, 10);
        velocity = new Vector2();
        dimensions = new Dimension();
        boundingBox = new Box2(position, dimensions);

        ResetPlayer();
    }
    
    private void LoadContent()
    {
        try
        {
            URL rocketImgUrl = this.getClass().getResource("/moon_lander/resources/images/rocket.png");
            rocketImg = ImageIO.read(rocketImgUrl);
            dimensions.setSize(rocketImg.getWidth(), rocketImg.getHeight());
            
            URL rocketLandedImgUrl = this.getClass().getResource("/moon_lander/resources/images/rocket_landed.png");
            rocketLandedImg = ImageIO.read(rocketLandedImgUrl);
            
            URL rocketCrashedImgUrl = this.getClass().getResource("/moon_lander/resources/images/rocket_crashed.png");
            rocketCrashedImg = ImageIO.read(rocketCrashedImgUrl);
            
            URL rocketFireImgUrl = this.getClass().getResource("/moon_lander/resources/images/rocket_fire.png");
            rocketFireImg = ImageIO.read(rocketFireImgUrl);
        }
        catch (IOException ex) {
            Logger.getLogger(PlayerRocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Here we set up the rocket when we starting a new game.
     */
    public void ResetPlayer()
    {
        landed = false;
        crashed = false;
        
        position.x = random.nextInt(Framework.frameWidth - (int)dimensions.getWidth());
        position.y = 10;
        getVelocity().set(0, 0);
        speed = 0.0;
        boundingBox.setLocation(position);
        boundingBox.setSize(dimensions);
    }
    
    
    /**
     * Here we move the rocket.
     */
    public void Update()
    {
    	speed *= 0.5;
        // Calculating speed for moving up or down.
        if(Canvas.keyboardKeyState(KeyEvent.VK_W))
        	speed += speedAccelerating;

        if(Canvas.keyboardKeyState(KeyEvent.VK_A)) {
        	angle.increment(-5);
        }
        
        if(Canvas.keyboardKeyState(KeyEvent.VK_D)) {
        	angle.increment(+5);
        }
        
        velocity.add(
    		(int)(speed * angle.sin()), 
    		-(int)(speed * angle.cos())
		);
        
        // Moves the rocket.
        position.translate((int)getVelocity().getX(), (int)getVelocity().getY());
        
        boundingBox.setLocation(position);
        boundingBox.setSize(dimensions);
    }
    
    public void Draw(Graphics2D g2d)
    {
    	ScreenLogger.add("Rocket coordinates: " + position.x + ", " + position.y);
    	ScreenLogger.add("Rocket velocity: " + velocity.getX() + ",  " + velocity.getY());
    	ScreenLogger.add("Rocket angle: " + angle.getAngle());
    	ScreenLogger.add("Rocket speed: " + speed);
        
        // If the rocket is landed.
        if(landed)
        {
            g2d.drawImage(rocketLandedImg, position.x, position.y, null);
        }
        // If the rocket is crashed.
        else if(crashed)
        {
            g2d.drawImage(rocketCrashedImg, position.x, boundingBox.bottom() - rocketCrashedImg.getHeight(), null);
        }
        // If the rocket is still in the space.
        else
        {
        	
        	// Store the old transform to revert to later
        	AffineTransform old = g2d.getTransform();
        	
        	// Rotate by the angle around the center point of the sprite
        	g2d.rotate(angle.toRadians(), boundingBox.getCenterX(), boundingBox.getCenterY());
            // If player holds down the W key we draw rocket fire.
            if(Canvas.keyboardKeyState(KeyEvent.VK_W))
                g2d.drawImage(rocketFireImg, boundingBox.left() + 12, boundingBox.bottom() - 10, null);
            g2d.drawImage(rocketImg, position.x, position.y, null);
            
            // Revert the transform
            g2d.setTransform(old);
        }

    }
    
    public Box2 getBoundingBox() {
    	return boundingBox;
    }


	/**
	 * @return the velocity
	 */
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public Dimension getDimensions() {
		return dimensions;
	}
    
}
