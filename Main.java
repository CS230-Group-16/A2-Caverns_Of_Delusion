/**
 * Main class to run aspects of the assignment
 * @author Bartosz Kubica
 */
public class Main {
    
    public static void main(String [] args){
        Game g = new Game("D:/Documents/NetBeansProjects/A2-Caverns_Of_Delusion/board1.txt",new String[]{"Super_Cool_Name","grapeLord5000"});
        g.toStr();
        
        Player p1 = new Player("Michelle", 2, 1);
		Player p2 = new Player("Chloe", 5, 3);
		
		p1.saveProfile();
	    p2.saveProfile();
	    p1.updateUsername ("Blah");
	    p1.saveProfile();
    }
}