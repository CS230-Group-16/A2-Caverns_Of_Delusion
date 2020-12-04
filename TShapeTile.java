/**
 * class represents the floor tile of type 'T-Shape'
 * @author Cameron McDonagh & Maciej Buczkowski
 * @version 1.2
 */

public class TShapeTile extends FloorTile {

    private int rotation;

    /**
     * Constructor used to make a T-Shape tile
     * @param rotation the rotation/orientation of the tile
     */
    public TShapeTile(int rotation) {
        super("TSHAPE");
        super.setRotation(rotation);
        this.generatePathways(rotation);
    }

    /**
     * Generates an int array (of length 4) that represents the pathways of the tile, the array represents the following [North, East, South, West]. '1' represents a path in that direction whereas '0' is a lack of a path.
     * @param rotation - generates the pathways based on the rotation (orientation) of the tile
     */
    public void generatePathways(int rotation) {
        int[] defaultPathway = new int[4];
        switch (rotation) {
            case 0:
                defaultPathway[0] = 0;
                defaultPathway[1] = 1;
                defaultPathway[2] = 1;
                defaultPathway[3] = 1;
                break;
            case 1:
                defaultPathway[0] = 1;
                defaultPathway[1] = 0;
                defaultPathway[2] = 1;
                defaultPathway[3] = 1;
                break;
            case 2:
                defaultPathway[0] = 1;
                defaultPathway[1] = 1;
                defaultPathway[2] = 0;
                defaultPathway[3] = 1;
                break;
            case 3:
                defaultPathway[0] = 1;
                defaultPathway[1] = 1;
                defaultPathway[2] = 1;
                defaultPathway[3] = 0;
                break;
        }
        super.setPathways(defaultPathway);
    }

    /**
     * returns the pathways array of the current tile, showing valid and invalid paths
     * @return pathways - the pathways array of the tile
     */
    public int[] getPathways() {
        return super.getPathways();
    }

    /**
     * change rotation by user when inserted
     * @param rotation new rotation of tile
     */
    public void setRotation(int rotation) {
        super.setRotation(rotation);
        this.rotation = rotation;
        this.generatePathways(rotation);
    }
}