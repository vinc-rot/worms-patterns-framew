package model;

public class Worm {

    private String wormName;
    private int lifePoints;
    private int posX;
    private int posY;

    public Worm() {
    }

    /**
     * creates a worm with the given data.
     *
     * @param wormName   Name of Worm
     * @param lifePoints Life Points of Worm
     * @param posX       x-Position of Worm
     * @param posY       y-Position of Worm
     */

    public Worm(String wormName, int lifePoints, int posX, int posY) {
        this.wormName = wormName;
        this.posX = posX;
        this.posY = posY;
        this.lifePoints = lifePoints;
    }

    public String getWormName() {
        return wormName;
    }

    public void setWormName(String wormName) {
        this.wormName = wormName;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}