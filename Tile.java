/**
 * class represents the abstract class of tile, cannot be
 * instantiated.
 * @author Cameron McDonagh & Maciej Buczkowski
 * @version 1.2
 */

public abstract class Tile {
    
    protected final String type; // type of tile

    /**
     * Constructor used to the Tile class
     * @param type the type of the current tile.
     */
    public Tile(String type){
        this.type = type;
    }

    /**
     * Gets the tile's type
     * @return tile type
     */
    public String getType() {
        return this.type;
    }
    
    /**
     * Prints the tile to screen
     */
    public void toStr() {
        System.out.println("Type:" + this.type);
    }
}