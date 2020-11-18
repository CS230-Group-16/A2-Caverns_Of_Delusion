abstract class Tile {
    protected final String type;

    public String getType() {
        return this.type;
    }

    public Tile (String type){
        this.type = type;
    }
}

abstract class FloorTile extends Tile {
    private boolean frozen;
    private boolean engulfed;
    private boolean occupied;
    private String tileType;

    public FloorTile(String type, String tileType) {
        super(type);
        this.tileType = tileType;
    }

    public void setFrozen(boolean value) {
        this.frozen = value;
    }
    public void setEngulfed(boolean value) {
        this.engulfed = value;
    }
    public void setOccupied(boolean value) {
        this.occupied = value;
    }

    public boolean isFrozen() {
        return this.frozen;
    }
    public boolean isEngulfed() {
        return this.engulfed;
    }
    public boolean isOccupied() {
        return this.occupied;
    }
}
