package controller;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.Worm;
import model.WormFX;

public class InGameController {

    WormFX player1FX;
    WormFX player2FX;
    WormFX activePlayerFX;

    public InGameController() {

    }

    @FXML
    private ImageView player1;

    @FXML
    private ImageView player2;

    @FXML
    private ImageView player1crossfade;

    @FXML
    private ImageView player2crossfade;

    @FXML
    private ImageView player1rocket;

    @FXML
    private ImageView player2rocket;

    @FXML
    private Pane borderPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        Worm player1Data = new Worm("Hans",100, 200, 100);
        Worm player2Data = new Worm("Hans",50, 200, 100);

        WormFX player1FX = new WormFX(player1Data, player1, player1crossfade, player1rocket, player1);
        WormFX player2FX = new WormFX(player2Data, player2, player2crossfade, player2rocket, player2);

        WormFX activePlayerFX = player1FX;

    }

    @FXML
    private void handleKeyPressed() {
        anchorPane.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                case KP_UP: target(+10); break;
                case DOWN:
                case KP_DOWN: target(-10); break;
                case RIGHT:
                case KP_RIGHT: walk(+20); break;
                case LEFT:
                case KP_LEFT: walk(-20); break;
                case SPACE: shoot(); break;
                case ENTER: jump(); break;
                default: break;
            }
        });
    }

    @FXML
    private void walk(int newVal) {
        walkAnimation(player1, player1crossfade, newVal);
    }

    @FXML
    private void target(int newVal) {
        targetAnimation(player1crossfade, player1crossfade.getTranslateY(), player1crossfade.getTranslateY() + newVal);
    }

    @FXML
    private void jump() {
        jumpAnimation(player1);
    }

    @FXML
    private void shoot() {
        shootAnimation(player1rocket);
    }

    private void walkAnimation(ImageView imgWorm, ImageView imgCrossfade, int newVal) {

        TranslateTransition ttWorm = new TranslateTransition(
                Duration.seconds(0.1), imgWorm);

        TranslateTransition ttCrossfade = new TranslateTransition(
                Duration.seconds(0.1), imgCrossfade);

        ttWorm.setFromX(imgWorm.getTranslateX());
        ttCrossfade.setFromX(imgCrossfade.getTranslateX());

        int newValRotationAddup = 0;

        if (newVal > 0 && imgWorm.getRotate() == 180 ) {
            imgWorm.setRotate(0);
            newValRotationAddup = 40;
            }
        else if (newVal > 0 && imgWorm.getRotate() == 0){
            imgWorm.setRotate(0);
            }
        else if (newVal < 0 && imgWorm.getRotate() == 180) {
            imgWorm.setRotate(180);
        }
        else if (newVal < 0 && imgWorm.getRotate() == 0) {
            imgWorm.setRotate(180);
            newValRotationAddup = -40;
        }

        ttCrossfade.setToX(imgCrossfade.getTranslateX()+newVal+newValRotationAddup);
        ttWorm.setToX(imgWorm.getTranslateX()+newVal);

        ttCrossfade.play();
        ttWorm.play();

    }

    private void targetAnimation(ImageView img, double oldVal, double newVal) {
        TranslateTransition tt = new TranslateTransition(
                Duration.seconds(0.1), img);
        tt.setFromY(oldVal);
        tt.setToY(newVal);
        tt.play();
    }

    private void jumpAnimation(ImageView img) {
        TranslateTransition tt = new TranslateTransition(
                Duration.seconds(0.1), img);
        tt.setFromY(img.getTranslateY());
        tt.setToY(img.getTranslateY() - 25);
        tt.setOnFinished(event -> fallAnimation(img));
        tt.play();
    }

    private void fallAnimation(ImageView img) {
        TranslateTransition tt = new TranslateTransition(
                Duration.seconds(0.1), img);
        tt.setFromY(img.getTranslateY());
        tt.setToY(img.getTranslateY() + 25);
        tt.play();
    }

    private void shootAnimation(ImageView img) {
        Path path = new Path();
        path.getElements().add(new MoveTo(20,20));
        path.getElements().add(new CubicCurveTo(380, 280, 1000, 1000, 600, 600));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(img);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.play();
    }
}
