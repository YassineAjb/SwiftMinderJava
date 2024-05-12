package org.example.controllers.Reservation;

import com.dlsc.gemsfx.TimePicker;
import com.dlsc.gemsfx.daterange.DateRangePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.models.Reservation.Terrain;
import org.example.services.Reservation.ServiceTerrain;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

public class ModifierTerrain {

    @FXML
    private VBox Emplacement;

    @FXML
    private TextField GeoX;

    @FXML
    private TextField GeoY;

    @FXML
    private Button Verifier;

    @FXML
    private TextField adresse;

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

    @FXML
    private Button btncalendrier;

    @FXML
    private DateRangePicker datedispo;

    @FXML
    private TextField description;

    @FXML
    private TimePicker fermeture;

    @FXML
    private TextField nom_Terrain;

    @FXML
    private TimePicker ouverture;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlMenus;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;
    private final ServiceTerrain sr = new ServiceTerrain();


    @FXML
    private ImageView Retour;

    private int idTerrain;
    private Terrain terrain;

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
    }
    @FXML
    void modifierTerrain(ActionEvent event) {
        try {
            sr.modifier(new Terrain(idTerrain,nom_Terrain.getText(),adresse.getText(),description.getText(),Double.parseDouble(GeoX.getText()),Double.parseDouble(GeoY.getText()),String.valueOf(ouverture.getTime()),String.valueOf(fermeture.getTime()),datedispo.getValue().toString()));
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }



    }

    @FXML
    void onVerifyButtonClicked(ActionEvent event) {

    }
    //    public Terrain(int id, String nomTerrain, String adresse, String description, double geoX, double geoY, String ouverture, String fermeture, String datedispo)
    public void initData(Terrain selectedTerrain) {
        if (selectedTerrain != null) {
            this.terrain = selectedTerrain;
            idTerrain = terrain.getId();
            nom_Terrain.setText(terrain.getNomTerrain());
            adresse.setText(String.valueOf(terrain.getAdresse()));
            ouverture.setTime(LocalTime.parse(String.valueOf(terrain.getOuverture())));
            fermeture.setTime(LocalTime.parse(String.valueOf(terrain.getFermeture())));

            String tooltipText = String.valueOf(terrain.getDatedispo());
            Tooltip tooltip = new Tooltip(tooltipText);
            datedispo.setTooltip(tooltip);

            description.setText(String.valueOf(terrain.getDescription()));
            GeoX.setText(String.valueOf(terrain.getGeoX()));
            GeoY.setText(String.valueOf(terrain.getGeoY()));
        } else {
            System.out.println("Selected terrain is null.");
        }
    }


    private void loadListeReservationView() {
        try {
            // Load the ListeReservation.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation/Reservation.fxml"));
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
