package com.maingame.game.sprites;

import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to define the Artificial Intelligence for non-player boats.
 */
public class AI {
    private final Boat boat;
    private final List<Obstacle> obstacleList;
    private final List<Boat> boats;
    private double randomVariable; // a double used to control how many times can the AI make the right move.
    public final Rectangle farRightBox; // a rectangle on the far right in front of the boat.
    public final Rectangle midRightBox; // a rectangle on the middle right in front of the boat.
    public final Rectangle midLeftBox; // a rectangle on the middle left in front of the boat.
    public final Rectangle farLeftBox; // a rectangle on the far left in front of the boat.
    public final Rectangle rightSideBox; // a rectangle on the right of the boat.
    public final Rectangle leftSideBox; // a rectangle on the left of the boat.

    public AI(Boat boat, int leg, List<Obstacle> obstacleList, List<Boat> boats, Boat player) {
        this.boat = boat;
        this.obstacleList = obstacleList;
        if (leg == 1) {
            randomVariable = 0.3;
        }else if (leg == 2) {
            randomVariable = 0.2;
        }else if (leg == 3){
            randomVariable = 0.1;
        }else {
            randomVariable = 0;
        }
        farRightBox = new Rectangle((float) boat.getPosX() + 70,(float) boat.getPosY() +90,20,50);
        midRightBox = new Rectangle((float) boat.getPosX() + 50,(float) boat.getPosY() +90,20,50);
        midLeftBox = new Rectangle((float) boat.getPosX() + 30,(float) boat.getPosY() +90,20,50);
        farLeftBox = new Rectangle((float) boat.getPosX() +10,(float) boat.getPosY() +90,20,50);
        rightSideBox = new Rectangle((float) boat.getPosX() + 90,(float) boat.getPosY() +10,30,130);
        leftSideBox = new Rectangle((float) boat.getPosX() - 20, (float) boat.getPosY() +10, 30,130);
        this.boats = new ArrayList<>(boats);
        this.boats.add(player);
        this.boats.remove(boat);
    }

    /**
     * Updates the boat location.
     * @see #isNearBoats(int) ,#isNearObstacles(int) ,{@link #moveLeft(int)} ,{@link #moveRight(int)}
     */
    public void update() {
        boat.setPosY(boat.getPosY() + boat.speed);
        int weight = 0;
        weight = isNearObstacles(weight);
        weight = isNearBoats(weight);

        double chance = Math.random();
        if (weight > 0 && chance > randomVariable) {
            moveLeft(weight);
        }else if (weight < 0 && chance > randomVariable) {
            moveRight(weight);
        }

        //TEAM19-START : make AI boats more likely to boost the further behind other boats they are
        //double chanceBoost = calculateDistance();
        //if (chanceBoost > 0.5) {
        //    boat.setPosY(boat.getPosY() + boat.acceleration);
        //}
        
    }

    // TODO
    // remember to define Boat playerBoat in class and constrcutor
    //private double calculateDistance() {
    //    double distance = playerBoat.getPosY() - boat.getPosY();
    //    return 1 - (1/Math.pow(20, distance));
    //}
    //TEAM19-END

    /**
     * Moves the AI boat to the right depending on the weight and the boat's maneuverability.
     * @param weight an integer controlling the direction of the movement of the AI.
     * @see Boat#setPosX(int),Boat#maneuverability.
     */
    private void moveRight(int weight) {
        while (weight < 0){
            if ((boat.getPosX() + boat.speed < boat.getRightBound())) {
                boat.setPosX(boat.getPosX() + boat.maneuverability/4);
            }
            weight ++;
        }
    }

    /**
     * Moves the AI boat to the left depending on the weight and the boat's maneuverability.
     * @param weight an integer controlling the direction of the movement of the AI
     * @see Boat#setPosX(int),Boat#maneuverability
     */
    private void moveLeft(int weight) {
        while (weight > 0){
            if ((boat.getPosX() - boat.speed > boat.getLeftBound())){
                boat.setPosX(boat.getPosX() - boat.maneuverability/4);
            }
            weight --;
        }
    }

    /**
     * Checks if the AI boat is near obstacles using collision bounds and adjusts the input weight.
     * @param weight an integer controlling the direction of the movement of the AI
     * @return the updated integer weight
     * @see Obstacle#getCollisionBounds() 
     */
    private int isNearObstacles(int weight) {
        for (Obstacle obstacle : obstacleList) {
            if (farRightBox.overlaps(obstacle.getCollisionBounds())) {
                weight += 1;
            }
            if (midRightBox.overlaps(obstacle.getCollisionBounds())) {
                weight += 2;
            }
            if (midLeftBox.overlaps(obstacle.getCollisionBounds())) {
                weight -= 2;
            }
            if (farLeftBox.overlaps(obstacle.getCollisionBounds())) {
                weight -= 1;
            }
            if (rightSideBox.overlaps(obstacle.getCollisionBounds())) {
                weight += 1;
            }
            if (leftSideBox.overlaps(obstacle.getCollisionBounds())) {
                weight -= 1;
            }
        }
        return weight;
    }

    /**
     * Checks if the AI boat is near other boats using collision bounds and adjusts the input weight.
     * @param weight an integer controlling the direction of the movement of the AI.
     * @return the updated integer weight.
     * @see Boat#collisionBounds.
     */
    private int isNearBoats(int weight) {
        for (Boat enemyBoat : boats) {
            if (farRightBox.overlaps(enemyBoat.collisionBounds)) {
                weight += 1;
            }
            if (midRightBox.overlaps(enemyBoat.collisionBounds)) {
                weight += 2;
            }
            if (midLeftBox.overlaps(enemyBoat.collisionBounds)) {
                weight -= 2;
            }
            if (farLeftBox.overlaps(enemyBoat.collisionBounds)) {
                weight -= 1;
            }
            if (rightSideBox.overlaps(enemyBoat.collisionBounds)) {
                weight += 1;
            }
            if (leftSideBox.overlaps(enemyBoat.collisionBounds)) {
                weight -= 1;
            }
        }
        return weight;
    }
}
