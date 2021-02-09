package com.maingame.game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.maingame.game.sprites.Boat;
import com.maingame.game.states.GameOverSpeed;
import com.maingame.game.states.GameStateManager;
import com.maingame.game.states.LeaderboardState;
import com.maingame.game.states.PlayState;
import com.maingame.game.states.WinState;

/**
 * Tests the flow of execution of the game, i.e. screens
 * appear and disappear correctly upon specific events
 * (gameover, next leg...).
 */
public class TestScreensGameFlow extends TestMovement {

    Boat nonPlayer;

    public void initFullGame(int leg) {
        player = new Boat("pink");
        nonPlayer = new Boat("red");
        boats.add(nonPlayer);
        boats.add(new Boat("yellow"));
        boats.add(new Boat("purple"));
        boats.add(player);

        gsm = new GameStateManager();
        playstate = new PlayState(gsm, boats, player, leg, 0);
    }

    /** Tests that the GameOverSpeed state is switched to in the third
     *  leg if the user does not finish in the top 3.
     */
    @Test
    public void testGameOverSpeedLeg3() {
        initFullGame(3);
        nonPlayer.setPosY(100);
        playstate.isLegOver();
        float counter = 30f;
        for (Boat boat : boats) {
            if (boat != player) {
                boat.setPosY(1000);
                boat.setTotalLegTime(counter);
                boat.setHasLost(false);
                counter = counter - 1;
            }
        }
        // The nonPlayer is the winner
        nonPlayer.setTotalLegTime(1.0f);
        player.setPosY(playstate.finishLinePosition+100000);
        player.setTotalLegTime(31.0f);
        player.setHasLost(false);
        playstate.finishLeg();
        assertTrue("Leaderboard failed, was " + gsm.peek().getClass(), gsm.peek() instanceof LeaderboardState);
        ((LeaderboardState) gsm.peek()).moveToNewState(3);
        assertTrue("GameOver failed, was" + gsm.peek().getClass(), gsm.peek() instanceof GameOverSpeed);

    }

    /** Test that the WinState is reached in the 4th leg when the player
     * is in first place.
     */
    @Test
    public void testWin() {
        initFullGame(4);
        player.setPosY(100);
        playstate.isLegOver();
        float counter = 30;
        for (Boat boat : boats) {
            if (boat != player) {
                boat.setPosY(1000);
                boat.setTotalLegTime(counter);
                boat.setHasLost(false);
                counter += 1;
            }
        }
        player.setPosY(playstate.finishLinePosition+100000);
        player.setTotalLegTime(1.0f);
        playstate.finishLeg();
        assertTrue("Leaderboard failed, was "+ gsm.peek().getClass(), gsm.peek() instanceof LeaderboardState);
        ((LeaderboardState) gsm.peek()).moveToNewState(4);
        assertTrue("Winstate failed, was "+gsm.peek().getClass(), gsm.peek() instanceof WinState);

    }

    //public void testMainMenuBoatSelect() {}
    // This is manually tested as it would require significant rewrites to become automatically testable.

    /** Test that when the player hits the finish line, and all boats have finished,
     *  the game enters the leaderboard state. */
    @Test
    public void testFinishLine() {
        createPlayer();
        boats.remove(0); // remove non-player boat so haveBoatsFinished will be true
        playstate.isLegOver();
        player.setPosY(playstate.finishLinePosition+100); // ensure definitely over finishline
        playstate.finishLeg();
        assertTrue("Leaderboard failed, was "+gsm.peek().getClass(),gsm.peek() instanceof LeaderboardState);
        
    }

    /** Test that the boat statistics (time penalty, health, fatigue, position, leg time) 
     * reset at the end of a leg.
     */
    @Test
    public void testEndOfLegStatsReset() {
        initFullGame(3);
        nonPlayer.setPosY(100);
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
        nonPlayer.setTotalLegTime(1.0f);
        player.setPosY(playstate.finishLinePosition+100000);
        player.setTotalLegTime(20.0f); // player is second
        player.setHasLost(false);
        playstate.finishLeg();
        // these should be constants but as they're only used once in this file it's fine
        ((LeaderboardState) gsm.peek()).moveToNewState(3);
        assertTrue("PlayState failed, was"+gsm.peek().getClass(), gsm.peek() instanceof PlayState);
        assertEquals("time penalty was not 0", 0, player.getTimePenalty());
        assertEquals("Health was not reset to 100", 100, player.getHealth());
        assertEquals("Fatigue was not reset to 600", 600, player.getFatigue());
        assertEquals("YPos was not reset to 0", 0, player.getPosY());
        assertEquals("Total leg time was not reset to 0", 0f, player.getTotalLegTime(), 0.1);

    }

    //public void testDemo() {}
    // This is also best manually tested.


}