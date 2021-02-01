package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maingame.game.MainGame;

/**
 * Shows a Game Over Screen for users who were too slow and lost the three legs.
 * Users can then retry beating the game by clicking any key or any part of the screen.
 */

public class GameOverSpeed extends State {

	//TEAM19-START : remove .png of text info and replace with font.draw.
	private final BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"),false);
	private final Texture background;
	private final Texture gameOverBtn;
	private final long countDown; // a counter used to allow the user to read what is onscreen.

	public GameOverSpeed(GameStateManager gsm) {
		super(gsm);
		background = new Texture("background.png");
		gameOverBtn = new Texture("gameOver.png");
		countDown = System.currentTimeMillis();
	}

	/**
	 * {@inheritDoc}
	 * Moves to the MenuState once the user clicks
	 * @see MenuState
	 */
	@Override
	public void handleInput() {
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
		if ((System.currentTimeMillis() - countDown)/1000f > 0.1) {
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
		font.draw(sb, "Your boat was not quick enough\nand you have lost the race!\nClick anywhere to continue.",  MainGame.WIDTH/20, (float) MainGame.HEIGHT / 3);
		sb.end();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		background.dispose();
		gameOverBtn.dispose();
	}
}

