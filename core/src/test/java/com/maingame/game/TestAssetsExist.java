package com.maingame.game;

import static org.junit.Assert.assertTrue;
import com.maingame.UnitTestInit;
import org.junit.Test;

/**
 * Tests that all required asset files are present.
 */
public class TestAssetsExist extends UnitTestInit {

    static final String[] assets = {"arrows.png", "backButton.png", "background.png", "Boat1.1.png", 
    "Boat1.2.png", "Boat2.1.png", "Boat2.2.png", "Boat3.1.png", "Boat3.2.png", "Boat4.1.png", 
    "Boat4.2.png", "Boat5.1.png", "Boat5.2.png", "Boat6.1.png", "Boat6.2.png", "Boat7.1.png", 
    "Boat7.2.png", "branch.png", "demo.png", "duck1.png", "duck2.png", "easy.png", "easyClicked.png", 
    "fatigueUp.png", "fatigueUp2.png", "finishLine.png", "font.fnt", "font.png", "gameOver.png", 
    "goose.png", "hard.png", "hardClicked.png", "healthUp.png", "healthUp2.png", "leftArrow.png", 
    "medium.png", "mediumClicked.png", "play.png", "rightArrow.png", "river.png", "river_reversed.png", 
    "rock1.png", "rock2.png", "speedUp.png", "synth_pop_game.mp3", "title.png", "wasd.png", "youWon.png"
};

    /** Tests that all required asset files are present.
     */
    @Test
    public void allAssetsPresent() {
        for (String asset : assets) {
            assertTrue("could not find asset " + asset,
            app.getFiles().internal("assets/" + asset).exists());
        }
    }
}
