/*
/ Name: Riley Nadwodny
/ Date: 9/11/2020
/ Description: Controller class use to control the turtle for movement.
/ This listens to the moust and keyboard for events and updates the model 
/ destination as necessary.
*/

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
    View view;
    Model model;
    Json ob = Json.newObject();
    boolean keyLeft;
    boolean keyRight;
    boolean keyUp;
    boolean keyDown;
    boolean keySpace;
    boolean keyControl;
    boolean addTubesEditor = false;
    boolean addGoombasEditor = false;
    int limit;
    
	Controller(Model m)
	{
            model = m;
	}

	public void actionPerformed(ActionEvent e)
	{
	}
        
        void setView(View v)
        {
            view = v;
        }
        
        public void mousePressed(MouseEvent e)
	{
        //Add Tube model
        if(addTubesEditor)
        {
            model.addTube(e.getX() + model.mario.x - 100, e.getY());
        }
        //Add Goomba model
        if(addGoombasEditor)
        {
            model.addGoomba(e.getX() + model.mario.x - 100, e.getY());
        }
	}

	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }
        
        public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: 
                keyRight = true; 
                break;
			case KeyEvent.VK_LEFT: 
                keyLeft = true; 
                break;
			case KeyEvent.VK_UP: 
                keyUp = true; 
                break;
			case KeyEvent.VK_DOWN: 
                keyDown = true; 
                break;
            case KeyEvent.VK_SPACE:
                keySpace = true;
                break;
            case KeyEvent.VK_CONTROL:
                keyControl = true;
                if(keyControl = true)
                {
                    //Intentionally designed to be able to spam a snake of fireballs.
                    model.addFireball(model.mario.x + model.mario.width, (model.mario.y + 20));
                    break;
                }
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: 
                keyRight = false; 
                break;
			case KeyEvent.VK_LEFT: 
                keyLeft = false; 
                break;
			case KeyEvent.VK_UP: 
                keyUp = false; 
                break;
			case KeyEvent.VK_DOWN: 
                keyDown = false; 
                break;
            case KeyEvent.VK_SPACE:
                keySpace = false;
                break;
            case KeyEvent.VK_CONTROL:
                keyControl = false;
                break;
		}
        char c = e.getKeyChar();
        //Enable or disable ability to add goombas
        if (c == 'g')
        {
            addGoombasEditor = !(addGoombasEditor);
            System.out.println("Goomba editing " + addGoombasEditor);
        }
        //Enable or disable ability to add or delete tubes
        if (c == 't')
        {
            addTubesEditor = !(addTubesEditor);
            System.out.println("Tube editing " + addTubesEditor);
        }
        //Quit the game
        if (c == 'q')
            System.exit(0);
            //    if (c == 's')
            //    {
            //        model.marshal().save("map.json");
            //        System.out.println("map.json saved!");
            //    }
            //    if (c == 'l')
            //    {
            //        Json j = Json.load("map.json");
            //        model.unmarshal(j);
            //        System.out.println("map.json loaded!");
            //    }
	}

	public void keyTyped(KeyEvent e)
	{
	}

    //Update method for controller. Save all previous sprite locations.
    //If Mario is not moving, make him stand still.
	void update()
    {
        for (int i = 0; i < model.sprites.size(); i++)
        {
            Sprite s = model.sprites.get(i);
            s.savePrev();
        }
        if(keyRight) model.mario.x += 5;
	    if(keyLeft) model.mario.x -= 5;
        if(keyUp || keySpace)
            model.mario.yeet();
        if((keyRight || keyLeft || keyUp || keySpace) == false)
            model.mario.marioImageNum = 1;
	}
}
