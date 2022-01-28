package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.HighScore;
import view.Main;

public class HighScoreController implements Initializable {

	@FXML
    private Label score_1,score_2,score_3,score_4,score_5,name_1,name_2,name_3,name_4,name_5;
	
	
	
	public void changeScreenBack(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home_Screen.fxml"));
        Parent sceneParent = loader.load();
        Scene scene = new Scene(sceneParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// HighScore.deserialize();
		/*Collections.sort(Main.highScores,Collections.reverseOrder());
		//Main.highScores.sort(Comparator.comparing(HighScore::getScore));
		name_1.setText(Main.highScores.get(0).getName());
		name_2.setText(Main.highScores.get(1).getName());
		name_3.setText(Main.highScores.get(2).getName());
		name_4.setText(Main.highScores.get(3).getName());
		name_5.setText(Main.highScores.get(4).getName());
		
		score_1.setText(""+Main.highScores.get(0).getScore());
		score_2.setText(""+Main.highScores.get(1).getScore());
		score_3.setText(""+Main.highScores.get(2).getScore());
		score_4.setText(""+Main.highScores.get(3).getScore());
		score_5.setText(""+Main.highScores.get(4).getScore());
		// TODO Auto-generated method stub*/
		
	}
	
}
