public class StraightTile extends FloorTile  {
    private int[] pathways = {0,0,0,0};
    private int rotation;

    public StraightTile (String type, int rotation) {
      super(type, "Straight");
      this.generatePathways(rotation);
    }

    public void generatePathways(int rotation) {
				int[] defaultPathway = new int[4];
        switch (rotation) {
  					case 0:
    						defaultPathway[0] = 1;
    						defaultPathway[1] = 0;
    						defaultPathway[2] = 1;
    						defaultPathway[3] = 0;
    						break;
            case 1:
    						defaultPathway[0] = 0;
    						defaultPathway[1] = 1;
    						defaultPathway[2] = 0;
    						defaultPathway[3] = 1;
    						break;
            case 2:
    						defaultPathway[0] = 1;
    						defaultPathway[1] = 0;
    						defaultPathway[2] = 1;
    						defaultPathway[3] = 0;
    						break;
            case 3:
    						defaultPathway[0] = 0;
    						defaultPathway[1] = 1;
    						defaultPathway[2] = 0;
    						defaultPathway[3] = 1;
    						break;
				}
				this.pathways = defaultPathway;
    }

		public int[] getPathways() {
			return this.pathways;
		}
}
