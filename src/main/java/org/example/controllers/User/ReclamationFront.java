package org.example.controllers.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    @FXML
    private Button btnBoutique;
    @FXML
    private Button btnTerrain;
    @FXML
    private Button btnContrats;
    @FXML
    private Button btnArticlles;

    @FXML
    private Button btnElection;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnReclamations;

    @FXML
    private Button btnJoueurs;

    @FXML
    private Button btnMatch;

    @FXML
    private Button btnReservation;

    @FXML
    private Button btnSignout;
    @javafx.fxml.FXML
    private Button ajouter;
    @javafx.fxml.FXML
    private TextField titre;
    @javafx.fxml.FXML
    private TextArea description;
    private int idUser= Session.getSession().getUser().getId(); //ONLY IN FRONT
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

        btnMatch.setOnAction(e -> {
            naviguezVers("/Article/affichermatch.fxml");
        });
        btnReservation.setOnAction(e -> {
            naviguezVers("/Reservation/listeReservation.fxml");
        });
        btnTerrain.setOnAction(e -> {
            naviguezVers("/Reservation/Reservation.fxml");
        });
        btnJoueurs.setOnAction(e -> {
            naviguezVers("/Employee/AffichageJoueur.fxml");
        });
        btnContrats.setOnAction(e -> {
            naviguezVers("/Employee/Contrat.fxml");
        });
        btnBoutique.setOnAction(e -> {
            naviguezVers("/Boutique/Store.fxml");
        });
        btnElection.setOnAction(e -> {
            naviguezVers("/Election/DashbordElection.fxml");
        });
        btnArticlles.setOnAction(e -> {
            naviguezVers("/Article/afficherarticles.fxml");
        });
        btnReclamations.setOnAction(e -> {
            naviguezVers("/User/tablereclamation.fxml");
        });
        btnUsers.setOnAction(e -> {
            naviguezVers("/User/Crud.fxml");
        });
        btnSignout.setOnAction(e -> {
            Session.getSession().clearSession();
            naviguezVers("/User/Login.fxml");
        });

    }

    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            btnElection.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
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
