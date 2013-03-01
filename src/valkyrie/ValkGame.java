package valkyrie;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
 
/**
 * Not in use -- For when I manage to make this game state-based; as of now it will open 
 * two concurrent gameplaystates.
 * @author C. Scott
 *
 */
public class ValkGame extends StateBasedGame {
 
    public static final int MAINMENUSTATE          = 0;
    public static final int GAMEPLAYSTATE          = 1;
 
    public ValkGame()
    {
        super("Valkyrie");
    }
 
    public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new ValkGame());
 
         app.setDisplayMode(800, 600, false);
         app.start();
    }
 
    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new MainMenuState(MAINMENUSTATE));
        //this.addState(new GameplayState(GAMEPLAYSTATE));
    }
}