/*
 * class represents the abstract class of tile, cannot be
 * instantiated.
 * @author Cameron McDonagh & Maciej Buczkowski
 * @version 1.2
 */

abstract class Tile {
    /* The 'type; attribute is protected, meaning that it
     * can only be access within the package, or by any
     * subclasses */
    protected final String type;

    /**
     * Constructor used to the Tile class
     *
     * @param type the type of the current tile.
     */
    public Tile (String type){
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
    public void toStr(){
        System.out.println(type);
    }
}
