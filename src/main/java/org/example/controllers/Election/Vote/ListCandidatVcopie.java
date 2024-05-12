package org.example.controllers.Election.Vote;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import org.example.controllers.Election.Candidat.CandidatItemController;
import org.example.models.Election.Candidat;
import org.example.services.Election.CandidatService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ListCandidatVcopie implements Initializable {


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
    private ListView<Candidat> listViewCAvoter;

    private final CandidatService candidatService=new CandidatService();

    //private ObservableList<Candidat> candidatFromService = FXCollections.observableArrayList();

    private List<Candidat> candidatFromService;  // Déplacez la déclaration ici

    Candidat currentCandidat;

    int idElection ;

    public void getidElectionV(int id) {
        idElection=id;
        System.out.println("idElection getidElectionV ListCandidatV  "+idElection);
        loadCandidats(idElection);
    }
    public void loadCandidats(int id){
    //List<Candidat> candidatFromService;
        try {
            System.out.println("45 ListCandidatV  "+idElection);
           // candidatFromService.setAll(candidatService.recupererCandidatsbyIdE(idElection));
            candidatFromService = candidatService.recupererCandidatsbyIdE(idElection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            listViewCAvoter.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    public void initialize(URL url, ResourceBundle rb) {

        //idElection =18;
        getidElectionV(idElection);
        //loadCandidats(idElection);

        System.out.println("///////////////////"+idElection);

        // Set a custom cell factory for the ListView
        listViewCAvoter.setCellFactory(param -> new ListCell<Candidat>() {
            @Override
            protected void updateItem(Candidat candidat, boolean empty) {
                super.updateItem(candidat, empty);

                if (empty || candidat == null) {
                    // If the cell is empty or the election is null, set the cell text to empty
                    setText("");
                } else {
                    // Load custom FXML layout for each election
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/Election/CandidatItem.fxml"));

                    try {
                        HBox hBox = fxmlLoader.load();
                        CandidatItemController candidatItemController = fxmlLoader.getController();
                        candidatItemController.setData(candidat);

                        // Set the cell's graphic to the loaded HBox
                        setGraphic(hBox);
                        /******Select item ******/
                        listViewCAvoter.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                            @Override
                            public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                                currentCandidat = listViewCAvoter.getSelectionModel().getSelectedItem();
                            }
                        });
                        /**************/
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.out.println("test "+candidatFromService);
        // Set the items in the ListView
        listViewCAvoter.getItems().setAll(candidatFromService);

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


    }


    @FXML
    void VoteButton(ActionEvent event) {

    }
}
