package model;

public class Game {

    private int levelMap;
    private int roundTime;
    private int wormsPerPlayer;
    private int initialLifePoints;
    private int teams;

    public Game(int levelMap, int roundTime, int wormsPerPlayer, int initialLifePoints, int teams) {
        this.levelMap = levelMap;
        this.roundTime = roundTime;
        this.wormsPerPlayer = wormsPerPlayer;
        this.initialLifePoints = initialLifePoints;
        this.teams = teams;
    }

}

