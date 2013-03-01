package valkyrie;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.XMLPackedSheet;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;

/**
 * Modifies the player class to create the main player (called Sia after the sprite used, from a 
 * GBA game called "Lady Sia")
 * @author C. Scott
 * @version 0.8.2
 */
public class Sia extends Player
{

	private Sound[][] sounds;
	
	public Sia() throws SlickException 
	{
		super();
		
		setBounds(new Polygon(new float[] { x, y, x + 30,
		        y, x + 30, y + 42, x, y + 42 }));
		
		
		sheet = new XMLPackedSheet("data/ladysia.png", "data/ladysia.xml");
		fillAnims();
		this.yOffset = 2;
		this.act = 4;
			
	    
	    this.sounds = new Sound[6][5];
	    this.vIni = 15;
	    fillSounds();
	    this.healthContainer = new Rectangle(x, this.bounds.getMaxY()+10, hp*3, 5);
	    this.healthBar = new Rectangle(x, this.bounds.getMaxY()+10, hp*3, 5);
		
	}
	
	/**
	 * Fills the animations array with all animations
	 */
	private void fillAnims()
	{
		//right-facing
			//slash
			for (int frm = 183; frm < 186; frm++)
				anims[RIGHT][SLASH].addFrame(sheet.getSprite("frame" + frm + ".png"), 100);		
				anims[RIGHT][SLASH].setLooping(false);
			//run
			for (int frm = 12; frm <19; frm++)
				anims[RIGHT][RUN].addFrame(sheet.getSprite("frame" + frm + ".png"), 100);
			//jump
			for (int frm = 158; frm <166; frm++)
				anims[RIGHT][JUMP].addFrame(sheet.getSprite("frame" + frm + ".png"), 150);
			//fall
			for (int frm = 165; frm <167; frm++)
				anims[RIGHT][FALL].addFrame(sheet.getSprite("frame" + frm + ".png"), 150);
			//stand
			for (int frm = 0; frm <6; frm++)
				anims[RIGHT][STAND].addFrame(sheet.getSprite("frame" + frm + ".png"), 150);
			//hurt
			for (int frm = 241; frm <243; frm++)
				anims[RIGHT][HURT].addFrame(sheet.getSprite("frame" + frm + ".png"), 150);
			anims[RIGHT][HURT].setLooping(false);
			anims[RIGHT][HURT].setAutoUpdate(true);
			//die
			for (int frm = 243; frm < 251; frm++)
				anims[RIGHT][DIE].addFrame(sheet.getSprite("frame" + frm + ".png"), 150);
			anims[RIGHT][DIE].setLooping(false);
			//put hair up
			for (int frm = 34; frm < 70; frm++)
				anims[RIGHT][CLBR].addFrame(sheet.getSprite("frame" + frm + ".png"), 120);
			for (int frm = 0; frm <6; frm++)
				anims[RIGHT][CLBR].addFrame(sheet.getSprite("frame" + frm + ".png"), 150);
			anims[RIGHT][CLBR].setLooping(false);
			
			
		//left-facing
			//slash
			for (int frm = 183; frm < 186; frm++)
				anims[LEFT][SLASH].addFrame(sheet.getSprite("frame" + frm + ".png").getFlippedCopy(true, false), 100);
				anims[LEFT][SLASH].setLooping(false);
			//run
			for (int frm = 12; frm <19; frm++)
				anims[LEFT][RUN].addFrame(sheet.getSprite("frame" + frm + ".png").getFlippedCopy(true, false), 100);
			//jump
			for (int frm = 158; frm <166; frm++)
				anims[LEFT][JUMP].addFrame(sheet.getSprite("frame" + frm + ".png").getFlippedCopy(true, false), 150);
			//fall
			for (int frm = 165; frm <167; frm++)
				anims[LEFT][FALL].addFrame(sheet.getSprite("frame" + frm + ".png").getFlippedCopy(true, false), 150);
			//stand
			for (int frm = 0; frm <6; frm++)
				anims[LEFT][STAND].addFrame(sheet.getSprite("frame" + frm + ".png").getFlippedCopy(true, false), 150);
			//hurt
			for (int frm = 241; frm <243; frm++)
				anims[LEFT][HURT].addFrame(sheet.getSprite("frame" + frm + ".png").getFlippedCopy(true, false), 150);
			anims[LEFT][HURT].setLooping(false);
			anims[LEFT][HURT].setAutoUpdate(true);
			
			//die
			for (int frm = 243; frm < 251; frm++)
				anims[LEFT][DIE].addFrame(sheet.getSprite("frame" + frm + ".png").getFlippedCopy(true, false), 150);
			anims[LEFT][DIE].setLooping(false);
			
			//celebrate
			for (int frm = 34; frm < 70; frm++)
				anims[LEFT][CLBR].addFrame(sheet.getSprite("frame" + frm + ".png").getFlippedCopy(true, false), 120);
			for (int frm = 0; frm <6; frm++)
				anims[LEFT][CLBR].addFrame(sheet.getSprite("frame" + frm + ".png").getFlippedCopy(true, false), 150);
			anims[LEFT][CLBR].setLooping(false);
	}
	
	/**
	 * Fills the sounds array with all sounds
	 * @throws SlickException
	 */
	public void fillSounds() throws SlickException
	{
	  //swing sword
	  	for (int i = 0; i < sounds[0].length; i++)
	  		sounds[0][i] = new Sound("data/sounds/sword" + i + ".wav");
	  //impact
	  	for (int i = 0; i < sounds[1].length; i++)
	  		sounds[1][i] = new Sound("data/sounds/impact" + i + ".wav");
	  	
	  //jump
	  	for (int i = 0; i < sounds[2].length; i ++)
	  		sounds[2][i] = new Sound("data/sounds/soft" + i + ".wav");
	  	
	  //run
	  	for (int i = 0; i < sounds[3].length; i ++)
	  		sounds[3][i] = new Sound("data/sounds/run" + i + ".wav");
	  	
	  //die
	  	for (int i = 0; i < 3; i ++)
	  	{
	  		sounds[4][i] = new Sound("data/sounds/sDeath" + i + ".wav");
	  	}
	}
	
	public Sound getSound(int x, int y)
	{
		return sounds[x][y];
	}
	
//==================================================================================|	
	
	
}
