import java.awt.Image;
import java.awt.geom.Rectangle2D;

public class SolidSprite extends Sprite implements Collidable {
    public SolidSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    @Override
    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    // Modifions la méthode intersect pour accepter un Rectangle2D, ce qui est plus générique.
    public boolean intersect(Rectangle2D hitBox) {
        return this.getHitBox().intersects(hitBox); // Comparaison avec un Rectangle2D
    }
}
 