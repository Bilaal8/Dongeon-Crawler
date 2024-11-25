import java.util.ArrayList;

public class PhysicEngine implements Engine {
    private ArrayList<DynamicSprite> movingSpriteList; // Liste des sprites en mouvement
    private ArrayList<Sprite> environment; // Liste des sprites de l'environnement

    public PhysicEngine() {
        movingSpriteList = new ArrayList<>();
        environment = new ArrayList<>();
    }

    // Ajoute un sprite à l'environnement
    public void addToEnvironmentList(Sprite sprite) {
        if (!environment.contains(sprite)) {
            environment.add(sprite);
        }
    }

    // Définit l'environnement de la physique
    public void setEnvironment(ArrayList<Sprite> environment) {
        this.environment = environment;
    }

    // Récupère la liste de l'environnement
    public ArrayList<Sprite> getEnvironment() {
        return environment;
    }

    // Récupère la liste des sprites en mouvement
    public ArrayList<DynamicSprite> getMovingSpriteList() {
        return movingSpriteList;
    }

    // Ajoute un sprite dynamique à la liste de mouvement
    public void addToMovingSpriteList(DynamicSprite sprite) {
        if (!movingSpriteList.contains(sprite)) {
            movingSpriteList.add(sprite);
        }
    }



    // Met à jour les positions des sprites en mouvement
    @Override
    public void update() {
        for (DynamicSprite dynamicSprite : movingSpriteList) {
            if(!( dynamicSprite instanceof Orc)){
            dynamicSprite.moveIfPossible(environment, movingSpriteList); // Déplace le sprite si possible
            }
        }
    }
}


