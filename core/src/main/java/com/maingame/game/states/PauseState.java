package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.maingame.game.MainGame;

//TEAM19-START
/** This is the screen displayed to the user when they have pause the game. */
public class PauseState extends State implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final Texture pauseScreen;
    private final Texture saveBtn;
    private final Rectangle saveBtnBounds;
    private final Texture exitBtn;
    private final Rectangle exitBtnBounds;
    MainGame game; 
    protected boolean paused;
    public Boolean space_pressed = false;


    public PauseState(GameStateManager gsm) {
        super(gsm);
        pauseScreen = new Texture("pause.png");
        saveBtn = new Texture("save.png");
        exitBtn = new Texture("exit.png");

        saveBtnBounds = new Rectangle(((float) MainGame.WIDTH / 3) - ((float) saveBtn.getWidth() / 20),
                (float) MainGame.HEIGHT / 3, saveBtn.getWidth(), saveBtn.getHeight());
        exitBtnBounds = new Rectangle((float) (((float) MainGame.WIDTH / 1.8) - ((float) exitBtn.getWidth() / 50)),
                (float) MainGame.HEIGHT / 3, exitBtn.getWidth(), exitBtn.getHeight());
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            if (exitBtnBounds.contains(Gdx.input.getX(), (float) MainGame.HEIGHT - Gdx.input.getY())) {
                gsm.set(new WelcomeState(gsm));
                dispose();
            }
            if (saveBtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())) {
                gsm.set(new SaveState(gsm));
            }
        }
    }

    public void update(float dt) {
        space_pressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        if (space_pressed){
            paused = !paused;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (paused) {
            gsm.pop();
        }
        handleInput();
    }

    public void render(SpriteBatch sb) {
        sb.begin();
        cam.setToOrtho(false, MainGame.WIDTH, MainGame.HEIGHT);
        sb.setProjectionMatrix(cam.combined);
        sb.draw(pauseScreen, 0, 0, MainGame.WIDTH, MainGame.HEIGHT);
        sb.draw(saveBtn, ((float) MainGame.WIDTH / 3) - ((float) saveBtn.getWidth() / 20),
                (float) MainGame.HEIGHT / 3);
        sb.draw(exitBtn, (float) (((float) MainGame.WIDTH / 1.8) - ((float) exitBtn.getWidth() / 50)),
                (float) ((float) MainGame.HEIGHT / 3));
        sb.end();
    }

    public void dispose() {
        pauseScreen.dispose();
        saveBtn.dispose();
        exitBtn.dispose();
    }
}

