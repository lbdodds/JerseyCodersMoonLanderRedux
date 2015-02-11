package moon_lander;

public class Asteroid extends Entity {
	public Asteroid() {
		super("/moon_lander/resources/images/asteroid.png");
		position.setLocation(random.nextInt(Framework.frameWidth), Framework.frameHeight);
		angle.setAngle(0 + random.nextInt(360));
		acceleration = 2;
	}
}
