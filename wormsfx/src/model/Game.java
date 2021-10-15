package model;

import controller.InGameController;

public class Game {

    private Worm activePlayer;

    private Worm networkPlayer;

    private static Game gameInstance;

    private static InGameController inGameControllerInstance;

    private Game() {
    }

    public static Game getInstance() {
        if (gameInstance == null)
            gameInstance = new Game();
        return gameInstance;
    }

    public Worm getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Worm activePlayer) {
        this.activePlayer = activePlayer;
    }

    public Worm getNetworkPlayer() {
        return networkPlayer;
    }

    public void setNetworkPlayer(Worm networkPlayer) {
        this.networkPlayer = networkPlayer;
    }

    public static InGameController getInGameControllerInstance() {
        return inGameControllerInstance;
    }

    public static void setInGameControllerInstance(InGameController inGameControllerInstance) {
        Game.inGameControllerInstance = inGameControllerInstance;
    }
}

