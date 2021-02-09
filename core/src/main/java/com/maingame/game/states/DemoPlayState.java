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

    Boat spareBoat;
    int counter = 0;
    
    public DemoPlayState(GameStateManager gsm, List<Boat> boats,Boat player,int leg, int difficulty){
        super(gsm, boats, player, leg, difficulty);

        // New boat to set at median position so can be passed to AI as player boat to chase
        spareBoat = new Boat("red");
        spareBoat.setPosX(-10000);
        
    }

    /** Same as the PlayState except with certain conditions removed (e.g., win condition)
     *  so it's more of an infinite scroller as a demo (except for health).
     */
    @Override
    public void update(float dt) {
        if ((System.currentTimeMillis() - countDown)/1000f > 3) {
            if (time == 0) {time = System.currentTimeMillis();}
            cam.position.y += player.speed;
            cam.update();
            updateCollisionBoundaries();
            updateRiver();
            collisionDetection();
            boatsOutOfBounds();
            repositionObstacles();
            updateBoatPenalties();
            for (int i = 0; i < boats.size(); i++) {
                boats.get(i).update(dt);
                AI ai = new AI(boats.get(i), leg,obstacleList, boats, player, 4);
                ai.update();
            }
            player.update(dt);
            int before_y_pos = player.getPosY();
            // weigh situation to boost "player" boat to mimic player movement
            int medianTotal = 0;
            
            for (Boat nBoat : boats) {
                medianTotal += nBoat.getPosY();
            }
            medianTotal = medianTotal / (boats.size());
            spareBoat.setPosY(medianTotal);

            AI ai_player = new AI(player, leg, obstacleList, boats, spareBoat, 4);
            ai_player.update();
            if (player.getPosY() > before_y_pos + player.speed) {
                if (player.getFatigue() > 0) {
                    System.out.println("-");
                    player.setFatigue(player.getFatigue()-2);
                }
                cam.position.y += player.acceleration;
                cam.update();
                counter -= 1;
                if (counter <0) { counter=0;}
            } else {
                if (player.getFatigue() < 600 && counter == 0) {
                    System.out.println("+");
                    player.setFatigue(player.getFatigue()+1);
                    // Counter required as AI is too advanced and feathers boosting frame perfectly
                    // which doesn't look very realistic
                    counter += 1;
                }
            }
            updateMapColour();
            checkBoatHealth();
        }
    }
}
//TEAM19-END