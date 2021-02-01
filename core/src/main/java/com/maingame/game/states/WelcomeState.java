package com.maingame.game.states;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
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
	//TEAM19-START, changed to demo button, add playBtnBounds so clicking play plays game
	private final Texture demo; 
	private final Rectangle demoBtnBounds;
	private final Rectangle playBtnBounds;
	private final Texture loadBtn;
	private final Rectangle loadBtnBounds;
	//TEAM19-END
	

	public WelcomeState(GameStateManager gsm) {
		super(gsm);
		background = new Texture("background.png");
		playBtn = new Texture("play.png");
		title = new Texture("title.png");
		//TEAM19-START
		playBtnBounds = new Rectangle(((float) MainGame.WIDTH / 3) - ((float) playBtn.getWidth() / 10), (float) MainGame.HEIGHT / 50, playBtn.getWidth(), playBtn.getHeight());
		demo = new Texture("demo.png");
		demoBtnBounds = new Rectangle((float) MainGame.HEIGHT/100, (float) MainGame.HEIGHT / 100, demo.getWidth(), demo.getHeight());
		loadBtn = new Texture("load.png");
		loadBtnBounds = new Rectangle(((float) MainGame.WIDTH / 3) - ((float) loadBtn.getWidth() / 10), (float) MainGame.HEIGHT / 50, loadBtn.getWidth(), loadBtn.getHeight());
		//TEAM19-END
	}	

	/**
	 * {@inheritDoc}
	 * Moves to the MenuState once any input is provided
	 * @see MenuState
	 */
	@Override
	public void handleInput() {
		//TEAM19-START
		// change from any button moves forward to must click play button, so we can add demo button that moves
		// to demo mode
		if (Gdx.input.justTouched()) {
			if (playBtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
				gsm.set(new MenuState(gsm));
				dispose();
			} else if (demoBtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
				gsm.set(new DemoState(gsm));
			} if (loadBtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
				gsm.set(new LoadState(gsm));
			}
		}
		//TEAM19-END
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
		sb.draw(demo, (float) MainGame.HEIGHT/100, (float) MainGame.HEIGHT / 100);
		sb.draw(loadBtn, ((float)MainGame.WIDTH / 3) - ((float) loadBtn.getWidth() / 10), (float) MainGame.HEIGHT / 50);
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
		demo.dispose();
		loadBtn.dispose();
	}
}

