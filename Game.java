/*
/ Name: Riley Nadwodny
/ Date: 9/11/2020
/ Description: This class initializes the Game itself. It creates a new
/ model, controller, and view. It sets the size, title, adds a key and mouse
/ listener, and a run function. While the game is run, it updates the
/ controller and model, as well as repaints the view.
*/

import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
    
    Model model;
    Controller controller;
    View view;
    
	public Game()
	{
        this.model = new Model();
        this.controller = new Controller(model);
        this.view = new View(controller, model);
        view.addMouseListener(controller);
        this.addKeyListener(controller);
		this.setTitle("Mario!");
		this.setSize(800, 500);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		//Load in map.json upon running
        Json j = Json.load("map.json");
        model.unmarshal(j);
        System.out.println("map.json loaded!");
	}
        
    public void run()
	{
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 40 miliseconds
			try
			{
				Thread.sleep(40);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public static void main(String[] args)
	{
		Game g = new Game();
                g.run();
	}
}
