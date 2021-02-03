package com.maingame.game.states;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.maingame.game.MainGame;
//TEAM19-START: 
/** This is the screen displayed to the user when they are saving the game. */
public class SaveState extends State {

    private final SaveInfo svInfo;
    private final Texture background;
    private Texture file1Btn;
    private Texture file2Btn;
    private Texture file3Btn;
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
	    file1BtnBounds = new Rectangle(130, 530, ((float)MainGame.WIDTH) - ((float) file1Btn.getWidth()), (float) MainGame.HEIGHT/6);
		file2BtnBounds = new Rectangle(130, 330, ((float)MainGame.WIDTH) - ((float) file2Btn.getWidth()), (float) MainGame.HEIGHT/6);
		file3BtnBounds = new Rectangle(130, 130, ((float)MainGame.WIDTH) - ((float) file3Btn.getWidth()), (float) MainGame.HEIGHT/6);
        backBtn = new Texture("backButton.png");
		backBtnBounds = new Rectangle(0, (float) MainGame.HEIGHT - backBtn.getHeight(), backBtn.getWidth(), backBtn.getHeight());
		
        gsm.pop();
        PlayState playstate = (PlayState) gsm.peek();

        // Create svInfo
        svInfo = new SaveInfo(playstate.boats, playstate.player, playstate.leg, playstate.obstacleList, 
                              pauseStateTime, playstate.finishLineBounds, 
                              playstate.finishLinePosition, playstate.DIFFICULTY, playstate.distanceTravelled);
    }
    
    /**Serialises the svInfo in order to save the game*/
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
    
    /**Create an area where it recognise the input click for the button file1, file2, file3 and back*/
    @Override
	public void handleInput() {
		if (Gdx.input.justTouched()) {
			if (file1BtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
                saveGame("Save1.sav", svInfo);
                file1Btn = new Texture("file1Green.png");
            } else if (file2BtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
                saveGame("Save2.sav", svInfo);
                file2Btn = new Texture("file2Green.png");
            } else if (file3BtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
                saveGame("Save3.sav", svInfo);
                file3Btn = new Texture("file3Green.png");
            } else if (backBtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())) {
				gsm.set(new WelcomeState(gsm));
			}
		}
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    /**Render the button file1, file2, file3, back and background*/
    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
		sb.draw(background, 0, 0, MainGame.WIDTH , MainGame.HEIGHT);
        sb.draw(file1Btn, 2, 400, ((float)MainGame.WIDTH ) - ((float) file1Btn.getWidth() / 10), (float) MainGame.HEIGHT/2);
        sb.draw(file2Btn, 2, 200,((float)MainGame.WIDTH) - ((float) file2Btn.getWidth() / 10), (float) MainGame.HEIGHT/2);
        sb.draw(file3Btn, 2, 0,((float)MainGame.WIDTH) - ((float) file3Btn.getWidth() / 10), (float) MainGame.HEIGHT/2);
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
//TEAM19-END



