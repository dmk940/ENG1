package com.maingame.game;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestLeftRight extends TestMovement {

    @Test
    public void testStartPos() {
        createPlayer();
        assertEquals(START_X, player.getPosX());
    }

    @Test
    public void testLeft() {
        createPlayer();
        int START_X = player.getPosX();
        playstate.left_pressed = true;
        playstate.handleInputLogic();
        assertEquals(START_X - player.maneuverability/2, player.getPosX());
    }

    @Test
    public void testRight() {
        createPlayer();
        int START_X = player.getPosX();
        playstate.right_pressed = true;
        playstate.handleInputLogic();
        assertEquals(START_X + player.maneuverability/2, player.getPosX());
    }
}