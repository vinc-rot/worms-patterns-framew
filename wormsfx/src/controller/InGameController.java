package controller;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

import java.util.EventObject;
import java.util.StringTokenizer;

public class InGameController implements NetworkInterface {

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
    private ImageView player1explosion;

    @FXML
    private ImageView player2explosion;

    @FXML
    private Text player1lifepoints;

    @FXML
    private Text player2lifepoints;

    @FXML
    private Text player1name;

    @FXML
    private Text player2name;

    @FXML
    private Text gameMessage;

    @FXML
    private ImageView player1icon;

    @FXML
    private ImageView player2icon;

    @FXML
    private Pane borderPane;

    @FXML
    private AnchorPane anchorPane;

    private Game activeGame;

    private Client activeClient;

    private WormFX activePlayerFX;
    private WormFX networkPlayerFX;

    @FXML
    public void initialize() {

        // Client + Game Instanzen werden initialisiert
        activeGame = Game.getInstance();
        activeClient = Game.getPeterClientInstance();

        // Zusammenfassen der JavaFX-Objekte für Player 1 + 2
        WormFX player1FX = new WormFX(new Worm(), player1, player1crossfade, player1rocket,
                player1, player1explosion, player1name, player1lifepoints);

        WormFX player2FX = new WormFX(new Worm(), player2, player2crossfade, player2rocket,
                player2, player2explosion, player2name, player2lifepoints);

        // Warten auf den Netzwerkspieler
        if (activeGame.getClientPlayerWorm().getPlayerNumber() == 1)
        {
            gameMessage.setText("[S] für Synchronisierung ");

            activePlayerFX = player1FX;
            networkPlayerFX = player2FX;

        }
        else
        {
            gameMessage.setText("[S] für Synchronisierng ");

            activePlayerFX = player2FX;
            networkPlayerFX = player1FX;

        }

        activePlayerFX.setWorm(activeGame.getClientPlayerWorm());
        networkPlayerFX.setWorm(activeGame.getServerPlayerWorm());

        setPlayerName(activePlayerFX, activePlayerFX.getWorm().getWormName());
        setLifePoints(activePlayerFX, activePlayerFX.getWorm().getLifePoints());

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
                case S: syncGame(); break;
                case ESCAPE: quit(); break;
                default: break;
            }
        });
    }

    @Override
    public void update(Object incomingMessage){

        System.out.println("CLIENT: " + (String) incomingMessage);

        StringTokenizer st = new StringTokenizer((String) incomingMessage, "#");
        String msgType = st.nextToken();

        switch(msgType) {
            case "walk":  walkAnimation(networkPlayerFX, Integer.parseInt(st.nextToken())); break;
            case "target":  targetAnimation(networkPlayerFX, Integer.parseInt(st.nextToken())); break;
            case "jump":  jumpAnimation(networkPlayerFX); break;
            case "shoot": shootAnimation(networkPlayerFX); break;
            case "lifepoints": setLifePoints(networkPlayerFX, Integer.parseInt(st.nextToken()));break;
            case "playername": setPlayerName(networkPlayerFX, st.nextToken()); break;
            case "playerskin": break;
            default: break;
        }

    }

    @FXML
    private void walk(int newVal) {
        if (activeGame.isGameIsRunning() & !activePlayerFX.isWalkActive()) {
            activePlayerFX.setWalkActive(true);
            walkAnimation(activePlayerFX, newVal);
            activeClient.addNextMessage("walk#" + newVal + "@player" + activeGame.getServerPlayerWorm().getPlayerNumber());
        }
    }

    @FXML
    private void target(int newVal) {
        if (activeGame.isGameIsRunning() & !activePlayerFX.isTargetActive()) {
            activePlayerFX.setTargetActive(true);
            targetAnimation(activePlayerFX, newVal);
            activeClient.addNextMessage("target#" + newVal + "@player" + activeGame.getServerPlayerWorm().getPlayerNumber());
        }
    }

    @FXML
    private void jump() {
        if (activeGame.isGameIsRunning() & !activePlayerFX.isTargetActive()) {
            activePlayerFX.setJumpActive(true);
            jumpAnimation(activePlayerFX);
            activeClient.addNextMessage("jump#" + " " + "@player" + activeGame.getServerPlayerWorm().getPlayerNumber());
        }
    }

    @FXML
    private void shoot() {
        if (activeGame.isGameIsRunning() & !activePlayerFX.isRocketActive()) {
            activePlayerFX.setRocketActive(true);
            shootAnimation(activePlayerFX);
            activeClient.addNextMessage("shoot#" + " " + "@player" + activeGame.getServerPlayerWorm().getPlayerNumber());
        }
    }

    private void walkAnimation(WormFX playerFX, int newVal) {
        TranslateTransition ttWorm = new TranslateTransition(
                Duration.seconds(0.1),
                playerFX.getWormLogo());

        TranslateTransition ttTarget = new TranslateTransition(
                Duration.seconds(0.1),
                playerFX.getWormTarget());

        ttWorm.setFromX(playerFX.getWormImage().getTranslateX());
        ttTarget.setFromX(playerFX.getWormTarget().getTranslateX());

        int newValRotationAddup = 0;

        if (newVal > 0 && playerFX.getWormImage().getRotate() == 180 ) {
            playerFX.getWormImage().setRotate(0);
            newValRotationAddup = 40;
        }
        else if (newVal > 0 && playerFX.getWormImage().getRotate() == 0){
            playerFX.getWormImage().setRotate(0);
            newValRotationAddup = 40;
        }
        else if (newVal < 0 && playerFX.getWormImage().getRotate() == 180) {
            playerFX.getWormImage().setRotate(180);
            newValRotationAddup = -40;
        }
        else if (newVal < 0 && playerFX.getWormImage().getRotate() == 0) {
            playerFX.getWormImage().setRotate(180);
            newValRotationAddup = -40;
        }

        ttTarget.setToX(playerFX.getWormImage().getTranslateX()+newVal+newValRotationAddup);
        ttWorm.setToX(playerFX.getWormImage().getTranslateX()+newVal);
        ttWorm.setOnFinished(event -> playerFX.setWalkActive(false));

        ttTarget.play();
        ttWorm.play();

    }

    private void targetAnimation(WormFX playerFX, int newVal) {
        TranslateTransition ttTarget = new TranslateTransition(
                Duration.seconds(0.1), playerFX.getWormTarget());

        ttTarget.setFromY(playerFX.getWormTarget().getTranslateY());
        ttTarget.setToY(playerFX.getWormTarget().getTranslateY()+newVal);

        ttTarget.setOnFinished(event -> playerFX.setTargetActive(false));

        ttTarget.play();
    }


    private void jumpAnimation(WormFX playerFX) {
        TranslateTransition ttTarget = new TranslateTransition(
                Duration.seconds(0.1), playerFX.getWormImage());

        ttTarget.setFromY(playerFX.getWormImage().getTranslateY());
        ttTarget.setToY(playerFX.getWormImage().getTranslateY()-40);
        ttTarget.setAutoReverse(true);
        ttTarget.setOnFinished(event -> playerFX.setJumpActive(false));
        ttTarget.play();
    }


    private void shootAnimation(WormFX playerFX) {
        double activeWormX = playerFX.getWormImage().getX()+playerFX.getWormImage().getTranslateX();
        double activeWormY = playerFX.getWormImage().getY()+playerFX.getWormImage().getTranslateY();
        int shootingDirection = 1;

        playerFX.getWormRocket().setVisible(true);

        Rocket activeRocket = new Rocket(100, Math.abs(Math.atan(playerFX.getWormTarget().getTranslateY()/40)),
                9.81, borderPane.getHeight()-activeWormY);

/*        System.out.println("Angle:" + Math.abs(Math.atan(playerFX.getWormTarget().getTranslateY()/40)) + " ,X_Peak: " +
                activeRocket.rocketPeakX() + " ,Y_Peak: " + activeRocket.rocketPeakY() + " " +
                ",X_End:" + activeRocket.rocketEndX() );*/

        if (playerFX.getWormImage().getRotate() == 180)
            shootingDirection = -1;

        double bezierPointX = activeWormX + activeRocket.rocketPeakX()*shootingDirection;
        double bezierPointY = borderPane.getHeight() - activeRocket.rocketBezierY();

        FadeTransition ftExplosion = new FadeTransition(
                Duration.seconds(1.0), playerFX.getWormExplosion());
        ftExplosion.setFromValue(1.0);
        ftExplosion.setToValue(0.0);

        Path path = new Path();
        path.getElements().add(new MoveTo(activeWormX,activeWormY));
        path.getElements().add(new CubicCurveTo(bezierPointX, bezierPointY,
                bezierPointX, bezierPointY, activeWormX + activeRocket.rocketEndX()*shootingDirection, borderPane.getHeight()));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(activeRocket.rocketTime()*100));
        pathTransition.setPath(path);
        pathTransition.setNode(playerFX.getWormRocket());
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setOnFinished(event -> { playerFX.getWormRocket().setVisible(false);
                                                playerFX.setRocketActive(false);
                                                playerFX.getWormExplosion().setVisible(true);
                                                ftExplosion.play();}
                                                );
        pathTransition.play();
    }

    private void setLifePoints(WormFX playerFX, int newVal) {
        playerFX.getWorm().setLifePoints(newVal);
        playerFX.getWormLifePoints().setText(String.valueOf(newVal));
    }

    private void setPlayerName(WormFX playerFX, String newName) {
        playerFX.getWorm().setWormName(newName);
        playerFX.getWormName().setText(String.valueOf(newName));
    }

    public void syncGame()
    {
        gameMessage.setText("");
        activeClient.addNextMessage("lifepoints#" + activePlayerFX.getWorm().getLifePoints() +
                "@player" + activeGame.getServerPlayerWorm().getPlayerNumber());
        activeClient.addNextMessage("playername#" + activePlayerFX.getWorm().getWormName() +
                "@player" + activeGame.getServerPlayerWorm().getPlayerNumber());
        activeClient.addNextMessage("playerskin#" + activePlayerFX.getWorm().getWormSkin() +
                "@player" + activeGame.getServerPlayerWorm().getPlayerNumber());
        activeGame.setGameIsRunning(true);

    }

    public void quit()
    {
        gameMessage.setText("");
        activeGame.setGameIsRunning(true);
    }

}