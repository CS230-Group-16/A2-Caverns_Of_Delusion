/**
 * The ActionTile class creates action tile
 * @author Michelle Bhaskaran & Chloe Thomas
 * @version 1.2
 */

abstract class ActionTile extends Tile {

    private String effect;
    private int turnDrawn;

    /**
     * Constructor used to make ActionTile
     * @param effect the effect of the tile
     */
    public ActionTile(String effect) {
        super(effect);
        this.effect = effect;
    }

    /**
     * gets effect of tile
     * @return effect
     */
    public String getEffect () {
        return effect;
    }

    /**
     * gets turn drawn
     * @return effect
     */
    public int getTurnDrawn () {
        return turnDrawn;
    }
}
