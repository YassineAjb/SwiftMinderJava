package org.example.controllers.Reservation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Reservation.Reservation;

public class Carte {

    @FXML
    private Label Nomterrain;

    @FXML
    private Label Note ;

    @FXML
    private ImageView imageView;

    public void initializeCard(Reservation cardData) {
        String relativeImagePath = "/images/Esprit2.png";
        Image image = new Image(getClass().getResource(relativeImagePath).toString());


        String choixTerrain = cardData.getChoixTerrain() ;
        String terrainName;

        // Conditionally set the text based on choixTerrain
        if (choixTerrain.equals("35")) {
            terrainName = "Terrain Esprit 1";
        } else if (choixTerrain.equals("36")) {
            terrainName = "Terrain Esprit 3";
        } else {
            // Handle other cases if needed
            terrainName = "Unknown Terrain";
        }

        Nomterrain.setText(terrainName);
        Note.setText(cardData.getNote());
    }



}
