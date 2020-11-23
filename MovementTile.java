/**
 * class represents the tiles effecting player movement
 * @author Cameron McDonagh & Maciej Buczkowski
 * @version 1.0
 */
public class MovementTile extends ActionTile {

    public Boolean movement;

    /**
     * Construtor for making movement based action tiles
     * @param effect desired effect (backTrack/doubleMove)
     * @param turnDrawn
     */
    public MovementTile(String effect,Integer turnDrawn) {
        super(effect, turnDrawn);
        if (effect == "BACKTRACK"){
            this.movement = false; // false means go back
        }else if (effect == "DOUBLEMOVE"){
            this.movement = true; // true means go forwards
        }

    }

    /**
     *
     * @return the type of movement (false return = backTrack/ true return = doubleMove)
     */
    public Boolean getMovement() {
        return movement;
    }

    /**
     *
     * @return turn the tile was drawn
     */
    public Integer getTurnDrawn(){
        return super.getTurnDrawn();
    }

}
