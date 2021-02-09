
package com.maingame.game;

import com.maingame.UnitTestInit;
import com.maingame.game.sprites.Boat;
import com.maingame.game.sprites.Obstacle;
import com.maingame.game.states.PlayState;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestDifficulty extends UnitTestInit {

    protected Obstacle obstacle;

    private static int DIFFICULTY = 0;
    protected List<Boat> boats = new ArrayList<>();
    protected Boat player;
    public TestDifficulty() {

    }

    /** Test that the easy difficulty is set correctly (i.e. num of obstacles) */
    @Test
    public void testEasy(){
        DIFFICULTY = 0;
        assertEquals(30,PlayState.setObstacleCount(1,DIFFICULTY));
        assertEquals(40,PlayState.setObstacleCount(2,DIFFICULTY));
        assertEquals(50,PlayState.setObstacleCount(3,DIFFICULTY));

    }

    /** Test that the medium difficulty is set correctly (i.e. num of obstacles) */
    @Test
    public void testMedium(){
        DIFFICULTY = 1;
        assertEquals(40,PlayState.setObstacleCount(1,DIFFICULTY));
        assertEquals(50,PlayState.setObstacleCount(2,DIFFICULTY));
        assertEquals(60,PlayState.setObstacleCount(3,DIFFICULTY));

    }

    /** Test that the hard difficulty is set correctly (i.e. num of obstacles) */
    @Test
    public void testHard(){
        DIFFICULTY = 2;
        assertEquals(50,PlayState.setObstacleCount(1,DIFFICULTY));
        assertEquals(60,PlayState.setObstacleCount(2,DIFFICULTY));
        assertEquals(70,PlayState.setObstacleCount(3,DIFFICULTY));

    }
}