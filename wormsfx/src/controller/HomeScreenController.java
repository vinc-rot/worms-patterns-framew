package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public class HomeScreenController {

    public HomeScreenController() {
    }

    @FXML
    private Label playerlabel;

    @FXML
    private Button quitButton;

    @FXML
    private RadioButton rb_CreateGame;

    @FXML
    private RadioButton rb_JoinGame;

    @FXML
    private TextField tf_IP;

    @FXML
    private TextField tf_Port;

    private Game activeGame;
    private int skinID = 0;
    public Pane mainPane;

    @FXML
    public Button bt_left;

    @FXML
    public Button bt_right;

    @FXML
    public ImageView iv_skin1;

    @FXML
    public ImageView iv_skin2;

    @FXML
    public ImageView iv_skin3;

    @FXML
    public ImageView iv_skin4;

    @FXML
    public ImageView iv_skin5;

    @FXML
    public void initialize() throws UnknownHostException {
        // Game-Instanz wird auf Variable activeGame gesetzt
        activeGame = Game.getInstance();

        playerlabel.setText(activeGame.getClientPlayerWorm().getWormName());

        ToggleGroup group = new ToggleGroup();
        rb_CreateGame.setToggleGroup(group);
        rb_CreateGame.setSelected(true);
        rb_JoinGame.setToggleGroup(group);

        tf_IP.setText(InetAddress.getLocalHost().getHostAddress());

        mainPane.setOnKeyPressed(event -> {
            event.consume();
            changeSkin(event);
        });

    }

    public static int getIntFromTextField(TextField tf_Port) {
        String port = tf_Port.getText();
        return Integer.parseInt(port);
    }

    public void changeScreenButtonPushed(ActionEvent event) throws IOException {

        //Setzen des Netzwerk-IP und -Port in der Spieleumgebung
        activeGame.setNetworkIP(tf_IP.getText());
        activeGame.setNetworkPort(getIntFromTextField(tf_Port));

        //Wenn CreateGame ausgewählt -> Server wird gestartet + Client wird Player1

        if (rb_CreateGame.isSelected()) {

            // Client wird Player1
            activeGame.getClientPlayerWorm().setPlayerNumber(1);

            // ServerPlayer wird Player2
            activeGame.getServerPlayerWorm().setPlayerNumber(2);

            // Server wird gestartet
            Thread runServer = new Thread(() -> {
                while (true) {
                    Server server = new Server();
                    try {
                        server.serverStart(activeGame.getNetworkPort());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            runServer.start();
        }

        if (rb_JoinGame.isSelected()) {

            // Client wird Player2
            activeGame.getClientPlayerWorm().setPlayerNumber(2);

            // ServerPlayer wird Player1
            activeGame.getServerPlayerWorm().setPlayerNumber(1);

        }

        // Netzwerk Client wird gestartet
        Client client = new Client(activeGame.getNetworkPort(),activeGame.getNetworkIP(),activeGame.getClientPlayerWorm().getWormName());

        // Instanz des Client wird dem Game-Objekt mitgeteilt
        activeGame.setPeterClientInstance(client.getInstance());

        // Game Stage wird geladen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/worms.fxml"));
        Parent sceneParent = loader.load();
        Game.setInGameControllerInstance((InGameController) loader.getController());
        Scene scene = new Scene(sceneParent);

        // Client Thread wird gestartet
        Thread ClientThread = new Thread(client);
        ClientThread.start();

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.setResizable(false);
        window.show();
        scene.getRoot().requestFocus();
    }


    public void changeScreenHighscore(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Highscore.fxml")));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public void changeScreenOptions(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Instructions.fxml")));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    private void changeSkin(KeyEvent event) {
        if (event.getCode() == KeyCode.RIGHT) {
            nextSkin();
            bt_left.setDefaultButton(true);
        } else if (event.getCode() == KeyCode.LEFT) {
            previousSkin();
            bt_right.setDefaultButton(true);
        } else if (event.isControlDown() && event.getCode() == KeyCode.P) {
            skinID = 99;
            nextSkin();
        }

    }

    @FXML
    public void previousSkin() {
        skinID--;
        if (skinID < 0) {
            skinID = Worm.WORM_SKINS - 1;
        }
        setSkin(skinID);
    }

    @FXML
    public void nextSkin() {
        skinID++;
        if (skinID >= Worm.WORM_SKINS) {
            if (skinID != 100) {
                skinID = 0;
            }
        }
        setSkin(skinID);
    }

    public void setSkin (int skin) {
        if (skin == 0) {
            iv_skin1.setVisible(true);
            iv_skin2.setVisible(false);
            iv_skin3.setVisible(false);
            iv_skin4.setVisible(false);
            iv_skin5.setVisible(false);
        }
        else if (skin == 1) {
            iv_skin1.setVisible(false);
            iv_skin2.setVisible(true);
            iv_skin3.setVisible(false);
            iv_skin4.setVisible(false);
            iv_skin5.setVisible(false);
        }
        else if (skin == 2) {
            iv_skin1.setVisible(false);
            iv_skin2.setVisible(false);
            iv_skin3.setVisible(true);
            iv_skin4.setVisible(false);
            iv_skin5.setVisible(false);
        }
        else if (skin == 3) {
            iv_skin1.setVisible(true);
            iv_skin2.setVisible(false);
            iv_skin3.setVisible(false);
            iv_skin4.setVisible(true);
            iv_skin5.setVisible(false);
        }
        else if (skin == 4) {
            iv_skin1.setVisible(true);
            iv_skin2.setVisible(false);
            iv_skin3.setVisible(false);
            iv_skin4.setVisible(true);
            iv_skin5.setVisible(false);
        }
    }


    // QuitButton-Handling
    @FXML
    public void quitButtonPushed(ActionEvent event) throws IOException {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
        System.exit(0);

    }


}
