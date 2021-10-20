package model;

import java.util.Random;

public class Worm {

    public static final int WORM_SKINS = 23;
    private String wormName;
    private int lifePoints;
    private int posX;
    private int posY;
    private int wormSkin = 0;
    private boolean changed = false;


    public Worm(String wormName) {
        this.wormName = wormName;
        setWormSkin(new Random().nextInt(WORM_SKINS));
    }

    /**
     * creates a worm with the given data.
     *
     * @param wormName   Name of Worm
     * @param lifePoints Life Points of Worm
     * @param posX       x-Position of Worm
     * @param posY       y-Position of Worm
     */

    public Worm(String wormName, int lifePoints, int posX, int posY, int skin) {
        this.wormName = wormName;
        this.posX = posX;
        this.posY = posY;
        this.lifePoints = lifePoints;
        setWormSkin(skin);
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

    public int getWormSkin() {
        return wormSkin;
    }

    /**
     * @param wormSkin Legen Sie den Skin des Wurms fest
     */
    private void setWormSkin(int wormSkin) {
        if (wormSkin == 100) {
            this.wormSkin = 100;
        }
        else if (wormSkin >= WORM_SKINS)
            throw new IllegalArgumentException(String.format("Angegebene Skin-Nummer [%d] Über dem Limit",wormSkin));
        else if (wormSkin < 0)
            throw new IllegalArgumentException(String.format("Angegebene Skin-Nummer [%d] Negativ", wormSkin));

        this.wormSkin = wormSkin;
    }
}