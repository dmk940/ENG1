package com.maingame.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maingame.game.states.GameStateManager;
import com.maingame.game.states.WelcomeState;

public class MainGame extends ApplicationAdapter implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1366;
	public static final int HEIGHT = 768;
	public static final String TITLE = "Dragonite Dragon Boat Race";
	private GameStateManager gsm;
	private SpriteBatch batch;
	private Music music;

	/**
	 * {@inheritDoc}
	 * Shows the WelcomeState when the game first starts.
	 * @see WelcomeState
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		music = Gdx.audio.newMusic(Gdx.files.internal("synth_pop_game.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		//music.play();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new WelcomeState(gsm));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose () {
		batch.dispose();
	}
}
