/*
/ Name: Riley Nadwodny
/ Date: 9/21/2020
/ Description: Tube class that contains an (x,y) for where the tube is on the screen.
/ Contains the width and height of tube.png
*/

import java.awt.Graphics;

abstract class Sprite 
{
    int x, y;
    int width, height;
    String type;
    
    //Functions for all classes that extend Sprite. Tube, Mario, Goomba, and Fireball.
    abstract void update();
    abstract void draw(Graphics g);
    abstract Json marshal();
    abstract void tubeCollision(Sprite s);
    abstract void savePrev();
    
    //Boolean values to detect what an object is. Overriden in their respective classes (isTube() returns true in Tube class)
    boolean isTube() {  return false;   }
    boolean isMario() {     return false;   }
    boolean isGoomba() {    return false;   }
    boolean isFireball() {  return false;   }
    boolean isBrick() { return false;   }

    
}
