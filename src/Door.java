import java.awt.Image;

public class Door extends Sprite{
    private final int doorFromLevel;
    private final int doorToLevel;


    public Door(double x, double y, Image image, int width, int height, int doorFromLevel ,int doorToLevel) {
        super(x, y, image, width, height);
        this.doorToLevel = doorToLevel;
        this.doorFromLevel = doorFromLevel;
    }




    public int getDoorFromLevel() {
        return doorFromLevel;
    }


    public int getDoorToLevel() {
        return doorToLevel;
    }

    
    

    



}
