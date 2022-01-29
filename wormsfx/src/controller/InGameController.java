package controller;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
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
import view.Main;

import java.io.IOException;
import java.util.Objects;
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

    @FXML
    private Button btn_continue;

    @FXML
    private ImageView backgroundImage;

    private Game activeGame;

    private Client activeClient;

    private WormFX activePlayerFX;
    private WormFX networkPlayerFX;

    private int explosionSize = 96;
    private int hitImpact = 30;
    private int wormHeight = 96;
    private int wormWidth = 48;

    @FXML
    public void initialize() {

        // Client + Game Instanzen werden initialisiert
        activeGame = Game.getInstance();
        activeClient = Game.getPeterClientInstance();

        System.out.println("Ausgelesenen Avatar: " + activeGame.getClientPlayerWorm().getWormAvatar().getUrl());

        // Zusammenfassen der JavaFX-Objekte für Player 1 + 2
        WormFX player1FX = new WormFX(new Worm(), player1, player1crossfade, player1rocket, player1icon,
                player1explosion, player1name, player1lifepoints);

        WormFX player2FX = new WormFX(new Worm(), player2, player2crossfade, player2rocket, player2icon,
                player2explosion, player2name, player2lifepoints);

        // Warten auf den Netzwerkspieler
        if (activeGame.getClientPlayerWorm().getPlayerNumber() == 1)
        {
            gameMessage.setText("Wait for Player 2");

            activePlayerFX = player1FX;
            networkPlayerFX = player2FX;

            setMap(activeGame.getSelectedMap().getUrl());

        }
        else
        {
            activePlayerFX = player2FX;
            networkPlayerFX = player1FX;

            sendSync();

        }

        activePlayerFX.setWorm(activeGame.getClientPlayerWorm());
        networkPlayerFX.setWorm(activeGame.getServerPlayerWorm());

        setPlayerName(activePlayerFX, activePlayerFX.getWorm().getWormName());
        setLifePoints(activePlayerFX, activePlayerFX.getWorm().getLifePoints());
        setPlayerWorm(activePlayerFX, activePlayerFX.getWorm().getWormAvatar());

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
            case "playerskin": setPlayerWorm(networkPlayerFX, new Image(st.nextToken()));break;
            case "gamewon": gameWon(); break;
            case "gamelost": gameLost(); break;
            case "map": setMap(st.nextToken()); break;
            case "sync": syncGame(); break;
            case "highscore": addHighScore(st.nextToken(),Integer.parseInt(st.nextToken())); break;
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
        if (activeGame.isGameIsRunning() & !activePlayerFX.isJumpActive()) {
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
                playerFX.getWormImage());

        TranslateTransition ttTarget = new TranslateTransition(
                Duration.seconds(0.1),
                playerFX.getWormTarget());

        ttWorm.setFromX(playerFX.getWormImage().getTranslateX());
        ttTarget.setFromX(playerFX.getWormTarget().getTranslateX());

        int newValRotationAddup = 0;

        if (newVal > 0 && playerFX.getWormImage().getRotate() == 180 ) {
            playerFX.getWormImage().setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            newValRotationAddup = 40;
        }
        else if (newVal > 0 && playerFX.getWormImage().getRotate() == 0){
            playerFX.getWormImage().setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            newValRotationAddup = 40;
        }
        else if (newVal < 0 && playerFX.getWormImage().getRotate() == 180) {
            playerFX.getWormImage().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            newValRotationAddup = -40;
        }
        else if (newVal < 0 && playerFX.getWormImage().getRotate() == 0) {
            playerFX.getWormImage().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
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
        TranslateTransition ttJump = new TranslateTransition(
                Duration.seconds(0.1), playerFX.getWormImage());

        ttJump.setFromY(playerFX.getWormImage().getTranslateY());
        ttJump.setToY(playerFX.getWormImage().getTranslateY()-40);
        ttJump.setAutoReverse(true);
        ttJump.setCycleCount(2);
        ttJump.setOnFinished(event -> playerFX.setJumpActive(false));
        ttJump.play();
    }

    private void shootAnimation(WormFX playerFX) {
        double positionWormX = playerFX.getWormImage().getX()+playerFX.getWormImage().getTranslateX()+wormWidth/2;
        double positionWormY = playerFX.getWormImage().getY()+playerFX.getWormImage().getTranslateY()+wormHeight/2;
        int shootingDirection;

        playerFX.getWormRocket().setVisible(true);

        Rocket activeRocket = new Rocket(100, Math.abs(Math.atan(playerFX.getWormTarget().getTranslateY()/40)),
                9.81, borderPane.getHeight()-positionWormY);

        if (playerFX.getWormImage().getNodeOrientation() == NodeOrientation.RIGHT_TO_LEFT)
            shootingDirection = -1;
        else
            shootingDirection = 1;

        double rocketBezierPointX = positionWormX + activeRocket.rocketPeakX()*shootingDirection;
        double rocketBezierPointY = borderPane.getHeight() - activeRocket.rocketBezierY();
        double rocketEndPointX = positionWormX + activeRocket.rocketEndX()*shootingDirection;
        double rocketEndPointY = borderPane.getHeight();

        playerFX.getWormExplosion().setTranslateX(rocketEndPointX-explosionSize/2);
        playerFX.getWormExplosion().setTranslateY(rocketEndPointY-explosionSize);

        FadeTransition ftExplosion = new FadeTransition(
                Duration.seconds(1.0), playerFX.getWormExplosion());
        ftExplosion.setFromValue(1.0);
        ftExplosion.setToValue(0.0);

        Path path = new Path();
        path.getElements().add(new MoveTo(positionWormX,positionWormY));
        path.getElements().add(new CubicCurveTo(rocketBezierPointX, rocketBezierPointY,
                rocketBezierPointX, rocketBezierPointY, rocketEndPointX, rocketEndPointY));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(activeRocket.rocketTime()*100));
        pathTransition.setPath(path);
        pathTransition.setNode(playerFX.getWormRocket());
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setOnFinished(event -> { playerFX.getWormRocket().setVisible(false);
            playerFX.setRocketActive(false);
            playerFX.getWormExplosion().setVisible(true);
            ftExplosion.play();
            if (playerFX.equals(networkPlayerFX))
                hitDetection(activePlayerFX, rocketEndPointX, rocketEndPointY);}
        );
        pathTransition.play();
    }

    private void hitDetection(WormFX playerFX, double rocketEndPointX, double rocketEndPointY) {
        double positionWormX = playerFX.getWormImage().getX()+playerFX.getWormImage().getTranslateX()+(wormWidth/2);
        double positionWormY = playerFX.getWormImage().getY()+playerFX.getWormImage().getTranslateY()+(wormHeight/2);

        double distanceWormVsExplosionX = Math.abs(positionWormX - rocketEndPointX);
        double distanceWormVsExplosionY = Math.abs(positionWormY - rocketEndPointY);

        double distanceWormVsExplosion = Math.sqrt(distanceWormVsExplosionX*distanceWormVsExplosionX+
                distanceWormVsExplosionY+distanceWormVsExplosionY);

        if (distanceWormVsExplosion <= explosionSize/2){

            int hitDamage = playerFX.getWorm().getLifePoints() - (int) (hitImpact * distanceWormVsExplosion / explosionSize * 2);
            setLifePoints(playerFX,hitDamage);
            activeClient.addNextMessage("lifepoints#" + hitDamage + "@player" + activeGame.getServerPlayerWorm().getPlayerNumber());
            //hitAnimation(playerFX);
        }

    }

    private void hitAnimation(WormFX playerFX)
    {
        playerFX.getWormImage().setEffect(new Glow(0.8));
    }

    private void setLifePoints(WormFX playerFX, int newVal) {
        if (newVal > 0) {
            playerFX.getWorm().setLifePoints(newVal);
            playerFX.getWormLifePoints().setText(String.valueOf(newVal));

        }
        else {
            playerFX.getWorm().setLifePoints(0);
            playerFX.getWormLifePoints().setText(String.valueOf(newVal));
            if (playerFX.equals(activePlayerFX))
                activeClient.addNextMessage("gamewon#" + " " + "@player" + activeGame.getServerPlayerWorm().getPlayerNumber());
            else
                activeClient.addNextMessage("gamelost#" + " " + "@player" + activeGame.getServerPlayerWorm().getPlayerNumber());
        }
    }

    private void setPlayerName(WormFX playerFX, String newName) {
        playerFX.getWorm().setWormName(newName);
        playerFX.getWormName().setText(String.valueOf(newName));
    }


    private void setPlayerWorm(WormFX playerFX, Image newImage) {
        playerFX.getWorm().setWormAvatar(newImage);
        playerFX.getWormImage().setImage(new Image(playerFX.getWorm().getWormAvatar().getUrl()));
        playerFX.getWormLogo().setImage(new Image(playerFX.getWorm().getWormAvatar().getUrl()));
    }

    private void setMap(String mapUrl) {
        System.out.println("Ausgelesene Map: " + mapUrl);
        backgroundImage.setImage(new Image(mapUrl));

    }

    private void sendMap(String mapUrl) {
        System.out.println("Gesendete Map: " + mapUrl);
        activeClient.addNextMessage("map#" + mapUrl +
                "@player" + activeGame.getServerPlayerWorm().getPlayerNumber());
    }

    private void sendSync(){
        activeClient.addNextMessage("sync#" +
                "@player" + activeGame.getServerPlayerWorm().getPlayerNumber());
    }


    public void syncGame()
    {
        gameMessage.setText("");
        activeClient.addNextMessage("lifepoints#" + activePlayerFX.getWorm().getLifePoints() +
                "@player" + activeGame.getServerPlayerWorm().getPlayerNumber());
        activeClient.addNextMessage("playername#" + activePlayerFX.getWorm().getWormName() +
                "@player" + activeGame.getServerPlayerWorm().getPlayerNumber());
        activeClient.addNextMessage("playerskin#" + activePlayerFX.getWorm().getWormAvatar().getUrl() +
                "@player" + activeGame.getServerPlayerWorm().getPlayerNumber());

        if (activeGame.getClientPlayerWorm().getPlayerNumber() == 1)
        {
            sendMap(activeGame.getSelectedMap().getUrl());
            sendSync();

        }

        activeGame.setGameIsRunning(true);

    }

    public void getHighScore(){
        activeClient.addNextMessage("gethighscore");
    }

    public void addHighScore(String name, int score){
        activeGame.getHighScoreList().getHighScores().add(new HighScore(score, 1, name));
    }

    public void sendHighScoretoServer(HighScore highScore){
        activeClient.addNextMessage("addhighscore#" + highScore.getName() + "#" + highScore.getScore());
    }

    public void gameWon(){
        activeGame.setGameWon(true);
        gameMessage.setText("You Won");
        btn_continue.setVisible(true);
        activeGame.setGameIsRunning(false);
        getHighScore();
        HighScore newHighScore = new HighScore(activePlayerFX.getWorm().getLifePoints(), 1, activePlayerFX.getWorm().getWormName());
        activeGame.getHighScoreList().getHighScores().add(newHighScore);
        sendHighScoretoServer(newHighScore);
    }

    public void gameLost(){
        gameMessage.setText("You Lose");
        btn_continue.setVisible(true);
        activeGame.setGameIsRunning(false);
        getHighScore();
        activeGame.getHighScoreList().getHighScores().add(new HighScore(networkPlayerFX.getWorm().getLifePoints(), 1, networkPlayerFX.getWorm().getWormName()));
    }

    public void logout(){
        activeClient.addNextMessage("logout");
    }



    public void changeScreenWinOrLose(ActionEvent event) throws IOException {
        if (activeGame.isGameWon()) {
            logout();
            Parent tableViewParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/GameOverWin.fxml")));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        } else {
            logout();
            Parent tableViewParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/GameOverLoose.fxml")));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }
    }
}