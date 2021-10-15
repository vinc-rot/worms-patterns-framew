package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

import model.Game;
import model.Worm;


public class GeneralMenueController {

    @FXML
    private TextField playername;

    public void changeScreenBack(ActionEvent event) throws IOException, IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home_Screen.fxml"));
        Parent sceneParent = loader.load();
        Scene scene = new Scene(sceneParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
    public void changeScreenLogin(ActionEvent event) throws IOException, IOException {

        Game.getInstance().setActivePlayer(new Worm(playername.getText(), 100, 200, 100));
        Game.getInstance().setNetworkPlayer(new Worm("NetworkPlayerTest", 100, 200, 100));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home_Screen.fxml"));
        Parent sceneParent = loader.load();
        Scene scene = new Scene(sceneParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
}
