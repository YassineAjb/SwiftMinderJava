package org.example.controllers.Election.Vote;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.controllers.Election.Election.ELectionItemController;
import org.example.models.Election.Election;
import org.example.services.Election.ElectionService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class VoteMembreController implements Initializable {


    @FXML
    private ListView<Election> listViewEV;

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
    private final ElectionService electionService=new ElectionService();
    Election currentElection;
    List<Election> electionFromService;
    {
        try {
            electionFromService = electionService.recupererElectionSysDate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            listViewEV.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void initialize(URL url, ResourceBundle rb) {

        //List<Election> elections = new ArrayList<>(getAllElections());
        /*try {
            List<Election> electionFromService = electionService.recuperer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
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


        // Set a custom cell factory for the ListView
        listViewEV.setCellFactory(param -> new ListCell<Election>() {
            @Override
            protected void updateItem(Election election, boolean empty) {
                super.updateItem(election, empty);

                if (empty || election == null) {
                    // If the cell is empty or the election is null, set the cell text to empty
                    setText("");
                } else {
                    // Load custom FXML layout for each election
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/Election/ElectionItem.fxml"));

                    try {
                        HBox hBox = fxmlLoader.load();
                        ELectionItemController electionItemController = fxmlLoader.getController();
                        electionItemController.setData(election);

                        // Set the cell's graphic to the loaded HBox
                        setGraphic(hBox);
                        /******Select item ******/
                        listViewEV.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                            @Override
                            public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                                currentElection = listViewEV.getSelectionModel().getSelectedItem();
                            }
                        });
                        /**************/
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        // Set the items in the ListView
        listViewEV.getItems().setAll(electionFromService);
    }



    @FXML
    void afficherCandidatEV(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Election/ListCandidatV.fxml"));
            Parent newPageRoot = loader.load();

            ListCandidatV listCandidatV= loader.getController();
            System.out.println("105 VoteMembreController  "+ currentElection.getIdE());
            //listCandidatV.getidElectionV(currentElection.getIdE());
            listCandidatV.setElectionId(currentElection.getIdE());


            Scene newPageScene = new Scene(newPageRoot);
            Stage currentStage = (Stage) listViewEV.getScene().getWindow();
            currentStage.setScene(newPageScene);
            currentStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/AfficherVote.fxml"));
            listViewEV.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
