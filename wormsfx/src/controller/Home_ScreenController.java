package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home_ScreenController implements Initializable {
    public void changeScreenButtonPushed(ActionEvent event) throws IOException, IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/view/worms.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public void changeScreenHighscore(ActionEvent event) throws IOException, IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/view/Highscore.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public void changeScreenOptions(ActionEvent event) throws IOException, IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/view/Instructions.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
