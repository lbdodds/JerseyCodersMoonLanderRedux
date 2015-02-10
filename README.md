# Jersey Coders Moon Lander Redux

This is the [Moon Lander Game](http://www.gametutorial.net/article/Keyboard-input---Moon-lander) from Game Tutorial, built upon their Java Framework.

The game is a simple landing game where the aim is to land your rocket onto a landing pad below a certain speed threshold to avoid crashing.

## What has been done

### 1. Refactoring

To start off simple, I have done some refactoring (Altering the internal workings of the code without altering the external behaviour) of the code base to consolidate some variables.

#### 1.1 Position

The `PlayerRocket` class contained two integer variables in order to track its on screen position: `x` and `y`. However, tracking a position in two-dimensional space can also be done via the `Point` class (from java.awt.geom.Point2D), which opens up ease-of-use methods (such as distance and translate).

To change from two integers into the single `Point` class, I removed `x` and `y` and introduced the variable `position`. Then, wherever `x` and `y` were previously used, I replaced them with the relevant attribute or method on the `position` variable. 

#### 1.2 The `Vector2` Class

Going forward into game programming in a two-dimensional space, it becomes necessary for a class that can be used to map vectors out, so we can hold things like velocity in them and also be able to perform operations on the vector (such as scale and adding two vectors together).

To achieve this, I implemented the `Vector2` class to represent a vector in two-dimensional space. Like `Point` it contains an `x` and a `y` attribute, but it stores its internal values as a `double` instead of an `int`, so that speed may be precisely increased if necessary (especially with relation to friction). The `Vector2` class includes `add` and `scale` as two methods for performing transformations upon its attributes, both can be supplied with a pair of precision based values or a `Vector2`.

#### 1.3 Velocity

For speed, the `PlayerRocket` contained two integer variables: `speedX` and `speedY`. Speed and directional velocity can and should be described as a vector, in order for proper scaling and operations to be run on them. So, I replaced both of these variables with a `Vector2` by the name of `velocity`.

The `velocity` then needs to be modified depending on the keystrokes, just as `speedX` and `speedY` were. I replaced the basic addition and subtraction with the `move` method for now.

#### 1.4 Dimensions

The `PlayerRocket` dimensions were contained by two integer variables: `rocketImgHeight` and `rocketImgWidth`. These were both set from the `rocketImg`. So, I introduced the `dimensions` variable to capture the width and the height of the rocket and keep track of it. This was a simple switch out, but `dimensions` variable needed to be accessible from outside of the class.

Due to the need for `dimensions` to be accessible outside, I introduced a getter (`getDimensions`) into the class and modified where the width and height were previously used to use the `dimension` instead.

#### 1.5 Box2

I wanted to introduce a bounding box to the `PlayerRocket` in order to open up things like the `intersects` method in order to determine collision properly. Normally, I would just use a `Rectangle` for the bounding box, but I also wanted some easy methods to determine where each of the sides were. So, I made `Box2`, which extends `Rectangle`.

`Box2`'s main features are the utility methods; `left`, `right`, `top`, `bottom` and `center`. These all just return their respective X or Y coordinates, but it's useful to avoid having to add the width to the X position in order to get the right position.

#### 1.6 Bounding Box

As I talked about in the previous section, a Bounding Box is something that I feel is pretty important. They're helpful for determining collisions and sides. So, I implemented a `Box2` for this purpose called `boundingBox`. The `boundingBox` needed to be kept up to date whenever a change is made to the location, so I made sure to put that in, and then replaced where the `position` was called for the purposes of determining the edges of the `PlayerRocket` with a call to the `boundingBox` instead.

### 2. Screen Logger

In `PlayerRocket` there was a part of the code that wrote out some debug information to the screen. Moving onto the future, I assumed that I may be wanting to write more things to the screen and I didn't want to bother having to deal with remembering the exact placement of what I'd already put on the screen. So, the `ScreenLogger` class does the tracking for me and at the end of the `Draw` function on `Game`, it flushes the `ScreenLogger` to the screen.

### 3. Angle

As part of my plans for changing the game, I decided that it was necessary to implement a way to rotate the `PlayerRocket`. So, I created the `Angle` class for this purpose.

#### 3.1 Angle Class

When doing things with angles, it is common to think about doing things in degrees. However, most actual work with angles is doing radians instead. That often makes things a bit awkward to diagnose if you forget to convert your angle from degrees to radians when you're doing the proper math with angles, as angles are important to trigonometry, which is important to a lot of game math.

The `Angle` class is designed to deal with working with angles in this context. It starts with the `tan`, `cos` and `sin` methods that map to their respective trigonometry functions. Moving on after that, I needed to make sure that there was an easy way to get the angle in radians out, so I implemented the `toRadians` method and that was the `Angle` class finished.

#### 3.2 Implementing the Angle Class

I wanted the player to be able to rotate their rocket. This keys in to future developments. So, I gave the `PlayerRocket` an `Angle` attribute by the name of `angle`. I added it to the `Initialize` method to make sure that it's instantiated when the `PlayerRocket` is made and then moved on to making sure that the player was able to rotate the rocket itself.

For now, I bound the angular movement of the rocket to Q and E. Hitting either of these altered the angle o the rocket by 5 degrees, so that it wasn't a slow turn. The next part was actually apply the angle, which meant transforming the plane to be drawn on, and then reverting it back to its original transform.

This meant setting a transform to rotate the drawing canvas around the center point of the rocket. If the rotation origin is not at the center point, then we start to rotate around {0,0} which just creates some funky behaviour that's not really wanted.

### 3.A Angle Bug Fix

While I was starting on the next stage of work, I noticed a strange behavior when the rocket was rotated to the left. Instead of going from 0 to 355 like it should, it was jumping from 0 to 365, which meant that the angle was making the rocket appear to jump an angle to the right. This was due attempting to do `angle = 360 - angle` in order to wrap the angle around. As the angle was -5, 360 - -5 became 365. So, I fixed this by changing it to `angle = 360 + angle`.

#### 4. Directional Velocity

As I move forward on my goal to completely overhaul the gameplay aspects of this game, I thought it was necessary to implemented directional velocity rather than just a move along the X or Y axis, especially as I had just implemented the `Angle` class. Why would I be able to rotate and then not allow my rocket to move in the direction it's pointing?

To start with, it was clear that simply adding to the velocity was not going to work. Due to the fact that we need to run some trigonometry functions on the speed of the rocket, I created a `double speed` variable on `PlayerRocket` to track the exact speed of the rocket and initialized it in one of the initialization functions.

At the start of update, I reduce the speed by half in order to simulate some form of friction. This isn't actually "realistic" for space, but I didn't want my rocket flinging off to the side just because I held down A for too long. Moving on, I replaced the W listener to increase the speed and removed where it reduced the speed if W was not pressed (that's handled by my faux friction). The only thing that could affect the speed should be pressing the W key, as forward is the only direction for the rocket.

After adding to the speed, I needed to add the directional velocity to the velocity itself. This means that I had to  

Due to the fact that I am now working with directional velocity, I no longer needed to manually change the X position with the A and D keys. I remove the logic from those keys and decided that it was better if they modified the angle instead, so I switched the angle keys from Q and E to A and D.

## Going Forward

Now that there has been some refactoring implemented into the game code, I have decided to start altering how the gameplay works. I'm not exactly sure where I want to take the game, but the first thing I'm interested in achieving is allowing the ship to reposition its angle.