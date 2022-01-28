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

import model.HighScore;

import java.io.IOException;
import java.util.ArrayList;


public class Main extends Application {

    // public static ArrayList<HighScore> highScores=new ArrayList<>();
    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            FXMLLoader startScreenFXML = new FXMLLoader(getClass().getResource("/view/Start_Screen.fxml"));
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


    public static void main(String[] args) throws IOException { 
   	// highScores.add(new HighScore(90,1,"Nils"));
   	// highScores.add(new HighScore(56,1,"Jon"));
   	// highScores.add(new HighScore(100,1,"Smith"));
   	// highScores.add(new HighScore(98,1,"Steav"));
   	// highScores.add(new HighScore(80,1,"Nils"));
    	
    	// HighScore.serialize();
    	
    	launch(args);}



}
