package com.maingame.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.maingame.game.sprites.Boat;
import com.maingame.game.sprites.Obstacle;

import java.util.ArrayList;
import java.util.List;

/** A class to encapsulate the necessary information to save the playstate to file. */
public class SaveInfo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    protected List<Boat> boats;
    protected Boat player;
    long time;
    protected int difficulty;
    protected List<Obstacle> obstacleList = new ArrayList<>(); // a list containing all the obstacles
    protected int leg;
    protected Rectangle finishLineBounds;
    protected int finishLinePosition;
    protected int distanceTravelled;

    public SaveInfo(List<Boat> boats_, Boat player_, int leg_, List<Obstacle> obstacleList_, long time_, 
                    Rectangle finishLineBounds_, int finishLinePosition_, int difficulty_, 
                    int distanceTravelled_) {
        this.leg = leg_;
        this.player = player_;
        this.boats = boats_;
        this.obstacleList = obstacleList_;
        this.time = time_;
        this.finishLineBounds = finishLineBounds_;
        this.finishLinePosition = finishLinePosition_;
        this.difficulty = difficulty_;
        this.distanceTravelled = distanceTravelled_;
    }
}