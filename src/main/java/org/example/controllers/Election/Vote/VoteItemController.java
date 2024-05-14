package org.example.controllers.Election.Vote;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Election.Candidat;
import org.example.models.Election.Election;
import org.example.models.Election.Vote;
import org.example.services.Election.CandidatService;
import org.example.services.Election.ElectionService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class VoteItemController implements Initializable {



    @FXML
    private Label nomVoteItemElection;

    @FXML
    private ImageView imgVoteItemCandidat;

    @FXML
    private Label prenomVoteItemCandidat;

    @FXML
    private Label nomVoteItemCandidat;

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
    private final CandidatService candidatService = new CandidatService();
    private final ElectionService electionService = new ElectionService();



    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            nomVoteItemCandidat.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setData(Candidat candidat, Election election) {
        if (candidat != null ||election != null ) {
            assert candidat != null;
            System.out.println("nom candidat votee   "+candidat.getNomC() );
            nomVoteItemCandidat.setText(candidat.getNomC());
            prenomVoteItemCandidat.setText(candidat.getPrenomC());
            nomVoteItemElection.setText(election.getNomE());


            String imagePath = candidat.getImgCpath();

            if (imagePath != null && !imagePath.isEmpty()) {
                try (InputStream stream = getClass().getResourceAsStream(imagePath)) {
                    if (stream != null) {
                        Image image = new Image(stream);
                        imgVoteItemCandidat.setImage(image);
                    } else {
                        System.err.println("Image not found:" + imagePath);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Error loading image:" + e.getMessage());
                }
            } else {
                System.err.println("Image path not found in the database for candidat: " + candidat.getIdC());
            }
        }

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
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


}
