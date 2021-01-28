package com.maingame.game;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import com.badlogic.gdx.math.Rectangle;
import com.maingame.UnitTestInit;
import com.maingame.game.sprites.Boat;

/**
 * Tests that boats have the correct stats (speed, acceleration, robustness, 
 * maneuverability) and bounding box.
 */
public class TestBoatStats extends UnitTestInit {

    private static final String RED = "red";
    private static final String PINK = "pink";
    private static final String BLUE = "blue";
    private static final String YELLOW = "yellow";
    private static final String ORANGE = "orange";
    private static final String GREEN = "green";
    private static final String PURPLE = "purple";

    // safe coordinates to test obstacle in
    private final int START_X = 9000;
    private final int START_Y = 9000;

    private Boat player;
    private static final HashMap<String, Integer[]> testBoatMap = new HashMap<>();

    public TestBoatStats() {
        // Order: speed, acceleration, robustness, maneuverability, 
        // followed by ...posX, posY (not included here as unneeded)
        testBoatMap.put(RED, new Integer[] {7, 7, 7, 7});
        testBoatMap.put(PINK, new Integer[] {8, 6, 8, 6});
        testBoatMap.put(BLUE, new Integer[] {9, 9, 4, 5});
        testBoatMap.put(YELLOW, new Integer[] {8, 8, 5, 7});
        testBoatMap.put(ORANGE, new Integer[] {8, 5, 10, 5});
        testBoatMap.put(GREEN, new Integer[] {6, 6, 9, 7,});
        testBoatMap.put(PURPLE, new Integer[] {6, 6, 6, 10});
    }

    public Integer[] getBoatData(String boatColour) {
        player = new Boat(boatColour);
        Integer[] arr = new Integer[] {player.speed, player.acceleration, 
            player.robustness, player.maneuverability};

        return arr;
    }

    public void testBoatBounds() {
        player.setPosX(START_X);
        player.setPosY(START_Y);
        player.updateCollisionBounds();
        
        Rectangle exp_rect = new Rectangle((float) player.getPosX() + 10,
                                           (float) player.getPosY() + 10, 
                                           80, 80);
        assertTrue(exp_rect.equals(player.collisionBounds));
    }

    @Test
    public void testRedBoatStatsCorrect() {
        assertArrayEquals(testBoatMap.get(RED), getBoatData(RED));
        testBoatBounds();
        
    }

    @Test
    public void testPinkBoatStatsCorrect() {
        assertArrayEquals(testBoatMap.get(PINK), getBoatData(PINK));
        testBoatBounds();
        
    }

    @Test
    public void testBlueBoatStatsCorrect() {
        assertArrayEquals(testBoatMap.get(BLUE), getBoatData(BLUE));
        testBoatBounds();
        
    }

    @Test
    public void testYellowBoatStatsCorrect() {
        assertArrayEquals(testBoatMap.get(YELLOW), getBoatData(YELLOW));
        testBoatBounds();
        
    }

    @Test
    public void testOrangeBoatStatsCorrect() {
        assertArrayEquals(testBoatMap.get(ORANGE), getBoatData(ORANGE));
        testBoatBounds();
        
    }

    @Test
    public void testGreenBoatStatsCorrect() {
        assertArrayEquals(testBoatMap.get(GREEN), getBoatData(GREEN));
        testBoatBounds();
        
    }

    @Test
    public void testPurpleBoatStatsCorrect() {
        assertArrayEquals(testBoatMap.get(PURPLE), getBoatData(PURPLE));
        testBoatBounds();
        
    }
}