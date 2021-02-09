package com.maingame.game;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/** Tests that forward and backwards game movement works
 * correctly, as well as the fatigue game mechanic.
 */
public class TestForwardBack extends TestMovement {

    /** Test that the player starts off at y=0. */
    @Test
    public void testStartPos() {
        createPlayer();
        assertEquals(0, player.getPosY());
    }

    /** Test that the boat's default speed (non-accelerating) is correct */
    @Test
    public void testDefaultSpeed() {
        createPlayer();
        int START_Y = player.getPosY();
        playstate.handleInputLogic(); // This is with no input
        assertEquals(START_Y + player.speed, player.getPosY());
    }

    /** Test that the player starts off with the correct maximum fatigue. */
    @Test
    public void testStartFatigue() {
        createPlayer();
        assertEquals(FATIGUE_INITIAL_VALUE, player.getFatigue());
    }

    /** Test that the player regenerates fatigue when not boosting. */
    @Test
    public void testRegenerateFatigue() {
        createPlayer();
        playstate.up_pressed = true;
        playstate.handleInputLogic();
        assertEquals(FATIGUE_INITIAL_VALUE - FATIGUE_FRAME_DECREASE_VALUE, player.getFatigue());
        playstate.up_pressed = false;
        playstate.handleInputLogic();
        assertEquals(FATIGUE_INITIAL_VALUE - FATIGUE_FRAME_DECREASE_VALUE + FATIGUE_INCREASE_VALUE, player.getFatigue());

    }

    /** Test that the correct amount of fatigue is subtracted when the player starts boosting. */
    @Test
    public void testMoveUpFatigue() {
        createPlayer();
        playstate.up_pressed = true;
        playstate.handleInputLogic();
        assertEquals(FATIGUE_INITIAL_VALUE - FATIGUE_FRAME_DECREASE_VALUE, player.getFatigue());
    }

    /** Test that no fatigue is subtracted when the player slows down. */
    @Test
    public void testMoveDownFatigue() {
        createPlayer();
        playstate.down_pressed = true;
        playstate.handleInputLogic();
        assertEquals(FATIGUE_INITIAL_VALUE, player.getFatigue());
    }
    
    /** Test that the player accelerates at the correct speed when boosting. */
    @Test
    public void testMoveUp() {
        createPlayer();
        int START_Y = player.getPosY();
        playstate.up_pressed = true;
        playstate.handleInputLogic();
        assertEquals(START_Y + player.speed + player.acceleration, player.getPosY());
    }

    /** Test that the player moves downwards at the correct speed when slowing down. */
    @Test
    public void testMoveDown() {
        createPlayer();
        int START_Y = player.getPosY();
        playstate.down_pressed = true;
        playstate.handleInputLogic();
        assertEquals(START_Y + player.speed - (player.maneuverability/2), player.getPosY());
    }
}