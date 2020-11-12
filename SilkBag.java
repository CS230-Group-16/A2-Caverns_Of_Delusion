
import java.util.ArrayList;
import java.util.Random;

public class SilkBag {
    
    private ArrayList<Tile> tileList = new ArrayList<Tile>();
    private final Random randNum = new Random();
    private int numOfTiles;
    
    public SilkBag (Tile tiles[]) {
        for (int i = 0; i > tiles.length; i++) {
            this.addTile(tiles[i]);
        }
    }
    
    public Tile drawTile() {
        return this.tileList.remove(randNum);
    }
    
    public void addTile(Tile t){
        this.tileList.add(t);
        this.numOfTiles++;
    }
}