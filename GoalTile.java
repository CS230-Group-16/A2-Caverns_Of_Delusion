/**
 * class represents the floor tile of type 'T-Shape'
 * @author Cameron McDonagh & Maciej Buczkowski
 * @version 1.3
 */
public class GoalTile extends FloorTile {

    private final int[] PATHWAYS = {1,1,1,1};
    
    /**
     * Constructor used to make the Goal tile
     */
    public GoalTile() {
        super("GOAL");
        super.setPathways(new int[] {1,1,1,1});
    }

    public GoalTile(boolean frozen, boolean engulfed, boolean fixed, boolean occupied, int rotation) {
        super("GOAL",frozen,engulfed,fixed,occupied,rotation);
    }
    
    /**
     * get pathways of goal tile
     * @return pathway (1 being true and 0 being false)
     */
    public int[] getPathways() {
        return super.getPathways();
    }
}