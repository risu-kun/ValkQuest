package valkyrie;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;

/**
 * Game - main game class for a simple platformer
 * @author C. Scott
 * @version 0.8.2
 */
public class Game extends BasicGame
{
	private Camera camera;
	
	private Random random = new Random();
	private final int LVL_NUM = random.nextInt(9);
	private final String BG_IMAGE = "data/bg"+ LVL_NUM + ".png";
		private final int GRAVITY = 1;
	
		private final int LEFT = 0;
		private final int RIGHT = 1;
		//private final int UP = 2;
		//private final int DOWN = 3;
	  
		private final int SLASH = 0;
		private final int RUN = 1;
		private final int JUMP = 2;
		private final int FALL = 3;
		private final int STAND = 4;
		private final int HURT = 5;
		private final int DIE = 6;
		private final int CLBR = 7;
	
	private Sia player;
	public BlockMap map;
	private ArrayList<Creep> creeps = new ArrayList<Creep>();
	private ArrayList<Potion> potions = new ArrayList<Potion>();
	private Sound BGSound;
	private Sound drinkPotion;
	private boolean attackPressed;
	boolean playedDeathSound;

	private long t; 
  
	public Game() 
	{
		super("Valkyrie Quest");
	}

  //================================[ begin init ]=======================================================]
	
	/* 
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException 
	{
		container.setShowFPS(false);
	    container.setVSync(true);
	    container.setTargetFrameRate(60);
	    if (LVL_NUM == 1 || LVL_NUM == 3 || LVL_NUM == 4 || LVL_NUM == 8)
	    {
	    	BGSound = new Sound("data/sounds/asgard.ogg");
	    	map = new BlockMap("data/map04.tmx");
	    }
	    else
	    {
	    	map = new BlockMap("data/map02.tmx");
	    	BGSound = new Sound("data/sounds/odin_sphere.ogg");
	    }
	    camera = new Camera(container, map.tmap);	    
	    drinkPotion = new Sound("data/sounds/potion.wav");
	    
	    //into/help dialog
	    JOptionPane.showMessageDialog(new JFrame(),
	        new String("Welcome to Valkyrie Quest! (Look out for the full version, coming soon)"+
	        			"\nUse the arrow keys to move, and the left ALT key to attack." +
	        			"\nWatch your health, and grab potions to heal yourself!" +
	        			"\nGood luck!"),
	        "Welcome",
	        JOptionPane.PLAIN_MESSAGE);
	    JOptionPane.showMessageDialog(new JFrame(),
		        new String("Level " + (LVL_NUM+1)),
		        "",
		        JOptionPane.PLAIN_MESSAGE);
		    
	    
	    BGSound.loop();
	    playedDeathSound = false;
	
	
	    player = new Sia();
	    
	    System.out.println("Sia's stats: \n" +
				"HP: " + player.getHp() +
				"\nSTR: " + player.getStr() +
				"\nDEF: " + player.getDef());
	    
	    player.setPos(50, 0);
	
	    addCreeps(random.nextInt(9)+3);
	    addPotions(creeps.size());
	}

  //==============================[ end init ]============================================================]
  
  
  //===============================[ begin update ]=======================================================]
	/**
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) throws SlickException 
	{
		attackPressed = container.getInput().isKeyDown(Input.KEY_LALT); //for shorthand use
	
		//check for cleared level  
		if (creeps.isEmpty())
		{
			player.setAct(CLBR);
			System.out.println("Level Cleared!");
			long td = Math.abs(t - System.currentTimeMillis());
		      if (td > 6000) 
		      {
		        t = System.currentTimeMillis();
		        JOptionPane.showMessageDialog(new JFrame(),
		    	        new String("Level Cleared!"+
		    	        			"\nPlease play again!"),
		    	        "Congratulations!",
		    	        JOptionPane.PLAIN_MESSAGE);
		        container.exit();
		      }
		}
  
		//check for player death  
		if (player.isDead())  
		{
			if (!playedDeathSound)
			{
				player.getSound(4,random.nextInt(3)).play();
				playedDeathSound=true;
			}
			player.setAct(DIE);
			System.out.println("You ded.");
			long td = Math.abs(t - System.currentTimeMillis());
		      if (td > 4000) 
		      {
		        t = System.currentTimeMillis();
		        JOptionPane.showMessageDialog(new JFrame(),
		    	        new String("Oops, you died."+
		    	        			"\nPlease play again!"),
		    	        "Most unfortunate.",
		    	        JOptionPane.PLAIN_MESSAGE);
		        container.exit();
		      }
		}	
		updatePotions();
	    updateCreeps();
	    updatePlayer(container);
   
	    camera.centerOn(player.getX(), player.getY());
	}
  //===================================[ end update ]===================================================]

//=====================================[ begin render ]=================================================]
	/**
	 * @see org.newdawn.slick.Game#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) throws SlickException 
	{
		g.setColor(new Color(0,255,0));
	    Image bg = new Image(BG_IMAGE);
	
	    bg.draw(0, 0);
	    camera.drawMap();
	    camera.translateGraphics();
	    
	    for (Potion po : potions)
	    {
	    	po.draw();
	    	//g.draw(po.getBounds()); //draw poly outline
	    }
	    
	    player.draw();
	    //g.draw(player.getBounds()); //draw poly outline
	    g.fill(player.healthBar);
	    g.setColor(new Color(0,0,0));
	    g.draw(player.healthContainer);
	
	    for (Creep c : creeps) 
	    {
	    	g.setColor(new Color(0,255,0));
		    c.draw();
		    //g.draw(c.getBounds()); //draw poly outline
		    g.fill(c.healthBar);
		    g.setColor(new Color(0,0,0));
		    g.draw(c.healthContainer);
	    }
	}
  
  //=====================================[ end render ]==================================================]
  
  //=====================================[ other methods ]===============================================]
  
	/**
	 * Finds a good position for each new creep (not colliding with player or level)
	 * @param count	number of creeps to be created
	 * @throws SlickException
	 */
	private void addCreeps(int count) throws SlickException 
	{
	    for (int i = 0; i < count; i++) 
	    {
	    	Creep c = new Creep();
	    	// c.setPos(Math.random(), y)
	    	float cx = (float) Math.random() * map.getWidth();
	    	float cy = (float) Math.random() * map.getHeight();
	    	Polygon poly = new Polygon(new float[] { cx, cy, cx + 65, cy, cx + 65,
	    											cy + 75, cx, cy + 75 });
	
	      while (collidesBlocks(poly) || collideWithCreep(poly)) 
	      {
	    	  cx = (float) Math.random() * map.getWidth();
	    	  cy = (float) Math.random() * map.getHeight();
	    	  poly = new Polygon(new float[] { cx, cy, cx + 65, cy, cx + 65, cy + 75,
	    			  							cx, cy + 75 });
	      }
	      c.setPos(cx, cy);
	      creeps.add(c);
	    }
	}
  
	/**
	 * Finds a good position for each new potion (not colliding with player or level)
	 * @param count	number of potions to be created
	 * @throws SlickException
	 */
	private void addPotions(int count) throws SlickException 
	{
	    for (int i = 0; i < count; i++) 
	    {
	    	Potion po = new Potion();
	    	float px = (float) Math.random() * map.getWidth();
	    	float py = (float) Math.random() * map.getHeight();
	    	Polygon poly = new Polygon(new float[] { px, py, px + 11, py, px + 11, py + 14,
	    												px, py + 14 });
	
	    	while (collidesBlocks(poly) || collideWithCreep(poly)) 
	    	{
	    		px = (float) Math.random() * map.getWidth();
	    		py = (float) Math.random() * map.getHeight();
	    		poly = new Polygon(new float[] { px, py, px + 11, py, px + 11, py + 14, 
	    										px, py + 14 });
	    	}
	    	po.setPos(px, py);
	    	potions.add(po);
	    }
	}
  
	/**
	 * Updates player position and action
	 * @param container	GameContainer
	 * @throws SlickException
	 */
	private void updatePlayer(GameContainer container) throws SlickException
	{
		if (!player.isDead() && !creeps.isEmpty())
			getInput(container);
		  
		//falling
	    if (!collidesBlocks(player.getBounds())) 
	    {
	    	if (player.vNow() > 0) //vNow = current downward speed
	    	{
	    		player.setAct(FALL);
	    		player.setUpdate(true);
	    	}
	      	
	    	player.setVNow(player.vNow() + GRAVITY);
	    	player.move(0, player.vNow());
	      
		    if (collidesBlocks(player.getBounds()))
		    {
		        player.move(0, -player.vNow());
		        player.setVNow(0);
		        player.setJump(false);
		    }
	    }
	    
	    //stand when not moving
	    if (!container.getInput().isKeyDown(Input.KEY_LALT)  &&
	      		!container.getInput().isKeyDown(Input.KEY_LEFT) &&
	      		!container.getInput().isKeyDown(Input.KEY_RIGHT) &&
	      		!player.isJumping() &&
	      		!player.isDead() &&
	    	  	!creepAttacking() &&
	    	  	!creeps.isEmpty())
	    {
	    	player.setAct(STAND);
	      	player.setUpdate(true);
	    }
  }
  
	/**
	 * Updates potions to disappear when used
	 */
	private void updatePotions()
	{
		for (int i = 0; i < potions.size(); i++) 
	    {
			Potion po = potions.get(i);
			if (collideWithPlayer(po.getBounds()) &&
				player.getHp() != player.getMaxHp())
			{
				drinkPotion.play();
				player.heal(10);
				potions.remove(i);
			}			    
	    }
	}
  
	/**
	 * Updates creeps position and action
	 * @throws SlickException
	 */
	private void updateCreeps() throws SlickException 
	{
	    for (int i = 0; i < creeps.size(); i++) 
	    {
	    	Creep c = creeps.get(i);
	    	c.setAttacking(false);
	    	if (!attackPressed)
	    		c.setBeingAttacked(false);
	      
	    	if (c.isBeingAttacked()) 
	    		c.setAct(HURT);
	    	else
	    		c.setAct(STAND);
	      
	    	float xDif = player.getX() - c.getX();
	    	float yDif = c.getBounds().getMaxY() - player.getBounds().getMaxY();
	    	float dirDif = c.getBounds().getCenterX() - player.getBounds().getCenterX();
	      
	    	if (collideWithPlayer(c.getBounds()) && !player.isDead())
	    	{
	    		c.setAct(SLASH);
		  	  	long td = Math.abs(t - System.currentTimeMillis());
		  	  	if (td > 1000 && !attackPressed) 
		  	  	{
		  	  		t = System.currentTimeMillis();	    	  
		  	  		c.setAttacking(true);
		    	  	c.getSound(2, random.nextInt(5)).play();
		    	  	player.damage(c);
		    	  	player.setAct(HURT);
		    	  	if (dirDif > 0)
		    	  		movePlayer(player, -10, 0);
		    	  	else if (dirDif < 0)
		    	  		movePlayer(player, 10, 0);
		    	  	player.getSound(2, random.nextInt(5)).play();
		    	  	System.out.println("Sia hit!  Current HP: " + player.getHp() +
			  			  				"\n Creep's STR: " + c.getStr());
		  	  	}
	    	}   
	    	if (!collidesBlocks(c.getBounds())) 
	    	{
	    		c.setVNow(c.vNow() + GRAVITY);
	    		c.move(0, c.vNow());
	      
	    		if (collidesBlocks(c.getBounds())) 
	    		{
	    			c.move(0, -c.vNow());
	    			c.setVNow(0);
	    			c.setJump(false);
	    		}
	    	}      
	    	if (Math.abs(xDif)< 200)
	    	{    	  
	    		if (xDif < 100 && yDif > 16)
	    		{
	    			if (!c.isJumping() && !player.isJumping()) 
	    			{
	    				long td = Math.abs(t - System.currentTimeMillis());
	    				if (td > 500) 
	    				{
	    					t = System.currentTimeMillis();
	    					c.setAct(JUMP);
	    					//player.getSound(2, random.nextInt(5)).play();
	    					c.setVNow(-c.getIni());
	    					c.setJump(true);
	    				}	
	    			}  
	    		}
	    		if (xDif < -30) 
	    		{
	    			movePlayer(c, -1, 0);
	    			c.setDir(LEFT);
	    			c.setAct(RUN);		  	
	    		}
	    		else if (xDif > 60) 
	    		{
	    			movePlayer(c, 1, 0);
	    			c.setDir(RIGHT);
	    			c.setAct(RUN);
	    		}		        
	      }      
	      while (collidesBlocks(c.getBounds()))
	      {
	    	  if (c.getDir() == LEFT)
	    		  c.move(1, 0);
	    	  else if (c.getDir() == RIGHT)
	    		  c.move(-1, 0);
	      }      
	    }
	}
  
	
	/**
	 * Gets and interprets input from keyboard
	 * @param container	GameContainer
	 * @throws SlickException
	 */
	public void getInput(GameContainer container) throws SlickException
	{
		if (container.getInput().isKeyPressed(Input.KEY_LALT))
	    {
	    	player.setUpdate(true);
	    	player.setAct(SLASH);
	    	player.setAttacking(true);
	    	player.getSound(0, random.nextInt(5)).play();
	    	swordHit();
	    }
	
	    if (container.getInput().isKeyDown(Input.KEY_LEFT)) 
	    {
	      player.setDir(LEFT);
	      player.setAct(RUN);
	      movePlayer(player, -3, 0);    
	    }
	
	    if (container.getInput().isKeyDown(Input.KEY_RIGHT)) 
	    {
	      player.setDir(RIGHT);
	      player.setAct(RUN);
	      movePlayer(player, 3, 0);
	    }
	
	    if (container.getInput().isKeyPressed(Input.KEY_UP)) 
	    {
	      if (!player.isJumping()) 
	      {
	        player.setAct(JUMP);
	        //player.getSound(JUMP, random.nextInt(5)).play(1.0f, 0.5f);
	        player.setVNow(-player.getIni());
	        player.setJump(true);
	      }
	    }
	}
  
	/**
	 * Moves player object, handles collision
	 * @param p	player object to be moved
	 * @param x	horizontal adjustment
	 * @param y	vertical adjustment
	 */
	private void movePlayer(Player p, int x, int y) 
	  {
	    p.move(x, y);
	      p.setUpdate(true);
	      if (p.isJumping()) {
	    	  if (x<0)
	    		  p.move(-1, 0);
	    	  else
	    		  p.move(1, 0);
	      }
	      while (collidesBlocks(p.getBounds())) {
	        p.move(-x, 0);
	      }
	  }
	
	/**
	 * Checks if player is attacking and if the attack is landed; deals damage.
	 * @return	true if attack is landed, false otherwise
	 * @throws SlickException
	 */
	public boolean swordHit() throws SlickException 
	  {
		float dirDif;
		float px = player.getX();
		float py = player.getY();
		Polygon playerPoly = new Polygon(new float[] { px, py, px + 45,
		        py, px + 45, py + 42, px, py + 42 });
		/*
		for (Object obj : BlockMap.entities)
	    {
	      Block block = (Block) obj;
	      if (player.getBounds().intersects(block.poly)) 
	      {
	        return true;
	      }
	    }
	    */
	
	    for (int i = 0; i < creeps.size(); i++) 
	    {
	      Creep c = creeps.get(i);
	      dirDif = player.getBounds().getCenterX() - c.getBounds().getCenterX();
	      if (c.getBounds().intersects(playerPoly) ||
	    	  c.getBounds().contains(playerPoly)) 
	      { 
	    	if (!c.isBeingAttacked())
	    	{
		        c.setBeingAttacked(true);
		    	c.damage(player);
		    	player.getSound(1, random.nextInt(5)).play();
		    	creeps.get(i).getSound(0, random.nextInt(5)).play();
		        System.out.println("Creep hit; current hp: " + c.getHp());
		        if (dirDif > 0)
		        	movePlayer(c,-20, 0);
		        else if (dirDif <0)
		        	movePlayer(c,20, 0);
		        if (c.isDead())
		        {
		        	System.out.println("Creep killed!");
		        	creeps.get(i).getSound(1, random.nextInt(5)).play();
		        	creeps.remove(c);
		        }
		        return true;
	      	}
	      }
	    }
	
	    return false;
	  }
	
	/**
	 * @return true if any creep on the map is attacking
	 */
	public boolean creepAttacking()
	  {
		  for (int i = 0; i < creeps.size(); i++) 
		    {
		      Creep c = creeps.get(i);
		      if (c.isAttacking()) 
		      {
		        return true;
		      }
		    }
		    return false;	  
	  }
	  
	/**
	 * Checks if a polygon is colliding with a map tile
	 * @param poly	polygon to check map collision with
	 * @return true if polygon collides with a map tile
	 */
	public boolean collidesBlocks(Polygon poly) {
	    for (int i = 0; i < BlockMap.entities.size(); i++) 
	    {
	      Block entity1 = (Block) BlockMap.entities.get(i);
	      if (poly.intersects(entity1.poly) ||
	    	   poly.contains(entity1.poly)) 
	      {
	        return true;
	      }
	    }
	    return false;
	  }
	  
	/**
	 * Checks if a polygon is colliding with a creep
	 * @param poly	polygon to check creep collision with
	 * @return true if polygon collides with a creep
	 */
	public boolean collideWithCreep(Polygon poly) 
	{
	    for (int i = 0; i < creeps.size(); i++) 
	    {
	      Creep c = creeps.get(i);
	      if (poly.intersects(c.getBounds())) 
	      {
	        return true;
	      }
	    }
	    return false;
	}
	
	/**
	 * Checks if a polygon is colliding with the player object
	 * @param poly	polygon to check player collision with
	 * @return true if polygon collides with player
	 */
	public boolean collideWithPlayer(Polygon poly) 
	{
		if (poly.intersects(player.getBounds()) || player.getBounds().contains(poly)) 
		 {
			 return true;
		 }
		 else
		    return false;
	}

  //==================================[ end other methods ]=============================================]
	  
	
	public static void main(String[] args) throws SlickException 
	{
		AppGameContainer container = new AppGameContainer(new Game(), 640, 480, false);
		container.start();
	}
}