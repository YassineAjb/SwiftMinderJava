package org.example.models.Employe;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.sql.Date;

public class ContratCell extends ListCell<Contrat> {
    private final GridPane gridPane = new GridPane();
    private final Label idLabel = new Label();
    private final Label idEmployeLabel = new Label();
    private final Label datedebutLabel = new Label();
    private final Label datefinLabel = new Label();
    private final Label salaireLabel = new Label();

    public ContratCell() {
        super();

        // Define column width constraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(20);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(20);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(20);
        ColumnConstraints col5 = new ColumnConstraints();
        col4.setPercentWidth(20);

        // Add column constraints to the GridPane
        gridPane.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
        gridPane.setAlignment(Pos.BASELINE_CENTER);

        // Add labels to the GridPane
        gridPane.add(idLabel, 0, 1);
        gridPane.add(idEmployeLabel, 1, 1);
        gridPane.add(datedebutLabel, 2, 1);
        gridPane.add(datefinLabel, 3, 1);
        gridPane.add(salaireLabel, 4, 1);

        // Set horizontal gap between columns
        gridPane.setHgap(5);
    }

    @Override
    protected void updateItem(Contrat contrat, boolean empty) {
        super.updateItem(contrat, empty);

        if (empty || contrat == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Update labels with contrat attributes
            idLabel.setText(String.valueOf(contrat.getId()));
            idEmployeLabel.setText(String.valueOf(contrat.getJoueur().getId()));
            datedebutLabel.setText(String.valueOf(Date.valueOf(contrat.getdate().getValue().getStartDate())));
            datefinLabel.setText(String.valueOf(Date.valueOf(contrat.getdate().getValue().getEndDate())));
            salaireLabel.setText(String.valueOf(contrat.getSalaire()));

            // Set the GridPane as the graphic of the cell
            setGraphic(gridPane);
        }
    }
}