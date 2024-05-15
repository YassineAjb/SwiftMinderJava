package org.example.controllers.Election.Candidat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.models.Election.Candidat;
import org.example.services.Election.CandidatService;
import org.example.services.Election.ExcelExporter;
import org.example.utils.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherCandidatController implements Initializable{

    @FXML
    private ListView<Candidat> listViewC;
    @FXML
    private Button btnArticlles;

    private final CandidatService candidatService=new CandidatService();
    Candidat currentCandidat;

    private int idElection;

    public void recupererIdE(int id) {
        idElection=id;
        System.out.println("initializeCandidat---->idE="+ idElection);
        List<Candidat> candidatFromService;
        {
            try {
                System.out.println("--------------->"+ idElection);
                candidatFromService = candidatService.recupererC(idElection);
                listViewC.getItems().setAll(candidatFromService);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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

    @FXML
    private Button btnAcceuil;
    @FXML
    private Button btncalendrier;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnReclamations;

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
    private Button deleteElectionButton;



    @FXML
    private Button modifyElectionButton;


    @FXML
    private Button btnAcceuil1;
    @FXML
    private ComboBox<String> idSortC;
    @FXML
    private ComboBox<String> idSearchWithC;
    @FXML
    private TextField idSearchC;
    private final String[] attributsSearchC = {"nomC", "prenomC", "ageC"};
    private final String[] attributsSortC = {"Nom Candidat", "Prenom Candidat", "Age"};


    @FXML
    void naviqueVersAjoutC(ActionEvent event) {
        System.out.println("VersAjoutCandidat---->idE=" + idElection);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Election/AjouterCandidat.fxml"));
            Parent newPageRoot = loader.load();

            AjouterCandidatController ajouterCandidatController = loader.getController();
            ajouterCandidatController.initializeElection_idToAddIn(idElection);

            Scene newPageScene = new Scene(newPageRoot);
            Stage currentStage = (Stage) listViewC.getScene().getWindow();
            currentStage.setScene(newPageScene);
            currentStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deleteElection(ActionEvent event) {
        try {
            candidatService.supprimer(currentCandidat.getIdC());
            refrechire();
            //initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void refrechire() {
        try {
            // Clear the list view
            listViewC.getItems().clear();

            // Retrieve the refreshed list of candidates
            List<Candidat> candidatFromService = candidatService.recupererC(idElection);
            System.out.println("Election ID: " + idElection); // Debug print

            // Set the items in the list view
            listViewC.getItems().setAll(candidatFromService);

            // Set the custom cell factory for the ListView
            listViewC.setCellFactory(param -> new ListCell<Candidat>() {
                @Override
                protected void updateItem(Candidat candidat, boolean empty) {
                    super.updateItem(candidat, empty);

                    if (empty || candidat == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        try {
                            // Load custom FXML layout for each candidat
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/Election/CandidatItem.fxml"));

                            AnchorPane anchorPane = fxmlLoader.load();
                            CandidatItemController candidatItemController = fxmlLoader.getController();
                            candidatItemController.setData(candidat);
                            setGraphic(anchorPane);
                        } catch (IOException e) {
                            e.printStackTrace();
                            setText("Error loading item");
                        }
                    }
                }
            });

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    private void searchautoV() {
        String searchAttribute = idSearchWithC.getValue();
        String searchKeyword = idSearchC.getText();

        if (searchKeyword.isEmpty()) {
            // Reset to original state if the search keyword is empty
            refrechire();
            return;
        }

        try {
            List<Candidat> searchResults = candidatService.searchCandidatByNomStartingWithLetter(searchAttribute, searchKeyword);

            // Debug: Print the size of the search results
            System.out.println("Search results size: " + searchResults.size());

            // Clear and update the ListView with search results
            listViewC.getItems().clear();
            listViewC.getItems().addAll(searchResults);

            // Set the custom cell factory again for the ListView
            listViewC.setCellFactory(param -> new ListCell<Candidat>() {
                @Override
                protected void updateItem(Candidat candidat, boolean empty) {
                    super.updateItem(candidat, empty);

                    if (empty || candidat == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        System.out.println("Updating item: " + candidat.getNomC()); // Debug print

                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/Election/CandidatItem.fxml"));

                        try {
                            AnchorPane anchorPane = fxmlLoader.load();
                            CandidatItemController candidatItemController = fxmlLoader.getController();
                            candidatItemController.setData(candidat);
                            setGraphic(anchorPane);
                        } catch (IOException e) {
                            e.printStackTrace();
                            setText("Error loading item");
                        }
                    }
                }
            });

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }




    public void initialize(URL url, ResourceBundle rb) {

        idSearchWithC.setItems(FXCollections.observableArrayList(attributsSearchC));
        idSortC.setItems(FXCollections.observableArrayList(attributsSortC));

        // Set a custom cell factory for the ListView
        listViewC.setCellFactory(param -> new ListCell<Candidat>() {
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
                        /*HBox hBox = fxmlLoader.load();
                        CandidatItemController candidatItemController = fxmlLoader.getController();
                        candidatItemController.setData(candidat);

                        // Set the cell's graphic to the loaded HBox
                        setGraphic(hBox);*/

                        AnchorPane anchorPane = fxmlLoader.load();
                        CandidatItemController candidatItemController = fxmlLoader.getController();
                        candidatItemController.setData(candidat);
                        setGraphic(anchorPane);
                        /******Select item ******/
                        listViewC.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                            @Override
                            public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                                currentCandidat = listViewC.getSelectionModel().getSelectedItem();
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
        //listViewC.getItems().setAll(candidatFromService);

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




    public void modifyCandidatC() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Election/ModifierCandidat.fxml"));
            Parent newPageRoot = loader.load();

            ModifierCandidatController modifyCandidatController= loader.getController();
            modifyCandidatController.initializeValues(
                    currentCandidat.getNomC(), currentCandidat.getPrenomC(),
                    currentCandidat.getAgeC(), currentCandidat.getImgCpath(),
                    currentCandidat.getIdC(),currentCandidat.getIdElection());

            Scene newPageScene = new Scene(newPageRoot);
            Stage currentStage = (Stage) listViewC.getScene().getWindow();
            currentStage.setScene(newPageScene);
            currentStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    @FXML
    private void sortDataCandidatC() {
        String selectedSortOption = idSortC.getValue();

        if (selectedSortOption != null) {
            ObservableList<Candidat> items = listViewC.getItems();

            switch (selectedSortOption) {
                case "Nom Candidat":
                    Collections.sort(items, Comparator.comparing(Candidat::getNomC));
                    break;
                case "Prenom Candidat":
                    Collections.sort(items, Comparator.comparing(Candidat::getPrenomC));
                    break;
                case "Age":
                    System.out.println("teeeeeeeeeeeeeeeeeeeeeeest");
                    items.sort(Comparator.comparing(Candidat::getAgeC));
                    System.out.println("teeeeeeeeeeeeeeeeeeeeeeest000");
                    break;
                default:
                    System.out.println("Default case - no sorting");
                    break;
            }

            // Print the sorted data for debugging
            items.forEach(election -> System.out.println(election.getNomC()));

            // Update the ListView with the sorted data
            listViewC.setItems(items);
        } else {
            System.out.println("Default case - initialize or display original data");
            // Default case, initialize or display original data in the ListView
            refrechire();
        }
    }


    void gobackListCandidat() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Election/AfficherCandidat.fxml"));
            Parent newPageRoot = loader.load();

            AfficherCandidatController afficherCandidatController= loader.getController();
            afficherCandidatController.recupererIdE(currentCandidat.getIdElection());
            System.out.println("66666"+ currentCandidat.getIdElection());


            Scene newPageScene = new Scene(newPageRoot);
            Stage currentStage = (Stage) listViewC.getScene().getWindow();
            currentStage.setScene(newPageScene);
            currentStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void deleteCandidatC(ActionEvent event) {
            try {
                candidatService.supprimer(currentCandidat.getIdC());
                //refrechire();
                //initialize();
                gobackListCandidat();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
    private void initialize() {
    }

    @FXML
    void ShowStatistic(ActionEvent event) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Election/DashbordCandidat.fxml"));
                Parent root = loader.load();
                DashboardController dashboardController = loader.getController();
                //  dashboardController.loadStatistics();
                // dashboardController.loadStatisticsRating();

                dashboardController.loadStatisticsRating(idElection);
                System.out.println("id election to showStat candidat"+idElection);

                idSearchC.getScene().setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
    @FXML
    private void export(ActionEvent event) {
        ExcelExporter d = new ExcelExporter();
        d.generateExcel(listViewC);
    }

    @FXML
    void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/AfficherElection.fxml"));
            listViewC.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
