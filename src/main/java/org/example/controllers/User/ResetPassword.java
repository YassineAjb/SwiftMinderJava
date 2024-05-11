package org.example.controllers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import org.example.models.User.User;
import org.example.services.User.Crud_user;
import org.example.utils.Encryptor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResetPassword implements Initializable {
    @javafx.fxml.FXML
    private PasswordField NewPassword;
    @javafx.fxml.FXML
    private Button SavePassword;
    @javafx.fxml.FXML
    private PasswordField ConfirmPassword;
    Crud_user us = new Crud_user();
    User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUser(User user) {
        this.user = user;
    }


    @javafx.fxml.FXML
    public void SaveNewPassword(ActionEvent actionEvent) {
        String password = NewPassword.getText();

        if (NewPassword.getText().equals(ConfirmPassword.getText())) {

            String encryptedPassword = Encryptor.encryptPassword(password);
            user.setMot_de_passe(encryptedPassword);
            us.modifier(user);
            System.out.println(user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
            Parent root = null;

            try {
                root = loader.load();
                Scene scene = NewPassword.getScene();
                scene.setRoot(root);
                System.out.println("Password modifié");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("no");
            alert.setHeaderText("no");
            alert.setContentText("no");
            alert.showAndWait();
        }
    }

}