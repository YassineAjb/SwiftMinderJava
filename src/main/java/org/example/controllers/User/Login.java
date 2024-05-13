package org.example.controllers.User;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.example.models.User.User;
import org.example.services.User.Crud_user;
import org.example.utils.Encryptor;
import org.example.utils.Session;

import java.io.IOException;


public class Login {
    @FXML
    private Button button_Sign_In;
    @FXML
    private TextField user_email;
    @FXML
    private TextField passwordTextfield;
    @FXML
    private PasswordField user_password;
    private final Crud_user cr = new Crud_user();
    @FXML
    private Label messageError;
    @FXML
    private CheckBox showpassword;
    @FXML
    private Hyperlink linkSignUp;
    @FXML
    private ImageView CloseWindow;
    @FXML
    private Hyperlink LinkForgotPassword;
    private Scene previousScene;


    @FXML
    public void Sign_In_OnClick(ActionEvent actionEvent) {
        String email = user_email.getText();
        String mot_de_passe = user_password.getText();
        if (email.isEmpty()) {
            messageError.setText("remplissez le champ");
        } else if (mot_de_passe.isEmpty()) {
            messageError.setText("remplissez le champ");
        }
        User loggedInUser = cr.Login(email);
        if (loggedInUser != null) {
            if(!Encryptor.TestPassword(loggedInUser.getMot_de_passe(),mot_de_passe))
            {
                messageError.setText("mot de passe invalide");
            }else {
                messageError.setText("Connexion réussie. Bienvenue, " + loggedInUser.getEmail());
                user_email.setText("");
                user_password.setText("");
                try {
                    Session.Start_session(loggedInUser);
                        if(loggedInUser.getRole().equals("admin")){
                        Parent root = FXMLLoader.load(getClass().getResource("/User/Crud.fxml"));

                        button_Sign_In.getScene().setRoot(root);
                    }else if(loggedInUser.getRole().equals("Journaliste")){
                        Parent root = FXMLLoader.load(getClass().getResource("/Article/afficherarticles.fxml"));

                        button_Sign_In.getScene().setRoot(root);
                    }else if(loggedInUser.getRole().equals("Membre")){
                        Parent root = FXMLLoader.load(getClass().getResource("/Employee/AffichageJoueur.fxml"));

                        button_Sign_In.getScene().setRoot(root);
                    }else if(loggedInUser.getRole().equals("MembrePlus")){
                        Parent root = FXMLLoader.load(getClass().getResource("/Employee/AffichageJoueur.fxml"));

                        button_Sign_In.getScene().setRoot(root);
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }


        } else {

            messageError.setText("Email ou mot de passe incorrect");
        }
    }


    @FXML
    public void changeVisbility(ActionEvent actionEvent) {
        if (showpassword.isSelected()) {
            passwordTextfield.setText(user_password.getText());
            passwordTextfield.setVisible(true);
            user_password.setVisible(false);
            return;
        }
        user_password.setText(passwordTextfield.getText());
        user_password.setVisible(true);
        passwordTextfield.setVisible(false);
    }



    @FXML
    public void linkSignUp_OnClick(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/User/SignUp.fxml"));
            linkSignUp.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void closeWindow(Event event) {
        Platform.exit();
    }

    @FXML
    public void ForgotPassword(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/User/ForgotPassword.fxml"));
            LinkForgotPassword.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }
}








