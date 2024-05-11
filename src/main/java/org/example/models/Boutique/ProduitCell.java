//package org.example.models.Boutique;
//
//import javafx.geometry.Pos;
//import javafx.scene.control.ListCell;
//import javafx.scene.control.Label;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.ColumnConstraints;
//import org.example.models.Produit;
//
//public class ProduitCell extends ListCell<Produit> {
//    private final GridPane gridPane = new GridPane();
//    private final Label nomLabel = new Label();
//    private final Label prixLabel = new Label();
//    private final Label tailleLabel = new Label();
//    private final Label typeLabel = new Label();
//    private final Label quantiteLabel = new Label();
//    private final Label image = new Label();
//
//    public ProduitCell() {
//        super();
//
//        ColumnConstraints col1 = new ColumnConstraints();
//        col1.setPercentWidth(20);
//        ColumnConstraints col2 = new ColumnConstraints();
//        col2.setPercentWidth(20);
//        ColumnConstraints col3 = new ColumnConstraints();
//        col3.setPercentWidth(20);
//        ColumnConstraints col4 = new ColumnConstraints();
//        col4.setPercentWidth(20);
//        ColumnConstraints col5 = new ColumnConstraints();
//        col5.setPercentWidth(20);
//        gridPane.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
//        gridPane.setHgap(10); // Augmenter l'espacement horizontal
//        gridPane.setAlignment(Pos.CENTER_LEFT); // Alignement à gauche
//        gridPane.add(nomLabel, 0, 0);
//        gridPane.add(prixLabel, 1, 0);
//        gridPane.add(tailleLabel, 2, 0);
//        gridPane.add(typeLabel, 3, 0);
//        gridPane.add(quantiteLabel, 4, 0);
//        nomLabel.getStyleClass().add("column-label");
//        prixLabel.getStyleClass().add("column-label");
//        tailleLabel.getStyleClass().add("column-label");
//        typeLabel.getStyleClass().add("column-label");
//        quantiteLabel.getStyleClass().add("column-label");
//
//        // Style CSS pour améliorer l'apparence des étiquettes
//        nomLabel.setStyle("-fx-font-weight: bold;");
//        prixLabel.setStyle("-fx-font-weight: bold;");
//        tailleLabel.setStyle("-fx-font-weight: bold;");
//        typeLabel.setStyle("-fx-font-weight: bold;");
//        quantiteLabel.setStyle("-fx-font-weight: bold;");
//    }
//
//    @Override
//    protected void updateItem(Produit produit, boolean empty) {
//        super.updateItem(produit, empty);
//
//        if (empty || produit == null) {
//            setText(null);
//            setGraphic(null);
//        } else {
//            nomLabel.setText(produit.getNomProduit());
//            prixLabel.setText(String.valueOf(produit.getPrixProduit()));
//            tailleLabel.setText(produit.getTailleProduit());
//            typeLabel.setText(produit.getType());
//            quantiteLabel.setText(String.valueOf(produit.getQuantiteProduit()));
//            image.setText(String.valueOf(produit.getImage()));
//            setGraphic(gridPane);
//        }
//    }
//}
