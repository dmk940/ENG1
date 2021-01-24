package com.maingame.game;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import com.maingame.game.sprites.Boat;
import com.maingame.game.states.GameOverSpeed;
import com.maingame.game.states.GameStateManager;
import com.maingame.game.states.LeaderboardState;
import com.maingame.game.states.PlayState;

/**
 * Tests the flow of execution of the game, i.e. screens
 * appear and disappear correctly upon specific events
 * (gameover, next leg...).
 */
public class TestScreensGameFlow extends TestMovement {

    Boat winner;

    public void initFullGame() {
        player = new Boat("pink");
        winner = new Boat("red");
        boats.add(winner);
        boats.add(new Boat("yellow"));
        boats.add(new Boat("purple"));
        boats.add(player);

        gsm = new GameStateManager();
        playstate = new PlayState(gsm, boats, player, 0);
    }

    @Test
    public void testGameOverSpeed() {
        initFullGame();
        winner.setPosY(100);
        playstate.isLegOver();
        int counter = 30;
        for (Boat boat : boats) {
            if (boat != player) {
                boat.setPosY(1000);
                boat.setTotalLegTime(counter);
                boat.setHasLost(false);
                counter = counter - 1;
            }
        }
        winner.setTotalLegTime(1);
        player.setPosY(playstate.finishLinePosition+100000);
        player.setTotalLegTime(31);
        player.setHasLost(false);
        playstate.finishLeg();
        assertTrue("leaderboard failed", gsm.peek() instanceof LeaderboardState);
        ((LeaderboardState) gsm.peek()).moveToNewState(0);
        assertTrue("gameover failed", gsm.peek() instanceof GameOverSpeed);

    }

    //public void testMainMenuBoatSelect() {}
    // This is manually tested as it would require significant rewrites to become automatically testable.

    @Test
    public void testFinishLine() {
        createPlayer();
        boats.remove(0); // remove non-player boat so haveBoatsFinished will be true
        playstate.isLegOver();
        player.setPosY(playstate.finishLinePosition+100); // ensure definitely over finishline
        playstate.finishLeg();
        assertTrue(gsm.peek() instanceof LeaderboardState);
        
    }



}