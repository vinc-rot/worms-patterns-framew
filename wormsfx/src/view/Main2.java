package view;

import controller.InGameController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class Main2 extends Application {

    static InGameController inGameControllerHandle;

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            FXMLLoader startScreenFXML = new FXMLLoader(getClass().getResource("Start_Screen.fxml"));
            Parent root = startScreenFXML.load();
            primaryStage.setTitle("Menü");

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

            /*Thread startClient = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Scanner scan = new Scanner(System.in);
                        ChatClient client = new ChatClient("Player1", "localhost", 4444);
                        client.startClient(scan);
                    }
                }
            });*/

            /*Thread startServer = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        ChatServer server = new ChatServer(5555);
                        server.startServer();
                    }
                }
            });*/

            //startServer.start();
            //startClient.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
    }


    public static void main(String[] args) throws IOException { launch(args);}



}
