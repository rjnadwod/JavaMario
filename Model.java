/*
/ Name: Riley Nadwodny
/ Date: 9/11/2020
/ Description: Model class that creates the turtle model, current coordinates for
/ the turtle as well as destination coordinates. Updates the location of
/ the turtle model and sets the destination on click.
*/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

class Model
{
        ArrayList<Sprite> sprites;
        Mario mario;

	Model()
	{
            sprites = new ArrayList<Sprite>();
            mario = new Mario(200, 200);
            sprites.add(mario);
	}

	public void update()
	{
        //Loop through each sprite in the sprites ArrayList and update
        for(int i = 0; i < sprites.size(); i++)
        {
            Sprite s = sprites.get(i);
            s.update();
            //If sprite s is a tube, loop through sprites again. If sprite being checked
            //is Mario, Goomba, or Fireball, do tube collision detection
            if (s.isTube())
            {
                for (int j = 0; j < sprites.size(); j++)
                {
                    if (sprites.get(j).isMario())
                        sprites.get(j).tubeCollision(s);
                    if (sprites.get(j).isGoomba())
                        sprites.get(j).tubeCollision(s);
                    if (sprites.get(j).isFireball())
                        sprites.get(j).tubeCollision(s);
                }
            }
            //If Sprite s is a fireball, store s into a Fireball object. Loop through
            //sprites again and if sprite j is a Goomba, store into Goomba g. Call the
            //fireball and goomba collision detection. Call the removeFireball function
            //to remove the Fireball if it goes off the screen in either direction.
            if (s.isFireball())
            {
                Fireball f = (Fireball)s;
                for (int j = 0; j < sprites.size(); j++)
                {
                    if (sprites.get(j).isGoomba())
                    {
                        Goomba g = (Goomba)sprites.get(j);
                        f.goombaCollision(g, f);
                    }
                }
                removeFireball(f);
            }     
        }
    }
    
    //Method to addTube at mouse x and mouse y
    public void addTube(int mouse_x, int mouse_y)
    {
        //Create null tube, t
        Tube t = null;

        //Loop through sprites. If sprite clicked on is tube, store sprite into temp tube.
        for(int i = 0; i < sprites.size(); i++)
        {
            if(sprites.get(i).isTube())
            {
                Tube temp = (Tube)sprites.get(i);
                //If temp tube is clicked, set t to temp and remove t. break from if statement
                if (temp.tubeClicked(mouse_x, mouse_y))
                {
                    t = temp;
                    sprites.remove(t);
                    break;
                }
            }
        }

        //If T is null still, add new tube at mouse x and mouse y
        if (t == null)
        {
            sprites.add(new Tube(mouse_x, mouse_y, this));
        }
    }
    
    //Save tubes and goombas to map.json
    Json marshal()
    {
        Json obj = Json.newObject();
        Json spritesOb = Json.newObject();
        Json tmpList = Json.newList();
        obj.add("sprites", spritesOb);
        spritesOb.add("tubes", tmpList);
        for (int i = 0; i < sprites.size(); i++)
        {
            if(sprites.get(i).isTube())
            {
                Tube t = (Tube)sprites.get(i);
                tmpList.add(t.marshal());
            }
        }

        tmpList = Json.newList();
        spritesOb.add("goombas", tmpList);
        for (int i = 0; i < sprites.size(); i++)
        {
            if(sprites.get(i).isGoomba())
            {
                Goomba g = (Goomba)sprites.get(i);
                tmpList.add(g.marshal());
            }
        }

        return obj;
    }
    
    //Load tubes and goombas from map.json
    void unmarshal(Json obj)
    {
        sprites = new ArrayList<Sprite>();
        sprites.add(mario);
        Json jsonList = obj.get("sprites");
        Json tubesList = jsonList.get("tubes");
        Json goombasList = jsonList.get("goombas");
        //Add Tubes back into sprites
        for (int i = 0; i < tubesList.size(); i++)
        {
            sprites.add(new Tube(tubesList.get(i), this));
        }
        //Add Goombas back into sprites
        for (int i = 0; i < goombasList.size(); i++)
        {
            sprites.add(new Goomba(goombasList.get(i), this));
        }
    }

    //Method to add Goomba at mouse x and mouse y in controller
    public void addGoomba(int x, int y)
    {
        Goomba g = new Goomba(x, y, this);
        sprites.add(g);
    }
        
    //Method to add fireball and x and y in controller
    public void addFireball(int x, int y)
    {
        Fireball f = new Fireball(x, y, this);
        sprites.add(f);
    }

    //Method to remove a Fireball if the specific Fireball is
    //off the screen in either direction
    void removeFireball(Fireball fire)
    {
        Fireball f = fire;
        if (f.x > mario.x + mario.width + 600)
            sprites.remove(f);
        if (f.x < mario.x + mario.width - 200)
            sprites.remove(f);
    }
}