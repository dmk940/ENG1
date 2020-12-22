package com.maingame.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;
import com.badlogic.gdx.math.Rectangle;

/**
 * A class to hold all the logic and attribute for obstacles
 */
public class Obstacle {
    public final String name;
    public final Texture img;
    public final int impactDamage;
    private int posX;
    private int posY;
    private final boolean isMovable;
    private boolean direction; // the direction of the boat with true being left and false being right.
    private Rectangle collisionBounds; // a box used to identify collisions.
    private static final String ROCK_1 = "rock1";
    private static final String ROCK_2 = "rock2";

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
        }else {
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
            boat.setHealth(boat.getHealth() - obstacleDamage.get(name));
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
        obstacleDamage.put("goose", 15);
        obstacleDamage.put("duck1", 5);
        obstacleDamage.put("duck2", 5);

    }

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
            case "goose":
                collisionBounds = new Rectangle(posX, (float) posY + 10, 70, 60);
                break;
            case "duck1":
                collisionBounds = new Rectangle((float) posX + 20, (float) posY + 27, 33, 33);
                break;
            default:
                collisionBounds = new Rectangle((float) posX + 10, (float) posY + 10, 40, 50);
                break;
        }
    }

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