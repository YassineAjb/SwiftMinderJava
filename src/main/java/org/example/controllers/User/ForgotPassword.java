package org.example.controllers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.models.User.User;
import org.example.services.User.Crud_user;
import org.example.utils.EmailSender;
import org.example.utils.Encryptor;
import org.example.utils.SMSsender;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPassword implements Initializable {
    @FXML
    private TextField EmailForgotPassword;
    @FXML
    private Button buttonForgotPassword;
    String GeneratedCode = "";
    String generatedCode;


    private int process;
    private Crud_user crudUser = new Crud_user();
    @FXML
    private ImageView returnLogin;
    @FXML
    private RadioButton SmsType;
    @FXML
    private RadioButton EmailType;
    @FXML
    private TextField InputCode;
    User user;
    @FXML
    private ToggleGroup radioChoix;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InputCode.setDisable(true);
        process = 1;

    }


    @FXML
    public void ForgotPasswordOn_Click(ActionEvent event) {
        if (process == 1) {
            if (EmailType.isSelected()) {
                String email = EmailForgotPassword.getText();
                user = crudUser.Login(email);
                System.out.println("USER FROM FORGET PASSWORD" + user);
                if (user != null) {
                    generatedCode = Encryptor.generateCode(6);
                    EmailSender emailSender = new EmailSender();
                    emailSender.sendEmail(email, "RESET PASSWORD", "Votre code est : " + generatedCode + "");


                    InputCode.setDisable(false);
                    EmailForgotPassword.setDisable(true);
                    process = 2;

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Email Not Valid");
                    alert.setHeaderText("NOT VALID EMAIL ");
                    alert.setContentText("Account NON VALID");
                    alert.showAndWait();
                }

            } else {
                String numTell = EmailForgotPassword.getText();
                System.out.println(numTell);
                user = crudUser.getUserByNumTell(numTell);
                if (user != null) {
                    InputCode.setDisable(false);
                    EmailForgotPassword.setDisable(true);
                    process = 2;
                    generatedCode = Encryptor.generateCode(5);
                    System.out.println(generatedCode);
                    SMSsender.Send("+216" + numTell, generatedCode);

                }
            }

        }else {
                 if (generatedCode.equals(InputCode.getText())) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ResetPassword.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                    ResetPassword newPasswordController = loader.getController();
                    // Passer l'objet User à la scène ResetPassword.fxml
                    newPasswordController.setUser(user);
                    // Charger la scène dans la fenêtre actuelle
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
             } else {
                // Afficher une alerte si le code n'est pas valide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("CODE Not Valid");
                alert.setHeaderText("NOT VALID CODE");
                alert.setContentText("NOT VALID CODE");
                alert.showAndWait();
            }}
    }

    @FXML
    public void returnToPreviousScene(MouseEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
            Parent root = loader.load();

            Login controller = loader.getController();
            controller.setPreviousScene(((Node) event.getSource()).getScene());

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

        } catch (
                IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
