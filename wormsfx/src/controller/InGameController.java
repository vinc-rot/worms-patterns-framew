package controller;

import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
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

        activePlayerFX = player1FX;

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
        walkAnimation(activePlayerFX, newVal);
    }

    @FXML
    private void target(int newVal) {
        targetAnimation(activePlayerFX, newVal);
    }

    @FXML
    private void jump() {
        jumpAnimation(activePlayerFX);
    }

    @FXML
    private void shoot() {
        shootAnimation(activePlayerFX);
    }

    private void walkAnimation(WormFX activePlayerFX, int newVal) {

        TranslateTransition ttWorm = new TranslateTransition(
                Duration.seconds(0.1),
                activePlayerFX.getWormLogo());

        TranslateTransition ttTarget = new TranslateTransition(
                Duration.seconds(0.1),
                activePlayerFX.getWormTarget());

        ttWorm.setFromX(activePlayerFX.getWormImage().getTranslateX());
        ttTarget.setFromX(activePlayerFX.getWormTarget().getTranslateX());

        int newValRotationAddup = 0;

        if (newVal > 0 && activePlayerFX.getWormImage().getRotate() == 180 ) {
            activePlayerFX.getWormImage().setRotate(0);
            newValRotationAddup = 40;
            }
        else if (newVal > 0 && activePlayerFX.getWormImage().getRotate() == 0){
            activePlayerFX.getWormImage().setRotate(0);
            }
        else if (newVal < 0 && activePlayerFX.getWormImage().getRotate() == 180) {
            activePlayerFX.getWormImage().setRotate(180);
        }
        else if (newVal < 0 && activePlayerFX.getWormImage().getRotate() == 0) {
            activePlayerFX.getWormImage().setRotate(180);
            newValRotationAddup = -40;
        }

        ttTarget.setToX(activePlayerFX.getWormImage().getTranslateX()+newVal+newValRotationAddup);
        ttWorm.setToX(activePlayerFX.getWormImage().getTranslateX()+newVal);

        ttTarget.play();
        ttWorm.play();

    }

    private void targetAnimation(WormFX activePlayerFX, int newVal) {

        TranslateTransition ttTarget = new TranslateTransition(
                Duration.seconds(0.1), activePlayerFX.getWormTarget());

        ttTarget.setFromY(activePlayerFX.getWormTarget().getTranslateY());
        ttTarget.setToY(activePlayerFX.getWormTarget().getTranslateY()+newVal);
        ttTarget.play();
    }

    private void jumpAnimation(WormFX activePlayerFX) {

        TranslateTransition tt1 = new TranslateTransition(
                Duration.seconds(0.1), activePlayerFX.getWormImage());
        tt1.setFromY(activePlayerFX.getWormImage().getTranslateY());
        tt1.setFromX(activePlayerFX.getWormImage().getTranslateX());
        tt1.setToX(activePlayerFX.getWormImage().getTranslateY()-50);
        tt1.setToY(activePlayerFX.getWormImage().getTranslateX()+50);

        TranslateTransition tt2 = new TranslateTransition(
                Duration.seconds(0.1), activePlayerFX.getWormImage());
        tt2.setFromY(activePlayerFX.getWormImage().getTranslateY()-50);
        tt2.setFromX(activePlayerFX.getWormImage().getTranslateX()+50);
        tt2.setToY(activePlayerFX.getWormImage().getTranslateY());
        tt2.setToX(activePlayerFX.getWormImage().getTranslateX()+50);

        SequentialTransition st = new SequentialTransition();
        st.getChildren().addAll(
                tt1,
                tt2
                );

        st.play();

    }


    private void shootAnimation(WormFX activePlayerFX) {
        Path path = new Path();
        path.getElements().add(new MoveTo(20,20));
        path.getElements().add(new CubicCurveTo(380, 280, 1000, 1000, 600, 600));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(activePlayerFX.getWormRocket());
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.play();
    }
}
