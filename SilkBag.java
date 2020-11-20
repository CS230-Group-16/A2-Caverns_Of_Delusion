
import java.util.ArrayList;
import java.util.Random;

/**
 * The silk bag class holding all the tiles ready to be played.
 * @author Bartosz Kubica & Marius Antemir
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
        int random = randNum.nextInt(numOfTiles);
        this.numOfTiles--;
        return this.tileList.remove(random);
    }
    
    /**
     * Method to insert tile into the silk bag.
     * @param t Tile to be inserted.
     */
    public void addTile(Tile t){
        this.tileList.add(t);
        this.numOfTiles++;
    }
    
    /**
     * method to print silk bag to console
     */
    public void toStr(){
        System.out.println("Current Number of Tiles: " + this.numOfTiles);
        System.out.print("Current Tiles in Bag: ");
        for (int i = 0; i < this.numOfTiles; i++) {
            this.tileList.get(i).toStr();
        }
    }
}