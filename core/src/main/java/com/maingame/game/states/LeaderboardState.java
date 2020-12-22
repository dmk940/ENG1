package com.maingame.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maingame.game.MainGame;
import com.maingame.game.sprites.Boat;

import java.util.ArrayList;
import java.util.List;

/**
 * Shows a leaderboard with the boats ranked according to how much time they took.
 */
public class LeaderboardState extends State{
    private final Texture background;
    private List<Boat> boatsInOrder; // A list of boats ordered by their total time in laps.
    private final BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"),false); // a font to draw text
    private final Boat player;
    private final int leg;

    public LeaderboardState(GameStateManager gsm, int leg, List<Boat> boats, Boat player) {
        super(gsm);
        background = new Texture("background.png");
        this.player = player;
        this.leg = leg;
        buildBoatsInOrder(boats);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
            moveToNewState(leg);
        }
        if(Gdx.input.justTouched()) {
            moveToNewState(leg);
        }
    }

    /**
     * {@inheritDoc}
     * @param dt the time between each start of a render()
     */
    @Override
    public void update(float dt) {
        handleInput();
    }

    /**
     * {@inheritDoc}
     * @param sb a batch for drawing objects
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        cam.setToOrtho(false,MainGame.WIDTH,MainGame.HEIGHT);
        sb.setProjectionMatrix(cam.combined);
        sb.draw(background, 0, 0, MainGame.WIDTH , MainGame.HEIGHT);
        if (leg != 4) {
            font.draw(sb, "Leg " + leg +" Leaderboard",(float) MainGame.WIDTH/2-250, 700);
        }else {
            font.draw(sb, "Final Leaderboard",(float) MainGame.WIDTH/2-250, 700);
        }
        for (int i = 0; i < boatsInOrder.size(); i++){
            sb.draw(boatsInOrder.get(i).images.get(0), (float) MainGame.WIDTH/4 + 100,500 - (float) (i * 120),125,125);
            font.draw(sb,boatsInOrder.get(i).getCumulativeLegTime() + "s",(float) MainGame.WIDTH/2 + 100,600  - (float)(i * 120));
        }
        sb.end();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
    }

    /**
     * Oragnises the boats using their time.
     * @param boats a list of boats
     * @see Boat#getCumulativeLegTime()
     */
    private void buildBoatsInOrder(List<Boat> boats) {
        List<Boat> newList = new ArrayList<>(boats);
        newList.add(player);
        boatsInOrder = new ArrayList<>();
        while (!newList.isEmpty()) {
            Boat highest = null;
            for (Boat boat:newList) {

                if (highest == null) {
                    highest = boat;
                }else if (boat.getCumulativeLegTime() < highest.getCumulativeLegTime()) {
                    highest = boat;
                }
            }
            boatsInOrder.add(boatsInOrder.size(),highest);
            newList.remove(highest);
        }
    }

    /**
     * Transitions to a different state depending on the current leg
     * @param leg the current leg
     * @see GameStateManager#set(State)
     */
    private void moveToNewState(int leg) {
        if (leg == 4) {
            gsm.set(new WelcomeState(gsm));
        }else if (leg == 3) {
            boatsInOrder = boatsInOrder.subList(0,3);
            boolean gameOver = true;
            for (Boat boat: boatsInOrder) {
                if (boat == player) {
                    gameOver = false;
                    break;
                }
            }
            if (gameOver) {
                gsm.set(new GameOverSpeed(gsm));
            }else {
                // resets the boat attributes
                for (Boat boat:boatsInOrder) {
                    boat.setPosY(0);
                    boat.setHealth(100);
                    boat.setFatigue(300);
                    boat.setTotalLegTime(0);
                }
                boatsInOrder.remove(player);
                gsm.set(new PlayState(gsm,boatsInOrder,player,leg + 1));
            }
        }else {
            // resets the boat attributes
            for (Boat boat:boatsInOrder) {
                boat.setPosY(0);
                boat.setHealth(100);
                boat.setFatigue(300);
                boat.setTotalLegTime(0);
            }
            boatsInOrder.remove(player);
            gsm.set(new PlayState(gsm,boatsInOrder,player,leg + 1));
        }
    }
}
