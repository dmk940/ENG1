package com.maingame.game;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestResolution {

    @Test
    public void testDefaultRes() {
        assertEquals(MainGame.HEIGHT, 768);
        assertEquals(MainGame.WIDTH, 1366);
    }
}