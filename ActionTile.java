/**
 * abstract superclass for making action tiles
 * @author Cameron McDonagh & Maciej Buczkowski
 * @version 1.4
 */
abstract class ActionTile extends Tile {

    public Integer turnDrawn;

    /**
     * Constructor for Action Tiles
     * @param effect effect of the tile
     * (effect codes: "engulf", "freeze", "backTrack", "doubleMove")
     * @param turnDrawn turn that the tile is drawn for the movement based effects
     */
    public ActionTile(String effect, Integer turnDrawn){
        super(effect);
        setTurnDrawn(turnDrawn);
    }

    /**
     *
     * @return effect type
     */
    public String getEffect() {
        return super.getType();
    }

    /**
     *
     * @return turn drawn
     */
    public Integer getTurnDrawn() {
        return turnDrawn;
    }

    /**
     * sets the draw turn for movement effects
     * @param turnDrawn the turn drawn
     */
    public void setTurnDrawn(Integer turnDrawn){
        this.turnDrawn = turnDrawn;
    }
    
    /**
     * Method to print ActionTile to string for testing
     */
    public void toStr(){
        super.toStr();
        System.out.println("Turn Drawn: " + this.turnDrawn);
        System.out.println();
    }
}
