abstract class FloorTile extends Tile {
    private boolean frozen;
    private boolean engulfed;
    private boolean occupied;
    private String tileType;

    public FloorTile(String type) {
        super(type);
        this.tileType = type;
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
