/**
 * Class represents effect tiles that effect the board.
 * @author Cameron McDonagh & Maciej Buczkowski.
 * @version 1.0
 */
public class EffectTile extends ActionTile {

    private int endTurn;
    /**
     * Constructor to make Effect Tiles (engulf/freeze).
     * @param effect Desired tile effect.
     * @param turnDrawn Turn drawn so that effects last the appropriate amount of time.
     */
    public EffectTile(String effect, Integer turnDrawn) {
        super(effect, turnDrawn);
    }

    /**
     * Sets a tile on fire.
     * @param tile The tile you wish to effect.
     */
    public static void engulf(FloorTile tile) {
        tile.setEngulfed(true);
    }

    /**
     * Gets the action effect it has on the tile.
     * @return Effect of tile.
     */
    public String getAction() {
        return super.getEffect();
    }

    /**
     * Freezes a tile.
     * @param tile The tile you with to effect.
     */
    public static void freeze(FloorTile tile) {
            tile.setFrozen(true);
    }
    
    /**
     * Set the end turn of the effect
     * @param endTurn Turn number that the effect stops
     */
    public void setEndTurn(int endTurn){
        this.endTurn = endTurn;
    }
    
    /**
     * Get the end turn of the effect
     * @return Turn number that the effect stops
     */
    public int getEndTurn(){
        return this.endTurn;
    }
}