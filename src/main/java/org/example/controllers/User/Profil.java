package org.example.controllers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.models.User.User;
import org.example.utils.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Profil implements Initializable {


    @javafx.fxml.FXML
    private Label nameValue;
    @javafx.fxml.FXML
    private Button buttonreturn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User u= Session.getSession().getUser();
        nameValue.setText(u.getEmail());

    }

    @javafx.fxml.FXML
    public void returnSign(ActionEvent actionEvent) {
        try{

            Session.getSession().clearSession();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
            Parent root = loader.load();

            Login controller = loader.getController();
            controller.setPreviousScene(((Node)actionEvent.getSource()).getScene());

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);

        } catch (
                IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
