package model;

import controller.InGameController;

public class Game {

    private Worm loggedInPlayer;

    private Worm serverPlayer;

    private Worm clientPlayer;

    private String networkIP;

    private int networkPort;

    private static Game gameInstance;

    private static InGameController inGameControllerInstance;

    private Game() {
    }

    public static Game getInstance() {
        if (gameInstance == null)
            gameInstance = new Game();
        return gameInstance;
    }

    public Worm getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public void setLoggedInPlayer(Worm loggedInPlayer) {
        this.loggedInPlayer = loggedInPlayer;
    }

    public Worm getServerPlayer() {
        return serverPlayer;
    }

    public void setServerPlayer(Worm serverPlayer) {
        this.serverPlayer = serverPlayer;
    }

    public Worm getClientPlayer() {
        return clientPlayer;
    }

    public void setClientPlayer(Worm clientPlayer) {
        this.clientPlayer = clientPlayer;
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
}

