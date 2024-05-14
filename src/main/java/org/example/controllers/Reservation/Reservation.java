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
import org.example.models.Reservation.TerrainCell;
import org.example.services.Reservation.ServiceTerrain;
import org.example.models.Reservation.Terrain;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Reservation {
//Affichage liste de terrains lel ADMIN Apres il y'a crud Terrains  :

    @FXML
    private ListView<Terrain> affichage;

    @FXML
    private Button btnAjouter ;

    @FXML
    private Button btnModifier ;
    @FXML
    private Button btnArticlles;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnReclamations;

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
        ServiceTerrain serviceProduit = new ServiceTerrain();
        try {
            List<Terrain> produits = serviceProduit.afficher();
            ObservableList<Terrain> observableArrayList = FXCollections.observableArrayList(produits);

            affichage.setCellFactory(new Callback<ListView<Terrain>, ListCell<Terrain>>() {
                @Override
                public ListCell<Terrain> call(ListView<Terrain> listView) {
                    return new TerrainCell();
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

    }

    @FXML
    void supprimerTerrain(ActionEvent event) throws SQLException {
        ServiceTerrain serviceTerrain = new ServiceTerrain();


        Terrain selectedTerrain = affichage.getSelectionModel().getSelectedItem();
        System.out.println(selectedTerrain);
        if (selectedTerrain != null) {
            int idTerrain = selectedTerrain.getId(); // Supposons que getId() renvoie l'ID du produit
            System.out.println("ID du Terrain sélectionné : " + idTerrain);
            serviceTerrain.supprimer(idTerrain);
            this.initialize();
        } else {
            // Aucun produit sélectionné, gestion de l'erreur ou notification à l'utilisateur
            System.out.println("Aucun Terrain sélectionné.");
        }


    }



    @FXML
    private void handleAjouterButtonAction(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation/AjouterTerrain.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) btnAjouter.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierTerrain(ActionEvent event) {
        Terrain selectedTerrain = affichage.getSelectionModel().getSelectedItem();
        if (selectedTerrain != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation/ModifierTerrain.fxml"));
                Parent root = loader.load();
                ModifierTerrain controller = loader.getController();
                controller.initData(selectedTerrain);
                Stage stage = (Stage) btnModifier.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        }}





    }


















