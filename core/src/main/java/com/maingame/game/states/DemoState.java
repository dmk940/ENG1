package com.maingame.game.states;

import com.maingame.game.sprites.Boat;

/**
 * Automates the PlayState, in the demo mode. Driver class.
 */
public class DemoState extends MenuState {

    public DemoState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void handleInput() {
        Boat playerBoat = boats.get(x);
		this.boats.remove(x);
        this.boats = playStateBoats();
        //this.boats.add(playerBoat);
        gsm.set(new DemoPlayState(gsm,boats, playerBoat,1, 0));
        

    }
}