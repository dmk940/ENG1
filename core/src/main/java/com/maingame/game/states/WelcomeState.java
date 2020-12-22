package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maingame.game.MainGame;

/**
 * Shows a Welcome Screen for users.
 * Users can then move to the menu by clicking any key or any part of the screen.
 */
public class WelcomeState extends State {
	private final Texture background;
	private final Texture playBtn;
	private final Texture title;
	private final Texture advance; // a message telling users what input is expected
	

	public WelcomeState(GameStateManager gsm) {
		super(gsm);
		background = new Texture("background.png");
		playBtn = new Texture("play.png");
		title = new Texture("title.png");
		advance = new Texture("advance.png");
	}

	/**
	 * {@inheritDoc}
	 * Moves to the MenuState once any input is provided
	 * @see MenuState
	 */
	@Override
	public void handleInput() {
	
		if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
			gsm.set(new MenuState(gsm));
			dispose();
		}
		if(Gdx.input.justTouched()) {
			gsm.set(new MenuState(gsm));
			dispose();
		}
	}

	/**
	 * {@inheritDoc}
	 * @param dt the time between each start of a render()
	 */
	@Override
	public void update(float dt) {
		handleInput();
	}

	/**
	 * {@inheritDoc}
	 * @param sb a batch for drawing objects
	 */
	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(background, 0, 0, MainGame.WIDTH , MainGame.HEIGHT);
		sb.draw(playBtn, ((float)MainGame.WIDTH / 3) - ((float) playBtn.getWidth() / 10), (float) MainGame.HEIGHT / 50);
		sb.draw(title, ((float)MainGame.WIDTH / 5) - ((float)title.getWidth() / 10), (float)MainGame.HEIGHT / 50);
		sb.draw(advance, ((float)MainGame.WIDTH / 20) - ((float) advance.getWidth() / 250), (float) MainGame.HEIGHT / 50);
		sb.end();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		background.dispose();
		playBtn.dispose();
		title.dispose();
		advance.dispose();
	}
}

