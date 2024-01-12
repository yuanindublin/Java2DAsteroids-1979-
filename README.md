# Java2DAsteroids-1979-
---
The game Asteroids built using JavaFX 17 and Java 19. This is a JavaFX game based on the 1979 version: http://www.freeasteroids.org/.

Check down below for a demo vedio.


## Demo
---

https://github.com/yuanindublin/Java2DAsteroids-1979-/assets/113253182/9ed7d5a7-e826-497d-8466-bde15d14b94f

---

## Requirements

- [JavaFX](https://docs.oracle.com/javase/8/javafx/get-started-tutorial/get_start_apps.htm#BACECIIB)
- [OpenJDK 19](http://jdk.java.net/) (or newer)

---

## Game Description 
Asteroids is a classic arcade game, where a space ship moves through space destroying asteroids and occasional
alien ships. The difficulty of the game increases as the levels progress. There are three types of asteroids:
- **Large:**  These typically move very slowly and are large and therefore easier to shoot. When destroyed, two medium sized asteroids are created. These newly created asteroids will move in random directions and with random speeds but generally faster than the large asteroid was moving.
- **Medium:** These are a bit smaller than the large asteroids and a bit faster too. When destroyed, two small sized asteroids are created. These newly created asteroids will move in random directions and with random speeds but generally faster than the medium asteroid was moving.
- **Small:** These are again smaller and faster than the medium sized asteroids. When destroyed, no new asteroids are created.
Occasionally, an alien ship will appear and move through the screen from one side to the other. During this movement, it will fire at the players ship. The player can either avoid it or attempt to destroy it.

## Movement
The players ship can perform 5 actions:
- **Rotate Right** Rotate the ship in the clockwise direction
- **Rotate Left** Rotate the ship in the anti-clockwise direction
- **Fire** Fire a bullet in the direction the ship is currently pointing Apply Thrust Add speed to the current motion in the direction the ship is currently pointing
- **Hyperspace** Jump Disappear from current location and reappear in a new location on the screen (The new
location should not be in contact with another object)
- **Momentum** When thrust is applied, the ship gains speed moving in the direction it is pointing. If we then stop thrusting, the ship will continue to move at the same speed in the same direction until we apply some thrust in the opposite
direction.
This means that if I apply one second of thrust with the ship pointing up, it will need one second of thrust with the ship pointing down for it to stop. This is then further complicated when the ship is pointed at an angle. When a ship, asteroid or bullet moves into the edge of the screen, it should then appear on the opposite side of the screen moving in the same direction. This means that objects will continue looping through the screen indefinitely. The only objects this is not the case for is bullets, these disappear automatically after travelling a set distance.

## Levels
The game is based on a series of increasingly more difficult levels. Once each is cleared, the next begins automatically. In the first level there will be only one slow moving asteroid, in the second there will be two and so on.\
While the game is being played, some info should be displayed on the screen such as the players current number of lives and their current score.\
When the player is destroyed, either by hitting an asteroid or has been shot by the alien he must be placed back in the game safely. This means that it is either invincible for 2-3 seconds or is placed in a position that is calculated as safe.\
After destruction one of the players lives is removed. On some versions versions of the game the player can regain lives by scoring 10000 points, but this is based on the number of points given for each destroyed asteroid.

---
## *Thank you!*

I just want to thank you due to giving me a chance to my project!

I hope you enjoy it :D
