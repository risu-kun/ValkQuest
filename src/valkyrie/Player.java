package valkyrie;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.XMLPackedSheet;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;

/**
 * Represents a player object (main player and creep are child classes)
 * @author C. Scott
 * @version 0.8.2
 */
public class Player{
  
  protected Animation anims[][] = new Animation[4][8];
  protected XMLPackedSheet sheet;
	
  protected Random roll;
  protected Polygon bounds;
  protected float x;
  protected float y;
  
  public Rectangle healthContainer;
  public Rectangle healthBar;
  
  protected int dir;
  	protected final int LEFT = 0;
	protected final int RIGHT = 1;
	protected final int UP = 2;  	
	protected final int DOWN = 3;
	
protected int act;
	protected final int SLASH = 0;
  	protected final int RUN = 1;
  	protected final int JUMP = 2;
  	protected final int FALL = 3;
  	protected final int STAND = 4;
  	protected final int HURT = 5;
  	protected final int DIE = 6;
  	protected final int CLBR = 7;
  
  private boolean isJumping = false;
  private boolean isDead = false;
  private boolean beingAttacked = false;
  private boolean isAttacking = false;
  private int vNow;
  protected int vIni;
  
  private int level;
  protected int maxHP;
  protected int hp;
  protected int str;
  protected int def;
  protected int yOffset;
  
  
  public Player() 
  {   
	this.roll = new Random();
    this.dir = RIGHT;
    this.act = STAND;
    this.level = 1;
    for (int i = 0; i<anims.length; i++)
		  for (int j = 0; j<anims[i].length; j++)
			  anims[i][j] = new Animation();
    
    this.hp = this.maxHP = roll.nextInt(6) + 10;
    this.str = roll.nextInt(6) + 10;
    this.def = str-10;
    
  }
  
/**
 * Calculates damage taken and updates hp when hit
 * @param player	player which hits this player
 */
public void damage(Player player)
  {
	  int attack = (int) (roll.nextDouble() * player.getStr());
	  int dmg = attack - def;
	  if (dmg >= 0)
		  setHp(hp-dmg);
	  	  setHealth();
	  if (hp <= 0)
		setIsDead(true);
	  
  }
  
/**
 * Adds health when a potion is used
 * @param h	amount of hp to add
 */
public void heal(int h)
  {
	  hp += h;
	  if (hp > maxHP)
		hp = maxHP;
	  setHealth();
  }

  public Polygon getBounds() {
    return bounds;
  }

  public void setBounds(Polygon bounds) {
    this.bounds = bounds;
  }
  
  public void setPos(float x, float y) {
    this.x = x;
    this.y = y;
    moveBounds();
  }
  
  protected void moveBounds() {
    bounds.setX(x);
    bounds.setY(y);
  }

/**
 * Moves a player's position
 * @param x	horizontal adjustment
 * @param y	vertical adjustment
 */
  public void move(float x, float y) 
  {
    this.x += x;
    this.y += y;
    //setBounds(new Polygon(new float[] { x, y, x + anims[dir][act].getWidth(),
	 //       y, x + anims[dir][act].getWidth(), y + anims[dir][act].getHeight(), x, y + anims[dir][act].getHeight() }));
    moveBounds();
    resetHealthMeter();
  }
	
	/**
	 * Moves the health bar with the player
	 */
	public void resetHealthMeter()
	{
		healthBar.setX(x);
		healthContainer.setX(x);
		healthBar.setY(bounds.getMaxY()+3);
		healthContainer.setY(bounds.getMaxY()+3);
	}
	
	
	/**
	 * Sets width of health bar to match current hp
	 */
	public void setHealth()
	{
		if (hp >=0)
			healthBar.setWidth(hp*3);
		else
			healthBar.setWidth(0);
	}
  
  public void setAct(int act)
	{
		
		this.act = act;
		
	}
  
 public int getAct()
  {
	  return this.act;
  }
	
public Animation getAnim(int dir, int act) 
	{
	    return anims[dir][act];
	}

public void setUpdate(boolean b) 
	{
		anims[dir][act].setAutoUpdate(b);
	}

/**
 *	Draws the player
 */
public void draw() 
	{		
		anims[dir][act].draw(x, bounds.getMaxY() - anims[dir][act].getHeight() + yOffset);		    
	}

  
  public float getX() {
    return x;
  }
  
  public float getY() {
    return y;
  }

  public void setDir(int dir) {
    this.dir = dir;
  }
  
  public int getDir() {
    return dir;
  }

  public boolean isJumping() {
    return isJumping;
  }

  public void setJump(boolean isJumping) {
    this.isJumping = isJumping;
  }

  public int vNow() {
    return vNow;
  }

  public void setVNow(int vNow) {
    this.vNow = vNow;
  }

  public int getIni() {
    return vIni;
  }

  public void setIni(int vIni) {
    this.vIni = vIni;
  }

public int getLevel() {
	return level;
}

public void setLevel(int level) {
	this.level = level;
}

public int getHp() {
	return hp;
}


public void setHp(int hp) {
	this.hp = hp;
}

public int getMaxHp()
{
	return this.maxHP;
}

public int getStr() {
	return str;
}

public void setStr(int str) {
	this.str = str;
}

public int getDef() {
	return def;
}

public void setDef(int def) {
	this.def = def;
}

public boolean isDead() {
	return isDead;
}

public void setIsDead(boolean d)
{
	this.isDead = d;
}

public boolean isBeingAttacked() {
	return beingAttacked;
}

public void setBeingAttacked(boolean beingAttacked) {
	this.beingAttacked = beingAttacked;
}

public boolean isAttacking() {
	return isAttacking;
}

public void setAttacking(boolean isAttacking) {
	this.isAttacking = isAttacking;
}

}
