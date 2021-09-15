package model;

public class Game {

    private int levelMap;
    private int roundTime;
    private Worm player1;
    private Worm player2;

    public Game(int levelMap, int roundTime, Worm player1, Worm player2) {
        this.levelMap = levelMap;
        this.roundTime = roundTime;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int getLevelMap() {
        return levelMap;
    }

    public void setLevelMap(int levelMap) {
        this.levelMap = levelMap;
    }

    public int getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(int roundTime) {
        this.roundTime = roundTime;
    }

    public Worm getPlayer1() {
        return player1;
    }

    public void setPlayer1(Worm player1) {
        this.player1 = player1;
    }

    public Worm getPlayer2() {
        return player2;
    }

    public void setPlayer2(Worm player2) {
        this.player2 = player2;
    }
}

