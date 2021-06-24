import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;

/*
/ Name: Riley Nadwodny
/ Date: 9/21/2020
/ Description: Tube class that contains an (x,y) for where the tube is on the screen.
/ Contains the width and height of tube.png
*/

class Tube extends Sprite
{
    static BufferedImage tube_image;
    Model model;

    public Tube(int tube_x, int tube_y, Model m) 
    {
        this.x = tube_x;
        this.y = tube_y;
        this.width = 55;
        this.height = 400;
        model = m;
        this.type = "tube";
        if (tube_image == null)
            tube_image = View.loadImage("tube.png");
    }
    
    //Load tube from unmarshaled map.json
    public Tube(Json obj, Model m)
    {
        this.x = (int)obj.getLong("x");
        this.y = (int)obj.getLong("y");
        width = 55;
        height = 400;
        model = m;
        tube_image = View.loadImage("tube.png");
    }
    
    //Save tube to map.json when marshaled
    Json marshal()
    {
        Json obj = Json.newObject();
        obj.add("x", x);
        obj.add("y", y);
        return obj;
    }
    
    //isTube() to confirm that object is, in fact, a tube
    @Override
    boolean isTube()
    {
        return true;
    }
 
    //Method to determine whether or not to add/remove Tube from screen
    boolean tubeClicked(int mouse_x, int mouse_y) 
    {
        if (mouse_x < this.x)
           return false;
        if (mouse_x > this.x + width)
            return false;
        if (mouse_y < this.y)
            return false;
        if (mouse_y > this.y + height)
            return false;
        return true;
    }

    //Tube does not need to detect collision with itself
    @Override
    void tubeCollision(Sprite s)
    {}

    //Tube does not need to save its previous coordinates
    @Override
    void savePrev() {}
    
    //Tube does not need to update
    void update() {}
    
    //Draw Tube
    void draw(Graphics g)
    {
        g.drawImage(tube_image, x - (model.mario.x - 100), y, null);
    }
}
