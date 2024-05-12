package org.example.controllers.Election.Election;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Election.Election;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ELectionItemController implements Initializable {


    @FXML
    private Label periodeItem;

    @FXML
    private Label nomItem;
    @FXML
    private Button btnArticlles;

    @FXML
    private Label dateItem;

    @FXML
    private ImageView imgItem;

    @FXML
    private Label posteItem;

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

    public void setData(Election election) {
        nomItem.setText(election.getNomE());
        posteItem.setText(election.getPosteE());
        periodeItem.setText(election.getPeriodeP());
        dateItem.setText(String.valueOf(election.getDateE()));

        String imagePath = election.getImgEpath();

        if (imagePath != null && !imagePath.isEmpty()) {
            // Load the image from the specified path
            try (InputStream stream = getClass().getResourceAsStream(imagePath)) {
                if (stream != null) {
                    Image image = new Image(stream);
                    imgItem.setImage(image);
                } else {
                    System.err.println("Image not found:" + imagePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error loading image:" + e.getMessage());
            }
        } else {
            // Handle the case when the image path is not provided in the database
            System.err.println("Image path not found in the database for election: " + election.getNomE());
        }
    }

    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            nomItem.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

}
