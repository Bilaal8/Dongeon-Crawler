import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {
    private ArrayList<Displayable> renderList;


    // Variables pour le calcul du FPS
    private long lastTime;     // Temps de la dernière frame (en nanosecondes)
    private int fps;           // Nombre de frames par seconde
    private int frameCount;    // Compteur de frames pour calculer les FPS
    private long fpsTimer;     // Chronomètre pour calculer les FPS sur une période de 1 seconde
    private double cameraX;
    private double cameraY;

    public RenderEngine(JFrame jFrame) {
        renderList = new ArrayList<>();


        // Initialisation des variables pour le calcul des FPS
        lastTime = System.nanoTime();
        fps = 0;
        frameCount = 0;
        fpsTimer = System.nanoTime();  // Démarrer le chronomètre
    }

    public void setCameraPosition(double x, double y) {
        this.cameraX = x;
        this.cameraY = y;
    }

    public void addToRenderList(Displayable displayable) {
        if (!renderList.contains(displayable)) {
            renderList.add(displayable);
        }
    }

    public void addToRenderList(ArrayList<Displayable> displayable) {
        if (!renderList.contains(displayable)) {
            renderList.addAll(displayable);
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g); 


        // Calculer le temps écoulé depuis la dernière frame
        long currentTime = System.nanoTime();
        lastTime = currentTime;  // Mettre à jour le temps de la dernière frame


        // Incrémenter le compteur de frames
        frameCount++;

    
        // Vérifier si une seconde s'est écoulée (1 seconde = 1 000 000 000 nanosecondes)
        long elapsedTimeSec = currentTime - fpsTimer;
        if (elapsedTimeSec >= 1_000_000_000) {  // Si une seconde est passée
            fps = frameCount;  // Mettre à jour les FPS avec le nombre de frames comptées
            frameCount = 0;    // Réinitialiser le compteur de frames
            fpsTimer = currentTime;  // Réinitialiser le chronomètre pour la prochaine seconde
        }
        // for (Displayable renderObject : renderList) {
        //     if (renderObject instanceof Sprite) {
        //         // Ajuster les coordonnées selon le joueur
        //         ((Sprite) renderObject).drawAt(g, player.getX(), player.getY());
        //     } else {
        //         renderObject.draw(g);
        //     }
        // }
    // Sauvegarder l'état actuel du Graphics
    Graphics2D g2d = (Graphics2D) g;
    g2d.translate(-cameraX, -cameraY); // Déplacer le contexte de dessin

    // Dessiner tous les objets dans la liste de rendu
    for (Displayable renderObject : renderList) {
        if (renderObject instanceof Heart_) {
            if(((Heart_) renderObject).pris() == false) {
                renderObject.draw(g2d);

            }
            
        }
        if (!(renderObject instanceof Heart_)) {
        renderObject.draw(g2d);
        }
        
    }

    drawFPS(g);
    g.dispose();

    }

    public void clearRenderList() {
        renderList.clear();
    }
    
    @Override
    public void update() {
        this.repaint();
    }


    private void drawFPS(Graphics g) {
        // Choisir la couleur et la police pour afficher les FPS
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        
        g.drawString("FPS: " + fps, 10, 30);
    }
}
