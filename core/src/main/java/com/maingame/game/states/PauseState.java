package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.maingame.game.MainGame;

//TEAM19-START
/** This is the screen displayed to the user when they have pause the game. */
public class PauseState extends State {

    private final Texture pauseScreen;
    private final Texture saveBtn;
    private final Rectangle saveBtnBounds;
    private final Texture exitBtn;
    private final Rectangle exitBtnBounds;
    MainGame game; 
    protected boolean paused = true;
    public Boolean space_pressed = false;
    protected long lastPressed;
    protected PlayState playstate;
    private final Texture backBtn;
	private final Rectangle backBtnBounds;

    protected long pauseStateTime;

    public PauseState(GameStateManager gsm, long pauseStateTime_, PlayState playstate_) {
        super(gsm);
        pauseStateTime = pauseStateTime_;
        pauseScreen = new Texture("pause.png");
        saveBtn = new Texture("save.png");
        exitBtn = new Texture("exit.png");

        lastPressed = System.currentTimeMillis();

        playstate = playstate_;

        backBtn = new Texture("backButton.png");
		backBtnBounds = new Rectangle(0, (float) MainGame.HEIGHT - backBtn.getHeight(), backBtn.getWidth(), backBtn.getHeight());

        saveBtnBounds = new Rectangle(((float) MainGame.WIDTH / 3) - ((float) saveBtn.getWidth() / 20),
                (float) MainGame.HEIGHT / 3, saveBtn.getWidth(), saveBtn.getHeight());
        exitBtnBounds = new Rectangle((float) (((float) MainGame.WIDTH / 1.8) - ((float) exitBtn.getWidth() / 50)),
                (float) MainGame.HEIGHT / 3, exitBtn.getWidth(), exitBtn.getHeight());
    }
    
    /**Create an area where it recognise the input click for the button save and exit*/
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            if (exitBtnBounds.contains(Gdx.input.getX(), (float) MainGame.HEIGHT - Gdx.input.getY())) {
                gsm.set(new WelcomeState(gsm));
                dispose();
            } else if (saveBtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())) {
                gsm.set(new SaveState(gsm, pauseStateTime));
            } else if (backBtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())) {
                gsm.pop(); // Back to playstate
                PlayState ps = (PlayState)gsm.peek();
                ps.paused = false;
                ps.time = System.currentTimeMillis() - pauseStateTime;
            }
        }
    }   
    
    /**Allow the player to resume the game by pressing space again*/
    public void update(float dt) {
        space_pressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        if (space_pressed && (System.currentTimeMillis() - lastPressed) > 500){
            paused = !paused;
            lastPressed = System.currentTimeMillis();
            playstate.lastPressed = lastPressed;

        }
        if (!(paused)) {
            playstate.time = System.currentTimeMillis() - pauseStateTime;
            playstate.paused = false;
            gsm.pop();
        }
        handleInput();
    }
    
    /**Render the pause screen and the save and exit button*/
    public void render(SpriteBatch sb) {
        sb.begin();
        cam.setToOrtho(false, MainGame.WIDTH, MainGame.HEIGHT);
        sb.setProjectionMatrix(cam.combined);
        sb.draw(pauseScreen, 0, 0, MainGame.WIDTH, MainGame.HEIGHT);
        sb.draw(saveBtn, ((float) MainGame.WIDTH / 3) - ((float) saveBtn.getWidth() / 20),
                (float) MainGame.HEIGHT / 3);
        sb.draw(exitBtn, (float) (((float) MainGame.WIDTH / 1.8) - ((float) exitBtn.getWidth() / 50)),
                (float) ((float) MainGame.HEIGHT / 3));
        sb.draw(backBtn, 0, MainGame.HEIGHT - backBtn.getHeight(), (float) backBtn.getWidth(),  (float) backBtn.getHeight()); //TEAM-19
        sb.end();
    }

    public void dispose() {
        pauseScreen.dispose();
        saveBtn.dispose();
        exitBtn.dispose();
    }
}
//TEAM19-END

