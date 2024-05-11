// ReservationCell.java
package org.example.models.Reservation;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class ReservationCell extends ListCell<Reservation>  {

    private final GridPane gridPane = new GridPane();
    private final Label choixTerrain = new Label();
    private final Label dateReservation = new Label();
    private final Label note = new Label();
    private final Label emplacement = new Label();

    public ReservationCell() {
        super();

        // Define column widths
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(25);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(25);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(25);

        // Add column constraints to GridPane
        gridPane.getColumnConstraints().addAll(col1, col2, col3, col4);
        gridPane.setAlignment(Pos.BASELINE_CENTER);

        // Add elements to GridPane
        gridPane.add(choixTerrain, 0, 0);
        gridPane.add(dateReservation, 1, 0);
        gridPane.add(note, 2, 0);
        gridPane.add(emplacement, 3, 0);

        // Horizontal spacing between columns
        gridPane.setHgap(5);
        choixTerrain.getStyleClass().add("centered-label");
        dateReservation.getStyleClass().add("centered-label");
        note.getStyleClass().add("centered-label");
        emplacement.getStyleClass().add("centered-label");
    }

    @Override
    protected void updateItem(Reservation reservation, boolean empty) {
        super.updateItem(reservation, empty);

        if (empty || reservation == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Update labels with Reservation attributes
            choixTerrain.setText(reservation.getChoixTerrain());
            dateReservation.setText(reservation.getDateReservation());
            note.setText(reservation.getNote());
            emplacement.setText(reservation.getEmplacement());

            // Set GridPane as the graphic of the cell
            setGraphic(gridPane);
        }
    }
}
