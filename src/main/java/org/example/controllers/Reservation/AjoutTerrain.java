package org.example.controllers.Reservation;

import com.dlsc.gemsfx.TimePicker;
import com.dlsc.gemsfx.daterange.DateRangePicker;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.services.Reservation.ServiceTerrain;
import org.example.models.Reservation.Terrain;
import java.io.IOException;
import java.sql.SQLException;

public class AjoutTerrain {

    @FXML
    private VBox Emplacement;

    @FXML
    private TextField GeoX;

    @FXML
    private TextField GeoY;

    private MapView mapView;

    @FXML
    private TextField nom_Terrain;

    @FXML
    private TextField adresse;

    @FXML
    private TextField description;

    @FXML
    private TimePicker ouverture;

    @FXML
    private TimePicker fermeture;

    @FXML
    private DateRangePicker datedispo;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ImageView Retour;

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

    public void initialize() {
        Retour.setOnMouseClicked(event -> {
            loadListeReservationView();
        });

        mapView = createMapView();
        Emplacement.getChildren().add(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);
        // Set initial values for GeoX and GeoY
        GeoX.setText("36.90367103784889");
        GeoY.setText("10.190358937780948");
        updateMapPoint(); // Update initially

        btnContrats.setOnAction(e -> {
            naviguezVers("/Employee/Contrat.fxml");
        });
        btnBoutique.setOnAction(e -> {
            naviguezVers("/Boutique/AfficherProduit.fxml");
        });
        btnElection.setOnAction(e -> {
            naviguezVers("/Election/DashbordElection.fxml");
        });
        btnReservation.setOnAction(e -> {
            naviguezVers("/Reservation/Reservation.fxml");
        });
        btnJoueurs.setOnAction(e -> {
            naviguezVers("/Employee/AffichageJoueur.fxml");
        });
        btnMatch.setOnAction(e -> {
            naviguezVers("/Article/affichermatch.fxml");
        });
    }

    @FXML
    private void addTerrain() {
        // Validate the nom_Terrain TextField
        String nomTerrain = nom_Terrain.getText().trim();
        if (nomTerrain.isEmpty()) {
            showAlert("Saisie invalide", "Veuillez saisir un nom de terrain valide.");
            return;
        }

        // Validate the adresse TextField
        String adresseValue = adresse.getText().trim();
        if (adresseValue.isEmpty()) {
            showAlert("Saisie invalide", "Veuillez saisir une adresse valide.");
            return;
        }

        // Validate that both ouverture and fermeture are selected
        if (ouverture.getTime() == null || fermeture.getTime() == null) {
            showAlert("Saisie invalide", "Veuillez sélectionner à la fois l'heure d'ouverture et l'heure de fermeture.");
            return;
        }

        // Validate that ouverture is before fermeture
        if (ouverture.getTime().isAfter(fermeture.getTime())) {
            showAlert("Saisie invalide", "L'heure de fermeture doit être après l'heure d'ouverture.");
            return;
        }

        // Continue with the rest of the data processing
        double GeoXValue;
        double GeoYValue;
        try {
            GeoXValue = Double.parseDouble(GeoX.getText());
            GeoYValue = Double.parseDouble(GeoY.getText());
        } catch (NumberFormatException e) {
            return;
        }

        String descriptionValue = description.getText().trim();
        String ouvertureValue = String.valueOf(ouverture.getTime());
        String fermetureValue = String.valueOf(fermeture.getTime());
        String datedispoValue = datedispo.getValue().toString();

        Terrain terrain = new Terrain(nomTerrain, adresseValue, descriptionValue, GeoXValue, GeoYValue, ouvertureValue, fermetureValue, datedispoValue);

        // Call the ServiceTerrain method to add terrain
        ServiceTerrain serviceTerrain = new ServiceTerrain();
        try {
            serviceTerrain.ajouter(terrain);

            // Show a success alert
            showAlert("Succès", "Terrain ajouté avec succès.");

            System.out.println(datedispo.getValue().toString());
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    private MapView createMapView() {
        MapView mapView = new MapView();
        mapView.setPrefSize(400, 400);
        mapView.setZoom(16);
        updateMapPoint();

        return mapView;
    }

    private void updateMapPoint() {
        try {
            double x = Double.parseDouble(GeoX.getText());
            double y = Double.parseDouble(GeoY.getText());
            MapPoint newPoint = new MapPoint(x, y);
            mapView.flyTo(0, newPoint, 0.1);
        } catch (NumberFormatException e) {

        }
    }

    @FXML
    private void onVerifyButtonClicked() {
        updateMapPoint();
        // Add any additional verification logic here
    }

    private void loadListeReservationView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation/Reservation.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) Retour.getScene().getWindow();

            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
