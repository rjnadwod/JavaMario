import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;

/*
/ Name: Riley Nadwodny
/ Date: 10/4/2020
/ Description: Mario class that contains the variables for mario's location, physiscs,
/ and the functions for his model.
*/

class Mario extends Sprite
{
    int px, py;
    int width, height;
    int jumps;
    int marioImageNum;
    boolean onTube = false;
    double verticalVelocity;
    static BufferedImage[] mario_images = null;
    int marioLocation;
    
    public Mario(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.width = 60;
        this.height = 95;
        this.type = "mario";
        marioImageNum = 0;
        verticalVelocity = 12.0;
        marioLocation = 100;
        
        //Loads Mario images into the array of mario images
        for (int i = 0; i < 5; i++)
        {
            mario_images = new BufferedImage[5];
                mario_images[0] = View.loadImage("mario1.png");
                mario_images[1] = View.loadImage("mario2.png");
                mario_images[2] = View.loadImage("mario3.png");
                mario_images[3] = View.loadImage("mario4.png");
                mario_images[4] = View.loadImage("mario5.png");
        }
    }
    
    //Mario not saved to map.json
    public Mario(Json obj)
    {}
    
    //Mario not saved to map.json
    Json marshal()
    {
        return null;
    }
    
    @Override
    boolean isMario()
    {
        return true;
    }
    
    //this is the jump function! :D
    void yeet()
    {
        if (jumps < 5)
            verticalVelocity = verticalVelocity - 5;
    }
    
    //Save Mario's previous coordinates
    void savePrev()
    {
        px = x;
        py = y;
    }
    
    void getOutOfTube(Tube tube)
    {
        //in left hand side of tube
        if (((x + width) >= tube.x) && ((px + width) < tube.x)) //maybe use <= and >=
            x = tube.x - width-1;
        //right hand side of tube
        if ((x <= (tube.x + tube.width)) && (px > (tube.x + tube.width)))
            x = tube.x + tube.width+1;
        //above the tube
        if (((y + height) >= tube.y) && ((py + height) < tube.y))
        {
            y = tube.y - height-1;
            jumps = 0;
        }
        //below the tube
        if ((y <= (tube.y + tube.height)) && (py > (tube.y + tube.height)))
            y = tube.y + height+1;
    }

    void tubeCollision(Sprite s)
    {
        //Store sprite into s
        Sprite sprite = s;
        boolean collision = true;

                    if ((x + width) < sprite.x)
                        collision = false;
                    if (x > (sprite.x + sprite.width))
                        collision = false;
                    if ((y + height) < sprite.y)
                        collision = false;
                    if (y > (sprite.y + sprite.height))
                        collision = false;

                    //If collision is true, get out of tube and set vertical velocity to 0.
                    if (collision == true)
                    {
                        getOutOfTube((Tube)sprite);
                        verticalVelocity = 0;
                    }
    }
    
    void update()
    {
        //Increment jump counter and marioImage.
        jumps++;
        marioImageNum++;
        verticalVelocity += 1.2;
        y+= verticalVelocity;
        
        //Don't let Mario fall under the ground. Set jumps = 0 and onTube = false when on solid ground.
        if (y > 400-height)
        {
            verticalVelocity = 0;
            y = 400 - height;
            jumps = 0;
            onTube = false;
        }
        
        //Don't let Mario go off the top of the screen
        if (y < 0)
            y = 0;
        
        //If marioImage is > 4, go back to the first image. Loop through
        if (marioImageNum > 4)
            marioImageNum = 0;
    }
    
    //Draw Mario
    void draw(Graphics g)
    {
        g.drawImage(mario_images[marioImageNum], marioLocation, y, null);
    }
}