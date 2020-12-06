/**
 * Class represents the floor tile of type Corner.
 * @author Cameron McDonagh & Maciej Buczkowski.
 * @version 1.3
 */
public class CornerTile extends FloorTile {

    private int rotation;

    /**
     * Constructor used to make a corner tile.
     * @param rotation Orientation of the tile.
     */
    public CornerTile(int rotation) {
        super("CORNER");
        super.setRotation(rotation);
        this.generatePathways(rotation);
    }

    public CornerTile(boolean frozen, boolean engulfed, boolean fixed, boolean occupied, int rotation) {
        super("CORNER", frozen, engulfed, fixed, occupied, rotation);
        this.rotation = rotation;
    }
    
    /**
     * Generates an int array (of length 4) that represents the pathways of the tile, the array represents the following [North, East, South, West]. 
     * '1' represents a path in that direction whereas '0' is a lack of a path.
     * @param rotation Generates the pathways based on the rotation (orientation) of the tile.
     */
    public void generatePathways(int rotation) {
        int[] defaultPathway = new int[4];
        switch (rotation) {
            //corner tile that has a path to the right and below
            case 0:
                defaultPathway[0] = 0;
                defaultPathway[1] = 1;
                defaultPathway[2] = 1;
                defaultPathway[3] = 0;
                break;
                //corner tile that has a path to below and to the left
            case 1:
                defaultPathway[0] = 0;
                defaultPathway[1] = 0;
                defaultPathway[2] = 1;
                defaultPathway[3] = 1;
                break;
            case 2:
                //corner tile that has a path above and to the left
                defaultPathway[0] = 1;
                defaultPathway[1] = 0;
                defaultPathway[2] = 0;
                defaultPathway[3] = 1;
                break;
            case 3:
                //corner tile that has a path above and to the right
                defaultPathway[0] = 1;
                defaultPathway[1] = 1;
                defaultPathway[2] = 0;
                defaultPathway[3] = 0;
                break;
        }
        super.setPathways(defaultPathway);
    }

    /**
     * Returns the pathways array of the current tile, showing valid and invalid paths.
     * @return pathways The pathways array of the tile.
     */
    public int[] getPathways() {
        return super.getPathways();
    }

    /**
     * Change rotation by user when inserted.
     * @param rotation New rotation of tile.
     */
    public void setRotation(int rotation) {
        super.setRotation(rotation);
        this.rotation = rotation;
        this.generatePathways(rotation);
    }
}