package model;

import controller.InGameController;

public class Game {

    private Worm serverPlayerWorm;

    private Worm clientPlayerWorm;

    private String networkIP;

    private int networkPort;

    private boolean gameIsRunning = false;

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
}

