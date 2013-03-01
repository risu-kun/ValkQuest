package valkyrie;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

/**
 * Represents a potion object
 * @author C. Scott
 * @version 0.8.2
 */
public class Potion 
{
	private Image img;
	protected Polygon bounds;
	protected float x = 340;
	protected float y = 240;
	
	public Potion() throws SlickException
	{
		this.img = new Image("data/potion.png");
		this.bounds = new Polygon(new float[] { x, y, x + 11, y, x + 11, y + 14,
	              x, y + 14 });
	}
		
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}

	public void setPos(float px, float py) {
		this.x = px;
		this.y = py;
		resetBounds();
		
	}

	private void resetBounds() {
		bounds.setX(x);
		bounds.setY(y);		
	}

	public void draw() {
		img.draw(x, y);
		
	}

	public Polygon getBounds()
	{
		return bounds;
	}
}
