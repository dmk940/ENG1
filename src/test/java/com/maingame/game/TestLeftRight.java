package com.maingame.game;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.badlogic.gdx.graphics.Texture;

/** Tests that left and right game movement works
 * correctly, including the starting X position.
 */
public class TestLeftRight extends TestMovement {

    @Test
    public void testStartPos() {
        createPlayer();
        river = new Texture("river.png");
        START_X = (river.getWidth() / 2) + (river.getWidth() * 2) - 50;
        
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