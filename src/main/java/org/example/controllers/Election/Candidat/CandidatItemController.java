package org.example.controllers.Election.Candidat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Election.Candidat;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CandidatItemController implements Initializable {



    @FXML
    private ImageView imgItemCandidat;

    @FXML
    private Button btnReclamations;
    @FXML
    private Label nomItemCandidat;
    @FXML
    private Label ageItemCandidat;
    @FXML
    private Label penomItemCandidat;



    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            ageItemCandidat.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    public void setData(Candidat candidat) {
        nomItemCandidat.setText(candidat.getNomC());
        penomItemCandidat.setText(candidat.getPrenomC());
        ageItemCandidat.setText(String.valueOf(candidat.getAgeC()));

        String imageCPath =  "C:/xampp/htdocs/Images/Candidats/"+candidat.getImgCpath();
        File file = new File(imageCPath);
        Image image = new Image(file.toURI().toString());
        imgItemCandidat.setImage(image);


//        if (imageCPath != null && !imageCPath.isEmpty()) {
//            // Load the image from the specified path
//            try (InputStream stream = getClass().getResourceAsStream(imageCPath)) {
//                if (stream != null) {
//                    Image image = new Image(stream);
//                    imgItemCandidat.setImage(image);
//                } else {
//                    System.err.println("Image not found:" + imageCPath);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.err.println("Error loading image:" + e.getMessage());
//            }
//        } else {
//            // Handle the case when the image path is not provided in the database
//            System.err.println("Image path not found in the database for Candidat: " + candidat.getNomC());
//        }

    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
