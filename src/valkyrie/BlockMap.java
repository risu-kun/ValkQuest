package valkyrie;

import java.util.ArrayList;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Creates a map using a tmx file made by the Tiled Map editor
 * @author C. Scott
 * @version 0.8
 */
public class BlockMap {

  public TiledMap tmap;
  private static int mapWidth;
  private static int mapHeight;
  private int square[] = { 1, 1, 15, 1, 15, 15, 1, 15 }; // square shaped tile
  public static ArrayList<Object> entities;

  public BlockMap(String ref) throws SlickException {
    entities = new ArrayList<Object>();
    tmap = new TiledMap(ref, "data");
    setWidth(tmap.getWidth() * tmap.getTileWidth());
    setHeight(tmap.getHeight() * tmap.getTileHeight());

    for (int x = 0; x < tmap.getWidth(); x++) {
      for (int y = 0; y < tmap.getHeight(); y++) {
        int tileID = tmap.getTileId(x, y, 0);
        if (tileID == 1) {
          entities.add(new Block(x * 16, y * 16, square, "square"));
        }
      }
    }
  }

  public int getHeight() {
    return mapHeight;
  }

  public static void setHeight(int mapHeight) {
    BlockMap.mapHeight = mapHeight;
  }

  public int getWidth() {
    return mapWidth;
  }

  public static void setWidth(int mapWidth) {
    BlockMap.mapWidth = mapWidth;
  }
}