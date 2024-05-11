package org.example.controllers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.models.User.Reclamation;
import org.example.services.User.CrudReclamation;
import org.example.utils.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReclamationFront implements Initializable {

    @javafx.fxml.FXML
    private Button ajouter;
    @javafx.fxml.FXML
    private TextField titre;
    @javafx.fxml.FXML
    private TextArea description;
    private int idUser=Session.getSession().getUser().getId(); //ONLY IN FRONT
    @javafx.fxml.FXML
    private Button buttonreturn;
    @javafx.fxml.FXML
    private ComboBox ComboCategorie;
    @javafx.fxml.FXML
    private ComboBox Combocasdurgence;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            Combocasdurgence.getItems().addAll("Normal","Moyenne","Urgent");
        Combocasdurgence.setValue("");
        ComboCategorie.getItems().addAll("Retard de livraison","Probléme de payement","Probleme de réservation");
        ComboCategorie.setValue("");

    }

    @javafx.fxml.FXML
    public void ajouterReclamation(ActionEvent actionEvent) {
        String titreValue=titre.getText();
        String descriptionValue=description.getText();
        CrudReclamation cr=new CrudReclamation();
        Reclamation r=new Reclamation( idUser, titreValue, descriptionValue,false);
        cr.create(r);

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
