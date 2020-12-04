/*
 * class represents the abstract class of tile, cannot be
 * instantiated.
 * @author Cameron McDonagh & Maciej Buczkowski
 * @version 1.3
 */

abstract class FloorTile extends Tile {

    private boolean frozen;
    private boolean engulfed;
    private boolean occupied;
    private boolean fixed;
    private final String tileType;
    private int rotation;
    private int[] pathways;

    /**
     * Constructor used to make for the FloorTile class
     *
     * @param type the type of tile
     */
    public FloorTile(String type) {
        super(type);
        this.tileType = type;
    }
    
    public FloorTile(String type, boolean frozen, boolean engulfed, boolean fixed, boolean occupied,int rotation){
        super(type);
        this.tileType = type;
        this.frozen = frozen;
        this.engulfed = engulfed;
        this.fixed = fixed;
        this.occupied = occupied;
        this.rotation = rotation;
    }

    /**
     * sets frozen attribute of the tile to the 'value' param
     *
     * @param value a boolean value showing engulfed status
     */
    public void setFrozen(boolean value) {
        this.frozen = value;
    }

    /**
     * sets engulfed attribute of the tile to the 'value' param
     *
     * @param value a boolean value showing engulfed status
     */
    public void setEngulfed(boolean value) {
        this.engulfed = value;
    }

    /**
     * sets occupied attribute of the tile to the 'value' param
     *
     * @param value a boolean value showing occupied status
     */
    public void setOccupied(boolean value) {
        this.occupied = value;
    }

    /**
     * gets the frozen attribute of tile
     *
     * @return frozen
     */
    public boolean isFrozen() {
        return this.frozen;
    }

    /**
     * gets the engulfed attribute of tile
     *
     * @return engulfed
     */
    public boolean isEngulfed() {
        return this.engulfed;
    }

    /**
     * gets the occupied attribute of tile
     *
     * @return Occupied
     */
    public boolean isOccupied() {
        return this.occupied;
    }

    /**
     * setting rotation of floor tile
     *
     * @param rotation rotation to set to
     */
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    /**
     * get rotation of floor tile
     *
     * @return rotation of floor tile
     */
    public int getRotation() {
        return this.rotation;
    }

    /**
     * get pathways of tile
     *
     * @return pathway of tile
     */
    public int[] getPathways() {
        return this.pathways;
    }

    /**
     * set pathways of tile
     *
     * @param path pathway of tile
     */
    public void setPathways(int[] path) {
        this.pathways = path;
    }

    public void setFixed(boolean set) {
        this.fixed = set;
    }

    public boolean isFixed() {
        return this.fixed;
    }

    /**
     * Method to print FloorTile to string for testing
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
     * convert to text to put into file
     *
     * @return string version of the game
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
