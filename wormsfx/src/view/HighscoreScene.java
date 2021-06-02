package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


    public class HighscoreScene extends Application {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            try {
                Parent root = new FXMLLoader().load(getClass().getResource("Highscore.fxml"));
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.setTitle("Highscore");
                primaryStage.show();
                scene.getRoot().requestFocus();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

