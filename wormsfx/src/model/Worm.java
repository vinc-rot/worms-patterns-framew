package model;

import javafx.scene.image.Image;

import java.util.Random;

public class Worm {

    public static final int WORM_SKINS = 9;
    private String wormName;
    private int lifePoints = 100;
    private int playerNumber = 0;
    private int wormSkin = 0;
    private Image wormAvatar;
    private boolean changed = false;

    public Worm() {

        setWormSkin(new Random().nextInt(WORM_SKINS));
    }


    public Worm(String wormName) {
        this.wormName = wormName;
        setWormSkin(new Random().nextInt(WORM_SKINS));
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

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
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

    public Image getWormAvatar() {
        return wormAvatar;
    }

    public void setWormAvatar(Image wormAvatar) {
        this.wormAvatar = wormAvatar;
    }
}