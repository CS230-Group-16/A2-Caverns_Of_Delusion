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
}
