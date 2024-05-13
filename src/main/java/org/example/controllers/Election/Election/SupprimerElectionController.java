package org.example.controllers.Election.Election;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.models.Election.Election;
import org.example.services.Election.ElectionService;
import org.example.utils.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SupprimerElectionController {

    @FXML
    private Button btnAcceuil;

    @FXML
    private Button btnBoutique;
    @FXML
    private Button btnArticlles;

    @FXML
    private Button btnContrats;

    @FXML
    private Button btnElection;

    @FXML
    private Button btnJoueurs;

    @FXML
    private Button btnMatch;

    @FXML
    private Button btnReservation;

    @FXML
    private Button btnSignout;
    @FXML
    private TableView<Election> tableViewS;

    @FXML
    private TextField idTFS;

    @FXML
    private TableColumn<?, ?> dateColS;

    @FXML
    private TableColumn<?, ?> descriptionColS;

    @FXML
    private TableColumn<?, ?> nomColS;
    private final ElectionService ps = new ElectionService();
    @FXML
    void supprimerElection(ActionEvent event) {
            try {
                int id = Integer.parseInt(idTFS.getText());

                // Assuming supprimerParId is a void method
                ps.supprimer(id);
                refreshTableView();
                // Assuming the method doesn't throw an exception on failure
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setContentText("L'élection a été supprimée avec succès.");
                alert.showAndWait();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("L'ID doit être valide.");
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();            }
        }
    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            tableViewS.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void initialize() {
        try {
            List<Election> elections = ps.recuperer();
            ObservableList<Election> observableList = FXCollections.observableList(elections);
            tableViewS.setItems(observableList);

            nomColS.setCellValueFactory(new PropertyValueFactory<>("nomE"));
            descriptionColS.setCellValueFactory(new PropertyValueFactory<>("description"));
            dateColS.setCellValueFactory(new PropertyValueFactory<>("dateE"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        btnMatch.setOnAction(e -> {
            naviguezVers("/Article/affichermatch.fxml");
        });
        btnReservation.setOnAction(e -> {
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
        btnSignout.setOnAction(e -> {
            Session.getSession().clearSession();
            naviguezVers("/User/Login.fxml");
        });

    }
    private void refreshTableView() {
        try {
            List<Election> elections = ps.recuperer();
            ObservableList<Election> observableList = FXCollections.observableList(elections);
            tableViewS.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void naviguezSVersAjout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/AjouterElection.fxml"));
            tableViewS.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezSVersModif(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/ModifierElection.fxml"));
            tableViewS.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/AfficherElection.fxml"));
            tableViewS.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}




