import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Timer;
import java.util.TimerTask;

public class Trap extends SolidSprite {
    private int damage;
    private boolean active;
    private Timer timer;
    private final long DELAI_MS = 1000; // Délai de 5 secondes

    public Trap(double x, double y, Image image, double width, double height, int damage) {
        super(x, y, image, width, height);
        this.damage = damage;
        this.active = true;
        this.timer = new Timer();  // Initialisation du Timer
    }

    public int getDamage() {
        return damage;
    }

    

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        if (active) {
            active = false;  // Désactive le piège

            // Utilisation de Timer pour réactiver le piège après DELAI_MS
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    active = true;  // Réactive le piège après DELAI_MS millisecondes
                }
            }, DELAI_MS);  // Attendre 5000ms (5 secondes) avant de réactiver
        }
    }


    @Override
    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x, y, width, height); // Retourne un Rectangle2D pour la hitbox
    }

    @Override
    public boolean intersect(Rectangle2D rectangle) {
        return getHitBox().intersects(rectangle);  // Vérifie la collision
    }
}
