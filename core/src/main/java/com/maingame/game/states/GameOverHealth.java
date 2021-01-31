package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maingame.game.MainGame;

/**
 * Shows a Game Over Screen for users who have hit too many obstacles and reached 0 health.
 * Users can then retry beating the game by clicking any key or any part of the screen.
 */

public class GameOverHealth extends State {

	private final Texture background;
	private final Texture gameOverBtn;
	private final Texture info; // the text presented to the user onscreen.
	private final long countDown; // a counter used to allow the user to read what is onscreen.
	

	public GameOverHealth(GameStateManager gsm) {
		super(gsm);
		background = new Texture("background.png");
		gameOverBtn = new Texture("gameOver.png");
		info = new Texture("gameOverHealth.png");
		countDown = System.currentTimeMillis();
	}

	/**
	 * {@inheritDoc}
	 * Moves to the WelcomeState once any input is provided
	 * @see MenuState
	 */
	@Override
	public void handleInput() {
	
		// TODO add a button to clickthrough
		if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
			gsm.set(new WelcomeState(gsm));
			dispose();
		}
		if(Gdx.input.justTouched()) {
			gsm.set(new WelcomeState(gsm));
			dispose();
		}
	}

	/**
	 * {@inheritDoc}
	 * @param dt the time between each start of a render()
	 */
	@Override
	public void update(float dt) {
		if ((System.currentTimeMillis() - countDown)/1000f > 0.2) {
			handleInput();
		}
	}

	/**
	 * {@inheritDoc}
	 * @param sb a batch for drawing objects
	 */
	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		cam.setToOrtho(false,MainGame.WIDTH,MainGame.HEIGHT);
		sb.setProjectionMatrix(cam.combined);
		sb.draw(background, 0, 0, MainGame.WIDTH , MainGame.HEIGHT);
		sb.draw(gameOverBtn, ((float) MainGame.WIDTH / 3) - ((float) gameOverBtn.getWidth() / 10), (float) MainGame.HEIGHT / 4);
		sb.draw(info, ((float) MainGame.WIDTH / 3) - ((float) info.getWidth() / 6), (float) MainGame.HEIGHT / 20);
		sb.end();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		background.dispose();
		gameOverBtn.dispose();
		info.dispose();
	}
}

