package com.maingame.game.sprites;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class to hold all the logic and attribute for boats
 */
public class Boat {
    public final String colour;
    public final List<Texture> images;
    public final Rectangle collisionBounds; // a box used to identify collisions.
    public  int speed;
    public final int maneuverability;
    public final int robustness;
    public final int acceleration;
    private int timePenalty;
    private float totalLegTime;
    private float cumulativeLegTime;
    private int health=100;
    private int penaltyBar = 100;
    private int fatigue = 600;
    private int posX;
    private int posY;
    private final HashMap<String, List<Texture>> boatImg = new HashMap<>(); //Contains the images for the boats in an array of strings. The strings are the file names of the images.
    private final HashMap<String, Integer[]> boatMap = new HashMap<>(); //Contains the attributes for each boat in an array of integers. 0 for speed 1 for acceleration 2 robustness 3 maneuverability.
    private float leftBound; // The left edges of a boat's lane.
    private float rightBound; // The right edges of a boat's lane.
    private final float maxFrameTime; // The maximum time allowed allowed for a frame.
    private float currentFrameTime; // The current time for a frame.
    private int frame; // a integer representing which boat texture would be loaded.
    private boolean hasLost; // a boolean indicating if a boat has lost.

    //TEAM19-START : created named constants for these variables
    private static final int PEN_START = 100; // penalty bar start val
    private static final int PEN_SUB = 2; // penalty bar subtraction each frame outside lane
    //TEAM19-END

    public Boat(String col){
        this.buildBoatData();
        colour = col;
        speed = boatMap.get(col)[0];
        acceleration =  boatMap.get(col)[1];
        robustness = boatMap.get(col)[2];
        maneuverability = boatMap.get(col)[3];
        posX = boatMap.get(col)[4];
        posY = boatMap.get(col)[5];
        images = boatImg.get(col);
        leftBound = 0;
        rightBound = 0;
        timePenalty = 0;
        frame = 0;
        maxFrameTime = 0.5f/2;
        collisionBounds = new Rectangle(posX, posY,80,80);
        hasLost = false;
    }

    /**
     * Builds the hashmaps for the boat data.
     */
    private void buildBoatData() {
        Texture a = new Texture("Boat1.1.png");
        Texture b = new Texture("Boat1.2.png");
        List<Texture> arr = new ArrayList<>();
        arr.add(a);
        arr.add(b);
        boatImg.put("red", arr);
        arr = new ArrayList<>();
        a = new Texture("Boat2.1.png");
        b = new Texture("Boat2.2.png");
        arr.add(a);
        arr.add(b);
        boatImg.put("pink", arr);
        arr = new ArrayList<>();
        a = new Texture("Boat3.1.png");
        b = new Texture("Boat3.2.png");
        arr.add(a);
        arr.add(b);
        boatImg.put("blue", arr);
        arr = new ArrayList<>();
        a = new Texture("Boat4.1.png");
        b = new Texture("Boat4.2.png");
        arr.add(a);
        arr.add(b);
        boatImg.put("yellow", arr);
        arr = new ArrayList<>();
        a = new Texture("Boat5.1.png");
        b = new Texture("Boat5.2.png");
        arr.add(a);
        arr.add(b);
        boatImg.put("orange", arr);
        arr = new ArrayList<>();
        a = new Texture("Boat6.1.png");
        b = new Texture("Boat6.2.png");
        arr.add(a);
        arr.add(b);
        boatImg.put("green", arr);
        arr = new ArrayList<>();
        a = new Texture("Boat7.1.png");
        b = new Texture("Boat7.2.png");
        arr.add(a);
        arr.add(b);
        boatImg.put("purple", arr);

        Integer[] arr2 = {7,7,7,7,0,0};
        boatMap.put("red", arr2);
        arr2 = new Integer[]{8, 6, 8, 6,0,0};
        boatMap.put("pink", arr2);
        arr2 = new Integer[]{9, 4, 4, 5,0,0};
        boatMap.put("blue", arr2);
        arr2 = new Integer[]{8, 7, 5, 7,0,0};
        boatMap.put("yellow", arr2);
        arr2 = new Integer[]{8, 5, 10, 5,0,0};
        boatMap.put("orange", arr2);
        arr2 = new Integer[]{7, 6, 9, 7,0,0};
        boatMap.put("green", arr2);
        arr2 = new Integer[]{7, 6, 6, 10,0,0};
        boatMap.put("purple", arr2);
    }

    //TEAM19-START : replaced hardcoded values with 
    // constants PEN_SUB, PEN_START
    /**
     * Checks if a boat is outside of its lane.
     * If yes, it decreases the penaltyBar.
     */
    public void isBoatOutOfLane() {
        if( posX < leftBound || posX > rightBound) {
            if (penaltyBar - PEN_SUB < 0) {
                penaltyBar = 0;
            }else {
                penaltyBar -= PEN_SUB;
            }
        }else {
            if (penaltyBar > 0) {
                penaltyBar = PEN_START;
            }
        }
    }
    //TEAM19-END

    /**
     * Sets the lane limits for a boat.
     * @param leftBound a float of where the boat lane starts
     * @param rightBound a float of where the boat lane ends
     */
    public void setBounds(float leftBound, float rightBound){
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    /**
     * Changes the current texture used for a boat to provide an animation.
     * @param dt the time between each start of a render()
     */
    public void update(float dt) {
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frame ++;
            currentFrameTime = 0;
        }
        if (frame >= 2) {
            frame = 0;
        }
    }

    /**
     * Checks if a boat has hit another boat and decreases the boat's health.
     * @param boats the list of boats excluding the player's boat
     * @param player the player boat
     */
    public void hasCollided(List<Boat> boats, Boat player) {
        List<Boat> newBoats = new ArrayList<>(boats);
        if (collisionBounds != player.collisionBounds) {
            newBoats.remove(this);
            if (collisionBounds.overlaps(player.collisionBounds)) {
                if (health - 2 < 0){
                    health = 0;
                }else{
                    health -= 2;
                }
            }
        }
        for (Boat enemyBoat : newBoats) {
            if (collisionBounds.overlaps(enemyBoat.collisionBounds)) {
                if (health - 2 < 0) {
                    health = 0;
                } else {
                    health -= 2;
                }
            }
        }
    }

    /**
     * Gets time penalty.
     *
     * @return the time penalty
     */
    public int getTimePenalty() {
        return timePenalty;
    }

    /**
     * Sets time penalty.
     *
     * @param timePenalty the time penalty of a boat in seconds
     */
    public void setTimePenalty(int timePenalty) {
        this.timePenalty = timePenalty;
    }

    /**
     * Gets health.
     *
     * @return the health
     */
    public int getHealth() { return health; }

    /**
     * Sets health.
     *
     * @param health the health of a boat out of 100
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Gets penalty bar.
     *
     * @return the penalty bar
     */
    public int getPenaltyBar() {
        return penaltyBar;
    }

    /**
     * Sets penalty bar.
     *
     * @param penaltyBar the length of the penalty bar
     */
    public void setPenaltyBar(int penaltyBar) {
        this.penaltyBar = penaltyBar;
    }

    /**
     * Gets fatigue.
     *
     * @return the fatigue
     */
    public int getFatigue() {
        return fatigue;
    }

    /**
     * Sets fatigue.
     *
     * @param fatigue the fatigue
     */
    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
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
     * Gets left bound.
     *
     * @return the left bound
     */
    public float getLeftBound() {
        return leftBound;
    }

    /**
     * Gets right bound.
     *
     * @return the right bound
     */
    public float getRightBound() {
        return rightBound;
    }

    /**
     * Gets frame.
     *
     * @return the frame
     */
    public int getFrame() {
        return frame;
    }

    /**
     * Has the boat not lost
     *
     * @return not hasLost
     */
    public boolean isHasNotLost() {
        return !hasLost;
    }

    /**
     * Sets hasLost.
     *
     * @param hasLost has a boat lost
     */
    public void setHasLost(boolean hasLost) {
        this.hasLost = hasLost;
    }

    /**
     * Gets total leg time.
     *
     * @return the total leg time
     */
    public float getTotalLegTime() {
        return totalLegTime;
    }

    /**
     * Sets total leg time and cumulative leg time.
     *
     * @param totalLegTime the total leg time
     */
    public void setTotalLegTime(float totalLegTime) {
        this.totalLegTime = totalLegTime + timePenalty;
        this.cumulativeLegTime += this.totalLegTime;
    }

    /**
     * Gets cumulative leg time.
     *
     * @return the cumulative leg time
     */
    public float getCumulativeLegTime() {
        return cumulativeLegTime;
    }

    // TEAM19-START
    /** Updates the collision bounds of the boat
     */
    public void updateCollisionBounds() {
        collisionBounds.setPosition((float) getPosX() + 10, (float) getPosY() + 10);
    }
    // TEAM19-END

    //TEAM19-START

    /**
     * Sets the boats speed
     *
     * @param newSpeed boats new speed
     */
    public void setSpeed(int newSpeed){
        this.speed = newSpeed;
    }

    /**
     * Gets boats current speed
     *
     * @return the boats current speed
     */
    public int getSpeed(){ return this.speed;}

    /**
     * resets the boats speed to its default
     */
    public void resetSpeed(){this.speed = boatMap.get(colour)[0];}
    //TEAM19-END
}
