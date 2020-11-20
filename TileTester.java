/*
Added this file to easily test tiles without needing a main
method in each class to test
*/

public class TileTester {
	public static void main(String[] args) {
		ActionTile t1 = new ActionTile("Freeze");
		System.out.println(t1.getType());
	}
}
