import java.awt.*;

public class Sprite implements Displayable{
    protected double x;
    protected double y;
    protected final Image image;
    protected final double width;
    protected final double height;
    protected final int camerax = 400;
    protected final int cameray = 400;

    public Sprite(double x, double y, Image image, double width, double height) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = width;
        this.height = height;
    }
    public void drawAt(Graphics g, double adjustedX, double adjustedY) {
        g.drawImage(image, (int)adjustedX, (int)adjustedY, null);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(image,(int) x,(int) y,null);
        // g.drawImage(image,(int)( camerax - x),(int)( cameray - y),null);

    }
}

// ID2E DE MAEL :       g.drawImage(image,(int)(cameraX -x),(int)y,null);