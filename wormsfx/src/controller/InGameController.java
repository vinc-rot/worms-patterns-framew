package controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class InGameController {

    @FXML
    private ImageView player1;

    @FXML
    private ImageView player1crossfade;

    @FXML
    private Pane borderPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private void handleKeyPressed() {
        anchorPane.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                case KP_UP: target(+10); break;
                case DOWN:
                case KP_DOWN: target(-10); break;
                case RIGHT:
                case KP_RIGHT: walk(+20); break;
                case LEFT:
                case KP_LEFT: walk(-20); break;
                case SPACE: shoot(); break;
                case ENTER: jump(); break;
                default: break;
            }
        });
    }

    @FXML
    private void walk(int newVal) {
        walkAnimation(player1, player1.getTranslateX(), player1.getTranslateX() + newVal);
    }

    @FXML
    private void target(int newVal) {
        targetAnimation(player1crossfade, player1crossfade.getTranslateY(), player1crossfade.getTranslateY() + newVal);
    }

    @FXML
    private void jump() {
        jumpAnimation(player1);
    }

    @FXML
    private void shoot() {
        shootAnimation(player1);
    }

    private void walkAnimation(ImageView img, double oldVal, double newVal) {
        TranslateTransition tt = new TranslateTransition(
                Duration.seconds(0.1), img);
        tt.setFromX(oldVal);
        if (newVal < oldVal) {
            img.setRotate(180);
            tt.setToX(newVal);
        } else {
            img.setRotate(0);
            tt.setToX(newVal);
        }
        tt.play();
    }

    private void targetAnimation(ImageView img, double oldVal, double newVal) {
        TranslateTransition tt = new TranslateTransition(
                Duration.seconds(0.1), img);
        tt.setFromY(oldVal);
        tt.setToY(newVal);
        tt.play();
    }

    private void jumpAnimation(ImageView img) {
        TranslateTransition tt = new TranslateTransition(
                Duration.seconds(0.1), img);
        tt.setFromY(img.getTranslateY());
        tt.setToY(img.getTranslateY() - 25);
        tt.setOnFinished(event -> fallAnimation(img));
        tt.play();
    }

    private void fallAnimation(ImageView img) {
        TranslateTransition tt = new TranslateTransition(
                Duration.seconds(0.1), img);
        tt.setFromY(img.getTranslateY());
        tt.setToY(img.getTranslateY() + 25);
        tt.play();
    }

    private void shootAnimation(ImageView img) {
        TranslateTransition tt = new TranslateTransition(
                Duration.seconds(0.1), img);
        tt.setFromY(img.getTranslateY());
        tt.setToY(img.getTranslateY() - 25);
        tt.setOnFinished(event -> fallAnimation(img));
        tt.play();
    }
}
