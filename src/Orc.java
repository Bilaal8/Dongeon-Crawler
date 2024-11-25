import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Orc extends DynamicSprite {
    private int health;
    private int maxHealth;
    private int attack;
    private int defense;
    private boolean isDead;
    private Patterntype movementPatternType;
    private int stepCounter = 0;
    private double timeBetweenFrame = 100;
    private int stepsPerDirection = 50;
    private final double xi;
    private final double yi;
    private int pasdroite = 500;




    public Orc(double x, double y, Image image, double width, double height, int maxHealth, int attack,
               int defense, Patterntype movementPatternType) {
        super(x, y, image, width, height);
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.attack = attack;
        this.defense = defense;
        this.isDead = false;
        this.movementPatternType = movementPatternType;
        this.xi = x;
        this.yi = y;
        
    }



    public Orc(double x, double y, Image image, double width, double height, int maxHealth, int attack,
               int defense) {
        super(x, y, image, width, height);
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.attack = attack;
        this.defense = defense;
        this.isDead = false;
        this.xi = x;
        this.yi = y;
    }
@Override
    public boolean isMovingPossible(ArrayList<Sprite> environment, ArrayList<DynamicSprite> MovingSpriteList) {
        
        // Crée deux rectangles pour les directions X et Y
        Rectangle2D.Double movedX = new Rectangle2D.Double();
        Rectangle2D.Double movedY = new Rectangle2D.Double();
        // Si la direction est diagonale, on déplace en X et Y en même temps
        switch (direction) {
            case EAST:
                movedX.setRect(super.getHitBox().getX()+ 10 + super.getSpeed(), super.getHitBox().getY()+ 5,
                        super.getHitBox().getWidth()-20, super.getHitBox().getHeight()-20);
                break;
            case WEST:
                movedX.setRect(super.getHitBox().getX()+ 10 - super.getSpeed(), super.getHitBox().getY()+ 5,
                        super.getHitBox().getWidth()-20, super.getHitBox().getHeight()-10);
                break;
            case NORTH:
                movedY.setRect(super.getHitBox().getX()+ 10, super.getHitBox().getY() - super.getSpeed()+ 5,
                        super.getHitBox().getWidth()-20, super.getHitBox().getHeight()-20);
                break;
            case SOUTH:
                movedY.setRect(super.getHitBox().getX()+ 10, super.getHitBox().getY() + super.getSpeed()+ 5,
                        super.getHitBox().getWidth()-20, super.getHitBox().getHeight()-20);
                break;
            case NORTH_EAST:
                movedX.setRect(super.getHitBox().getX() + 10+ super.getSpeed() *0.71, super.getHitBox().getY() + 5- super.getSpeed() *0.71,
                        super.getHitBox().getWidth()-20, super.getHitBox().getHeight()-20);
                movedY.setRect(super.getHitBox().getX() + 10+ super.getSpeed() *0.71, super.getHitBox().getY()+ 5 - super.getSpeed() *0.71,
                        super.getHitBox().getWidth()-20, super.getHitBox().getHeight()-20);
                break;
            case NORTH_WEST:
                movedX.setRect(super.getHitBox().getX()+ 10 - super.getSpeed() *0.71, super.getHitBox().getY()+ 5 - super.getSpeed() *0.71,
                        super.getHitBox().getWidth()-20, super.getHitBox().getHeight()-20);
                movedY.setRect(super.getHitBox().getX()+ 10 - super.getSpeed() *0.71, super.getHitBox().getY()+ 5 - super.getSpeed() *0.71,
                        super.getHitBox().getWidth()-20, super.getHitBox().getHeight()-20);
                break;
            case SOUTH_EAST:
                movedX.setRect(super.getHitBox().getX()+ 10 + super.getSpeed() *0.71, super.getHitBox().getY()+ 5 + super.getSpeed()*0.71,
                        super.getHitBox().getWidth()-20, super.getHitBox().getHeight()-20);
                movedY.setRect(super.getHitBox().getX()+ 10 + super.getSpeed() *0.71, super.getHitBox().getY()+ 5 + super.getSpeed() *0.71,
                        super.getHitBox().getWidth()-20, super.getHitBox().getHeight()-20);
                break;
            case SOUTH_WEST:
                movedX.setRect(super.getHitBox().getX()+ 10 - super.getSpeed()*0.71, super.getHitBox().getY()+ 5 + super.getSpeed() *0.71,
                        super.getHitBox().getWidth()-20, super.getHitBox().getHeight()-20);
                movedY.setRect(super.getHitBox().getX()+ 10 - super.getSpeed() *0.71, super.getHitBox().getY()+ 5 + super.getSpeed() *0.71,
                        super.getHitBox().getWidth()-20, super.getHitBox().getHeight()-20);
                break;
        }
    
        for (Sprite s : environment){
            if ((s instanceof Trap) && (s!=this)){
                if (((Trap) s).intersect(movedX) || ((Trap) s).intersect(movedY)){
                    if (this instanceof Orc){
                        if(((Trap) s).isActive()){
                            ((Orc) this).takeDamage(((Trap) s).getDamage()); 
                            ((Trap) s).deactivate();
                        }  
                    }
                    return true;
                }
            }
            if ((s instanceof Spike) && (s!=this)){
                if (((Spike) s).intersect(movedX) || ((Spike) s).intersect(movedY)){
                    if (this instanceof Orc){
                        if(((Spike) s).isActive()){
                            ((Orc) this).takeDamage(((Spike) s).getDamage()); // Inflige des dégâts au héros 
                            ((Spike) s).deactivate();
                        }  // Désactive le piège après l'activation
                    }
                    return true;
                }
            }
            if ((s instanceof SolidSprite) && (s != this)) {
                // Vérifier les collisions séparément sur les deux axes
                if (((SolidSprite) s).intersect(movedX) || ((SolidSprite) s).intersect(movedY)) {
                    return false;  // Retourner false si une collision est détectée
                }
            }
        }
        return true;  // Retourner true si aucune collision n'est détectée
    }


    public void updateMovement(ArrayList<Sprite> environment, ArrayList<DynamicSprite> MovingSpriteList) {
        if (isDead) return;

        moveIfPossible(environment, MovingSpriteList);
    }
    public void reset() {
        System.out.println("testORC");
        x =xi;
        y =yi;
        health = maxHealth;
    }

    private void backAndForthMovement() {
        if (stepCounter < stepsPerDirection) {
            setDirection(Direction.EAST);
        } else if (stepCounter < stepsPerDirection * 2) {
            setDirection(Direction.WEST);
        }
        stepCounter = (stepCounter + 1) % (stepsPerDirection * 2);
    }

    private void squareMovement() {
        if (stepCounter < stepsPerDirection) {
            setDirection(Direction.EAST);
        } else if (stepCounter < stepsPerDirection * 2) {
            setDirection(Direction.SOUTH);
        } else if (stepCounter < stepsPerDirection * 3) {
            setDirection(Direction.WEST);
        } else if (stepCounter < stepsPerDirection * 4) {
            setDirection(Direction.NORTH);
        }
        stepCounter = (stepCounter + 1) % (stepsPerDirection * 4);
    }
    
    

    public int getHealth() {
        return health;
    }

    public double getX() {
        return x;
    }


    
    public int getAttack() {
        return attack;
    }

    public double getY() {
        return y;
    }


        public void takeDamage(int damage) {
        double defenseFactor = Math.log1p(defense) / Math.log1p(40);
        double damageReduction = Math.min(defenseFactor, 1.0);
        int damageTaken = (int) (damage * (1 - damageReduction));
        health = Math.max(0, health - damageTaken);


        if (health <= 0 && !isDead) {
            isDead = true;
        }
    }

    public boolean isDead() {
        return isDead;
    }
    @Override
    public void move(){
        switch (direction){
            case NORTH -> {
            }
            case SOUTH -> {
            }
            case EAST -> {
            }
            case WEST -> {
            }
            case NORTH_EAST ->{

            }
            case NORTH_WEST ->{

            }
            case SOUTH_EAST ->{

            }
            case SOUTH_WEST ->{

            }
            case NONE ->{
            }
        }
    }


    private Direction lastDirection;

    @Override
    public void draw(Graphics g) {
        if (health > 0) {
        if(getSpeed() != 0) {
            lastDirection = direction;
    
            int index = (int) (System.currentTimeMillis() / timeBetweenFrame % 8);
    
            g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                (int) (index * this.width), (int) (direction.getFrameLineNumber() * height),
                (int) ((index + 1) * this.width), (int) ((direction.getFrameLineNumber() + 1) * this.height), null);
        }
        
        if(getSpeed() == 0) {
            g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                0, (int) (lastDirection.getFrameLineNumber() * height), // Premier frame (index 0) dans la ligne de `lastDirection`
                (int) (width), (int) ((lastDirection.getFrameLineNumber() + 1) * height), null);
        }
        drawHealthBar(g);
        }

    }

    private void drawHealthBar(Graphics g) {
        int barWidth = 40;
        int barHeight = 5;
        int x = (int) this.x + (int)(width / 2) - (barWidth / 2);
        int y = (int) this.y ;

        g.setColor(Color.BLACK);
        g.fillRect(x, y, barWidth, barHeight);
        
        g.setColor(Color.RED);
        int currentBarWidth = (int)((health / (double) maxHealth) * barWidth);
        g.fillRect(x, y, currentBarWidth, barHeight);

    }

@Override
    public Rectangle getHitBox() {
        return new Rectangle((int) x+16, (int) y+16, (int) 32, (int) 32);
    }




    

}
