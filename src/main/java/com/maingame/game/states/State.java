package com.maingame.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {
	
	protected OrthographicCamera cam;
	protected GameStateManager gsm;
	
	protected State(GameStateManager gsm) {
		this.gsm = gsm;
		cam = new OrthographicCamera();
		
	}

	/**
	 * Handles user input within a state.
	 */
	public abstract void handleInput();

	/**
	 * Updates other functions/values in a state.
	 * @param dt the time between each start of a render()
	 */
	public abstract void update(float dt);

	/**
	 * Renders the different elements in a state
	 * @param sb a batch for drawing objects
	 */
	public abstract void render(SpriteBatch sb);

	/**
	 * Disposes of assets when they're no longer in use.
	 */
	public abstract void dispose();
}
