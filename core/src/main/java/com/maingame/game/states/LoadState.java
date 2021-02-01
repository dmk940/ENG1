package com.maingame.game.states;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.maingame.game.MainGame;
//from save state where     three save file 
//continue button + countdown.
//single save race?
//save time date

public class LoadState extends State implements java.io.Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    protected State playstate;
    private final Texture background;
    private final Texture file1Btn;
    private final Texture file2Btn;
    private final Texture file3Btn;
	private final Rectangle file1BtnBounds;
    private final Rectangle file2BtnBounds;
    private final Rectangle file3BtnBounds;
  


    public LoadState(GameStateManager gsm_) {
		super(gsm_);
		background = new Texture("background.png");
        file1Btn = new Texture("file1.png");
        file2Btn = new Texture("file2.png");
        file3Btn = new Texture("file3.png");
		file1BtnBounds = new Rectangle(((float) MainGame.WIDTH / 1) - ((float) file1Btn.getWidth() / 10), (float) MainGame.HEIGHT / 10, file1Btn.getWidth(), file1Btn.getHeight());
		file2BtnBounds = new Rectangle(((float) MainGame.WIDTH / 2) - ((float) file2Btn.getWidth() / 10), (float) MainGame.HEIGHT / 20, file2Btn.getWidth(), file2Btn.getHeight());
		file3BtnBounds = new Rectangle(((float) MainGame.WIDTH / 3) - ((float) file3Btn.getWidth() / 10), (float) MainGame.HEIGHT / 30, file3Btn.getWidth(), file3Btn.getHeight());
        gsm = gsm_;
    }	
    private void loadGame(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            playstate = (State) ois.readObject();
            ois.close();
            System.out.print("\n-Game loaded-\n");
        } catch (Exception e) {
            System.out.print("Serialization Error! Can't load data\n");
        }
    }
    
    @Override
	public void handleInput() {
		if (Gdx.input.justTouched()) {
			if (file1BtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
                loadGame("Save1.sav");
                gsm.set(playstate);
            } 
            if (file2BtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
                loadGame("Save2.sav");
                gsm.set(playstate);
            } 
            if (file3BtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
                loadGame("Save3.sav");
                gsm.set(playstate);
            }
		}
	}

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
		sb.draw(background, 0, 0, MainGame.WIDTH , MainGame.HEIGHT);
        sb.draw(file1Btn, ((float)MainGame.WIDTH / 1) - ((float) file1Btn.getWidth() / 10), (float) MainGame.HEIGHT / 10);
        sb.draw(file2Btn, ((float)MainGame.WIDTH / 2) - ((float) file2Btn.getWidth() / 10), (float) MainGame.HEIGHT / 20);
        sb.draw(file3Btn, ((float)MainGame.WIDTH / 3) - ((float) file3Btn.getWidth() / 10), (float) MainGame.HEIGHT / 30);
		sb.end();
        

    }

    @Override
    public void dispose() {
        background.dispose();
        file1Btn.dispose();
        file2Btn.dispose();
        file3Btn.dispose();
    }

    
}
