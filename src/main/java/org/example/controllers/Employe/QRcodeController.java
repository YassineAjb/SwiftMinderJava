package org.example.controllers.Employe;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Employe.Joueur;

import java.nio.file.Path;
import java.nio.file.Paths;

public class QRcodeController {
    public static String getProjectPath() {
        Path currentPath = Paths.get("");
        return currentPath.toString();
    }

    @FXML
    private ImageView QRcode;

    public void setData(Joueur joueur) {
        System.out.println(getProjectPath());
        Image image = new Image("/Employee/QR/"+ joueur.getId() +".png");
        QRcode.setImage(image);
    }

}
