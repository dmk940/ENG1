package com.maingame.game;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/** Tests that forward and backwards game movement works
 * correctly, as well as the fatigue game mechanic.
 */
public class TestForwardBack extends TestMovement {

    @Test
    public void testStartPos() {
        createPlayer();
        assertEquals(0, player.getPosY());
    }

    @Test
    public void testDefaultSpeed() {
        createPlayer();
        int START_Y = player.getPosY();
        playstate.handleInputLogic(); // This is with no input
        assertEquals(START_Y + player.speed, player.getPosY());
    }

    @Test
    public void testStartFatigue() {
        createPlayer();
        assertEquals(FATIGUE_INITIAL_VALUE, player.getFatigue());
    }

    @Test
    public void testMoveUpFatigue() {
        createPlayer();
        playstate.up_pressed = true;
        playstate.handleInputLogic();
        assertEquals(FATIGUE_INITIAL_VALUE - FATIGUE_FRAME_DECREASE_VALUE, player.getFatigue());
    }

    @Test
    public void testMoveDownFatigue() {
        createPlayer();
        playstate.down_pressed = true;
        playstate.handleInputLogic();
        assertEquals(FATIGUE_INITIAL_VALUE, player.getFatigue());
    }
    
    @Test
    public void testMoveUp() {
        createPlayer();
        int START_Y = player.getPosY();
        playstate.up_pressed = true;
        playstate.handleInputLogic();
        assertEquals(START_Y + player.speed + player.acceleration, player.getPosY());
    }

    @Test
    public void testMoveDown() {
        createPlayer();
        int START_Y = player.getPosY();
        playstate.down_pressed = true;
        playstate.handleInputLogic();
        assertEquals(START_Y + player.speed - (player.maneuverability/2), player.getPosY());
    }
}