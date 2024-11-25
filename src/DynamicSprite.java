import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite{
    protected Direction direction = Direction.NONE;
    private double speed = 1;
    private double timeBetweenFrame = 100;

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    
    public void setSpeed(double speed) {
        this.speed = speed;
    }



    public double getSpeed() {
        return speed;
    }


    public boolean isMovingPossible(ArrayList<Sprite> environment, ArrayList<DynamicSprite> MovingSpriteList) {
        
        // Crée deux rectangles pour les directions X et Y
        Rectangle2D.Double movedX = new Rectangle2D.Double();
        Rectangle2D.Double movedY = new Rectangle2D.Double();
        // Si la direction est diagonale, on déplace en X et Y en même temps
        switch (direction) {
            case EAST:
                movedX.setRect(super.getHitBox().getX()+ 5 + speed, super.getHitBox().getY()+ 5,
                        super.getHitBox().getWidth()-10, super.getHitBox().getHeight()-10);
                break;
            case WEST:
                movedX.setRect(super.getHitBox().getX()+ 5 - speed, super.getHitBox().getY()+ 5,
                        super.getHitBox().getWidth()-10, super.getHitBox().getHeight()-10);
                break;
            case NORTH:
                movedY.setRect(super.getHitBox().getX()+ 5, super.getHitBox().getY() - speed+ 5,
                        super.getHitBox().getWidth()-10, super.getHitBox().getHeight()-10);
                break;
            case SOUTH:
                movedY.setRect(super.getHitBox().getX()+ 5, super.getHitBox().getY() + speed+ 5,
                        super.getHitBox().getWidth()-10, super.getHitBox().getHeight()-10);
                break;
            case NORTH_EAST:
                movedX.setRect(super.getHitBox().getX() + 5+ speed *0.71, super.getHitBox().getY() + 5- speed *0.71,
                        super.getHitBox().getWidth()-10, super.getHitBox().getHeight()-10);
                movedY.setRect(super.getHitBox().getX() + 5+ speed *0.71, super.getHitBox().getY()+ 5 - speed *0.71,
                        super.getHitBox().getWidth()-10, super.getHitBox().getHeight()-10);
                break;
            case NORTH_WEST:
                movedX.setRect(super.getHitBox().getX()+ 5 - speed *0.71, super.getHitBox().getY()+ 5 - speed *0.71,
                        super.getHitBox().getWidth()-10, super.getHitBox().getHeight()-10);
                movedY.setRect(super.getHitBox().getX()+ 5 - speed *0.71, super.getHitBox().getY()+ 5 - speed *0.71,
                        super.getHitBox().getWidth()-10, super.getHitBox().getHeight()-10);
                break;
            case SOUTH_EAST:
                movedX.setRect(super.getHitBox().getX()+ 5 + speed *0.71, super.getHitBox().getY()+ 5 + speed*0.71,
                        super.getHitBox().getWidth()-10, super.getHitBox().getHeight()-10);
                movedY.setRect(super.getHitBox().getX()+ 5 + speed *0.71, super.getHitBox().getY()+ 5 + speed *0.71,
                        super.getHitBox().getWidth()-10, super.getHitBox().getHeight()-10);
                break;
            case SOUTH_WEST:
                movedX.setRect(super.getHitBox().getX()+ 5 - speed*0.71, super.getHitBox().getY()+ 5 + speed *0.71,
                        super.getHitBox().getWidth()-10, super.getHitBox().getHeight()-10);
                movedY.setRect(super.getHitBox().getX()+ 5 - speed *0.71, super.getHitBox().getY()+ 5 + speed *0.71,
                        super.getHitBox().getWidth()-10, super.getHitBox().getHeight()-10);
                break;
        }
    
        for (Sprite s : environment){
            //tue le hero si il marche sur un trap
            if ((s instanceof Trap) && (s!=this)){
                if (((Trap) s).intersect(movedX) || ((Trap) s).intersect(movedY)){
                    if (this instanceof Hero){
                        if(((Trap) s).isActive()){
                            ((Hero) this).takeDamage(((Trap) s).getDamage()); // Inflige des dégâts au héros
                            ((Trap) s).deactivate();
                        }  // Désactive le piège après l'activation
                    }
                    return true;
                }
            }
            // fais des degat au hero si il marche sur des spike
            if ((s instanceof Spike) && (s!=this)){
                if (((Spike) s).intersect(movedX) || ((Spike) s).intersect(movedY)){
                    if (this instanceof Hero){
                        if(((Spike) s).isActive()){
                            ((Hero) this).setSpeed(getSpeed()/3);
                            ((Hero) this).takeDamage(((Spike) s).getDamage()); // Inflige des dégâts au héros 
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

        for (DynamicSprite s : MovingSpriteList){

            if ((s instanceof Heart_) && (s!=this)){
                if (((Heart_) s).intersect(movedX) || ((Heart_) s).intersect(movedY)){
                    if (this instanceof Hero){
                        if(((Heart_) s).pris() == false){
                            ((Hero) this).setSpeed(getSpeed()*2);
                            ((Hero) this).Heal(30);
                            ((Heart_) s).setPris(true);
                        }  
                    }
                    
                }
            }
            if ((s instanceof Orc) && (s!=this)){
                if (((Orc) s).intersect(movedX) || ((Orc) s).intersect(movedY)){
                    if (this instanceof Hero){
                        if(((Orc) s).isDead() == false){
                            ((Hero) this).setSpeed(getSpeed()/3);
                            ((Orc) s).setSpeed(getSpeed()/3);
                            ((Hero) this).takeDamage(((Orc) s).getAttack()); 
                            ((Orc) s).takeDamage(((Hero) this).getAttack());
                            return false;
                        }  
                    return true;
                    }
                    
                }
            }


            
        }
        return true;  // Retourner true si aucune collision n'est détectée
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move(){
        switch (direction){
            case NORTH -> {
                this.y-=speed;
            }
            case SOUTH -> {
                this.y+=speed;
            }
            case EAST -> {
                this.x+=speed;
            }
            case WEST -> {
                this.x-=speed;
            }
            case NORTH_EAST ->{
                this.y-=speed*0.71;
                this.x+=speed*0.71;
            }
            case NORTH_WEST ->{
                this.y-=speed*0.71;
                this.x-=speed*0.71;
            }
            case SOUTH_EAST ->{
                this.y+=speed*0.71;
                this.x+=speed*0.71;
            }
            case SOUTH_WEST ->{
                this.y+=speed*0.71;
                this.x-=speed*0.71;
            }
            case NONE ->{
            }
        }
    }

    public void moveIfPossible(ArrayList<Sprite> environment, ArrayList<DynamicSprite> MovingSpriteList) {
        if (isMovingPossible(environment,MovingSpriteList)) {
            move();
        }
    }
    


    @Override
    public void draw(Graphics g) {
        
    }
}
