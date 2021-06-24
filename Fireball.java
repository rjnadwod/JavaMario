/*
/ Name: Riley Nadwodny
/ Date: 10/21/2020
/ Description: Fireball class that contains the necessary data and functions for
/ fireballs. Also does collision detection between a goomba and fireball.
*/

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Fireball extends Sprite {
    
    int px, py;
    static BufferedImage fireballImg;
    Model model;
    int verticalVelocity;
    boolean left = false;
    
    public Fireball(int x, int y, Model m)
    {
        this.x = x;
        this.y = y;
        this.model = m;
        this.width = 47;
        this.height = 47;
        if(fireballImg == null)
            fireballImg = View.loadImage("fireball.png");
    }
    
    @Override
    boolean isFireball() 
    {
        return true;
    }
    
    void update()
    {
        //Save previous coordinates of Fireball for collision detection
        savePrev();
        
        //Give fireball gravity and make bounce
        verticalVelocity += 1.2;
        this.y+= verticalVelocity;
        this.y-= 6;
        
        //Don't allow fireball to drop under the ground
        if (y > 400-height)
        {
            verticalVelocity = 0;
            this.y = 400 - height;
        }

        //Don't allow fireball to go off the top of the screen
        if (y < 0)
        this.y = 0;
        
        //If !left (true), move to the right. If left (false), move to the left.
        if (!left)
        this.x+=6;
        if (left)
        this.x-=6;

    }

    void getOutOfTube(Tube tube)
    {
        //left hand side of tube
        if (((x + width) >= tube.x) && ((px + width) <= tube.x)) //maybe use <= and >=
        {
            System.out.println("Left collision detected!");
            x = tube.x - width-1;
        }
        //right hand side of tube
        if ((x <= (tube.x + tube.width)) && (px >= (tube.x + tube.width)))
        {
            System.out.println("Right collision detected!");
            x = tube.x + tube.width+1;
        }
    }

    void tubeCollision(Sprite s)
    {
        //Store tube into s
        Sprite sprite = s;
        boolean collision = true;

                    //Check left, right, top, and bottom bounds
                    if ((x + width) < sprite.x)
                        collision = false;
                    if (x > (sprite.x + sprite.width))
                        collision = false;
                    if ((y + height) < sprite.y)
                        collision = false;
                    if (y > (sprite.y + sprite.height))
                        collision = false;

                    //If true, get the fireball out of the tube and switch directions
                    if (collision == true)
                    {
                        getOutOfTube((Tube)sprite);
                        left = !left;
                    }
    }

    void goombaCollision(Goomba g, Fireball f)
    {
        //Store goomba sprite into g, fireball sprite into f. Set collision to true by default
        Goomba goom = g;
        Fireball fire = f;
        boolean collision = true;

        //Check left, right, top, and bottom of goomba.
        if ((fire.x + fire.width) < goom.x)
                collision = false;
        if (fire.x > (goom.x + goom.width))
                collision = false;
        if ((fire.y + fire.height) < goom.y)
                collision = false;
        if (fire.y > (goom.y + goom.height))
            collision = false;
        
        //If collision is true, remove this fireball and set goomba's onFire boolean to true.
        if (collision == true)
        {
            model.sprites.remove(this);
            goom.onFire = true;
        }

    }

    //Save previous coordinates in update()
    void savePrev()
    {
        px = x;
        py = y;
    }
    
    //No marshal/unmarshal for fireballs
    @Override
    Json marshal() {return null;}
    
    //Draw itself at Mario's x position
    void draw(Graphics g)
    {
        g.drawImage(fireballImg, (x - model.mario.x + model.mario.marioLocation), y, null);
    }
}
