package com.maingame.game;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests that players are given a time penalty if they stay
 * outside of their lane for too long.
 */
public class TestOutsideLane extends TestMovement {

    private static final int PEN_START = 100; // penalty bar start val
    private static final int PEN_SUB = 2; // penalty bar subtraction each frame outside lane
    private static final int PENALTY_SEC = 2;

    /** Test that the time penalty counter starts counting down when outside of the lane. */
    @Test
    public void testMoveOutsideLane() {
        createPlayer();
        player.setPosX(player.getPosX() - 200); // definitely out of lane
        player.isBoatOutOfLane();
        assertEquals(PEN_START - PEN_SUB, player.getPenaltyBar());
    }

    /** Test that the penalty time is added to the legTime when the penalty counter runs out. */
    @Test
    public void testPenaltyBar() {
        createPlayer();
        assertEquals(0, player.getTimePenalty());
        player.setPenaltyBar(0);
        playstate.updateBoatPenalties();
        assertEquals(0+PENALTY_SEC, player.getTimePenalty());   
    }
}