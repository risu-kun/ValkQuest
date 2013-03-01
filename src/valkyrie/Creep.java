package valkyrie;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.XMLPackedSheet;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;

/**
 * Modifies a player object to become a creep object
 * @author C. Scott
 * @version 0.8 *
 */
public class Creep extends Player 
{
	private final int COLOR;
	private Sound[][] sounds;
	private XMLPackedSheet sheet;

  public Creep() throws SlickException 
  {
    super();
    sheet = new XMLPackedSheet("data/creeps.png", "data/creeps.xml");
   
    this.yOffset = 10;
    this.act = STAND;
    this.COLOR = roll.nextInt(6);
    this.sounds = new Sound[3][5];
    this.vIni = 15;
    
    setBounds(new Polygon(new float[] { x, y+3, x + 60,
            y+3, x + 60, y + 60, x, y + 60 }));
    this.healthContainer = new Rectangle(x, this.bounds.getMaxY()+10, hp*3, 5);
    this.healthBar = new Rectangle(x, this.bounds.getMaxY()+10, hp*3, 5);
    
    fillAnims();
    fillSounds();
    
  }
  
/**
 * Fills the animations array with all animations
 */
public void fillAnims()
  {
	  int frameNum;
	  //left
	  for (int frame = 0; frame <6; frame++)
	  {
		  frameNum = frame + 6*COLOR;
		  anims[LEFT][RUN].addFrame(sheet.getSprite("frame" + (frameNum) + ".png" ), 150);
		  anims[LEFT][JUMP].addFrame(sheet.getSprite("frame" + (frameNum) + ".png" ), 150);
	  	  anims[LEFT][FALL].addFrame(sheet.getSprite("frame" + (frameNum) + ".png" ), 150);
	  	  anims[LEFT][STAND].addFrame(sheet.getSprite("frame" + (frameNum + 36) + ".png" ), 150);
	  }
	  	  anims[LEFT][HURT].addFrame(sheet.getSprite("frame" + (COLOR + 72) + ".png"), 150);
	  for (int frame = 78; frame < 82; frame++)
	  {
		  frameNum = frame + 4*COLOR;
		  anims[LEFT][SLASH].addFrame(sheet.getSprite("frame" + (frameNum) + ".png"), 150);
		  
	  }
	  
	  //right
	  for (int frame = 0; frame <6; frame++)
	  {
		  frameNum = frame + 6*COLOR;
		  anims[RIGHT][RUN].addFrame(sheet.getSprite("frame" + (frameNum) + ".png" ).getFlippedCopy(true, false), 150);
		  anims[RIGHT][JUMP].addFrame(sheet.getSprite("frame" + (frameNum) + ".png" ).getFlippedCopy(true, false), 150);
		  anims[RIGHT][FALL].addFrame(sheet.getSprite("frame" + (frameNum) + ".png" ).getFlippedCopy(true, false), 150);
	  	  anims[RIGHT][STAND].addFrame(sheet.getSprite("frame" + (frameNum + 36) + ".png" ).getFlippedCopy(true, false), 150);
	  }
	  	  anims[RIGHT][HURT].addFrame(sheet.getSprite("frame" + (COLOR + 72) + ".png").getFlippedCopy(true,false), 150);
  	for (int frame = 78; frame < 82; frame++)
	  {
		  frameNum = frame + 4*COLOR;
		  anims[RIGHT][SLASH].addFrame(sheet.getSprite("frame" + (frameNum) + ".png").getFlippedCopy(true, false), 150);
		  
	  }
  
  }
  
/**
 * Fills the sounds array with sounds
 * @throws SlickException
 */
public void fillSounds() throws SlickException
  {
	  //hit
	  	for (int i = 0; i < sounds[0].length; i++)
	  		sounds[0][i] = new Sound("data/sounds/gethit" + i + ".wav");
	  //die
	  	for (int i = 0; i < sounds[1].length; i++)
	  		sounds[1][i] = new Sound("data/sounds/death" + i + ".wav");
	  //attack
	  	for (int i = 0; i < 5; i++)
	  		sounds[2][i] = new Sound("data/sounds/cAttack" + i + ".wav");
  }
  
  
  public Sound getSound(int x, int y)
	{
		return sounds[x][y];
	}

}
