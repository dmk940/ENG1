package com.maingame.game;

import com.badlogic.gdx.math.Rectangle;
import com.maingame.UnitTestInit;
import com.maingame.game.sprites.Boat;
import com.maingame.game.sprites.Obstacle;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class TestPowerUps extends UnitTestInit {

    protected static final HashMap<String, Integer> testObstacleHealthMap = new HashMap<>();
    protected static final HashMap<String, Integer[]> testObstacleBoundsMap = new HashMap<>();

    protected Boat player;
    protected Obstacle obstacle;

    private static final String HEALTHUP = "healthUp";
    private static final String FATIGUEUP = "fatigueUp";
    private static final String HEALTHUP2 = "healthUp2";
    private static final String FATIGUEUP2 = "fatigueUp2";
    private static final String SPEEDUP = "speedUp";



    // safe coordinates to test obstacle in
    protected final int START_X = 9000;
    protected final int START_Y = 9000;

    public TestPowerUps() {
        // The health amount each obstacle should subtract
        testObstacleHealthMap.put(HEALTHUP,-10);
        testObstacleHealthMap.put(FATIGUEUP,0);
        testObstacleHealthMap.put(FATIGUEUP2,0);
        testObstacleHealthMap.put(HEALTHUP2,-20);
        testObstacleHealthMap.put(SPEEDUP,0);

        // These values are used to recreate the rectangle collision box
        // In order of X=obstacle.posX + _, Y=obstacle.posY + _, rec_width, rec_height
        testObstacleBoundsMap.put(HEALTHUP, new Integer[] {10, 10, 40, 50});
        testObstacleBoundsMap.put(FATIGUEUP, new Integer[] {10, 10, 40, 50});
        testObstacleBoundsMap.put(HEALTHUP2, new Integer[] {10, 10, 40, 50});
        testObstacleBoundsMap.put(FATIGUEUP2, new Integer[] {10, 10, 40, 50});
        testObstacleBoundsMap.put(SPEEDUP, new Integer[] {10, 10, 40, 50});

    }

    public void testObstacleHit(String obstacleName) {
        player = new Boat("red");
        player.setPosX(START_X);
        player.setPosY(START_Y);
        player.updateCollisionBounds();;

        obstacle = new Obstacle(obstacleName);
        obstacle.setPosX(START_X);
        obstacle.setPosY(START_Y);
        obstacle.updateCollisionBounds();

        int start_health = player.getHealth();
        obstacle.checkHit(player);
        assertEquals(start_health - testObstacleHealthMap.get(obstacleName), player.getHealth());
    }

    public void testHealthUpObstacleHit(String obstacleName){
        player = new Boat("red");
        player.setPosX(START_X);
        player.setPosY(START_Y);
        player.updateCollisionBounds();;

        obstacle = new Obstacle(obstacleName);
        obstacle.setPosX(START_X);
        obstacle.setPosY(START_Y);
        obstacle.updateCollisionBounds();

        player.setHealth(10);
        int start_health = player.getHealth();
        obstacle.checkHit(player);
        assertEquals(start_health - testObstacleHealthMap.get(obstacleName), player.getHealth());
    }

    public void testObstacleBounds(String obstacleName) {
        obstacle = new Obstacle(obstacleName);
        // x,y=(0,0) has different obstacle collisions but guaranteed not to spawn with an obstacle
        //so this doesn't ever need to be tested. Thus we set to START_X, START_Y, and update bounds
        obstacle.setPosX(START_X);
        obstacle.setPosY(START_Y);
        obstacle.updateCollisionBounds();

        Integer[] obs_arr = testObstacleBoundsMap.get(obstacleName);
        Rectangle expect_rect = new Rectangle((float) obstacle.getPosX() + obs_arr[0],
                (float) obstacle.getPosY() + obs_arr[1],
                obs_arr[2], obs_arr[3]);

        assertTrue("Obstacle collision bounds were wrong", expect_rect.equals(obstacle.collisionBounds));

    }

    public void testFatigueIncrease(String obstacleName, int increase) {
        player = new Boat("red");
        player.setPosX(START_X);
        player.setPosY(START_Y);
        player.updateCollisionBounds();;
        player.setFatigue(0);

        obstacle = new Obstacle(obstacleName);
        obstacle.setPosX(START_X);
        obstacle.setPosY(START_Y);
        obstacle.updateCollisionBounds();

        int start_fatigue = player.getFatigue();
        obstacle.checkHit(player);
        assertEquals(start_fatigue + increase, player.getFatigue());
    }

    /** Test fatigue is increased by the correct amount when hitting the FATIGUEUP obstacle */
    @Test
    public void TestFatigueUpIncrease() {testFatigueIncrease(FATIGUEUP,15);}

    /** Test fatigue is increased by the correct amount when hitting the FATIGUEUP2 obstacle */
    @Test
    public void TestFatigueUp2Increase() {testFatigueIncrease(FATIGUEUP2,30);}

    /** Test speed is increased when hitting the SPEEDUP obstacle */
    @Test
    public void testSpeedIncrease(){
        player = new Boat("red");
        player.setPosX(START_X);
        player.setPosY(START_Y);
        player.updateCollisionBounds();;

        obstacle = new Obstacle(SPEEDUP);
        obstacle.setPosX(START_X);
        obstacle.setPosY(START_Y);
        obstacle.updateCollisionBounds();

        int start_speed = player.getSpeed();
        obstacle.checkHit(player);
        assertEquals(start_speed + 1, player.getSpeed());
    }

    /** Test health is increased when hitting the HEALTHUP obstacle */
    @Test
    public void testHealthUpHit() { testHealthUpObstacleHit(HEALTHUP); }

    /** Test the HEALTHUP obstacle's bounds */
    @Test
    public void testHealthUpBounds() { testObstacleBounds(HEALTHUP); }

    /** Test health is increased when hitting the HEALTHUP2 obstacle */
    @Test
    public void testHealthUp2Hit() {
        testHealthUpObstacleHit(HEALTHUP2);
    }

    /** Test that the HEALTHUP2 obstacle's bounds are correct */
    @Test
    public void testHealthUp2Bounds() {
        testObstacleBounds(HEALTHUP2);
    }

    /** Test that the FATIGUEUP obstacle's bounds are correct */
    @Test
    public void testFatigueUpBounds() {
        testObstacleBounds(FATIGUEUP);
    }

    /** Test fatigue is increased when hitting the FATIGUEUP obstacle */
    @Test
    public void testFatigueUpHit() {
        testObstacleHit(FATIGUEUP);
    }

    /** Test that the FATIGUEUP2 obstacle's bounds are correct */
    @Test
    public void testFatigueUp2Bounds() {
        testObstacleBounds(FATIGUEUP2);
    }

    /** Test that the FATIGEUP2 obstacle does no damage. */
    @Test
    public void testFatigueUp2Hit() {
        testObstacleHit(FATIGUEUP2);
    }

    /** Test that the SPEEDUP obstacle's bounds are correct */
    @Test
    public void testSpeedUpBounds() {
        testObstacleBounds(SPEEDUP);
    }

    /** Test that the SPEEDUP obstacle does no damage. */
    @Test
    public void testSpeedUpHit() {
        testObstacleHit(SPEEDUP);
    }

}
