import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.TimerTask;




import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {


    JFrame displayZoneFrame;

    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;
    private Timer timer;
    private final long DELAI_MS = 2000;


    public static void main(String[] args) throws Exception {
        // Créer et afficher l'écran d'accueil
        JFrame welcomeFrame = new JFrame("Écran d'accueil");
        MainMenu mainMenu = new MainMenu();
        welcomeFrame.add(mainMenu);
        welcomeFrame.setSize(1550, 800); // Taille de la fenêtre
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setVisible(true);
        mainMenu.requestFocusInWindow(); // Pour s'assurer que le JPanel reçoit les événements de clavier
    }
    public Main() throws Exception{

        
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(1550,800);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Hero hero = new Hero(70, 200,ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50, 100, 30, 25);
        Orc orc1 = new Orc(260.0, 270.0, ImageIO.read(new File("./img/orc2.png")), 64, 64, 50, 30, 20, Patterntype.SQUARE);
  
            
        Orc orc2 = new Orc(450.0, 510.0, ImageIO.read(new File("./img/orc2.png")), 64, 64,  50, 30,20, Patterntype.BACK_AND_FORTH);

        Orc orc3 = new Orc(1420.0, 510.0, ImageIO.read(new File("./img/orc1.png")), 64, 64,  50, 30,20);
        Orc orc4 = new Orc(70.0, 575.0, ImageIO.read(new File("./img/orc1.png")), 64, 64,  50, 30,20);
        Orc orc5 = new Orc(1080.0, 260.0, ImageIO.read(new File("./img/orc2.png")), 64, 64,  50, 30,20, Patterntype.BACK_AND_FORTH);


        Heart_ heart1 = new Heart_(1420, 75, ImageIO.read(new File("./img/heart.png")), 32, 32, 30);
        Heart_ heart2 = new Heart_(1420, 650, ImageIO.read(new File("./img/heart.png")), 32, 32, 30);
        Heart_ heart3 = new Heart_(70, 75, ImageIO.read(new File("./img/heart.png")), 32, 32, 30);
        Heart_ heart4 = new Heart_(780, 580, ImageIO.read(new File("./img/heart.png")), 32, 32, 30);


        Playground level1 = new Playground("./data/level2.txt");
        orc2.setDirection(Direction.WEST);

        orc3.setDirection(Direction.WEST);
        orc4.setDirection(Direction.EAST);
        orc5.setDirection(Direction.WEST);

        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();
        GameEngine gameEngine = new GameEngine(hero, renderEngine, displayZoneFrame, level1);
        

        Timer renderTimer = new Timer(10,(time)-> renderEngine.update());
        Timer gameTimer = new Timer(10,(time)-> gameEngine.update());
        Timer physicTimer = new Timer(10,(time)-> physicEngine.update());


        Timer timerspeed = new Timer(1,e -> { for(DynamicSprite movingObjects : physicEngine.getMovingSpriteList()){ movingObjects.setSpeed(0);}});
        renderTimer.start();
        gameTimer.start();
        physicTimer.start();
        timerspeed.setRepeats(false);
        timerspeed.start();

        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);

        renderEngine.addToRenderList(level1.getSpriteList());
        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level1.getSolidSpriteList());

        renderEngine.addToRenderList(orc1);
        renderEngine.addToRenderList(orc2);
        renderEngine.addToRenderList(orc3);
        renderEngine.addToRenderList(orc4);
        renderEngine.addToRenderList(orc5);


        physicEngine.addToMovingSpriteList(orc1);
        physicEngine.addToMovingSpriteList(orc2);
        physicEngine.addToMovingSpriteList(orc3);
        physicEngine.addToMovingSpriteList(orc4);
        physicEngine.addToMovingSpriteList(orc5);


        renderEngine.addToRenderList(heart1);
        renderEngine.addToRenderList(heart2);
        renderEngine.addToRenderList(heart3);
        renderEngine.addToRenderList(heart4);

        physicEngine.addToMovingSpriteList(heart1);
        physicEngine.addToMovingSpriteList(heart2);
        physicEngine.addToMovingSpriteList(heart3);
        physicEngine.addToMovingSpriteList(heart4);


        displayZoneFrame.addKeyListener(gameEngine);

    }
    public static void returnToMainMenu(JFrame currentFrame) {
        if (currentFrame != null) {
            currentFrame.dispose(); // Ferme la fenêtre actuelle
        }
        JFrame welcomeFrame = new JFrame("Écran d'accueil");
        MainMenu mainMenu = new MainMenu();
        welcomeFrame.add(mainMenu);
        welcomeFrame.setSize(1550, 800); // Taille de la fenêtre
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setVisible(true);
        mainMenu.requestFocusInWindow(); // Pour s'assurer que le JPanel reçoit les événements de clavier

        
    }


    
}
