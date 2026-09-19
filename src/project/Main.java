package project;
        import project.UI.*;
import project.gameobjects.*;
public class Main {
    public static void main (String[] args) {
        
        boolean testing_worm_algorithm = false;
        if (testing_worm_algorithm) {
            Grid myGrid = new Grid();
            myGrid.initializeGrid();

            Worm worm = new Worm(Worm.Type.L1, 50, 50);
            System.out.println("here");
            System.out.println(worm.body.length);
            for (int i = 0; i < worm.body.length; i++) {
                System.out.println("Segment" + i + ": ("
                    + worm.body[i].getX() + ", " + worm.body[i].getY()
                );
                // System.out.println("Segement " + i + ": " + "(" + worm.body[i].getX() + ", " + worm.body[i].getY() + ")");
            }
        } else {
            Screen screen = new Screen();
        }

    }

}