package org.example.controllers.Reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.models.Reservation.Reservation;
import org.example.services.Reservation.ServiceReservation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListeReservation {

    @FXML
    private ListView<Reservation> affichage;

    private Button btnReserver;

    private List<Reservation> produits;

    @FXML
    private GridPane produitContainer;
    @FXML
    private Button btnAcceuil;

    @FXML
    private Button btnBoutique;

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

    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            btnAcceuil.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        ServiceReservation serviceProduit = new ServiceReservation();
        try {
            produits = serviceProduit.afficher();
            afficherProduits();
        } catch (SQLException e) {
            e.printStackTrace();
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

    }



    private void afficherProduits() {
        produitContainer.getChildren().clear();
        int column = 0;
        int row = 1;
        try {
            for (Reservation produit : produits) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Reservation/Carte.fxml"));

                AnchorPane cardPane = fxmlLoader.load();

                // Get the controller associated with the "JoueurCell.fxml" file
                Carte carteController = fxmlLoader.getController();

                // Initialize the card with data
                carteController.initializeCard(produit);

                produitContainer.add(cardPane, column++, row);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                produitContainer.setMargin(cardPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Terrain.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
