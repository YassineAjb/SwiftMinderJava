package org.example.controllers.Election.Election;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Election.Election;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ELectionItemController implements Initializable {


    @FXML
    private Label periodeItem;

    @FXML
    private Label nomItem;

    @FXML
    private Label dateItem;

    @FXML
    private ImageView imgItem;

    @FXML
    private Label posteItem;



    public void setData(Election election) {
        nomItem.setText(election.getNomE());
        posteItem.setText(election.getPosteE());
        periodeItem.setText(election.getPeriodeP());
        dateItem.setText(String.valueOf(election.getDateE()));

        String imagePath ="C:/xampp/htdocs/Images/Elections/" + election.getImgEpath();
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        imgItem.setImage(image);

//        if (imagePath != null && !imagePath.isEmpty()) {
//            // Load the image from the specified path
//            try (InputStream stream = getClass().getResourceAsStream(imagePath)) {
//                if (stream != null) {
//                    Image image = new Image(stream);
//                    imgItem.setImage(image);
//                } else {
//                    System.err.println("Image not found:" + imagePath);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.err.println("Error loading image:" + e.getMessage());
//            }
//        } else {
//            // Handle the case when the image path is not provided in the database
//            System.err.println("Image path not found in the database for election: " + election.getNomE());
//        }
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

    }

}
