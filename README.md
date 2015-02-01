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



## Going Forward

Now that there has been some refactoring implemented into the game code, I have decided to start altering how the gameplay works. I'm not exactly sure where I want to take the game, but the first thing I'm interested in achieving is allowing the ship to reposition its angle.