package org.example.controllers.Election.Vote;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Election.Candidat;
import org.example.models.Election.Election;
import org.example.services.Election.CandidatService;
import org.example.services.Election.ElectionService;

import java.io.File;
import java.io.IOException;
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

            String imagePath ="C:/xampp/htdocs/Images/Candidats/" + candidat.getImgCpath();
            File file = new File(imagePath);
            Image image = new Image(file.toURI().toString());
            imgVoteItemCandidat.setImage(image);

            /*String imagePath = candidat.getImgCpath();

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
            }*/
        }

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
