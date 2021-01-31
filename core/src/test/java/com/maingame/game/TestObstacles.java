package com.maingame.game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import com.badlogic.gdx.math.Rectangle;
import com.maingame.UnitTestInit;
import com.maingame.game.sprites.Boat;
import com.maingame.game.sprites.Obstacle;

/**
 * Test that Obstacles subtract the correct amount of health
 * when hit, and that each Obstacle's collision bounds are correct.
 */
public class TestObstacles extends UnitTestInit {

    protected static final HashMap<String, Integer> testObstacleHealthMap = new HashMap<>();
    protected static final HashMap<String, Integer[]> testObstacleBoundsMap = new HashMap<>();

    protected Boat player;
    protected Obstacle obstacle;

    private static final String ROCK_1 = "rock1";
    private static final String ROCK_2 = "rock2";
    private static final String GOOSE = "goose";
    private static final String DUCK_1 = "duck1";
    private static final String DUCK_2 = "duck2";

    // safe coordinates to test obstacle in
    protected final int START_X = 9000;
    protected final int START_Y = 9000;

    public TestObstacles() {
        // The health amount each obstacle should subtract
        testObstacleHealthMap.put(ROCK_1, 10);
        testObstacleHealthMap.put(ROCK_2, 10);
        testObstacleHealthMap.put(GOOSE, 15);
        testObstacleHealthMap.put(DUCK_1, 5);
        testObstacleHealthMap.put(DUCK_2, 5);

        // These values are used to recreate the rectangle collision box
        // In order of X=obstacle.posX + _, Y=obstacle.posY + _, rec_width, rec_height
        testObstacleBoundsMap.put(ROCK_1, new Integer[] {20, 20, 30, 30});
        testObstacleBoundsMap.put(ROCK_2, new Integer[] {5, 35, 30, 25});
        testObstacleBoundsMap.put(GOOSE, new Integer[] {0, 10, 70, 60});
        testObstacleBoundsMap.put(DUCK_1, new Integer[] {20, 27, 33, 33});
        // This is equiv to "default" bounding box
        testObstacleBoundsMap.put(DUCK_2, new Integer[] {10, 10, 40, 50});
    }

    public void testObstacleHit(String obstacleName) {
        player = new Boat("red");
        player.setPosX(START_X);
        player.setPosY(START_Y);
        player.updateCollisionBounds();

        obstacle = new Obstacle(obstacleName);
        obstacle.setPosX(START_X);
        obstacle.setPosY(START_Y);
        obstacle.updateCollisionBounds();

        int start_health = player.getHealth();
        obstacle.checkHit(player);
        assertEquals(start_health - testObstacleHealthMap.get(obstacleName), player.getHealth());
    }

    public void testObstacleBounds(String obstacleName) {
        obstacle = new Obstacle(obstacleName);
        // x,y=(0,0) has different obstacle collisions but guaranteed not to spawn with an obstacle
        // so this doesn't ever need to be tested. Thus we set to START_X, START_Y, and update bounds
        obstacle.setPosX(START_X);
        obstacle.setPosY(START_Y);
        obstacle.updateCollisionBounds();

        Integer[] obs_arr = testObstacleBoundsMap.get(obstacleName);
        Rectangle expect_rect = new Rectangle((float) obstacle.getPosX() + obs_arr[0], 
                                              (float) obstacle.getPosY() + obs_arr[1],
                                              obs_arr[2], obs_arr[3]);

        assertTrue(expect_rect.equals(obstacle.collisionBounds));

    }

    @Test
    public void testNoHealthGameOver() {
        TestMovement tm = new TestMovement();
        tm.createPlayer();
        tm.player.setHealth(0);
        tm.playstate.checkBoatHealth();
        assertFalse(tm.player.isHasNotLost());
    }

    @Test
    public void testRock1Hit() {
        testObstacleHit(ROCK_1);
    }

    @Test
    public void testRock1Bounds() {
        testObstacleBounds(ROCK_1);
    }

    @Test
    public void testRock2Hit() {
        testObstacleHit(ROCK_2);
    }

    @Test
    public void testRock2Bounds() {
        testObstacleBounds(ROCK_2);
    }

    @Test
    public void testGooseHit() {
        testObstacleHit(GOOSE);
    }

    @Test
    public void testGooseBounds() {
        testObstacleBounds(GOOSE);
    }

    @Test
    public void testDuck1Hit() {
        testObstacleHit(DUCK_1);
    }

    @Test
    public void testDuck1Bounds() {
        testObstacleBounds(DUCK_1);
    }

    @Test
    public void testDuck2Hit() {
        testObstacleHit(DUCK_2);
    }

    @Test
    public void testDuck2Bounds() {
        testObstacleBounds(DUCK_2);
    }
}