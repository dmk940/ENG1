package com.maingame.game;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import com.maingame.game.states.LeaderboardState;

/**
 * Tests the flow of execution of the game, i.e. screens
 * appear and disappear correctly upon specific events
 * (gameover, next leg...).
 */
public class TestScreensGameFlow extends TestMovement {

    // TODO work out execution / game flow and transfer between
    // depending on different events. Find way to manipulate this
    // to ensure testing happens.
    public void testGameOverSpeed() {
        assertTrue(true);
    }

    public void testMainMenuBoatSelect() {
        assertTrue(true);
    }
    //....

    @Test
    public void testFinishLine() {
        createPlayer();
        boats.remove(0); // remove non-player boat so haveBoatsFinished will be true
        playstate.isLegOver();
        player.setPosY(playstate.finishLinePosition+100000); // ensure definitely over finishline
        playstate.finishLeg();
        assertTrue(gsm.peek() instanceof LeaderboardState);
        
    }



}