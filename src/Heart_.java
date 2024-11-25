import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Heart_ extends DynamicSprite{
    private int heal;
    private boolean pris;

    public Heart_(double x, double y, Image image, double width, double height, int heal) {
        super(x, y, image, width, height);
        this.heal = heal;
        this.pris = false;
    }

    public int getHeal() {
        return heal;
    }


    public void setPris(boolean pris) {
        this.pris = pris;
    }


    public boolean isPris() {
        return pris;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public boolean pris() {
        return pris;
    }

    public Rectangle getHitBox() {
        
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image,(int) x,(int) y,null);

    }


}
