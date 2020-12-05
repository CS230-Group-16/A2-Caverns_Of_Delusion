/**
 * Abstract superclass for making action tiles.
 * @author Cameron McDonagh & Maciej Buczkowski.
 * @version 1.4
 */
public abstract class ActionTile extends Tile {

    public Integer turnDrawn;

    /**
     * Constructor for Action Tiles.
     * @param effect Effect of the tile (effect codes: "engulf", "freeze", "backTrack", "doubleMove").
     * @param turnDrawn Turn that the tile is drawn for the movement based effects.
     */
    public ActionTile(String effect, Integer turnDrawn) {
        super(effect);
        setTurnDrawn(turnDrawn);
    }

    /**
     * Gets the effect type.
     * @return Effect type.
     */
    public String getEffect() {
        return super.getType();
    }

    /**
     * Gets the turn drawn.
     * @return Turn drawn.
     */
    public Integer getTurnDrawn() {
        return turnDrawn;
    }

    /**
     * Sets the draw turn for movement effects.
     * @param turnDrawn The turn the tile was drawn.
     */
    public void setTurnDrawn(Integer turnDrawn) {
        this.turnDrawn = turnDrawn;
    }

    /**
     * Method to print ActionTile to string for testing.
     */
    public void toStr() {
        super.toStr();
        System.out.println("Turn Drawn: " + this.turnDrawn);
        System.out.println();
    }

    /**
     * Convert to text to put into file.
     * @return String version of the game.
     */
    public String toText() {
        String result = "";
        result += super.type + ","
                + this.turnDrawn + "\n";
        return result;
    }
}