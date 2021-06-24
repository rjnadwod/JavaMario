import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;

/*
/ Name: Riley Nadwodny
/ Date: 9/21/2020
/ Description: Goomba class that contains the necessary data and functions
/ for each Goomba.
*/

class Goomba extends Sprite 
{
    int px, py;
    static BufferedImage goombaImg;
    Model model;
    int verticalVelocity;
    boolean flip = false;
    boolean onFire = false;
    //Counter for removing the goomba from the screen.
    int frames;

    public Goomba(int x, int y, Model m)
    {
        this.x = x;
        this.y = y;
        this.width = 37;
        this.height = 45;
        this.model = m;
        frames = 0;
        if (goombaImg == null)
            goombaImg = View.loadImage("goomba.png");
    }

    //Load in goomba from the unmarshaled map.json file
    public Goomba(Json obj, Model m)
    {
        this.x = (int)obj.getLong("x");
        this.y = (int)obj.getLong("y");
        this.width = 37;
        this.height = 45;
        this.model = m;
        if (goombaImg == null)
            goombaImg = View.loadImage("goomba.png");
    }

    @Override
    boolean isGoomba()
    {
        return true;
    }

    //Marshal() method to save Goomba to map.json
    Json marshal()
    {
        Json obj = Json.newObject();
        obj.add("x", x);
        obj.add("y", y);
        return obj;
    }

    void update()
    {
        //Save previous coordinates of Goomba
        savePrev();
        
        //Give Goomba gravity. Does not bounce
        verticalVelocity += 1.2;
        y+= verticalVelocity;
        
        //Do not let Goomba drop under the ground
        if (y > 400-height)
        {
            verticalVelocity = 0;
            y = 400 - height;
        }
        
        //Do not let Goomba go off the top of the screen
        if (y < 0)
            y = 0;
        
        //If !flip (true), go to the right. If flip (false), go to the left.
        if (!flip)
            x+=4;
        if (flip)
            x-=4;

        //If the Goomba is onFire, increment the frame counter. When frames reaches 25 for
        //the specific Goomba, remove this Goomba from the sprites ArrayList.
        if (onFire == true)
        {
            frames++;
            if (frames == 50)
                model.sprites.remove(this);
        }
    }
    
    @Override
    void tubeCollision(Sprite s)
    {
        //Store tube into s
        Sprite sprite = s;
        boolean collision = true;

                    //Check right, left, top, and bottom of tube
                    if ((x + width) < sprite.x)
                        collision = false;
                    if (x > (sprite.x + sprite.width))
                        collision = false;
                    if ((y + height) < sprite.y)
                        collision = false;
                    if (y > (sprite.y + sprite.height))
                        collision = false;

                    //If collision is true, get out of the tube and make the Goomba go the other direction
                    //from where it was previously going
                    if (collision == true)
                    {
                        getOutOfTube((Tube)sprite);
                        flip = !flip;
                    }
    }

    void getOutOfTube(Tube tube)
    {
        //in left hand side of tube
        if (((x + width) >= tube.x) && ((px + width) < tube.x)) //maybe use <= and >=
        {
            System.out.println("Left collision detected!");
            x = tube.x - width-1;
        }
        //right hand side of tube
        if ((x <= (tube.x + tube.width)) && (px > (tube.x + tube.width)))
        {
            System.out.println("Right collision detected!");
            x = tube.x + tube.width+1;
        }
    }

    //Save previous coordinates
    void savePrev()
    {
        px = x;
        py = y;
    }

    void draw(Graphics g)
    {
        //If onFire is false, draw the regular Goomba image
        if (onFire == false)
            g.drawImage(goombaImg, this.x - (model.mario.x - 100), this.y, null);
        //If onFire is true, draw the Goomba on fire image for the individual Goomba.
        if (onFire == true)
            g.drawImage(View.loadImage("goomba_fire.png"), this.x - (model.mario.x - 100), this.y, null);
    }
    
}
