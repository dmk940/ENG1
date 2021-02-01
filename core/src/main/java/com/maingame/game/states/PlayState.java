package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.maingame.game.MainGame;
import com.maingame.game.sprites.AI;
import com.maingame.game.sprites.Boat;
import com.maingame.game.sprites.Obstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Handles the logic of the main game and loading of assets.
 */
public class PlayState extends State {
    private final Texture river; // the river asset
    private final Texture riverReversed; // the river asset reversed
    private final Texture finishLine;
    protected List<Boat> boats; // a list containing all the boats
    protected final int leg; // the current leg number
    public int finishLinePosition; // an integer to keep track of the finishLine coord pos
    long time; // Used to track time elapsed from the start of a leg
    protected long countDown; // a countdown used to show when the game starts.
    protected Boat player;
    private float riverPos1; // A tracker for the positions of the river assets
    private float riverPos2; // A tracker for the positions of the river assets
    private final BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"),false); // a font to draw text
    private final Pixmap healthMap; // a map to render the health bar background.
    private final Pixmap healthMap2; // a map to render the health bar background.
    private final Pixmap fatigueMap; // a map to render the fatigue bar.
    private final Pixmap fatigueMap2; // a map to render the fatigue bar background.
    private final Pixmap penaltyMap; // a map to render the penalty bar.
    private final Pixmap penaltyMap2; // a map to render the penalty bar background.
    public List<Obstacle> obstacleList = new ArrayList<>(); // a list containing all the obstacles.
    public Rectangle finishLineBounds; // a box to detect when a boat reaches the finish line.
    private final Random generator = new Random();
    private static final String GREEN = "345830";
    private static final String WHITE = "eeeded";
    private static final String RED = "823038";
    private static final String ORANGE = "F95738";
    private static final String YELLOW ="F2BB05";
    //TEAM19-START : made this a constant instead of hardcoded variable
    private static final int PENALTY_SEC = 2;
    //TEAM19-END

    public Boolean up_pressed = false;
    public Boolean down_pressed = false;
    public Boolean left_pressed = false;
    public Boolean right_pressed = false;

    //TEAM19-START : moved to constant leg distance, changed from time to distance
    // as this is more fun
    private static final double LEG_LENGTH = 5000; //distance in meters
    int DIFFICULTY = 0;
    int distanceTravelled; //distance travelled by the boats

    protected boolean paused;
    public Boolean space_pressed = false;
    //TEAM19-END

    public PlayState(GameStateManager gsm, List<Boat> boats,Boat player,int leg, int difficulty){
        super(gsm);
        finishLine = new Texture("finishLine.png");
        river = new Texture("river.png");
        riverReversed = new Texture("river_reversed.png");

        //TEAM19 resets distance to 0 each time the map is created
        DIFFICULTY = difficulty;
        distanceTravelled = 0; 

        healthMap = new Pixmap(200,30, Pixmap.Format.RGBA8888);
        healthMap2 = new Pixmap(210,40, Pixmap.Format.RGBA8888);
        fatigueMap = new Pixmap(200,30, Pixmap.Format.RGBA8888);
        fatigueMap2 = new Pixmap(210,40, Pixmap.Format.RGBA8888);
        penaltyMap = new Pixmap(200,30, Pixmap.Format.RGBA8888);
        penaltyMap2 = new Pixmap(210,40, Pixmap.Format.RGBA8888);
        healthMap.setColor(Color.valueOf(GREEN));
        healthMap2.setColor(Color.valueOf(WHITE));
        fatigueMap.setColor(Color.valueOf(GREEN));
        fatigueMap2.setColor(Color.valueOf(WHITE));
        penaltyMap.setColor(Color.valueOf(GREEN));
        penaltyMap2.setColor(Color.valueOf(WHITE));
        healthMap.fill();
        healthMap2.fill();
        fatigueMap.fill();
        fatigueMap2.fill();
        penaltyMap.fill();
        penaltyMap2.fill();

        this.leg = leg;
        this.boats = boats;
        this.player = player;
        cam.setToOrtho(false, (float) river.getWidth()*5,MainGame.HEIGHT);
        if (leg != 4){
            try{
                boats.get(0).setPosX(river.getWidth()/2-50);
                boats.get(1).setPosX(river.getWidth()/2 + river.getWidth()-50);
                player.setPosX(river.getWidth()/2 + (river.getWidth()*2)-50);
                boats.get(2).setPosX(river.getWidth()/2 + (river.getWidth()*3)-50);
                boats.get(3).setPosX(river.getWidth()/2 + (river.getWidth()*4)-50);
            }catch (Exception e){ }
        }else {
            boats.get(0).setPosX(river.getWidth()/2-50);
            player.setPosX(river.getWidth()/2 + (river.getWidth()*2)-50);
            boats.get(1).setPosX(river.getWidth()/2 + (river.getWidth()*4)-50);
        }
        riverPos1 = 0;
        riverPos2 = river.getHeight();

        this.buildObstaclesList(leg);
        countDown = System.currentTimeMillis();
    }

    public PlayState(GameStateManager gsm, List<Boat> boats, Boat player, int leg, List<Obstacle> obstacleList, int difficulty) {
        this(gsm, boats, player, leg, difficulty);
        this.obstacleList = obstacleList;
    }

    //TEAM19-START : moved input logic to new function handleInputLogic() to allow for testing
    // as GDX inputs cannot easily be simulated.
    /**
     * {@inheritDoc}
     * Gives the player an initial speed and makes the camera follow the player.
     * Given a user input, handles acceleration and movement of player on the screen.
     * Also handles player fatigue.
     */
    @Override
    public void handleInput() {
        up_pressed = (Gdx.input.isKeyPressed(Input.Keys.W) || (Gdx.input.isKeyPressed(Input.Keys.UP)));
        left_pressed = (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT));
        right_pressed = (Gdx.input.isKeyPressed(Input.Keys.D)|| Gdx.input.isKeyPressed(Input.Keys.RIGHT));
        down_pressed = (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN));
        space_pressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        handleInputLogic();
    }

    public void handleInputLogic() {
        player.setPosY(player.getPosY() + player.speed);
        cam.position.y += player.speed;
        if (time == 0){
            time = System.currentTimeMillis();
        }
        //TEAM19-START - updated so that no movement is possible when the player touches the
        // finish line
        if (player.getTotalLegTime() == 0) {
            if (up_pressed) {
                if (player.getFatigue() > 0 ){
                    player.setPosY(player.getPosY() + player.acceleration);
                    player.setFatigue(player.getFatigue() - 2);
                    cam.position.y += player.acceleration;
                    //TEAM 19 START: assuming speed and acceleration is in m, it adds it to distance travelled
                    distanceTravelled += (player.speed + player.acceleration);                 
                }
            } else {
                //TEAM19-START: user is not currently boosting, slowly restore fatigue
                distanceTravelled += player.speed; //assuming speed is in m, it adds it to distance travelled
                if (player.getFatigue() < 600) { player.setFatigue(player.getFatigue() + 1); }
            }

            if (left_pressed) {
                player.setPosX(player.getPosX() - player.maneuverability/2);
            }
            if (right_pressed) {
                player.setPosX(player.getPosX() + player.maneuverability/2);
            } //TEAM19-START : added ability to slow down slightly
            if (down_pressed) {
                player.setPosY(player.getPosY() - player.maneuverability/2);
                cam.position.y -= (player.maneuverability/2);
            } //pause press
            if (space_pressed){
                paused = !paused;
                //avoid instant pause and unpause
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else{
                cam.update();
            }
        }
    }
    //TEAM19-END

    /**
     * {@inheritDoc}
     * Sets a 3 second countdown after which the game starts.
     * @param dt the time between each start of a render()
     */
    @Override
    public void update(float dt) {
        if ((System.currentTimeMillis() - countDown)/1000f > 3) {
            handleInput();
            updateCollisionBoundaries();
            updateRiver();
            collisionDetection();
            boatsOutOfBounds();
            updateMapColour();
            repositionObstacles();
            updateBoatPenalties();
            for (int i = 0; i < boats.size(); i++) {
                boats.get(i).update(dt);
                AI ai = new AI(boats.get(i), leg,obstacleList, boats, player, DIFFICULTY);
                ai.update();
                List<Boat> newBoatList = new ArrayList<>(boats);
                boats.get(i).hasCollided(newBoatList,player);
            }
            player.update(dt);
            player.hasCollided(boats,player);
            checkBoatHealth();
            if ((distanceTravelled > LEG_LENGTH && finishLinePosition == 0)) {
                isLegOver();
            }
            finishLeg();
        }
        if (paused) {
            gsm.push(new PauseState(gsm, System.currentTimeMillis()));
        }
    }

    //TEAM19-START : refactored some tightly coupled game logic into its own method
    public void isLegOver() {
            finishLinePosition = getWinningBoat().getPosY() + river.getHeight() +100;
            finishLineBounds = new Rectangle(0,finishLinePosition,finishLine.getWidth(),finishLine.getHeight());
    }

    /**
     * {@inheritDoc}
     * @param sb a batch for drawing objects
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        // Checks which leg it is and renders the boats and the background.
        sb.draw(river, 0, riverPos1);
        sb.draw(riverReversed, 0, riverPos2);
        sb.draw(riverReversed, river.getWidth(), riverPos1);
        sb.draw(river, river.getWidth(), riverPos2);
        sb.draw(river, (float) river.getWidth() * 2, riverPos1);
        sb.draw(riverReversed, (float) river.getWidth() * 2, riverPos2);
        sb.draw(riverReversed, (float) river.getWidth() * 3, riverPos1);
        sb.draw(river, (float) river.getWidth() * 3, riverPos2);
        sb.draw(river, (float) river.getWidth() * 4, riverPos1);
        sb.draw(riverReversed, (float) river.getWidth() * 4, riverPos2);

        if ((distanceTravelled > LEG_LENGTH - 10)) { //TEAM 19: shows the finish line if its 10m away from the end
            sb.draw(finishLine,0,finishLinePosition);
        }
        drawBoats(leg,sb);

        // renders obstacles.
        for (int i = 0; i < obstacleList.size() - 1; i++) {
            Obstacle obstacle = obstacleList.get(i);
            sb.draw(obstacle.img,obstacle.getPosX(),obstacle.getPosY(),70,70,0,0,obstacle.img.getWidth(),obstacle.img.getHeight(),obstacle.isDirection(),false);
        }

        // Renders time and stat bars including health bar , fatigue bar, penalty bar.
        Texture pix2 = new Texture(fatigueMap2);
        Texture pix = new Texture(fatigueMap);
        font.draw(sb,"Fatigue: ",cam.position.x/2 - pix.getWidth() - 200,cam.position.y + 358);
        sb.draw(pix2,cam.position.x/2 - pix2.getWidth() ,cam.position.y + 310);
        int fatigueBar = (player.getFatigue() * 200)/600;
        sb.draw(pix,cam.position.x/2 - pix.getWidth() - 5,cam.position.y + 315,fatigueBar,30);
        pix2 = new Texture(healthMap2);
        pix = new Texture(healthMap);
        font.draw(sb,"Health: ",cam.position.x/2 - pix.getWidth() - 200,cam.position.y + 308);
        sb.draw(pix2,cam.position.x/2 - pix2.getWidth() ,cam.position.y + 260);
        int healthBar = (player.getHealth() * 200)/100;
        sb.draw(pix,cam.position.x/2 - pix.getWidth() - 5,cam.position.y + 265,healthBar,30);
        pix2 = new Texture(penaltyMap2);
        pix = new Texture (penaltyMap);
        font.draw(sb, "Penalty: ",cam.position.x/2 - pix.getWidth() - 200,cam.position.y + 260);
        sb.draw(pix2, cam.position.x/2 - pix2.getWidth(), cam.position.y + 212);
        int penaltyBar = (player.getPenaltyBar() * 200)/100;
        sb.draw(pix, cam.position.x/2 - pix.getWidth() - 5,cam.position.y + 217, penaltyBar, 30);
        if (time != 0){
            font.draw(sb,"Time: " + String.format("%.02f", (System.currentTimeMillis() - time)/1000f + player.getTimePenalty()) + "s" ,cam.position.x/2 - pix.getWidth() - 200,cam.position.y + 210);
        }

        // renders a countdown at the start of each leg.
        if ((System.currentTimeMillis() - countDown)/1000f < 3) {
            font.draw(sb,"Countdown: " + (3 - (System.currentTimeMillis() - countDown)/1000),cam.position.x - 170,cam.position.y+50);
        }
        sb.end();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        river.dispose();
        riverReversed.dispose();
        font.dispose();
        for (Obstacle obstacle : obstacleList) {
            obstacle.img.dispose();
        }
        fatigueMap.dispose();
        fatigueMap2.dispose();
        healthMap.dispose();
        healthMap2.dispose();
        penaltyMap.dispose();
        penaltyMap2.dispose();
    }

    /**
     * Repositions the river assets once they are under a player's x position.
     */
    protected void updateRiver() {
        if (player.getPosY() > riverPos1 + river.getHeight()){
            riverPos1 += river.getHeight() * 2;
        }else if (player.getPosY() > riverPos2 + river.getHeight()){
            riverPos2 += river.getHeight()* 2;
        }
    }

    /**
     * Checks if for each boat it is out of its lane including the player boat.
     * @see Boat#isBoatOutOfLane()
     */
    protected void boatsOutOfBounds() {
        for (Boat boat : boats) {
            boat.isBoatOutOfLane();
        }
        player.isBoatOutOfLane();
    }

    /**
     * Changes the colours of bars depending on the percentage left in each bar.
     */
    protected void updateMapColour() {
        if (player.getHealth() <= 25){
            healthMap.setColor(Color.valueOf(RED));
        }else if (player.getHealth() <=50){
            healthMap.setColor(Color.valueOf(ORANGE));
        }else if (player.getHealth() <= 75){
            healthMap.setColor(Color.valueOf(YELLOW));
        }
        int fatiguePercent = player.getFatigue()*100/600;
        if (fatiguePercent <= 25){
            fatigueMap.setColor(Color.valueOf(RED));
        }else if (fatiguePercent <=50){
            fatigueMap.setColor(Color.valueOf(ORANGE));
        }else if (fatiguePercent <= 75){
            fatigueMap.setColor(Color.valueOf(YELLOW));
        //TEAM19-START : corrected fatigue bar so correctly 
        //goes back to green after regeneration > 76
        } else if (fatiguePercent > 76){
            fatigueMap.setColor(Color.valueOf(GREEN));
        } //TEAM19-END
        if (player.getPenaltyBar() <= 25){
            penaltyMap.setColor(Color.valueOf(RED));
        }else if (player.getPenaltyBar() <=50){
            penaltyMap.setColor(Color.valueOf(ORANGE));
        }else if (player.getPenaltyBar() <= 75){
            penaltyMap.setColor(Color.valueOf(YELLOW));
        }else {
            penaltyMap.setColor(Color.valueOf(GREEN));
        }
        healthMap.fill();
        fatigueMap.fill();
        penaltyMap.fill();
    }


    /**
     * Generates a fixed amount of random Obstacles depending on which leg it is.
     * @param leg an int representing the current leg.
     */
    private void buildObstaclesList(int leg) {
        //TEAM19-START: Added powerups to obstacle list and set total number of powerups
        String[] possibleObstacles = {"rock1","rock2","goose","duck1","duck2","healthUp","fatigueUp","fatigueUp2","healthUp2","speedUp"};
        int totalPowerUp = 7;
        //TEAM19-END
        int obstacleCount;
        if (leg == 1) {
            obstacleCount = 20;
        }else if (leg == 2) {
            obstacleCount = 30;
        }else {
            obstacleCount = 40;
        }
        //TEAM19-START: Power-up spawning added, keeping total number at 5. Added more obstacles at greate difficulties
        if (DIFFICULTY == 1) {
            obstacleCount += 10;
        } else if (DIFFICULTY == 2) {
            obstacleCount += 20;
        } else if (DIFFICULTY == 3) {
            obstacleCount += 30;
        }
        
        int powerUpCount = 0;
        for (int i = 0; i < obstacleCount; i++) {
            Obstacle obstacle = new Obstacle(possibleObstacles[generator.nextInt(possibleObstacles.length)]);
            if (obstacle.name == "healthUp" || obstacle.name == "fatigueUp" || obstacle.name == "healthUp2" || obstacle.name == "fatigueUp2" || obstacle.name =="speedUp"){
                if(powerUpCount < totalPowerUp){
                    obstacle.setDirection(generator.nextFloat() > 0.5);
                    this.obstacleList.add(obstacle);
                    powerUpCount += 1;
                }
            }
            else {
                obstacle.setDirection(generator.nextFloat() > 0.5);
                this.obstacleList.add(obstacle);
            }
        }
        repositionObstacles();
    }

    /**
     * Repositions Obstacles once they are off the screen.
     * The obstacles are repositioned randomly to have a y value higher the top edge of the screen.
     * @see Obstacle#moveObstacle()
     */
    protected void repositionObstacles() {
        for (int i = 0; i < obstacleList.size() - 1; i++){
            Obstacle obstacle = obstacleList.get(i);
            if ((obstacle.getPosY() + obstacle.img.getHeight() < player.getPosY()) || (obstacle.getPosX() > river.getWidth() * 5) || (obstacle.getPosX() + obstacle.img.getWidth() < 0) ){
                obstacle.setPosX(ThreadLocalRandom.current().nextInt(river.getWidth()*5));
                obstacle.setPosY(ThreadLocalRandom.current().nextInt(player.getPosY() + river.getHeight(),player.getPosY() + (river.getHeight()*3)));
            }
                obstacle.moveObstacle();
        }
    }

    /**
     * Updates the collisionBoundaries for the boats and the obstacles.
     * This is done to match the current position of a boat/obstacle.
     * @see Obstacle#updateCollisionBounds()
     */
    protected void updateCollisionBoundaries() {
        for (Boat boat : boats) {
            //TEAM19-START : moved hardcoded logic into Boat logic class
            boat.updateCollisionBounds();
            //TEAM19-END
        }
        player.collisionBounds.setPosition((float) player.getPosX() +10,(float) player.getPosY() +10);
        for (int i = 0; i < obstacleList.size() - 1; i++){
            Obstacle obstacle = obstacleList.get(i);
            obstacle.updateCollisionBounds();
        }
    }

    /**
     * Detects collisions by calling checkHit for each obstacle and boat combination.
     * @see Obstacle#checkHit(Boat)
     */
    protected void collisionDetection() {
        for (int x = 0; x < obstacleList.size()-1; x++) {
            Obstacle obstacle = obstacleList.get(x);
            for (Boat boat : boats) {
                obstacle.checkHit(boat);
            }
            obstacle.checkHit(player);
        }
    }

    //TEAM19-START : replace hardcoded values with constants
    /**
     * Adds a time penalty to each boat including the player boat when the penaltyBar is empty.
     */
    public void updateBoatPenalties() {
        for (Boat boat : boats) {
            if (boat.getPenaltyBar() == 0) {
                boat.setPenaltyBar(100);
                boat.setTimePenalty(boat.getTimePenalty() + PENALTY_SEC);
            }
        }
        if (player.getPenaltyBar() == 0) {
            player.setPenaltyBar(100);
            player.setTimePenalty(player.getTimePenalty() + PENALTY_SEC);
        }
    }
    //TEAM19-END
    //TEAM19-START : changed visibility of these to public to allow testing.
    // This isn't necessarily a good idea - it suggests this class should be 
    // refactored to separate game logic into more functions, but with how 
    // tightly coupled it is this would require major structural change.
    /**
     * Checks if the boat has depleted its health and changes hasLost
     * @see Boat#setHasLost(boolean)
     */
    public void checkBoatHealth() {
        for (Boat boat : boats) {
            if (boat.getHealth() <= 0) {
                boat.setHasLost(true);
            }
        }
        if (player.getHealth() <= 0) {
            gsm.set(new GameOverHealth(gsm));
            player.setHasLost(true);
        }
    }

    /**
     * checks if the leg is over and transitions to the leaderboard state.
     */
    public void finishLeg() {
        if (finishLineBounds != null){
            for (Boat boat:boats) {
                if (boat.getPosY() > finishLinePosition +10 && boat.isHasNotLost()) {
                    if (boat.getTotalLegTime() == 0){
                        boat.setTotalLegTime((System.currentTimeMillis() - time)/1000f);
                    }
                    boat.setPosY(finishLinePosition +10);
                }
            }
            if (player.getPosY() >= finishLinePosition +10) {
                player.setPosY(finishLinePosition + 10);
                cam.position.y -= player.speed;
                if (player.getTotalLegTime() == 0) {
                    player.setTotalLegTime((System.currentTimeMillis() - time)/1000f);
                }
                boolean haveBoatsFinished = true;
                for (Boat boat:boats) {
                    if (boat.getTotalLegTime() == 0 && boat.isHasNotLost()){
                        haveBoatsFinished = false;
                    }
                }
                if (haveBoatsFinished) {
                    List<Boat> newList = new ArrayList<>();
                    for (Boat boat:boats) {
                        if (boat.isHasNotLost()){
                            newList.add(boat);
                        }else {
                            boat.images.get(0).dispose();
                            boat.images.get(1).dispose();
                        }
                    }
                    gsm.set(new LeaderboardState(gsm,leg,newList,player, DIFFICULTY));
                }
            }
        }
    }
    //TEAM19-END

    /**
     * Draw the boats on specified locations depending on which lap it is.
     * @param leg the current leg
     * @param sb the spritebatch used for rendering
     */
    private void drawBoats(int leg,SpriteBatch sb) {
        try{
            if (leg != 4) {
                Boat boat = boats.get(0);
                boat.setBounds(0,(float) river.getWidth()-50);
                if (boat.isHasNotLost()) {
                    sb.draw(boat.images.get(boat.getFrame()), boat.getPosX(),boat.getPosY(),100,100);
                }
                boat = boats.get(1);
                boat.setBounds((float) river.getWidth()-50,(float) river.getWidth()*2-50);
                if (boat.isHasNotLost()) {
                    sb.draw(boat.images.get(boat.getFrame()), boat.getPosX(),boat.getPosY(),100,100);
                }
                boat = player;
                boat.setBounds((float) river.getWidth()*2-50,(float) river.getWidth()*3-50);
                if (boat.isHasNotLost()) {
                    sb.draw(boat.images.get(boat.getFrame()),boat.getPosX(),boat.getPosY(),100,100);
                }
                boat = boats.get(2);
                boat.setBounds((float) river.getWidth()*3-50,(float) river.getWidth()*4-50);
                if (boat.isHasNotLost()) {
                    sb.draw(boat.images.get(boat.getFrame()), boat.getPosX(),boat.getPosY(),100,100);
                }
                boat = boats.get(3);
                boat.setBounds((float) river.getWidth()*4-50,(float) river.getWidth()*5-50);
                if (boat.isHasNotLost()) {
                    sb.draw(boat.images.get(boat.getFrame()), boat.getPosX(),boat.getPosY(),100,100);
                }
            }else {
                Boat boat = boats.get(0);
                boat.setBounds(0,(float) river.getWidth()-50);
                if (boat.isHasNotLost()) {
                    sb.draw(boat.images.get(boat.getFrame()), boat.getPosX(),boat.getPosY(),100,100);
                }
                boat = player;
                boat.setBounds((float) river.getWidth()*2-50,(float) river.getWidth()*3-50);
                if (boat.isHasNotLost()) {
                    sb.draw(boat.images.get(boat.getFrame()),boat.getPosX(),boat.getPosY(),100,100);
                }
                boat = boats.get(1);
                boat.setBounds((float) river.getWidth()*4-50,(float) river.getWidth()*5-50);
                if (boat.isHasNotLost()) {
                    sb.draw(boat.images.get(boat.getFrame()), boat.getPosX(),boat.getPosY(),100,100);
                }
            }
        }catch (Exception e){

        }
    }

    /**
     * Returns the boat currently in first position
     * @return thw winning boat
     */
    private Boat getWinningBoat(){
        Boat winningBoat = boats.get(0);
        for (Boat boat:boats) {
            if (boat.isHasNotLost() && winningBoat.getPosY() < boat.getPosY()) {
                winningBoat = boat;
            }
        }
        if (winningBoat.getPosY() < player.getPosY()) {
            winningBoat = player;
        }
        return winningBoat;
    }
}
