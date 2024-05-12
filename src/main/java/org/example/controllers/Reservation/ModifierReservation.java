package org.example.controllers.Reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.models.Reservation.Reservation;
import org.example.services.Reservation.ServiceReservation;
import org.example.services.Reservation.ServiceTerrain;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierReservation {


    @FXML
    private ImageView Retour;
    @FXML
    private Button btnArticlles;

    @FXML
    private ChoiceBox<String> Choixterrain;

    @FXML
    private ListView<String> DateReservation;
    ServiceTerrain serviceTerrain = new ServiceTerrain();
    private String Emplacement;

    @FXML
    private TextField Note;
    private int idReservation;

    private int reservation ;
    private final ServiceReservation sr = new ServiceReservation();

    private String[] lol = {"9-10:30", "10-11:30", "12-13:30", "14-15:30", "16 - 17:30", "17:30-19"};

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
    private void initialize() {
        DateReservation.getItems().addAll(lol);

        Retour.setOnMouseClicked(event -> {
            loadListeReservationView();
        });

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
    }


    @FXML
    void modifierReservation(ActionEvent event) {
        try {
            sr.modifier(new Reservation(serviceTerrain.afficherTerrain(Choixterrain.getValue()),DateReservation.getItems().toString(),Note.getText(),Emplacement));
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

    }

    public void initData(Reservation selectedReservation) {
        if (selectedReservation != null) {
            // Populate UI components with data from the selectedReservation object
            Choixterrain.setValue(selectedReservation.getChoixTerrain());

            // Assuming DateReservation.getItems() is a List<String>
            DateReservation.getItems().setAll(selectedReservation.getDateReservation());

            Note.setText(selectedReservation.getNote());
            // Set Emplacement based on your logic
        } else {
            System.out.println("Selected reservation is null.");
        }
    }

    private void loadListeReservationView() {
        try {
            // Load the ListeReservation.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation/listeReservation.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the current stage (window)
            Stage stage = (Stage) Retour.getScene().getWindow();

            // Set the scene on the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }

}
