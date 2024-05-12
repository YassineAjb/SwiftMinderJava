package org.example.controllers.Election.Vote;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import org.example.controllers.Election.Candidat.CandidatItemController;
import org.example.models.Election.Candidat;
import org.example.models.Election.Vote;
import org.example.services.Election.CandidatService;
import org.example.services.Election.VoteService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ListCandidatV implements Initializable {


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
    int idCandidatpourVouter;

    int idElection ;



    public void setElectionId(int id) {
        idElection = id;
        System.out.println("idElection setElectionId ListCandidatV  " + idElection);
        loadCandidats(idElection);
        initializeListView();
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

    public void initializeListView() {
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
                                idCandidatpourVouter = currentCandidat.getIdC();
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
    }




    public void initialize(URL url, ResourceBundle rb) {
        // Load candidats when idElection is set
        //setElectionId(idElection);
        setElectionId(idElection);

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


        // Uncomment this line if you want to load candidats when the controller is initialized
        // loadCandidats(idElection);

    }


    @FXML
    void VoteButton(ActionEvent event) {
        System.out.println("ID du candidat sélectionné pour voter : " + idCandidatpourVouter);
        System.out.println("ID de l'élection sélectionnée pour voter : " + idElection);

// Créez un objet Vote avec les ID du candidat et de l'élection
        Vote vote = new Vote();
        vote.setIdCandidatV(idCandidatpourVouter);
        vote.setIdElectionV(idElection);

        // Appelez la méthode ajouter de votre service VoteService
        try {
            VoteService voteService = new VoteService();
            voteService.ajouter(vote);
            showSuccessMessage(" Vote ajouté avec succès !");

            // Ajoutez ici le code pour afficher un message de succès ou rediriger l'utilisateur
            System.out.println("Vote ajouté avec succès !");
            goToVoterMembre();
        } catch (SQLException e) {
            e.printStackTrace();
            // Ajoutez ici le code pour gérer les erreurs d'ajout du vote
            System.out.println("Erreur lors de l'ajout du vote : " + e.getMessage());
            showErrorAlert("Failed to add vote. Please try again.");

        }
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void goToVoterMembre(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/VoterMembre.fxml"));
            listViewCAvoter.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
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


    @FXML
    void goToMembreVote(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/VoterMembre.fxml"));
            listViewCAvoter.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
