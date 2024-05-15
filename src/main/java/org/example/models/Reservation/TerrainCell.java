package org.example.models.Reservation;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class TerrainCell extends ListCell<Terrain>  {

    private final GridPane gridPane = new GridPane();

    private final Label nomTerrain = new Label();
    private final Label adresse = new Label();
    private final Label description = new Label();
    private final Label ouverture = new Label();
    private final Label fermeture = new Label();
    private final Label datedispo = new Label();



    public TerrainCell() {
        super();

        // Définir la largeur des colonnes
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(23); // Largeur de la première colonne (pour le nom)
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(23); // Largeur de la deuxième colonne (pour le prix)
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(23);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(23);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(23);
        ColumnConstraints col6 = new ColumnConstraints();
        col5.setPercentWidth(23);

        // Ajouter les contraintes de colonnes au GridPane
        gridPane.getColumnConstraints().addAll(col1,col2,col3,col4,col5);
        gridPane.setAlignment(Pos.BASELINE_CENTER);

        // Ajouter les éléments au GridPane
        gridPane.add(nomTerrain, 0, 5); // Nom dans la première colonne
        gridPane.add(adresse, 1, 5);
        gridPane.add(description, 2, 5);
        gridPane.add(ouverture, 3, 5);
        gridPane.add(fermeture, 4, 5);
        gridPane.add(datedispo, 5, 5);


        // Espacement horizontal entre les colonnes
        gridPane.setHgap(5);
        nomTerrain.getStyleClass().add("centered-label");
        description.getStyleClass().add("centered-label");
        adresse.getStyleClass().add("centered-label");
        ouverture.getStyleClass().add("centered-label");
        fermeture.getStyleClass().add("centered-label");
        datedispo.getStyleClass().add("centered-label");


    }

    @Override
    protected void updateItem(Terrain terrain, boolean empty) {
        super.updateItem(terrain, empty);

        if (empty || terrain == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Mise à jour des labels avec les attributs du Terrain

            nomTerrain.setText(terrain.getNomTerrain());
            adresse.setText(terrain.getAdresse());
            description.setText(terrain.getDescription());
            ouverture.setText(terrain.getOuverture());
            fermeture.setText(terrain.getFermeture());


            // Définir le GridPane comme le graphique de la cellule
            setGraphic(gridPane);
        }
    }
}


