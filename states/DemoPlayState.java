package com.maingame.game.states;

import java.util.List;

import com.maingame.game.sprites.AI;
import com.maingame.game.sprites.Boat;

//TEAM19-START
/**
 * This class handles the playstate for the Demo version of the game - no input, and the 
 * player boat moves by itself.
 */

public class DemoPlayState extends PlayState {

    protected static int LEG_TIME = 20;
    
    public DemoPlayState(GameStateManager gsm, List<Boat> boats,Boat player,int leg){
        super(gsm, boats, player, leg);
    }

    @Override
    public void update(float dt) {
        if ((System.currentTimeMillis() - countDown)/1000 > 3) {
            //handleInput();
            cam.position.y += player.speed;
            cam.update();
            updateCollisionBoundaries();
            updateRiver();
            collisionDetection();
            boatsOutOfBounds();
            updateMapColour();
            repositionObstacles();
            updateBoatPenalties();
            for (int i = 0; i < boats.size(); i++) {
                boats.get(i).update(dt);
                AI ai = new AI(boats.get(i), leg,obstacleList, boats, player);
                ai.update();
                //List<Boat> newBoatList = new ArrayList<>(boats);
                //boats.get(i).hasCollided(newBoatList,player);
            }
            player.update(dt);
            int before_y_pos = player.getPosY();
            AI ai_player = new AI(player, leg, obstacleList, boats.subList(0, boats.size()-2), boats.get(boats.size()-1));
            ai_player.update();
            if (player.getPosY() > before_y_pos + player.speed) {
                cam.position.y += player.acceleration;
                cam.update();
            }
            //player.hasCollided(boats,player);
            checkBoatHealth();
            if ((System.currentTimeMillis() - countDown)/1000 > LEG_TIME && finishLinePosition == 0) {
                isLegOver();
            }
            finishLeg();
        }
    }
}
//TEAM19-END