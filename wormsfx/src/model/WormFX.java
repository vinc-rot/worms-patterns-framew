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

}
