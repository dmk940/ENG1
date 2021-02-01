package com.maingame.game.states;

import com.maingame.game.sprites.Boat;
import com.maingame.game.sprites.Obstacle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SaveInfo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    protected List<Boat> boats; 
    protected int leg;
    private long time;
    protected Boat player;
    protected List<Obstacle> obstacleList = new ArrayList<>(); // a list containing all the obstacles.
    private int playerPosX;
    private int playerPosY;
    private int penalty;
    private int health;
    private int fatigue;

    public void SaveInfo(int leg, Boat player, int time) {
        this.leg = leg;
        this.player = player;
        this.playerPosX = player.getPosX();
        this.playerPosY = player.getPosY();
        this.health = player.getHealth();
        this.fatigue = player.getFatigue();
        this.penalty = player.getPenaltyBar();
        this.time = player.getTimePenalty();
    }
        
    public class ObstacleInfo{
        List<Obstacle> obstacleList = new ArrayList<>();
    }
    public void ObstacleInfo(){
        this.obstacle = obstacle;
    }
    public class AIboatInfo{
        
    }
    public void AIboatInfo(){

    }
}