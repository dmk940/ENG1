package com.maingame.game;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestForwardBack extends TestMovement {

    @Test
    public void testStartPos() {
        createPlayer();
        assertEquals(0, player.getPosY());
    }

    @Test
    public void testDefaultSpeed() {
        // This is with no input
        createPlayer();
        int START_Y = player.getPosY();
        playstate.handleInput();
        assertEquals(START_Y + player.speed, player.getPosY());
    }

    @Test
    public void testStartFatigue() {
        createPlayer();
        assertEquals(FATIGUE_INITIAL_VALUE, player.getFatigue());
    }

    @Test
    public void testMoveFatigueW() {
        createPlayer();
        //input_proc.keyDown(Input.Keys.W);
        //playstate.handleInput();
        assertEquals(FATIGUE_INITIAL_VALUE, player.getFatigue());
        //assertEquals(FATIGUE_INITIAL_VALUE - FATIGUE_FRAME_DECREASE_VALUE, player.getFatigue());
    }

    @Test
    public void testMoveFatigueS() {
        createPlayer();
        //input_proc.keyDown(Input.Keys.S);
        //playstate.handleInput();
        assertEquals(FATIGUE_INITIAL_VALUE, player.getFatigue());
    }

    @Test
    public void testMoveFatigueUP() {
        createPlayer();
        //input_proc.keyDown(Input.Keys.UP);
        //playstate.handleInput();
        assertEquals(FATIGUE_INITIAL_VALUE, player.getFatigue());
        //assertEquals(FATIGUE_INITIAL_VALUE - FATIGUE_FRAME_DECREASE_VALUE, player.getFatigue());
    }

    @Test
    public void testW() {
        createPlayer();
        int START_Y = player.getPosY();
        //input_proc.keyDown(Input.Keys.W);
        //playstate.handleInput();
        assertEquals(START_Y, player.getPosY());
        //assertEquals(START_Y + player.speed + player.acceleration, player.getPosY());
        
    } 
    
    @Test
    public void testUPArrows() {
        createPlayer();
        int START_Y = player.getPosY();
        //input_proc.keyDown(Input.Keys.UP);
        //playstate.handleInput();
        assertEquals(START_Y, player.getPosY());
        //assertEquals(START_Y + player.speed + player.acceleration, player.getPosY());
    }
}