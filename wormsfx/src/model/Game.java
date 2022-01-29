package model;

import controller.InGameController;
import javafx.scene.image.Image;

public class Game {

    private Worm serverPlayerWorm;

    private Worm clientPlayerWorm;

    private String networkIP;

    private int networkPort;

    private Image selectedMap;

    private int backgroundID;

    private HighScoreList highScoreList = new HighScoreList();

    private boolean gameIsRunning = false;

    private boolean gameWon = false;

    private static Game gameInstance;

    private static InGameController inGameControllerInstance;

    private static Client clientInstance;

    private Game() {
    }

    public static Game getInstance() {
        if (gameInstance == null)
            gameInstance = new Game();
        return gameInstance;
    }

    public Worm getServerPlayerWorm() {
        return serverPlayerWorm;
    }

    public void setServerPlayerWorm(Worm serverPlayerWorm) {
        this.serverPlayerWorm = serverPlayerWorm;
    }

    public Worm getClientPlayerWorm() {
        return clientPlayerWorm;
    }

    public void setClientPlayerWorm(Worm clientPlayerWorm) {
        this.clientPlayerWorm = clientPlayerWorm;
    }

    public String getNetworkIP() { return networkIP; }

    public void setNetworkIP(String networkIP) { this.networkIP = networkIP;  }

    public int getNetworkPort() { return networkPort; }

    public void setNetworkPort(int networkPort) { this.networkPort = networkPort; }

    public static InGameController getInGameControllerInstance() {
        return inGameControllerInstance;
    }

    public static void setInGameControllerInstance(InGameController inGameControllerInstance) {
        Game.inGameControllerInstance = inGameControllerInstance;
    }

    public static Client getPeterClientInstance() {
        return clientInstance;
    }

    public static void setPeterClientInstance(Client clientInstance) {
        Game.clientInstance = clientInstance;
    }

    public boolean isGameIsRunning() {
        return gameIsRunning;
    }

    public void setGameIsRunning(boolean gameIsRunning) {
        this.gameIsRunning = gameIsRunning;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    public Image getSelectedMap() {
        return selectedMap;
    }

    public void setSelectedMap(Image selectedMap) {
        this.selectedMap = selectedMap;
    }

    public HighScoreList getHighScoreList() {
        return highScoreList;
    }

    public void setHighScoreList(HighScoreList highScoreList) {
        this.highScoreList = highScoreList;
    }

    public int getBackgroundID() { return backgroundID; }

    public void setBackgroundID(int backgroundID) { this.backgroundID = backgroundID; }

}

