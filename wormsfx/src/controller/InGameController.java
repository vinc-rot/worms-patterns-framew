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
import model.PeterClient;
import model.Rocket;
import model.WormFX;

public class InGameController implements InGameNetworkInterface{

    WormFX activePlayerFX;
    WormFX serverPlayerFX;
    WormFX clientPlayerFX;

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

    private PeterClient activeClient;

    @FXML
    public void initialize() {

        activeGame = Game.getInstance();
        activeClient = Game.getPeterClientInstance();

        serverPlayerFX = new WormFX(activeGame.getServerPlayer(), player1, player1crossfade, player1rocket, player1);
        clientPlayerFX = new WormFX(activeGame.getClientPlayer(), player2, player2crossfade, player2rocket, player2);

        player1name.setText(activeGame.getServerPlayer().getWormName());
        player2name.setText(activeGame.getClientPlayer().getWormName());

        player1lifepoints.setText(String.valueOf(activeGame.getServerPlayer().getLifePoints()));
        player2lifepoints.setText(String.valueOf(activeGame.getClientPlayer().getLifePoints()));

        if (player1name.getText() != "ServerPlayer" && player2name.getText() == "ClientPlayer") {
            activePlayerFX = serverPlayerFX;
        } else if (player1name.getText() == "ServerPlayer" && player2name.getText() != "ClientPlayer"){
            activePlayerFX = clientPlayerFX;
        }
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
        activeClient.addNextMessage("walk#Client 1");

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

    @Override
    public void update(Object test){
        player1name.setText((String) test);
    }

    public void updateTest(String test){
        player1name.setText(test);
    }

    public void walkNetworkClient(int newVal) {
        walkAnimation(clientPlayerFX, newVal);
    }

    public void targetNetworkClient(int newVal) {
        targetAnimation(clientPlayerFX, newVal);
    }

    public void jumpNetworkClient() {
        jumpAnimation(clientPlayerFX);
    }

    public void shootNetworkClient() {
        shootAnimation(clientPlayerFX);
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