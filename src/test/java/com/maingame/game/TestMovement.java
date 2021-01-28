package com.maingame.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.maingame.UnitTestInit;
import com.maingame.game.sprites.Boat;
import com.maingame.game.states.GameStateManager;
import com.maingame.game.states.PlayState;

/** Sets up the environment required to test boat movement,
 * allowing tests to be separated into more classes - avoiding
 * a single large test file.
 */
public class TestMovement extends UnitTestInit {

    protected static int FATIGUE_INITIAL_VALUE = 600;
    protected int FATIGUE_FRAME_DECREASE_VALUE = 2;

    protected int START_X;
    protected Texture river;

    protected Boat player;
    protected List<Boat> boats = new ArrayList<>();
    protected GameStateManager gsm;
    
    protected PlayState playstate;

    // Initialise data; utilised by child classes
	public void createPlayer() {
        player = new Boat("pink");
        
        boats.add(new Boat("red"));
        boats.add(player);

        gsm = new GameStateManager();
        playstate = new PlayState(gsm, boats, player, 0);
	}
}