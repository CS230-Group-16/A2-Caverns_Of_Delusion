
import java.util.ArrayList;
import java.util.Random;

/**
 * The silk bag class holding all the tiles ready to be played.
 * @author Bartosz Kubica
 * @version 1.0
 */
public class SilkBag {
    
    private final Random randNum = new Random();
    
    private ArrayList<Tile> tileList = new ArrayList<Tile>();
    private int numOfTiles;
    
    /**
     * Constructs a new silk bag.
     * @param tiles array of tiles to be placed in silk bag.
     */
    public SilkBag (Tile tiles[]) {
        for (Tile tile : tiles) {
            this.addTile(tile);
        }
    }
    
    /**
     * Method to take a tile from the bag at random.
     * @return randomly selected tile from bag.
     */
    public Tile drawTile() {
        return this.tileList.remove(randNum);
    }
    
    /**
     * Method to insert tile into the silk bag.
     * @param t Tile to be inserted.
     */
    public void addTile(Tile t){
        this.tileList.add(t);
        this.numOfTiles++;
    }
}