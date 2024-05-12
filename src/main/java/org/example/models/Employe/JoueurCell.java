package org.example.models.Employe;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class JoueurCell extends ListCell<Joueur> {
    private final GridPane gridPane = new GridPane();
    private final Label idLabel = new Label();
    private final Label positionLabel = new Label();
    private final Label hauteurLabel = new Label();
    private final Label poidsLabel = new Label();
    private final Label piedfortLabel = new Label();
    private final Label nomLabel = new Label();
    private final Label prenomLabel = new Label();
    private final Label ageLabel = new Label();
    private final Label imagePathLabel = new Label(); // Added imagePath Label
    private final Label shirtnumLabel = new Label(); // Added imagePath Label
    private ImageView importedImage;
    public JoueurCell() {
        super();

        // Define column width constraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(17);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(16);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(9);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(8);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(8);
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setPercentWidth(8);
        ColumnConstraints col7 = new ColumnConstraints();
        col7.setPercentWidth(9);
        ColumnConstraints col8 = new ColumnConstraints();
        col8.setPercentWidth(16);
        ColumnConstraints col9 = new ColumnConstraints();
        col9.setPercentWidth(9);




        // Add column constraints to the GridPane
        gridPane.getColumnConstraints().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9);
        gridPane.setAlignment(Pos.BASELINE_LEFT);

        // Add labels to the GridPane

        gridPane.add(nomLabel, 0, 0);
        gridPane.add(prenomLabel, 1, 0);
        gridPane.add(ageLabel, 2, 0);
        gridPane.add(piedfortLabel, 3, 0);
        gridPane.add(positionLabel, 4, 0);
        gridPane.add(hauteurLabel, 5, 0);
        gridPane.add(poidsLabel, 6, 0);
        gridPane.add(imagePathLabel, 7, 0);
        gridPane.add(shirtnumLabel, 8, 0);

//        nomLabel.getStyleClass().add("grid-cell");
//        prenomLabel.getStyleClass().add("grid-cell");
//        ageLabel.getStyleClass().add("grid-cell");
//        piedfortLabel.getStyleClass().add("grid-cell");
//        positionLabel.getStyleClass().add("grid-cell");
//        hauteurLabel.getStyleClass().add("grid-cell");
//        poidsLabel.getStyleClass().add("grid-cell");
//        imagePathLabel.getStyleClass().add("grid-cell");

        // Set horizontal gap between columns
        gridPane.setHgap(4);

        // Add style classes to labels if needed
        // Example: label.getStyleClass().add("centered-label");
    }

    @Override
    protected void updateItem(Joueur joueur, boolean empty) {
        super.updateItem(joueur, empty);

        if (empty || joueur == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Update labels with joueur attributes
           // idLabel.setText(String.valueOf(joueur.getId()));
            positionLabel.setText(joueur.getPosition());
            hauteurLabel.setText(String.valueOf(joueur.getHauteur()));
            poidsLabel.setText(String.valueOf(joueur.getPoids()));
            piedfortLabel.setText(String.valueOf(joueur.getPiedfort()));
            nomLabel.setText(joueur.getNom());
            prenomLabel.setText(joueur.getPrenom());
            ageLabel.setText(String.valueOf(joueur.getAge()));
            imagePathLabel.setText(joueur.getImagePath()); // Set imagePath Label text
            shirtnumLabel.setText(String.valueOf(joueur.getShirtnum()));


            // Set the GridPane as the graphic of the cell
            setGraphic(gridPane);
        }
    }
}
