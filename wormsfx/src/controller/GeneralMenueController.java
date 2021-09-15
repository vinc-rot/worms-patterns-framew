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

public class GeneralMenueController {

    @FXML
    private TextField playername;

    public void changeScreenBack(ActionEvent event) throws IOException, IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/view/Home_Screen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public void changeScreenLogin(ActionEvent event) throws IOException, IOException {

        System.out.println(playername.getText());

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/view/Home_Screen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
}
