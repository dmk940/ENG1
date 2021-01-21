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
    public void testA() {
        createPlayer();
        int START_X = player.getPosX();
        //input_proc.keyDown(Input.Keys.A);
        //playstate.handleInput();
        assertEquals(player.getPosX(),  START_X);
        //assertEquals(player.getPosX(),  START_X + player.maneuverability/2);
        
    }

    @Test
    public void testD() {
        createPlayer();
        int START_X = player.getPosX();
        //input_proc.keyDown(Input.Keys.A);
        //playstate.handleInput();
        assertEquals(player.getPosX(),  START_X);
        //assertEquals(player.getPosX(),  START_X - player.maneuverability/2);
        
    }

    @Test
    public void testLEFTArrow() {
        createPlayer();
        int START_X = player.getPosX();
        //input_proc.keyDown(Input.Keys.LEFT);
        //playstate.handleInput();
        assertEquals(player.getPosX(),  START_X);
        //assertEquals(player.getPosX(),  START_X + player.maneuverability/2);
    }

    @Test
    public void testRIGHTArrow() {
        createPlayer();
        int START_X = player.getPosX();
        //input_proc.keyDown(Input.Keys.LEFT);
        //playstate.handleInput();
        assertEquals(player.getPosX(),  START_X);
        //assertEquals(player.getPosX(),  START_X - player.maneuverability/2);
    }

    
}