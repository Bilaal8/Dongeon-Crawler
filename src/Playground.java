import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Playground {
    private ArrayList<Sprite> background = new ArrayList<>();
    private ArrayList<SolidSprite> obstcale = new ArrayList<>();
    private ArrayList<SolidSprite> peutInteragir = new ArrayList<>();
    private static ArrayList<Trap> traps = new ArrayList<>(); // Liste séparée pour les pièges
    private static ArrayList<Spike> spikes = new ArrayList<>();
        private static Map<Character, Image> imageMap = new HashMap<>(); // Map pour stocker les images
    
        private int levelWidth; // Largeur du niveau en nombre de colonnes
        private int levelHeight; // Hauteur du niveau en nombre de lignes
        private int spriteWidth; // Largeur d'un sprite (en pixels)
        private int spriteHeight; // Hauteur d'un sprite (en pixels)
    
        public Playground(String pathName) {
            try {
                // Charge les images
                loadImages();
    
                // Initialise les variables pour la lecture du niveau
                BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
                String line = bufferedReader.readLine();
                int lineNumber = 0;
                int columnNumber;
    
                // Calculer la taille du niveau en termes de nombre de colonnes et de lignes
                while (line != null) {
                    columnNumber = 0; // Réinitialiser le numéro de colonne pour chaque ligne
                    levelWidth = Math.max(levelWidth, line.length()); // Le niveau est large autant que la plus longue ligne
                    levelHeight++; // Compter le nombre de lignes
                    for (byte element : line.getBytes(StandardCharsets.UTF_8)) {
                        createSprite(element, columnNumber, lineNumber);
                        columnNumber++;
                    }
                    lineNumber++;
                    line = bufferedReader.readLine();
                }
    
                // Définir la taille en pixels du niveau
                if (!imageMap.isEmpty()) {
                    // Utilise la taille de l'herbe (qui est généralement la plus simple à utiliser pour obtenir la taille du sprite)
                    Image grassImage = imageMap.get(' ');
                    if (grassImage != null) {
                        spriteWidth = grassImage.getWidth(null);
                        spriteHeight = grassImage.getHeight(null);
                    }
                }
    
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
        // Méthode pour récupérer la taille du niveau en largeur (en pixels)
        public int getLevelWidth() {
            return levelWidth * spriteWidth; // Largeur totale = largeur du niveau en colonnes * largeur d'un sprite
        }
    
        // Méthode pour récupérer la taille du niveau en hauteur (en pixels)
        public int getLevelHeight() {
            return levelHeight * spriteHeight; // Hauteur totale = hauteur du niveau en lignes * hauteur d'un sprite
        }
    
        // Méthode pour récupérer l'image d'herbe
        public static Image getGrassImage() {
            return imageMap.get(' ');  // ' ' correspond à l'image de l'herbe dans le map
        }
    
        // Méthode pour charger toutes les images utilisées dans le jeu
        private void loadImages() throws Exception {
            imageMap.put('T', ImageIO.read(new File("./img/tree.png")));   // Arbre
            imageMap.put(' ', ImageIO.read(new File("./img/grass.png")));  // Herbe
            imageMap.put('R', ImageIO.read(new File("./img/rock.png")));   // Rocher
            imageMap.put('X', ImageIO.read(new File("./img/trap.png")));   // Piège
            imageMap.put('S', ImageIO.read(new File("./img/spike.png")));   // spike
            imageMap.put('1', ImageIO.read(new File("./img/door.png")));   // spike
            imageMap.put('2', ImageIO.read(new File("./img/door.png")));   // spike
            imageMap.put('3', ImageIO.read(new File("./img/door.png")));   // spike
            imageMap.put('4', ImageIO.read(new File("./img/door.png")));   // spike
            imageMap.put('5', ImageIO.read(new File("./img/door.png")));   // spike
            imageMap.put('6', ImageIO.read(new File("./img/door.png")));   // spike
            imageMap.put('h', ImageIO.read(new File("./img/eau_haut.png")));   // spike
            imageMap.put('f', ImageIO.read(new File("./img/eau_full.png")));   // spike
            imageMap.put('b', ImageIO.read(new File("./img/eau_bas.png")));   // spike

        }
    
        // Méthode pour créer un sprite en fonction du caractère rencontré dans le fichier
        private void createSprite(byte element, int columnNumber, int lineNumber) {
            Image image = imageMap.get((char) element);
            if (image == null) return;
    
            double x = columnNumber * image.getWidth(null);
            double y = lineNumber * image.getHeight(null);
    
            switch ((char) element) {
                case 'T': // Arbre
                case 'R': // Rocher
                case 'f':
                case 'h':
                case 'b':
                    obstcale.add(new SolidSprite(x, y, image, image.getWidth(null), image.getHeight(null)));
                    break;
                case 'X': // Piège
                    Trap trap = new Trap(x, y, image, image.getWidth(null), image.getHeight(null), 3000); // 3000 dégâts
                    peutInteragir.add(trap);  // Ajouter à l'environnement
                    traps.add(trap);        // Ajouter à la liste des pièges
                    break;
                case ' ': // Herbe (ne fait rien, juste pour afficher)

                    background.add(new Sprite(x, y, image, image.getWidth(null), image.getHeight(null)));
                    break;
                case 'S': // Piège
                    Spike spike = new Spike(x, y, image, image.getWidth(null), image.getHeight(null), 100); // 100 dégâts
                    peutInteragir.add(spike);  // Ajouter à l'environnements
                    spikes.add(spike);        // Ajouter à la liste des spikes
                    break;   
                case '1': // Porte
                    Door door1 = new Door(x, y, image, image.getWidth(null), image.getHeight(null),1,2);
                    background.add(door1); // Ajoutez aux obstacles pour permettre la collision
                    break;
                case '2': // Porte
                    Door door2 = new Door(x, y, image, image.getWidth(null), image.getHeight(null),2,1);
                    background.add(door2); // Ajoutez aux obstacles pour permettre la collision
                    break;
                case '3': // Porte
                    Door door3 = new Door(x, y, image, image.getWidth(null), image.getHeight(null),2,1);
                    background.add(door3); // Ajoutez aux obstacles pour permettre la collision
                    break;
                case '4': // Porte
                    Door door4 = new Door(x, y, image, image.getWidth(null), image.getHeight(null),1,2);
                    background.add(door4); // Ajoutez aux obstacles pour permettre la collision
                    break;
                case '5': // Porte
                    Door door5 = new Door(x, y, image, image.getWidth(null), image.getHeight(null),2,3);
                    background.add(door5); // Ajoutez aux obstacles pour permettre la collision
                    break;
            }
        }
    
        // Méthode pour récupérer la liste des pièges
        public static ArrayList<Trap> getTraps() {
            return traps;
        }
        public static ArrayList<Spike> getSpikes() {
            return spikes;
    }

    // Méthode pour récupérer la liste de tous les sprites solides (comme les arbres et les rochers)
    public ArrayList<Sprite> getSolidSpriteList() {
        ArrayList<Sprite> solidSpriteArrayList = new ArrayList<>();
        for (Sprite sprite : obstcale) {
            if (sprite instanceof SolidSprite) solidSpriteArrayList.add(sprite);
        }
        for (Sprite sprite : peutInteragir) {
            if (sprite instanceof SolidSprite) solidSpriteArrayList.add(sprite);
        }

        return solidSpriteArrayList;
    }

    // Méthode pour récupérer la liste de tous les sprites (utilisé pour le rendu)
    public ArrayList<Displayable> getSpriteList() {
        ArrayList<Displayable> displayableArrayList = new ArrayList<>();
        for (Sprite SolidSprite : obstcale) {
            displayableArrayList.add((Displayable) SolidSprite);
        }
        for (Sprite sprite : peutInteragir) {
            displayableArrayList.add((Displayable) sprite);
        }
        for (Sprite sprite : background) {
            displayableArrayList.add((Displayable) sprite);
        }
        return displayableArrayList;
    }
}
