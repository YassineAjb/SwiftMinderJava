// Terrain.java
package org.example.controllers.Reservation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.models.Reservation.Reservation;
import org.example.services.Reservation.ServiceReservation;
import org.example.services.Reservation.ServiceTerrain;
import org.example.utils.MyDataBase;
import org.example.utils.Session;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Terrain {
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
    private ChoiceBox<String> Choixterrain;

    @FXML
    private ListView<String> DateReservation;

    @FXML
    private TextField Note;

    @FXML
    private Button addReservation;

    @FXML
    private VBox Emplacement;

    private final ServiceReservation serviceReservation = new ServiceReservation();

    private final String GET_TERRAINS_QUERY = "SELECT nom_terrain FROM terrain";

    @FXML
    private ImageView Retour;

    private String[] lol = {"9-10:30", "10-11:30", "12-13:30", "14-15:30", "16 - 17:30", "17:30-19"};

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
    private void initialize() {

        populateTerrainChoiceBox();

        DateReservation.getItems().addAll(lol);

        Retour.setOnMouseClicked(event -> {
            loadListeReservationView();
        });

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
        private void handleReserverButtonAction() throws SQLException {
            // This method will be called when the "Reserver" button is pressed
            ServiceTerrain serviceTerrain = new ServiceTerrain();
            int id = serviceTerrain.afficherTerrain(Choixterrain.getValue());

            // Get data from controls
            int choixTerrain = id;
            String dateReservation = DateReservation.getSelectionModel().getSelectedItem();
            String note = Note.getText();
            // You may need to handle Emplacement as well

            // Create a Reservation object with the obtained data
            Reservation reservation = new Reservation(id, dateReservation, note, null);  // Set null for Emplacement for now


            // Add the reservation to the database
            serviceReservation.addReservation(reservation,Choixterrain.getValue());

        }

    private void populateTerrainChoiceBox() {
        try {
            PreparedStatement preparedStatement = MyDataBase.getInstance().getConnection().prepareStatement(GET_TERRAINS_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> terrainList = new ArrayList<>();

            while (resultSet.next()) {
                String nomTerrain = resultSet.getString("nom_terrain");
                terrainList.add(nomTerrain);
            }

            Choixterrain.getItems().addAll(terrainList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadListeReservationView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation/listeReservation.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) Retour.getScene().getWindow();

            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }
