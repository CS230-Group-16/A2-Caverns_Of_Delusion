public class ActionTileTest {

    public static void main(String[] args) {

        //making test objects
        EffectTile fire = new EffectTile("engulf",0);
        EffectTile ice = new EffectTile("freeze",0);

        MovementTile move = new MovementTile("backTrack", 0 );
        MovementTile moveTwo = new MovementTile("doubleMove", 0 );

        CornerTile testTile = new CornerTile(1);

        System.out.println(fire.getAction());
        System.out.println(fire.getTurnDrawn());

        System.out.println(testTile.isEngulfed());//checking whether the effect has actually taken place
        EffectTile.engulf(testTile);
        System.out.println(testTile.isEngulfed());


        System.out.println(ice.getAction());
        System.out.println(ice.getTurnDrawn());

        System.out.println(testTile.isFrozen());// ' '
        EffectTile.freeze(testTile);
        System.out.println(testTile.isFrozen());


        System.out.println(move.getMovement());//checking if both movement types work
        System.out.println(moveTwo.getMovement());

        System.out.println(move.getTurnDrawn());//testing for correct return of round drawn for player movements
        System.out.println(moveTwo.getTurnDrawn());

    }

}
