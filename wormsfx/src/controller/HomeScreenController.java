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
import java.util.Scanner;

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
    public ImageView iv_skin;

    @FXML
    public void initialize() throws UnknownHostException {
        activeGame = Game.getInstance();

        playerlabel.setText(activeGame.getLoggedInPlayer().getWormName());

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
        Game.getInstance().setNetworkIP(tf_IP.getText());
        Game.getInstance().setNetworkPort(getIntFromTextField(tf_Port));

        /* Wenn RadioButton "CreateGame" ausgewählt ist, dann wird
           - der LoggedInPlayer zum ServerPlayer
           - der ChatServer mit Netzwerk-IP und gewähltem Port und
           - der ChatClient mit dem ServerPlayer-Namen, der Netzwerk-IP und -Port gestartet
        */

        if (rb_CreateGame.isSelected()) {

            // LoggedInPlayer wird zum ServerPlayer
            //Game.getInstance().setServerPlayer(Game.getInstance().getLoggedInPlayer());
            Game.getInstance().setServerPlayer(new Worm(playerlabel.getText()));

            Thread runServer = new Thread(() -> {
                while (true) {
                    Server server = new Server();
                    try {
                        server.serverStart();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            runServer.start();

            PeterClient client = new PeterClient(1234,"User1234");
            Thread ClientThread = new Thread(client);
            ClientThread.start();

            Game.setPeterClientInstance(client.getInstance());

            // ChatServer mit Netzwerk-IP und -Port als Thread starten
            /*  Thread startServer = new Thread(() -> {
                while (true) {
                    ChatServer server = new ChatServer(Game.getInstance().getNetworkPort());
                    server.startServer();
                }
            });
            startServer.start();*/

            // ChatClient ServerPlayer-Namen und Netzwerk-IP und -Port starten
            /*  Thread startClient = new Thread(() -> {
                while (true) {
                    Scanner scan = new Scanner(System.in);
                    ChatClient client = new ChatClient((activeGame.getServerPlayer().getWormName()), Game.getInstance().getNetworkIP(), Game.getInstance().getNetworkPort());
                    client.startClient(scan);
                }
            });

            startClient.start();*/

        }

        /* Wenn RadioButton "JoinGame" ausgewählt ist, dann wird
           - der LoggedInPlayer zum ClientPlayer und
           - der ChatClient mit dem ClientPlayer-Namen, der Netzwerk-IP und -Port gestartet
        */

        if (rb_JoinGame.isSelected()) {

            //Game.getInstance().setClientPlayer(Game.getInstance().getLoggedInPlayer());
            Game.getInstance().setClientPlayer(new Worm(playerlabel.getText()));

            // Starten des ChatClient mit Spielernamen und Netzwerkinformationen (IP und Port)
            /*            Thread startClient = new Thread(() -> {
                while (true) {
                    Scanner scan = new Scanner(System.in);
                    ChatClient client = new ChatClient((activeGame.getClientPlayer().getWormName()), Game.getInstance().getNetworkIP(), Game.getInstance().getNetworkPort());
                    client.startClient(scan);
                }
            });

            startClient.start();*/

            PeterClient client = new PeterClient(1234,"User1234");
            Thread ClientThread = new Thread(client);
            ClientThread.start();

            Game.setPeterClientInstance(client.getInstance());

         }

        Parent sceneParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/worms.fxml")));
        Scene scene = new Scene(sceneParent);



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

    public void previousSkin() {
        skinID--;
        if (skinID < 0) {
            skinID = Worm.WORM_SKINS - 1;
        }
        iv_skin.setImage(new Image(String.format("/resources/worms/worm%d.png", skinID)));
    }

    public void nextSkin() {
        skinID++;
        if (skinID >= Worm.WORM_SKINS) {
            if (skinID != 100) {
                skinID = 0;
            }
        }
        iv_skin.setImage(new Image(String.format("/resources/worms/Rworm%d.png", skinID)));
    }


    // QuitButton-Handling
    @FXML
    public void quitButtonPushed(ActionEvent event) throws IOException {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
        System.exit(0);

    }


}
