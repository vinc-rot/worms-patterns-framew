package model;

import javafx.scene.image.ImageView;

public class WormFX {

    private Worm wormData;
    private ImageView wormImage;
    private ImageView wormTarget;
    private ImageView wormRocket;
    private ImageView wormLogo;

    public WormFX(Worm wormData, ImageView wormImage, ImageView wormTarget, ImageView wormRocket, ImageView wormLogo) {
        this.wormData = wormData;
        this.wormImage = wormImage;
        this.wormTarget = wormTarget;
        this.wormRocket = wormRocket;
        this.wormLogo = wormLogo;
    }

    public Worm getWormData() {
        return wormData;
    }

    public void setWormData(Worm wormData) {
        this.wormData = wormData;
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


}
