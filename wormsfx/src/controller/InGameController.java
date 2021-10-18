package controller;

import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Game;
import model.Rocket;
import model.WormFX;

public class InGameController {

    WormFX activePlayerFX;
    WormFX networkPlayerFX;

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
    private Text player1lifepoints;

    @FXML
    private Text player2lifepoints;

    @FXML
    private Text player1name;

    @FXML
    private Text player2name;

    @FXML
    private ImageView player1icon;

    @FXML
    private ImageView player2icon;

    @FXML
    private Pane borderPane;

    @FXML
    private AnchorPane anchorPane;

    private ServerThread ServerThread;

    private Game activeGame;

    @FXML
    public void initialize() {

        activeGame = Game.getInstance();

        activePlayerFX = new WormFX(activeGame.getServerPlayer(), player1, player1crossfade, player1rocket, player1);
        networkPlayerFX = new WormFX(activeGame.getClientPlayer(), player2, player2crossfade, player2rocket, player2);

        player1name.setText(activeGame.getServerPlayer().getWormName());
        player2name.setText(activeGame.getClientPlayer().getWormName());

        player1lifepoints.setText(String.valueOf(activeGame.getServerPlayer().getLifePoints()));
        player2lifepoints.setText(String.valueOf(activeGame.getClientPlayer().getLifePoints()));

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

    public void walkNetworkClient(int newVal) {
        walkAnimation(networkPlayerFX, newVal);
    }

    public void targetNetworkClient(int newVal) {
        targetAnimation(networkPlayerFX, newVal);
    }

    public void jumpNetworkClient() {
        jumpAnimation(networkPlayerFX);
    }

    public void shootNetworkClient() {
        shootAnimation(networkPlayerFX);
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
            newValRotationAddup = 40;
        }
        else if (newVal < 0 && activePlayerFX.getWormImage().getRotate() == 180) {
            activePlayerFX.getWormImage().setRotate(180);
            newValRotationAddup = -40;
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
        TranslateTransition ttTarget = new TranslateTransition(
                Duration.seconds(0.1), activePlayerFX.getWormImage());

        ttTarget.setFromY(activePlayerFX.getWormImage().getTranslateY());
        ttTarget.setToY(activePlayerFX.getWormImage().getTranslateY()-40);
        ttTarget.setAutoReverse(true);
        ttTarget.play();
    }


    private void shootAnimation(WormFX activePlayerFX) {
        double activeWormX = activePlayerFX.getWormImage().getX()+activePlayerFX.getWormImage().getTranslateX();
        double activeWormY = activePlayerFX.getWormImage().getY()+activePlayerFX.getWormImage().getTranslateY();

        activePlayerFX.getWormRocket().setVisible(true);

        Rocket activeRocket = new Rocket(100, Math.atan(activePlayerFX.getWormTarget().getTranslateY()/40),9.81, borderPane.getHeight()-activeWormY);

        Path path = new Path();
        path.getElements().add(new MoveTo(activeWormX,activeWormY));
        path.getElements().add(new CubicCurveTo(activeWormX,activeWormY, activeWormX+activeRocket.rocketPeakX(), borderPane.getHeight()-activeRocket.rocketPeakY(),0, borderPane.getHeight()));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(activeRocket.rocketTime()));
        pathTransition.setPath(path);
        pathTransition.setNode(activePlayerFX.getWormRocket());
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setOnFinished(event -> activePlayerFX.getWormRocket().setVisible(false));
        pathTransition.play();
    }
}