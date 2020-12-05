/**
 * Class represents the abstract class of tile, cannot be instantiated.
 * @author Cameron McDonagh & Maciej Buczkowski.
 * @version 1.3
 */

public abstract class FloorTile extends Tile {

    private boolean frozen;
    private boolean engulfed;
    private boolean occupied;
    private boolean fixed;
    private final String tileType;
    private int rotation;
    private int[] pathways;

    /**
     * Constructor used to make for the FloorTile class.
     * @param type The type of tile.
     */
    public FloorTile(String type) {
        super(type);
        this.tileType = type;
    }
    
    /**
     * Constructor used to make for the FloorTile class.
     * @param type The type of tile.
     * @param frozen Is the floor tile is frozen.
     * @param engulfed Is the floor tile is engulfed.
     * @param fixed The floor tile is fixed.
     * @param occupied Is the floor tile is occupied.
     * @param rotation The rotation of the floor tile.
     */
    public FloorTile(String type, boolean frozen, boolean engulfed, boolean fixed, boolean occupied, int rotation) {
        super(type);
        this.tileType = type;
        this.frozen = frozen;
        this.engulfed = engulfed;
        this.fixed = fixed;
        this.occupied = occupied;
        this.rotation = rotation;
    }

    /**
     * Sets frozen attribute of the tile to the 'value' param.
     * @param value A boolean value showing if the tile is frozen.
     */
    public void setFrozen(boolean value) {
        this.frozen = value;
    }

    /**
     * Sets engulfed attribute of the tile to the 'value' param.
     * @param value A boolean value showing if the tile is on fire.
     */
    public void setEngulfed(boolean value) {
        this.engulfed = value;
    }

    /**
     * Sets occupied attribute of the tile to the 'value' param.
     * @param value A boolean value showing occupied status.
     */
    public void setOccupied(boolean value) {
        this.occupied = value;
    }

    /**
     * Checks if the tile is frozen.
     * @return frozen True if the tile is frozen.
     */
    public boolean isFrozen() {
        return this.frozen;
    }

    /**
     * Checks if the tile is engulfed.
     * @return engulfed True if the tile is engulfed.
     */
    public boolean isEngulfed() {
        return this.engulfed;
    }

    /**
     * Checks to see if a player is on the tile.
     * @return Occupied True if a player is on the tile.
     */
    public boolean isOccupied() {
        return this.occupied;
    }

    /**
     * Setting rotation of the floor tile.
     * @param rotation The rotation to set to.
     */
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    /**
     * get rotation of floor tile.
     * @return rotation The rotation of the tile.
     */
    public int getRotation() {
        return this.rotation;
    }

    /**
     * get pathways of tile.
     * @return pathway The pathways of the tile.
     */
    public int[] getPathways() {
        return this.pathways;
    }

    /**
     * Set pathways of tile.
     * @param pathway The pathways of the tile.
     */
    public void setPathways(int[] path) {
        this.pathways = path;
    }
    
    /**
     * Set the tile to be fixed.
     * @param set The tile to set as fixed.
     */
    public void setFixed(boolean set) {
        this.fixed = set;
    }
    
    /**
     * Checks if the tile fixed.
     * @return True if the tile is fixed, false otherwise.
     */
    public boolean isFixed() {
        return this.fixed;
    }

    /**
     * Method to print FloorTile to string for testing.
     */
    public void toStr() {
        super.toStr();
        System.out.println("Frozen: " + this.frozen);
        System.out.println("Engulfed: " + this.engulfed);
        System.out.println("Occupied: " + this.occupied);
        System.out.println("Rotation: " + this.rotation);
        System.out.println();
    }

    /**
     * Convert the tile to text to be put into a file.
     * @return String version of the game.
     */
    public String toText() {
        String result = "";
        result += super.type + ","
                + this.frozen + ","
                + this.engulfed + ","
                + this.fixed + ","
                + this.occupied + ","
                + this.rotation + "\n";
        return result;
    }
}