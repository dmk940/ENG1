package com.maingame.game.states;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.maingame.game.MainGame;

public class SaveState extends State {

    private final SaveInfo svInfo;
    private final Texture background;
    private final Texture file1Btn;
    private final Texture file2Btn;
    private final Texture file3Btn;
	private final Rectangle file1BtnBounds;
    private final Rectangle file2BtnBounds;
    private final Rectangle file3BtnBounds;

    private final Texture backBtn;
	private final Rectangle backBtnBounds;

    public SaveState(GameStateManager gsm, long pauseStateTime) {
        super(gsm);
		background = new Texture("background.png");
        file1Btn = new Texture("file1.png");
        file2Btn = new Texture("file2.png");
        file3Btn = new Texture("file3.png");
		file1BtnBounds = new Rectangle(((float) MainGame.WIDTH / 1) - ((float) file1Btn.getWidth() / 10), (float) MainGame.HEIGHT / 10, file1Btn.getWidth(), file1Btn.getHeight());
		file2BtnBounds = new Rectangle(((float) MainGame.WIDTH / 2) - ((float) file2Btn.getWidth() / 10), (float) MainGame.HEIGHT / 20, file2Btn.getWidth(), file2Btn.getHeight());
		file3BtnBounds = new Rectangle(((float) MainGame.WIDTH / 3) - ((float) file3Btn.getWidth() / 10), (float) MainGame.HEIGHT / 30, file3Btn.getWidth(), file3Btn.getHeight());
        backBtn = new Texture("backButton.png");
		backBtnBounds = new Rectangle(0, (float) MainGame.HEIGHT - backBtn.getHeight(), backBtn.getWidth(), backBtn.getHeight());
		
        
        gsm.pop();
        PlayState playstate = (PlayState) gsm.peek();

        // Create svInfo
        svInfo = new SaveInfo(playstate.boats, playstate.player, playstate.leg, playstate.obstacleList, 
                              pauseStateTime - playstate.time, playstate.finishLineBounds, 
                              playstate.finishLinePosition, playstate.DIFFICULTY, playstate.distanceTravelled);
    }	
    private void saveGame(String filename, SaveInfo svInfo) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(svInfo);
            oos.flush(); // write out any buffered bytes
            oos.close();
            System.out.print("Game saved\n");
        } catch (Exception e) {
            System.out.println("Serialization Error! Can't save data." + e);
        }
    }
    @Override
	public void handleInput() {
		if (Gdx.input.justTouched()) {
			if (file1BtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
                saveGame("Save1.sav", svInfo);
            } else if (file2BtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
                saveGame("Save2.sav", svInfo);
            } else if (file3BtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
                saveGame("Save3.sav", svInfo);
            } else if (backBtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())) {
				gsm.set(new WelcomeState(gsm));
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
        sb.draw(backBtn, 0, MainGame.HEIGHT - backBtn.getHeight(), (float) backBtn.getWidth(),  (float) backBtn.getHeight()); //TEAM-19

        sb.end();
        

    }

    @Override
    public void dispose() {
        background.dispose();
        file1Btn.dispose();
        file2Btn.dispose();
        file3Btn.dispose();
        backBtn.dispose();
    }

    
}



