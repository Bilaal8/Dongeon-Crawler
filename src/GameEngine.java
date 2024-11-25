import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameEngine implements Engine, KeyListener {
    private Hero hero;
    private Set<Integer> keysPressed;
    public boolean isRunning;
    private PhysicEngine physicEngine;
    private RenderEngine renderEngine;
    private double cameraX;
    private double cameraY;
    private JFrame displayZoneFrame;
    private Playground currentPlayground;



    public GameEngine(Hero hero, RenderEngine renderEngine, JFrame displayZoneFrame, Playground initialPlayground) {
        this.hero = hero;
        this.keysPressed = new HashSet<>();
        this.isRunning = false;
        this.renderEngine = renderEngine;
        this.displayZoneFrame = displayZoneFrame;
        this.currentPlayground = initialPlayground;
    }
    private void updateCamera() {
        cameraX = hero.getX() - (renderEngine.getWidth() / 2);
        cameraY = hero.getY() - (renderEngine.getHeight() / 2);
        cameraX = Math.max(0, Math.min(cameraX, 1500 - renderEngine.getWidth()));
        cameraY = Math.max(0, Math.min(cameraY, 800 - renderEngine.getHeight()));
        renderEngine.setCameraPosition(cameraX, cameraY);

    }





    @Override
    public void update() {
        updateCamera();

        for (DynamicSprite sprite : physicEngine.getMovingSpriteList()) {
            if (sprite instanceof Orc) {
                if(((Orc) sprite).isDead() == false){
                    ((Orc) sprite).updateMovement(physicEngine.getEnvironment(),physicEngine.getMovingSpriteList());
                }
            }
            sprite.moveIfPossible(physicEngine.getEnvironment(),physicEngine.getMovingSpriteList());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Ne rien faire ici pour l'instant
    }


    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed.add(e.getKeyCode());

        if (keysPressed.contains(KeyEvent.VK_CAPS_LOCK)) {
            isRunning = true;
        }
        if (keysPressed.contains(KeyEvent.VK_I)) {
            hero.reduceHealth(30); // Réduire la vie de 30 quand "i" est enfoncé
        }
        if (keysPressed.contains(KeyEvent.VK_H)) {
            hero.Heal(100); // Soigne de 100 pv 
        }
        if (keysPressed.contains(KeyEvent.VK_P)) {
            hero.PourcentHeal(50);
        }
        if (keysPressed.contains(KeyEvent.VK_Y)) {
            if (hero.getHealth() <= 0) {
                hero.x = 70;
                hero.y = 200;
                hero.PourcentHeal(100);
                resetGame();
                System.out.println("test1");

            }
        }
        if (keysPressed.contains(KeyEvent.VK_N)) {
            if (hero.getHealth() <= 0) {
                renderEngine.clearRenderList();
                SwingUtilities.invokeLater(() -> Main.returnToMainMenu(displayZoneFrame));
        }
        }
        updateHeroMovement();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed.remove(e.getKeyCode());

        if (!keysPressed.contains(KeyEvent.VK_CAPS_LOCK)) {
            isRunning = false;
        }

        if (keysPressed.isEmpty()) {
            hero.setSpeed(0);
        } else {
            updateHeroMovement();
        }
    }

    private void updateHeroMovement() {
        Direction direction = Direction.SOUTH; // Direction par défaut
        boolean isMoving = false;

        if (keysPressed.contains(KeyEvent.VK_Z)) { // Haut
            direction = Direction.NORTH;
            isMoving = true;
        }
        if (keysPressed.contains(KeyEvent.VK_S)) { // Bas
            direction = Direction.SOUTH;
            isMoving = true;
        }
        if (keysPressed.contains(KeyEvent.VK_Q)) { // Gauche
            direction = Direction.WEST;
            isMoving = true;
        }
        if (keysPressed.contains(KeyEvent.VK_D)) { // Droite
            direction = Direction.EAST;
            isMoving = true;
        }
        if (keysPressed.contains(KeyEvent.VK_Z) && keysPressed.contains(KeyEvent.VK_D)) {
            direction = Direction.NORTH_EAST;
            isMoving = true;
        }
        if (keysPressed.contains(KeyEvent.VK_S) && keysPressed.contains(KeyEvent.VK_D)) {
            direction = Direction.SOUTH_EAST;
            isMoving = true;
        }
        if (keysPressed.contains(KeyEvent.VK_Q) && keysPressed.contains(KeyEvent.VK_Z)) { 
            direction = Direction.NORTH_WEST;
            isMoving = true;
        }
        if (keysPressed.contains(KeyEvent.VK_S) && keysPressed.contains(KeyEvent.VK_Q)) { 
            direction = Direction.SOUTH_WEST;
            isMoving = true;
        }

        if (isMoving) {
            hero.setSpeed(isRunning ? 10 : 6); // Vitesse différente selon l'état de course
            hero.setDirection(direction);
        }
    }

    public void resetGame() {
        System.out.println("testRESET");
        for (DynamicSprite s : physicEngine.getMovingSpriteList()) {
            System.out.println("testRESET2");

            if (s instanceof Orc) {
                System.out.println("testORC");
                ((Orc) s).reset();
            }
            if (s instanceof Heart_){
                System.out.println("testCOEUR");
                ((Heart_) s).setPris(false);

            }
        }
        for ( Sprite sprite : physicEngine.getEnvironment()){
            if( sprite instanceof Spike){
                ((Spike) sprite).setActive(true);
            }
            if( sprite instanceof Trap){
                ((Trap) sprite).setActive(true);
            }
        }

    }
}
