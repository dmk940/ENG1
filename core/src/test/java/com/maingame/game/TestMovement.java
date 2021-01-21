package com.maingame.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.maingame.UnitTestInit;
import com.maingame.game.sprites.Boat;
import com.maingame.game.states.GameStateManager;
import com.maingame.game.states.PlayState;

public class TestMovement extends UnitTestInit {

    protected static int FATIGUE_INITIAL_VALUE = 600;
    protected int FATIGUE_FRAME_DECREASE_VALUE = 2;

    protected int START_X;
    protected Texture river;

    protected Boat player;
    protected List<Boat> boats = new ArrayList<>();

    protected InputProcessor input_proc;
    protected PlayState playstate;
    protected static Application app;

	public void createPlayer() {
        player = new Boat("pink");
        river = new Texture("river.png");
        START_X = (river.getWidth() / 2) + (river.getWidth() * 2) - 50;

        boats.add(new Boat("red"));
        boats.add(player);

        playstate = new PlayState(new GameStateManager(), boats, player, 0);
	}
}