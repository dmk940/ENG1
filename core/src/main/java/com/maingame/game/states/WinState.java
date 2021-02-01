package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.maingame.game.MainGame;

//TEAM19-START
/** This is the screen displayed to the user when they have won the game. */
public class WinState extends State {

    private final Texture background;
    private final Texture finishBtn;
    private final Texture exitBtn;
    private final Rectangle finishBtnBounds;
    private final Rectangle exitBtnBounds;
    private final BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"),false); 

    public WinState(GameStateManager gsm) {
        super(gsm);

        background = new Texture("background.png");
        finishBtn = new Texture("youWon.png");
        exitBtn = new Texture("exit.png");
        finishBtnBounds =  new Rectangle(MainGame.WIDTH /2f - finishBtn.getWidth()/2, MainGame.HEIGHT/2f - finishBtn.getHeight()/2, finishBtn.getWidth(), finishBtn.getHeight());
        exitBtnBounds =  new Rectangle(MainGame.WIDTH /2f - exitBtn.getWidth()/2, MainGame.HEIGHT/2f - exitBtn.getHeight()/2 - finishBtn.getHeight()*2, exitBtn.getWidth(), exitBtn.getHeight());
    }

	public void handleInput() {
        if (Gdx.input.justTouched()) {
			if (finishBtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
				gsm.set(new WelcomeState(gsm));
				dispose();
		    } else if (exitBtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
				Gdx.app.exit();
            }
        }
    }

	public void update(float dt) {
        handleInput();
    }


	public void render(SpriteBatch sb) {
        sb.begin();
        cam.setToOrtho(false,MainGame.WIDTH,MainGame.HEIGHT);
        sb.setProjectionMatrix(cam.combined);
        sb.draw(background, 0, 0, MainGame.WIDTH , MainGame.HEIGHT);

        font.draw(sb, "YOU WON", MainGame.WIDTH/2 - 170, 700);
        sb.draw(finishBtn, MainGame.WIDTH /2f - finishBtn.getWidth()/2, MainGame.HEIGHT/2f - finishBtn.getHeight()/2);
        sb.draw(exitBtn, MainGame.WIDTH /2f - exitBtn.getWidth()/2, MainGame.HEIGHT/2f - exitBtn.getHeight()/2 - finishBtn.getHeight()*2);
        
        sb.end();
    }

	
	public void dispose() {
        background.dispose();
		finishBtn.dispose();
		font.dispose();
    }
    
}
//TEAM19-END