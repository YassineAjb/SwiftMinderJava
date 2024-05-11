package org.example.controllers.Article;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.models.Article.Match;
import org.example.services.Article.MatchService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class AjouterMatch {

    @FXML
    private TextField adversaire;

    @FXML
    private Button affichermatch;

    @FXML
    private Button ajoutermatch;

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

    @FXML
    private Button btncalendrier;

    @FXML
    private DatePicker date ;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlMenus;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private TextField score;

    @FXML
    void afficher(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/Article/affichermatch.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setRoot(root);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }

    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            btnAcceuil.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void initialize(){
        btnContrats.setOnAction(e -> {
            naviguezVers("/Employee/Contrat.fxml");
        });
        btnBoutique.setOnAction(e -> {
            naviguezVers("/Boutique/AfficherProduit.fxml");
        });
        btnElection.setOnAction(e -> {
            naviguezVers("/Election/DashbordElection.fxml");
        });
        btnReservation.setOnAction(e -> {
            naviguezVers("/Reservation/Reservation.fxml");
        });
        btnJoueurs.setOnAction(e -> {
            naviguezVers("/Employee/AffichageJoueur.fxml");
        });
        btnMatch.setOnAction(e -> {
            naviguezVers("/Article/affichermatch.fxml");
        });
    }

    @FXML
    public void addMatch(ActionEvent event) {
        MatchService x = new MatchService();
        try {
            System.out.println("test");
            String adv = adversaire.getText();
            LocalDate dat = date.getValue();

            // Always set the score to 0 for matches in the future
            String scr = dat.isAfter(LocalDate.now()) ? "0" : score.getText();


            // Vérifier si la date est passée ou future
            LocalDate currentDate = LocalDate.now();

            if (dat.isAfter(currentDate)) {
                // Date future, hide the score field

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Avertissement");
                alert.setHeaderText(null);
                alert.setContentText("Ce match est à joué. La saisie du score est désactivée pour les matches futurs.");
                alert.showAndWait();
            } else if (dat.isBefore(currentDate)) {
                // Date passée, show a warning if the score is empty
                if ("0".equals(scr)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Avertissement");
                    alert.setHeaderText(null);
                    alert.setContentText("Ce match est passé. Veuillez entrer un score.");
                    alert.showAndWait();
                    return;  // Sortir de la fonction si la date est passée et le score est vide
                }
            }

            Match match = new Match(adv, Date.valueOf(dat), scr);
            x.add(match);

            Parent root = FXMLLoader.load(getClass().getResource("/Article/affichermatch.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setRoot(root);

        } catch (SQLException e) {
            e.getErrorCode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            // Gérer l'exception si la conversion en entier échoue
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer un numéro valide pour le score.");
            alert.showAndWait();
        }
    }





}
