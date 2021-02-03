package com.maingame.game.states;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.maingame.game.MainGame;
import com.maingame.game.sprites.Boat;
import com.maingame.game.sprites.Obstacle;

/** Loads a game from a .sav save file. */
public class LoadState extends State {

    protected SaveInfo svInfo;
    private final Texture background;
    private final Texture file1Btn;
    private final Texture file2Btn;
    private final Texture file3Btn;
    private final Rectangle file1BtnBounds;
    private final Rectangle file2BtnBounds;
    private final Rectangle file3BtnBounds;

    private final Texture backBtn;
    private final Rectangle backBtnBounds;
//TEAM19-START
/** This is the screen displayed to the user when they are loading the game. */
    public LoadState(GameStateManager gsm_) {
        super(gsm_);
        background = new Texture("background.png");
        file1Btn = new Texture("file1.png");
        file2Btn = new Texture("file2.png");
        file3Btn = new Texture("file3.png");
        file1BtnBounds = new Rectangle(130, 530, ((float)MainGame.WIDTH) - ((float) file1Btn.getWidth()), (float) MainGame.HEIGHT/6);
		file2BtnBounds = new Rectangle(130, 330, ((float)MainGame.WIDTH) - ((float) file2Btn.getWidth()), (float) MainGame.HEIGHT/6);
		file3BtnBounds = new Rectangle(130, 130, ((float)MainGame.WIDTH) - ((float) file3Btn.getWidth()), (float) MainGame.HEIGHT/6);
        gsm = gsm_;

        backBtn = new Texture("backButton.png");
        backBtnBounds = new Rectangle(0, (float) MainGame.HEIGHT - backBtn.getHeight(), backBtn.getWidth(), backBtn.getHeight());

    }
    //deserialised the filename in order to load the saved game
    private void loadGame(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            svInfo = (SaveInfo) ois.readObject();
            ois.close();
            System.out.print("\n-Game loaded-\n");
        } catch (Exception e) {
            System.out.print("Serialization Error! Can't load data\n" + e);
        }
    }
    //generate components that couldn't be serialised 
    private void rebuildGame(String filename) {
        loadGame(filename);

        // Since textures are unserialisable, we must set them again for both the obstacles
        // and boats (otherwise NPE)
        for (Obstacle obst : svInfo.obstacleList) {
            obst.img = new Texture(obst.name + ".png");
        }

        for (Boat boat : svInfo.boats) {
            boat.boatImg = new java.util.HashMap<>();
            boat.boatMap = new java.util.HashMap<>();
            boat.buildBoatData();
            boat.images = boat.boatImg.get(boat.colour);
        }

        svInfo.player.boatImg = new java.util.HashMap<>();
        svInfo.player.boatMap = new java.util.HashMap<>();
        svInfo.player.buildBoatData();
        svInfo.player.images = svInfo.player.boatImg.get(svInfo.player.colour);

        PlayState playstate = new PlayState(gsm, svInfo.boats, svInfo.player, svInfo.leg, svInfo.obstacleList,
                svInfo.difficulty);
        
        // Update the cam position and all other important variables
        playstate.cam.position.y = playstate.player.getPosY() + 384;
        playstate.cam.update();

        playstate.paused = true;
        playstate.finishLineBounds = svInfo.finishLineBounds;
        playstate.finishLinePosition = svInfo.finishLinePosition;
        playstate.distanceTravelled = svInfo.distanceTravelled;
        playstate.countDown = System.currentTimeMillis();
        playstate.time = System.currentTimeMillis() - svInfo.time + 3000; // 3000 to compensate for countdown timer
        playstate.startTime = svInfo.time;
        playstate.paused = false;
        // ensure river position assets are in correct place so naive PlayState.updateRiver() can handle 
        playstate.riverPos1 = playstate.player.getPosY() - (playstate.player.getPosY() % playstate.river.getHeight());
        playstate.riverPos2 = playstate.riverPos1 + playstate.river.getHeight();
        gsm.set(playstate);
        
    }
    //create an area where it recognise the input click for the button file1, file2, file3 and back
    @Override
	public void handleInput() {
		if (Gdx.input.justTouched()) {
			if (file1BtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
                rebuildGame("Save1.sav");   
            } else if (file2BtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
                rebuildGame("Save2.sav");
            } else if (file3BtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())){
                rebuildGame("Save3.sav");
            } else if (backBtnBounds.contains(Gdx.input.getX(),(float) MainGame.HEIGHT - Gdx.input.getY())) {
				gsm.set(new WelcomeState(gsm));
			}
		}
	}

    @Override
    public void update(float dt) {
        handleInput();
    }
    //render the button file1, file2, file3, back and background
    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
		sb.draw(background, 0, 0, MainGame.WIDTH , MainGame.HEIGHT);
        sb.draw(file1Btn, 2, 400, ((float)MainGame.WIDTH ) - ((float) file1Btn.getWidth() / 10), (float) MainGame.HEIGHT/2 );
        sb.draw(file2Btn, 2, 200,((float)MainGame.WIDTH) - ((float) file2Btn.getWidth() / 10), (float) MainGame.HEIGHT/2 );
        sb.draw(file3Btn, 2, 0,((float)MainGame.WIDTH) - ((float) file3Btn.getWidth() / 10), (float) MainGame.HEIGHT/2 );
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

    
}//TEAM19-END
