/*
/ Name: Riley Nadwodny
/ Date: 9/11/2020
/ Description: Creates the view for the game. Creates a button and a function to
/ remove the button upon click. The view paints the game.
*/

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
//import java.io.IOException;
import java.io.File;
//import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel
{
        static BufferedImage tube_image;
        static BufferedImage[] mario_images;
        Model model;

	View(Controller c, Model m)
	{
                model = m;
                c.setView(this);
	}
        
    static BufferedImage loadImage(String filename)
    {
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(new File(filename));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
        return img;
    }
        
    public void paintComponent(Graphics g)
	{
        //draw sky
        g.setColor(new Color(128, 255, 255));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        //draw sprites
        for(int i = 0; i < model.sprites.size(); i++)
        {
            model.sprites.get(i).draw(g);
        }
        
        //draw ground
        g.setColor(new Color(100, 255, 80));
        g.fillRect(0, 400, this.getWidth(), 100);
	}
}
