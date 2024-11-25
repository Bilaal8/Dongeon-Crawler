import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
public class Hero extends DynamicSprite {
    private int health;          // Points de vie
    private int maxHealth;       // Points de vie maximum
    private int attack;          // Points d'attaque
    private int defense;         // Points de défense
    private boolean isDead;
    private double timeBetweenFrame = 100;
    private Image gameOverImage =Toolkit.getDefaultToolkit().getImage("./img/gameover.png");

    public Hero(double x, double y, Image image, double width, double height, int maxHealth, int attack, int defense) {
        super(x, y, image, width, height);
        this.maxHealth = maxHealth;
        this.health = maxHealth; // Le héros commence avec un maximum de points de vie
        this.attack = attack;
        this.defense = defense;
        this.isDead = false;
        
        
    }

    

    public int getHealth() {
        return health;
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
    

    public void Heal(int amount) {
        health = Math.min(maxHealth, health + amount);
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

    public void PourcentHeal(int amount) {
        health = Math.min(maxHealth, health+ maxHealth*amount/100);
    }

    public void reduceHealth(int amount) {
        takeDamage(amount);
    }

    private Direction lastDirection;


    @Override
    public void draw(Graphics g) {
        if (health <= 0) {
            g.drawImage(gameOverImage, 500, 200, 546, 400,null);
        }
        if (health > 0) {
            if(getSpeed() != 0) {
                lastDirection = direction;
        
                int index = (int) (System.currentTimeMillis() / timeBetweenFrame % 10);
        
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
        int y = (int) this.y - 10;

        g.setColor(Color.BLACK);
        g.fillRect(x, y, barWidth, barHeight);
        
        g.setColor(Color.GREEN);
        int currentBarWidth = (int)((health / (double) maxHealth) * barWidth);
        g.fillRect(x, y, currentBarWidth, barHeight);
    }
    public Rectangle getHitBox() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }


}
