package org.example.controllers.Employe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.models.Employe.Joueur;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JoueurRowController {

    @FXML
    private Label JoueurAge;

    @FXML
    private ImageView JoueurImg;

    @FXML
    private Label JoueurNom;

    @FXML
    private Label JoueurPrenom;

    @FXML
    private Button JoueurDetails;

    private Joueur joueur;

    public static String getProjectPath() {
        Path currentPath = Paths.get("").toAbsolutePath();
        return currentPath.toString();
    }

    public void setData(Joueur joueur){
        JoueurAge.setText(String.valueOf(joueur.getAge()));
        JoueurNom.setText(joueur.getNom());
        JoueurNom.setWrapText(true);
        JoueurPrenom.setText(joueur.getPrenom());
        Image image = new Image("/Employee/images/"+joueur.getImagePath());
        JoueurImg.setImage(image);
        JoueurDetails.setText("Plus de details");
        JoueurDetails.setOnAction(e -> {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Employee/QRcode.fxml"));
            try {
                Parent root = fxmlLoader.load();
                QRcodeController qRcodeController = fxmlLoader.getController();
                qRcodeController.setData(joueur);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }



}
