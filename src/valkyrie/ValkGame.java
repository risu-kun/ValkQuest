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
 
    public static final int MAINMENUSTATE          = 1;
    public static final int GAMEPLAYSTATE          = 2;
 
    public ValkGame()
    {
        super("Valkyrie");
    }
 
    public static void main(String[] args) throws SlickException
    {
    		AppGameContainer container = new AppGameContainer(new ValkGame(), 640, 480, false);
    		container.start();
    }
 
    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new MainMenuState());
        this.addState(new GameplayState());
    }
}