import java.awt.geom.Rectangle2D;

public interface Collidable {
    Rectangle2D getHitBox();
    boolean intersect(Rectangle2D rectangle);
}




