package org.example.controllers.Election.Vote;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.example.models.Election.Candidat;
import org.example.models.Election.Election;
import org.example.models.Election.Vote;
import org.example.services.Election.VoteService;
import org.example.utils.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherVoteController implements Initializable {


    @FXML
    private TextField idSearchC;
    @FXML
    private Button exportButtonVote;
    @FXML
    private Button btnArticlles;
    @FXML
    private ComboBox<?> idSortC;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnReclamations;

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
    private ListView<Vote> listViewV;

    @FXML
    private ComboBox<?> idSearchWithV;

    @FXML
    private Button btncalendrier;

    @FXML
    private Button btnAcceuil1;


    private final VoteService voteService=new VoteService();
    Vote currentVote;
    List<Vote> voteFromService;
    {
        try {
            voteFromService = voteService.recuperer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            btnBoutique.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
   /* public void initialize(URL url, ResourceBundle rb) {

        // Set a custom cell factory for the ListView
        listViewV.setCellFactory(param -> new ListCell<Vote>() {
            @Override
            protected void updateItem(Vote vote, boolean empty) {
                super.updateItem(vote, empty);

                if (empty || vote == null) {
                    // If the cell is empty or the election is null, set the cell text to empty
                    setText("");
                } else {
                    // Load custom FXML layout for each election
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/Election/VoteItem.fxml"));

                    try {
                        HBox hBox = fxmlLoader.load();
                        VoteItemController voteItemController = fxmlLoader.getController();
                        voteItemController.setData(vote);

                        // Set the cell's graphic to the loaded HBox
                        setGraphic(hBox);

                        listViewV.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                            @Override
                            public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                                currentVote = listViewV.getSelectionModel().getSelectedItem();
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        // Set the items in the ListView
        listViewV.getItems().setAll(voteFromService);
    }*/


    public void initialize(URL url, ResourceBundle rb) {

listViewV.setCellFactory(param -> new ListCell<Vote>() {
            @Override
            protected void updateItem(Vote vote, boolean empty) {
                super.updateItem(vote, empty);

                if (empty || vote == null) {
                    setText("");
                } else {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/Election/VoteItem.fxml"));

                        AnchorPane anchorPane = fxmlLoader.load();
                        VoteItemController voteItemController = fxmlLoader.getController();

                        // Fetch the necessary data using the service
                        Candidat candidat = getCandidatForVote5(vote);
                        Election election = getElectionForVote5(vote);
                        //System.out.println(election.getNomE());
                       // assert candidat != null;
                        //System.out.println(candidat.getNomC());

                        // Pass the relevant data to the setData method
                        assert candidat != null;
                        System.out.println("hello  "+candidat);
                        voteItemController.setData( candidat, election);

                        setGraphic(anchorPane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        listViewV.getItems().setAll(voteFromService);

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

    private Candidat getCandidatForVote5(Vote vote) {
        try {
            // Use your VoteService or another service to fetch the Election data
            // You might need to adjust the method signature or implementation based on your service
            System.out.println("------------getCandidatForVote5---------------");
            System.out.println("1   "+voteService.getCandidatForVote(vote));
            System.out.println("2   "+vote);
            System.out.println("---------------------------");
            return voteService.getCandidatForVote(vote);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Handle the exception according to your needs
        }
    }


    // Fetch Election data for a Vote
    private Election getElectionForVote5(Vote vote) {
        // Use your VoteService or another service to fetch the Election data
        // You might need to adjust the method signature or implementation based on your service
        System.out.println("------------getElectionForVote5---------------");
        System.out.println("1   "+voteService.getElectionForVote(vote));
        System.out.println("2   "+vote);
        System.out.println("---------------------------");
        return voteService.getElectionForVote(vote);
    }


    @FXML
    void goToVoter(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/VoterMembre.fxml"));
            listViewV.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/AfficherElection.fxml"));
            listViewV.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



}
