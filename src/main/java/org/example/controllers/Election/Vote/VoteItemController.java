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
import org.example.utils.Session;

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
    private Button btnArticlles;

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

  /*  public void setData(Vote vote) {
        int idVote=vote.getIdV() ;
        nomVoteItemCandidat.setText(vote.getNomCandidatbyId(idVote));
        prenomVoteItemCandidat.setText(vote.getPrenomCandidatbyId(idVote));
        nomVoteItemElection.setText(vote.getNomElectionbyId(idVote));

        String imagePath = vote.getImgCpathbyId(idVote);

        if (imagePath != null && !imagePath.isEmpty()) {
            // Load the image from the specified path
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
            // Handle the case when the image path is not provided in the database
            System.err.println("Image path not found in the database for election: " + vote.getNomE());
        }
    }*/

    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            nomVoteItemCandidat.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setData(Vote vote, Candidat candidat, Election election) {
        if (candidat != null ||election != null ) {
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


}
