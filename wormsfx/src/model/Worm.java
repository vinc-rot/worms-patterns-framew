package model;

public class Worm {

    private String wormName;
    private int lifePoints;
    private int posX;
    private int posY;

    /**
     * creates a worm with the given data.
     * @param wormName Name of Worm
     * @param lifePoints Life Points of Worm
     * @param posX x-Position of Worm
     * @param posY y-Position of Worm
     */

    public Worm (String wormName, int lifePoints, int posX, int posY) {
        this.wormName = wormName;
        this.posX = posX;
        this.posY = posY;
        this.lifePoints = lifePoints;
    }
}