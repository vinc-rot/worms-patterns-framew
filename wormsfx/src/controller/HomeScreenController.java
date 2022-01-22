package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Client;
import model.Game;
import model.Server;
import model.Worm;

import java.io.IOException;
import java.net.BindException;
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
    private ToggleGroup group;

    @FXML
    private RadioButton rb_CreateGame;

    @FXML
    private RadioButton rb_JoinGame;

    @FXML
    private HBox mapSelect;

    @FXML
    private RadioButton rb_NormalMap;

    @FXML
    private RadioButton rb_VulkanMap;

    @FXML
    private RadioButton rb_MoonMap;

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
    public ImageView iv_skin6;

    @FXML
    public ImageView iv_skin7;

    @FXML
    public ImageView iv_skin8;

    @FXML
    public ImageView iv_skin9;

    @FXML
    public ImageView iv_skin10;

    @FXML
    public void initialize() throws UnknownHostException {
        // Game-Instanz wird auf Variable activeGame gesetzt
        activeGame = Game.getInstance();

        playerlabel.setText(activeGame.getClientPlayerWorm().getWormName());

/*        ToggleGroup group = new ToggleGroup();
        rb_CreateGame.setToggleGroup(group);
        rb_CreateGame.setSelected(true);
        rb_JoinGame.setToggleGroup(group);*/

/*        ToggleGroup groupMap = new ToggleGroup();
        rb_NormalMap.setToggleGroup(groupMap);
        rb_NormalMap.setSelected(true);
        rb_VulkanMap.setToggleGroup(groupMap);
        rb_MoonMap.setToggleGroup(groupMap);*/

        /* Eventlistener für die Mapauswahl
            - ist RadioButton rb_createGame ausgewählt, kann eine Map gewählt werden
            - ist RadioButten rb_joinGame ausgewählt, wird keine Map-Auswahl angeboten
         */
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if (rb_CreateGame.isSelected()) {
                    mapSelect.setVisible(true);
                }
                else {
                    mapSelect.setVisible(false);
                }
            }
        });

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
            Server server = new Server(activeGame.getNetworkPort());

            // Server Thread wird gestartet
            Thread ClientThread = new Thread(server);

            ClientThread.start();


/*            Thread runServer = new Thread(() -> {
                while (true) {
                    Server server = new Server();
                    try {
                        server.serverStart(activeGame.getNetworkPort());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            runServer.start();*/


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

    public void changeScreenBack(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home_Screen.fxml"));
        Parent sceneParent = loader.load();
        Scene scene = new Scene(sceneParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
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
            iv_skin6.setVisible(false);
            iv_skin7.setVisible(false);
            iv_skin8.setVisible(false);
            iv_skin9.setVisible(false);
            iv_skin10.setVisible(false);
        }
        else if (skin == 1) {
            iv_skin1.setVisible(false);
            iv_skin2.setVisible(true);
            iv_skin3.setVisible(false);
            iv_skin4.setVisible(false);
            iv_skin5.setVisible(false);
            iv_skin6.setVisible(false);
            iv_skin7.setVisible(false);
            iv_skin8.setVisible(false);
            iv_skin9.setVisible(false);
            iv_skin10.setVisible(false);
        }
        else if (skin == 2) {
            iv_skin1.setVisible(false);
            iv_skin2.setVisible(false);
            iv_skin3.setVisible(true);
            iv_skin4.setVisible(false);
            iv_skin5.setVisible(false);
            iv_skin6.setVisible(false);
            iv_skin7.setVisible(false);
            iv_skin8.setVisible(false);
            iv_skin9.setVisible(false);
            iv_skin10.setVisible(false);
        }
        else if (skin == 3) {
            iv_skin1.setVisible(false);
            iv_skin2.setVisible(false);
            iv_skin3.setVisible(false);
            iv_skin4.setVisible(true);
            iv_skin5.setVisible(false);
            iv_skin6.setVisible(false);
            iv_skin7.setVisible(false);
            iv_skin8.setVisible(false);
            iv_skin9.setVisible(false);
            iv_skin10.setVisible(false);
        }
        else if (skin == 4) {
            iv_skin1.setVisible(false);
            iv_skin2.setVisible(false);
            iv_skin3.setVisible(false);
            iv_skin4.setVisible(false);
            iv_skin5.setVisible(true);
            iv_skin6.setVisible(false);
            iv_skin7.setVisible(false);
            iv_skin8.setVisible(false);
            iv_skin9.setVisible(false);
            iv_skin10.setVisible(false);
        }
        else if (skin == 5) {
            iv_skin1.setVisible(false);
            iv_skin2.setVisible(false);
            iv_skin3.setVisible(false);
            iv_skin4.setVisible(false);
            iv_skin5.setVisible(false);
            iv_skin6.setVisible(true);
            iv_skin7.setVisible(false);
            iv_skin8.setVisible(false);
            iv_skin9.setVisible(false);
            iv_skin10.setVisible(false);
        }
        else if (skin == 6) {
            iv_skin1.setVisible(false);
            iv_skin2.setVisible(false);
            iv_skin3.setVisible(false);
            iv_skin4.setVisible(false);
            iv_skin5.setVisible(false);
            iv_skin6.setVisible(false);
            iv_skin7.setVisible(true);
            iv_skin8.setVisible(false);
            iv_skin9.setVisible(false);
            iv_skin10.setVisible(false);
        }
        else if (skin == 7) {
            iv_skin1.setVisible(false);
            iv_skin2.setVisible(false);
            iv_skin3.setVisible(false);
            iv_skin4.setVisible(false);
            iv_skin5.setVisible(false);
            iv_skin6.setVisible(false);
            iv_skin7.setVisible(false);
            iv_skin8.setVisible(true);
            iv_skin9.setVisible(false);
            iv_skin10.setVisible(false);
        }
        else if (skin == 8) {
            iv_skin1.setVisible(false);
            iv_skin2.setVisible(false);
            iv_skin3.setVisible(false);
            iv_skin4.setVisible(false);
            iv_skin5.setVisible(false);
            iv_skin6.setVisible(false);
            iv_skin7.setVisible(false);
            iv_skin8.setVisible(false);
            iv_skin9.setVisible(true);
            iv_skin10.setVisible(false);
        }
        else if (skin == 9) {
            iv_skin1.setVisible(false);
            iv_skin2.setVisible(false);
            iv_skin3.setVisible(false);
            iv_skin4.setVisible(false);
            iv_skin5.setVisible(false);
            iv_skin6.setVisible(false);
            iv_skin7.setVisible(false);
            iv_skin8.setVisible(false);
            iv_skin9.setVisible(false);
            iv_skin10.setVisible(true);
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