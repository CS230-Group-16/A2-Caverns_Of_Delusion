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
    private String tileType;
    private int rotation;

    /**
     * Constructor used to make for the FloorTile class
     * @param type the type of tile
     */
    public FloorTile(String type) {
        super(type);
        this.tileType = type;
    }

    /**
     * sets frozen attribute of the tile to the 'value' param
     * @param value a boolean value showing engulfed status
     */
    public void setFrozen(boolean value) {
        this.frozen = value;
    }

    /**
     * sets engulfed attribute of the tile to the 'value' param
     * @param value a boolean value showing engulfed status
     */
    public void setEngulfed(boolean value) {
        this.engulfed = value;
    }

    /**
     * sets occupied attribute of the tile to the 'value' param
     * @param value a boolean value showing occupied status
     */
    public void setOccupied(boolean value) {
        this.occupied = value;
    }

    /**
     * gets the frozen attribute of tile
     * @return frozen
     */
    public boolean isFrozen() {
        return this.frozen;
    }

    /**
     * gets the engulfed attribute of tile
     * @return engulfed
     */
    public boolean isEngulfed() {
        return this.engulfed;
    }

    /**
     * gets the occupied attribute of tile
     * @return Occupied
     */
    public boolean isOccupied() {
        return this.occupied;
    }
    
    /**
     * setting rotation of floor tile
     * @param rotation rotation to set to
     */
    public void setRotation(int rotation){
        this.rotation = rotation;
    }
    
    /**
     * get rotation of floor tile
     * @return rotation of floor tile
     */
    public int getRotation(){
        return this.rotation;
    }
    
    /**
     * Method to print FloorTile to string for testing
     */
    public void toStr(){
        super.toStr();
        System.out.println("Frozen: " + this.frozen);
        System.out.println("Engulfed: " + this.engulfed);
        System.out.println("Occupied: " + this.occupied);
        System.out.println();
    }
}
