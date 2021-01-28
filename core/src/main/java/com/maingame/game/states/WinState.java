package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.maingame.game.MainGame;

public class WinState extends State {

    private final Texture background;
    private final Texture finishBtn;
    private final Rectangle finishBtnBounds;
    private final BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"),false); 

    public WinState(GameStateManager gsm) {
        super(gsm);

        background = new Texture("background.png");
        finishBtn = new Texture("youWon.png");
        finishBtnBounds =  new Rectangle(((float) MainGame.WIDTH / 3) - ((float) finishBtn.getWidth() / 10), (float) MainGame.HEIGHT / 50, finishBtn.getWidth(), finishBtn.getHeight());
    }

	public void handleInput() {
        if (Gdx.input.justTouched()) {
			if (finishBtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
				gsm.set(new WelcomeState(gsm));
				dispose();
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

        font.draw(sb, "YOU WON",(float) MainGame.WIDTH/2-250, 700);
        sb.draw(finishBtn, ((float)MainGame.WIDTH / 3) - ((float) finishBtn.getWidth() / 10), (float) MainGame.HEIGHT / 50);
        sb.end();
    }

	
	public void dispose() {
        background.dispose();
		finishBtn.dispose();
		font.dispose();
    }
    
}