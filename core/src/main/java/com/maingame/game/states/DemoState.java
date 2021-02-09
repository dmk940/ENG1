package com.maingame.game.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maingame.game.MainGame;
import com.maingame.game.sprites.Boat;

/**
 * Automates the PlayState, in the demo mode. Driver class.
 */
public class DemoState extends State {

    private final Texture background;
    private final Random generator = new Random();
    protected List<Boat> boats = new ArrayList<>();
    //protected int x = generator.nextInt(6);
    protected int x = 0;
    Boat playerBoat;

    public DemoState(GameStateManager gsm) {
        super(gsm);
        buildBoats();
        background = new Texture("loading.png");
        playerBoat = boats.get(x);
        boats.remove(x);
        boats = playStateBoats();
    }

    private void buildBoats() {
        boats.add(new Boat("red"));
        boats.add(new Boat("pink"));
        boats.add(new Boat("blue"));
        boats.add(new Boat("yellow"));
        boats.add(new Boat("orange"));
        boats.add(new Boat("green"));
        boats.add(new Boat("purple"));
    }

    protected List<Boat> playStateBoats() {
        List<Boat> output = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Boat boat = this.boats.get(generator.nextInt(this.boats.size() - 1));
            output.add(boat);
            this.boats.remove(boat);
        }
        return output;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, MainGame.WIDTH , MainGame.HEIGHT);
        sb.end();
    }

    @Override
    public void dispose() {}

    @Override
    public void handleInput() {}

    @Override
    public void update(float dt) {
        gsm.set(new DemoPlayState(gsm, boats, playerBoat, 1, 0));

    }
}