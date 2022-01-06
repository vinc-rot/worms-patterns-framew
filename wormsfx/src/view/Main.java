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


public class Main extends Application {

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
