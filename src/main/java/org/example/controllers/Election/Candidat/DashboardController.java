package org.example.controllers.Election.Candidat;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import org.example.models.Election.Candidat;
import org.example.services.Election.CandidatService;
import org.example.utils.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    private CandidatService candidatService = new CandidatService();

    @FXML
    private PieChart pieChart;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnReclamations;

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

    int id_election ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //recupererIdE(id_election);
        try {
            loadStatisticsRating(1);
            System.out.println(id_election);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
        btnReclamations.setOnAction(e -> {
            naviguezVers("/User/tablereclamation.fxml");
        });
        btnUsers.setOnAction(e -> {
            naviguezVers("/User/Crud.fxml");
        });
        btnSignout.setOnAction(e -> {
            Session.getSession().clearSession();
            naviguezVers("/User/Login.fxml");
        });

    }


    public void loadStatisticsRating(int id) throws SQLException {


        id_election=id;
        System.out.println("initializeCandidatStat---->idE="+ id_election);
        List<Candidat> candidats;
        {
            try {
                System.out.println("-0000-------------->"+ id_election);
                candidats = candidatService.recupererC(id_election);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        //List<Candidat> candidats = candidatService.recupererC(id_election);
        System.out.println(id_election);
        System.out.println(candidats);
        // Initialize counts for different rating ranges
        int nbCandidatSup85 = 0;
        int nbCandidatSup65 = 0;
        int nbCandidatSup45 = 0;
        int nbCandidatSup25 = 0;

        // Count occurrences of each rating range
        for (Candidat candidat : candidats) {
            int age = candidat.getAgeC();
            if (age >= 85) {
                nbCandidatSup85++;
            } else if (age >= 65 ) {
                nbCandidatSup65++;
            } else if (age >= 45) {
                nbCandidatSup45++;
            } else if (age >= 25) {
                nbCandidatSup25++;
            }
        }

        // Create pie chart data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Candidat plus de 85 ans", nbCandidatSup85),
                new PieChart.Data("Candidat entre 66 et 84 ans", nbCandidatSup65),
                new PieChart.Data("Candidat entre 46 et 65", nbCandidatSup45),
                new PieChart.Data("Candidat entre 25 et 45 ans", nbCandidatSup25)
        );

        // Set pie chart data
        pieChart2.setData(pieChartData);

    }


    public void loadStatistics() throws SQLException {
        List<Candidat> candidats = candidatService.recuperer();

        // Initialize a map to store role counts
        Map<String, Integer> roleCounts = new HashMap<>();

        // Count occurrences of each role
        for (Candidat candidat : candidats) {
            String role = candidat.getNomC().toLowerCase();
            roleCounts.put(role, roleCounts.getOrDefault(role, 0) + 1);
        }

           /* // Create pie chart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            // Add data for each role to the pie chart
            for (Map.Entry<String, Integer> entry : roleCounts.entrySet()) {
                pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }

            // Set pie chart data
            pieChart.setData(pieChartData);*/

    }


    @FXML
    void goBack(ActionEvent event) {
        try {
            /*Parent root = FXMLLoader.load(getClass().getResource("/AfficherCandidat.fxml"));
            pieChart2.getScene().setRoot(root);

            AfficherCandidatController afficherCandidatController= root.getController();
            afficherCandidatController.recupererIdE(id_election);*/

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Election/AfficherCandidat.fxml"));
            Parent newPageRoot = loader.load();

            AfficherCandidatController afficherCandidatController= loader.getController();
            afficherCandidatController.recupererIdE(id_election);

            Scene newPageScene = new Scene(newPageRoot);
            Stage currentStage = (Stage) pieChart2.getScene().getWindow();
            currentStage.setScene(newPageScene);
            currentStage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
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
}