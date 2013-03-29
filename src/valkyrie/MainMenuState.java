package valkyrie;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
 
/**
 * Not in use -- For when I manage to make this game state-based; as of now it will open 
 * two concurrent gameplaystates.
 * @author C. Scott
 *
 */
public class MainMenuState extends BasicGameState {
 
    private int stateID = 1;
    private StateBasedGame sbg;
 
    @Override
    public int getID() {
        return stateID;
    }
 
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
    {
    	this.sbg = sbg;
    	gc.setShowFPS(false);
 
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
    {
    	g.setColor(Color.white);
	    g.drawString("Valkyrie Quest", 50, 10);
	 
	    g.drawString("1. Play Game", 50, 100);
	    g.drawString("2. High Scores", 50, 120);
	    g.drawString("3. Quit", 50, 140);
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
    {
 
    }
    
    public void keyReleased(int key, char c) {
        switch(key) {
        case Input.KEY_1:
        	
            sbg.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            break;
        case Input.KEY_2:
            // TODO: Implement later
            break;
        case Input.KEY_3:
            // TODO: Implement later
            break;
        default:
            break;
        }
    }
 
}