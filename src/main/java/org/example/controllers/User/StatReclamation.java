package org.example.controllers.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import org.example.services.User.CrudReclamation;

import java.net.URL;
import java.util.ResourceBundle;

public class StatReclamation implements Initializable {

    @javafx.fxml.FXML
    private PieChart pieChart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CrudReclamation cr=new CrudReclamation();
        int traiter=cr.getReclmationByEtat(true);
        int non_traiter = cr.getReclmationByEtat(false);
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Reclmation traité : "+traiter, traiter),
                        new PieChart.Data("Reclamation non traité : "+non_traiter, non_traiter));
                        pieChart.getData().addAll(pieChartData);
    }
}

