/**
 * class represents effect tiles that effect the board
 * @author Cameron McDonagh & Maciej Buczkowski
 * @version 1.0
 */
public class EffectTile extends ActionTile {

    /**
     * Constructor to make Effect Tiles (engulf/freeze)
     * @param effect desired tile effect
     * @param turnDrawn turn drawn so that effects last the appropriate amount of time
     */
    public EffectTile(String effect, Integer turnDrawn){
        super(effect, turnDrawn);
    }

    /**
     * sets tile on fire
     * @param tile tile you wish to effect
     */
    public static void engulf(FloorTile tile){
        tile.setEngulfed(true);
    }

    /**
     *
     * @return effect of tile
     */
    public String getAction(){
        return super.getEffect();
    }

    /**
     * freezes tile
     * @param tile tile you with to effect
     */
    public static void freeze(FloorTile tile){
            tile.setFrozen(true);
    }

}
