import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.imageio.ImageIO;

public class MainMenu extends JPanel implements KeyListener {
    private Image backgroundImage;

    public MainMenu() {
        try {
            backgroundImage = ImageIO.read(new File("./img/welcome.png")); // Remplacez par le chemin de votre image
        } catch (Exception e) {
            e.printStackTrace();
        }
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Lancer le jeu
            System.out.println("Espace pressé, démarrer le jeu !");
            SwingUtilities.invokeLater(() -> {
                try {
                    new Main(); // Appelle le constructeur de la classe Main pour démarrer le jeu
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose(); // Ferme l'écran d'accueil
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}