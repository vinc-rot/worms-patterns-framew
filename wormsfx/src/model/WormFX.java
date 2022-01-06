package model;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class WormFX {

    private Worm worm;
    private ImageView wormImage;
    private ImageView wormTarget;
    private ImageView wormRocket;
    private ImageView wormLogo;
    private ImageView wormExplosion;
    private Text wormName;
    private Text wormLifePoints;
    private boolean walkActive = false;
    private boolean targetActive = false;
    private boolean rocketActive = false;
    private boolean jumpActive = false;

    public WormFX(){
    };

    public WormFX(Worm worm, ImageView wormImage, ImageView wormTarget, ImageView wormRocket, ImageView wormLogo,
                  ImageView wormExplosion, Text wormName, Text wormLifePoints) {
        this.worm = worm;
        this.wormImage = wormImage;
        this.wormTarget = wormTarget;
        this.wormRocket = wormRocket;
        this.wormLogo = wormLogo;
        this.wormExplosion = wormExplosion;
        this.wormName = wormName;
        this.wormLifePoints = wormLifePoints;
    }

    public Worm getWorm() {
        return worm;
    }

    public void setWorm(Worm worm) {
        this.worm = worm;
    }

    public ImageView getWormImage() {
        return wormImage;
    }

    public void setWormImage(ImageView wormImage) {
        this.wormImage = wormImage;
    }

    public ImageView getWormTarget() {
        return wormTarget;
    }

    public void setWormTarget(ImageView wormTarget) {
        this.wormTarget = wormTarget;
    }

    public ImageView getWormRocket() {
        return wormRocket;
    }

    public void setWormRocket(ImageView wormRocket) {
        this.wormRocket = wormRocket;
    }

    public ImageView getWormLogo() {
        return wormLogo;
    }

    public void setWormLogo(ImageView wormLogo) {
        this.wormLogo = wormLogo;
    }

    public ImageView getWormExplosion() {
        return wormExplosion;
    }

    public void setWormExplosion(ImageView wormExplosion) {
        this.wormExplosion = wormExplosion;
    }

    public Text getWormName() {
        return wormName;
    }

    public void setWormName(Text wormName) {
        this.wormName = wormName;
    }

    public Text getWormLifePoints() {
        return wormLifePoints;
    }

    public void setWormLifePoints(Text wormLifePoints) {
        this.wormLifePoints = wormLifePoints;
    }

    public boolean isWalkActive() {
        return walkActive;
    }

    public void setWalkActive(boolean walkActive) {
        this.walkActive = walkActive;
    }

    public boolean isTargetActive() {
        return targetActive;
    }

    public void setTargetActive(boolean targetActive) {
        this.targetActive = targetActive;
    }

    public boolean isRocketActive() {
        return rocketActive;
    }

    public void setRocketActive(boolean rocketActive) {
        this.rocketActive = rocketActive;
    }

    public boolean isJumpActive() {
        return jumpActive;
    }

    public void setJumpActive(boolean jumpActive) {
        this.jumpActive = jumpActive;
    }
}
