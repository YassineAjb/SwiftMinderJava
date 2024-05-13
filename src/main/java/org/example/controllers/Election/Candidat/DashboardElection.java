package org.example.controllers.Election.Candidat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import org.example.models.Election.Election;
import org.example.services.Election.ElectionService;
import org.example.utils.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardElection implements Initializable {

    private ElectionService electionService = new ElectionService();

    @FXML
    private PieChart pieChart;
    @FXML
    private Button btnArticlles;
    @FXML
    private PieChart pieChart2;

    @FXML
    private Button btnAcceuil;

    @FXML
    private Button btnBoutique;

    @FXML
    private Button btnContrats;

    @FXML
    private Button btnElection;

    @FXML
    private Button btnJoueurs;

    @FXML
    private Button btnMatch;

    @FXML
    private Button btnReservation;

    @FXML
    private Button btnSignout;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadStatistics();
        loadStatisticsRating();

        btnMatch.setOnAction(e -> {
            naviguezVers("/Article/affichermatch.fxml");
        });
        btnReservation.setOnAction(e -> {
            naviguezVers("/Reservation/Reservation.fxml");
        });
        btnJoueurs.setOnAction(e -> {
            naviguezVers("/Employee/AffichageJoueur.fxml");
        });
        btnContrats.setOnAction(e -> {
            naviguezVers("/Employee/Contrat.fxml");
        });
        btnBoutique.setOnAction(e -> {
            naviguezVers("/Boutique/Store.fxml");
        });
        btnElection.setOnAction(e -> {
            naviguezVers("/Election/DashbordElection.fxml");
        });
        btnArticlles.setOnAction(e -> {
            naviguezVers("/Article/afficherarticles.fxml");
        });
        btnSignout.setOnAction(e -> {
            Session.getSession().clearSession();
            naviguezVers("/User/Login.fxml");
        });

        if(Session.getSession().getUser().getRole().equals("MembrePlus")){
            btnMatch.setVisible(false);
            btnJoueurs.setVisible(true);
            btnBoutique.setVisible(true);
            btnContrats.setVisible(false);
            btnElection.setVisible(true);
            btnReservation.setVisible(false);
            btnArticlles.setVisible(false);
        }

    }

    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            pieChart.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void loadStatisticsRating() {
      /*  try {
            List<User> users = userService.recuperer();

            // Initialize counts for different rating ranges
            int excellentCount = 0;
            int goodCount = 0;
            int averageCount = 0;
            int poorCount = 0;

            // Count occurrences of each rating range
            for (User user : users) {
                double rating = user.getRating();
                if (rating >= 4.5) {
                    excellentCount++;
                } else if (rating >= 3.5) {
                    goodCount++;
                } else if (rating >= 2.5) {
                    averageCount++;
                } else {
                    poorCount++;
                }
            }

            // Create pie chart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Excellent (4.5+)", excellentCount),
                    new PieChart.Data("Good (3.5-4.49)", goodCount),
                    new PieChart.Data("Average (2.5-3.49)", averageCount),
                    new PieChart.Data("Poor (<2.5)", poorCount)
            );

            // Set pie chart data
            pieChart2.setData(pieChartData);

        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }


    public void loadStatistics() {
        try {
            List<Election> elections = electionService.recuperer();

            // Initialize a map to store role counts
            Map<String, Integer> roleCounts = new HashMap<>();

            // Count occurrences of each role
            for (Election election : elections) {
                String role = election.getPosteE().toLowerCase();
                roleCounts.put(role, roleCounts.getOrDefault(role, 0) + 1);
            }

            // Create pie chart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            // Add data for each role to the pie chart
            for (Map.Entry<String, Integer> entry : roleCounts.entrySet()) {
                pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }

            // Set pie chart data
            pieChart.setData(pieChartData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/AfficherElection.fxml"));
            pieChart.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}