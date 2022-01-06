package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Game;
import model.Worm;

import java.io.IOException;


public class GeneralMenueController {

    @FXML
    private TextField playername;

    public void changeScreenBack(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home_Screen.fxml"));
        Parent sceneParent = loader.load();
        Scene scene = new Scene(sceneParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
    public void changeScreenLogin(ActionEvent event) throws IOException {

        // Game Objekt wird angelegt + Client Worm initalisisiert
        Game.getInstance().setClientPlayerWorm(new Worm(playername.getText()));

        // Server Player Worm wird initialisiert
        Game.getInstance().setServerPlayerWorm(new Worm());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home_Screen.fxml"));
        Parent sceneParent = loader.load();
        Scene scene = new Scene(sceneParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
}
