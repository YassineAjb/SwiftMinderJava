package org.example.controllers.Reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.models.Reservation.Reservation;
import org.example.models.Reservation.ReservationCell;
import org.example.services.Reservation.ServiceReservation;
import org.example.utils.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListeReservation {
    @FXML
    private Button btnUsers;

    @FXML
    private Button btnReclamations;
    @FXML
    private Button btnBoutique;
    @FXML
    private Button btnArticlles;
    @FXML
    private Button btnTerrain;
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
    private ListView<Reservation> affichage;
    @FXML
    private Button btnReserver;

    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            btnSignout.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        ServiceReservation serviceProduit = new ServiceReservation();
        try {
            List<Reservation> produits = serviceProduit.afficher();
            ObservableList<Reservation> observableArrayList = FXCollections.observableArrayList(produits);

            affichage.setCellFactory(new Callback<ListView<Reservation>, ListCell<Reservation>>() {
                @Override
                public ListCell<Reservation> call(ListView<Reservation> listView) {
                    return new ReservationCell();
                }
            });

            affichage.setItems(observableArrayList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

    @FXML
    void supprimerReservation(ActionEvent event) throws SQLException {
        ServiceReservation serviceReservation = new ServiceReservation();

        Reservation selectedReservation = affichage.getSelectionModel().getSelectedItem();
        System.out.println(selectedReservation);
        if (selectedReservation != null) {
            int idReservation = selectedReservation.getReservationID();
            System.out.println("ID du produit sélectionné : " + idReservation);
            serviceReservation.supprimer(idReservation);
            this.initialize();
        } else {
            System.out.println("Aucune Reservation sélectionné.");
        }


    }

    @FXML
    void modifierReservation(ActionEvent event) {
        Reservation selectedReservation = affichage.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation/ModifierReservation.fxml"));
                Parent root = loader.load();
                ModifierReservation controller = loader.getController();
                controller.initData(selectedReservation); // Passer le Reservation sélectionné au contrôleur ModifierReservation
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }}


    @FXML
    private void handleReserverButtonAction(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation/AjouterTerrain.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
