package com.maingame.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;
import com.badlogic.gdx.math.Rectangle;

/**
 * A class to hold all the logic and attribute for obstacles
 */
public class Obstacle implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    public final String name;
    transient public Texture img;
    public final int impactDamage;
    private int posX;
    private int posY;
    private final boolean isMovable;
    private boolean direction; // the direction of the boat with true being left and false being right.
    public Rectangle collisionBounds; // a box used to identify collisions.
    private static final String ROCK_1 = "rock1";
    private static final String ROCK_2 = "rock2";
    //TEAM19-START : move the rest of these hardcoded strings to constants
    private static final String GOOSE = "goose";
    private static final String DUCK_1 = "duck1";
    private static final String DUCK_2 = "duck2";
    private static final String HEALTHUP = "healthUp";
    private static final String FATIGUEUP = "fatigueUp";
    private static final String HEALTHUP2 = "healthUp2";
    private static final String FATIGUEUP2 = "fatigueUp2";
    private static final String SPEEDUP = "speedUp";

    //TEAM19-END
    
    HashMap<String, Integer> obstacleDamage = new HashMap<>();

    public Obstacle(String obstacleName) {
        name = obstacleName;
        buildObstacleData();
        img = new Texture(obstacleName + ".png");
        impactDamage = obstacleDamage.get(obstacleName);
        posX = -9000;
        posY = -9000;

        if ((obstacleName.equals(ROCK_1)) || (obstacleName.equals(ROCK_2))){
            isMovable = false;
            collisionBounds = new Rectangle(posX,posY,30,30);
        }
        //TEAM19-START: Make it so powerups don't move
        else if(obstacleName == "healthUp" || obstacleName == "fatigueUp" || obstacleName == "fatigueUp2" || obstacleName == "healthUp2" || obstacleName == "speedUp") {
            isMovable = false;
            collisionBounds = new Rectangle(posX,posY,80,80);
        }
        //TEAM19-END
        else {
            isMovable = true;
            collisionBounds = new Rectangle(posX,posY,70,70);
        }
    }

    /**
     * Checks if a boat has collided with an obstacle.
     * If yes, the boat's health is decreased and repositions the obstacle.
     * @param boat a class representing a boat.
     */
    public void checkHit(Boat boat) {
        if (collisionBounds.overlaps(boat.collisionBounds) && (boat.isHasNotLost())){
            //TEAM19-START stops health from going above max when picking up power-up
            if(boat.getHealth()-obstacleDamage.get(name) < 100) {
                boat.setHealth(boat.getHealth() - obstacleDamage.get(name));
            }
            else if(boat.getHealth()-obstacleDamage.get(name) >= 100) {
                boat.setHealth(100);
            }
            if(name == "fatigueUp"){
                if(boat.getFatigue()+15 < 600) {
                    boat.setFatigue(boat.getFatigue() + 15);
                }
                else{
                    boat.setFatigue(600);
                }
            }
            else if(name == "fatigueUp2"){
                if(boat.getFatigue()+30 < 600) {
                    boat.setFatigue(boat.getFatigue() + 30);
                }
                else{
                    boat.setFatigue(600);
                }
            }
            else if(name == "speedUp"){
                boat.setSpeed( boat.getSpeed() + 1);
            }
            posX = -9000;
            posY = -9000;
            collisionBounds.setPosition(posX,posY);
            if (boat.getHealth() < 0) {
                boat.setHealth(0);
            }
        }
    }

    /**
     * Adds how much health would be lost from each obstacle impact.
     */
    private void buildObstacleData() {
        obstacleDamage.put(ROCK_1, 10);
        obstacleDamage.put(ROCK_2, 10);
        //TEAM19-START replace with new constant names
        obstacleDamage.put(GOOSE, 15);
        obstacleDamage.put(DUCK_1, 5);
        obstacleDamage.put(DUCK_2, 5);
        obstacleDamage.put(HEALTHUP,-10);
        obstacleDamage.put(FATIGUEUP,0);
        obstacleDamage.put(HEALTHUP2,-20);
        obstacleDamage.put(FATIGUEUP2,0);
        obstacleDamage.put(SPEEDUP,0);
        //TEAM19-END

    }

    //TEAM19-START : made cases GOOSE and DUCK_1 constants
    /**
     * Updates the collision bounds to match the current obstacle position.
     */
    public void updateCollisionBounds() {
        switch (name) {
            case ROCK_1:
                collisionBounds = new Rectangle((float) posX + 20, (float) posY + 20, 30, 30);
                break;
            case ROCK_2:
                collisionBounds = new Rectangle((float) posX + 5, (float) posY + 35, 30, 25);
                break;
            case GOOSE:
                collisionBounds = new Rectangle(posX, (float) posY + 10, 70, 60);
                break;
            case DUCK_1:
                collisionBounds = new Rectangle((float) posX + 20, (float) posY + 27, 33, 33);
                break;
            default:
                collisionBounds = new Rectangle((float) posX + 10, (float) posY + 10, 40, 50);
                break;
        }
    }
    //TEAM19-END

    /**
     * Moves movable obstacles on the screen.
     */
    public void moveObstacle() {
        if (isMovable) {
            if (!direction){
                posX += 1;
            }else {
                posX -= 1;
            }
        }
    }

    /**
     * Gets pos x.
     *
     * @return the pos x
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Sets pos x.
     *
     * @param posX the pos x
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Gets pos y.
     *
     * @return the pos y
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Sets pos y.
     *
     * @param posY the pos y
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Is direction boolean.
     *
     * @return the direction of the obstacle
     */
    public boolean isDirection() {
        return direction;
    }

    /**
     * Sets direction.
     *
     * @param direction the direction of the boat with true being left and false being right
     */
    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    /**
     * Gets collision bounds.
     *
     * @return the collision bounds
     */
    public Rectangle getCollisionBounds() {
        return collisionBounds;
    }
}