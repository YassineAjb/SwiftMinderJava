package org.example.controllers.Election.Vote;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.example.controllers.Election.Candidat.CandidatItemController;
import org.example.models.Election.Candidat;
import org.example.models.Election.Vote;
import org.example.services.Election.CandidatService;
import org.example.services.Election.VoteService;
import org.example.utils.Session;
import org.example.services.User.Crud_user;

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
    private Button btnArticlles;

    @FXML
    private Button btnContrats;

    @FXML
    private Button btnElection;

    @FXML
    private Button btnJoueurs;

    @FXML
    private Button btnMatch;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnReclamations;

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

    @FXML
    private TextField mailMembre;


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
                        AnchorPane anchorPane = fxmlLoader.load();
                        CandidatItemController candidatItemController = fxmlLoader.getController();
                        candidatItemController.setData(candidat);
                        // Set the cell's graphic to the loaded HBox
                        setGraphic(anchorPane);
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


        // Uncomment this line if you want to load candidats when the controller is initialized
        // loadCandidats(idElection);

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
    void VoteButton(ActionEvent event) {
        String MailMembre = mailMembre.getText();

        // Vérifiez si un candidat a été sélectionné et si un email a été saisi
        if (idCandidatpourVouter != 0 && !MailMembre.isEmpty()) {
            System.out.println("ID du candidat sélectionné pour voter : " + idCandidatpourVouter);
            System.out.println("ID de l'élection sélectionnée pour voter : " + idElection);

            // Récupérez l'ID de l'utilisateur avec l'email spécifié
            int userId = getUserIdByMail(MailMembre);

            // Vérifiez si l'adresse e-mail est valide
            if (userId == -1) {
                // Affichez une alerte indiquant que l'adresse e-mail est invalide
                showErrorAlert("Adresse e-mail invalide.");
            } else {
                // Vérifiez si l'utilisateur a déjà voté dans cette élection
                if (!hasUserVoted(idElection, userId)) {
                    // Créez un objet Vote avec les ID du candidat, de l'élection et de l'utilisateur
                    Vote vote = new Vote();
                    vote.setIdCandidatV(idCandidatpourVouter);
                    vote.setIdElectionV(idElection);
                    vote.setIdUser(userId);

                    // Appelez la méthode ajouter de votre service VoteService
                    try {
                        VoteService voteService = new VoteService();
                        voteService.ajouter(vote);
                        showSuccessMessage("Vote ajouté avec succès !");
                        System.out.println("Vote ajouté avec succès !");
                        goToVoterMembre();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Erreur lors de l'ajout du vote : " + e.getMessage());
                        showErrorAlert("Erreur lors de l'ajout du vote. Veuillez réessayer.");
                    }
                } else {
                    // Affichez un message indiquant que l'utilisateur a déjà voté dans cette élection
                    showErrorAlert("Membre déjà voté dans cette élection.");
                }
            }
        } else {
            // Affichez une alerte indiquant qu'aucun candidat n'a été sélectionné ou que l'email n'a pas été saisi
            showErrorAlert("Veuillez sélectionner un candidat et écrire l'adresse e-mail du membre avant de voter.");
        }
    }

    private int getUserIdByMail(String email) {
        Crud_user userService = new Crud_user();
        return userService.getUserIdByEmail(email);
        //return 2 ;
    }



    // Méthode pour vérifier si l'utilisateur a déjà voté dans une élection donnée
    private boolean hasUserVoted(int idElection, int idUser) {
        // Utilisez votre service VoteService pour vérifier si l'utilisateur a déjà voté dans l'élection donnée
        VoteService voteService = new VoteService();
        System.out.println(voteService.hasVoted(idElection, idUser));
        return voteService.hasVoted(idElection, idUser);
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
