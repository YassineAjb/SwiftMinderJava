package org.example.controllers.Election.Election;

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
import org.example.controllers.Election.Candidat.AfficherCandidatController;
import org.example.controllers.Election.Candidat.DashboardElection;
import org.example.models.Election.Election;
import org.example.services.Election.ElectionService;
import org.example.utils.MyDataBase;
import org.example.utils.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AfficherElectionController implements Initializable{

    @FXML
    private ListView<Election> listViewE;
    @FXML
    private Button btnArticlles;
    @FXML
    private Button btnTerrain;

    private final ElectionService electionService=new ElectionService();
    Election currentElection;
    List<Election> electionFromService;
    {
        try {
            electionFromService = electionService.recuperer();
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



    @FXML
    private Button btnAcceuil;

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
    private Button modifyElectionButton;

    @FXML
    private ComboBox<String> idSort;
    @FXML
    private ComboBox<String> idSearchWith;
    @FXML
    private TextField idSearch;
    private final String[] attributsSearch = {"nomE", "posteE", "periodeP"};
    private final String[] attributsSort = {"Nom Election", "Poste", "Periode", "Date Election"};



    @FXML
    void naviqueVersAjoutE(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/AjouterElection.fxml"));
            idSearch.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void deleteElection(ActionEvent event) {
            try {
                electionService.supprimer(currentElection.getIdE());
                refrechire();
                //initialize();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
    public void refrechire() {
        try {
            // Clear the list view
            listViewE.getItems().clear();

            // Retrieve the refreshed list of elections
            List<Election> electionsFromService = electionService.recuperer();

            // Set the custom cell factory again (if needed)
            listViewE.setCellFactory(param -> new ListCell<Election>() {
                @Override
                protected void updateItem(Election election, boolean empty) {
                    super.updateItem(election, empty);

                    if (empty || election == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/Election/ElectionItem.fxml"));

                        try {
                            AnchorPane anchorPane = fxmlLoader.load();
                            ELectionItemController electionItemController = fxmlLoader.getController();
                            electionItemController.setData(election);
                            setGraphic(anchorPane);
                        } catch (IOException e) {
                            e.printStackTrace();
                            setText("Error loading item");
                        }
                    }
                }
            });

            // Set the items in the list view
            listViewE.getItems().setAll(electionsFromService);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Election> getAllElections() {
        List<Election> elections = new ArrayList<>();

        try (Connection connection = MyDataBase.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM evenement")) {

            while (resultSet.next()) {
                Election election = new Election();
                election.setNomE(resultSet.getString("nomE"));
                election.setDateE(LocalDate.parse(resultSet.getString("dateE"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                election.setPosteE(resultSet.getString("posteE"));
                election.setPeriodeP(resultSet.getString("periodeP"));
                election.setImgEpath(resultSet.getString("imgEpath"));
                System.out.println(resultSet.getString("imgEpath"));
                elections.add(election);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception as needed
        }

        return elections;
    }

    public void initialize(URL url, ResourceBundle rb) {

        idSearchWith.setItems(FXCollections.observableArrayList(attributsSearch));
        idSort.setItems(FXCollections.observableArrayList(attributsSort));


        btnMatch.setOnAction(e -> {
            naviguezVers("/Article/affichermatch.fxml");
        });
        btnReservation.setOnAction(e -> {
            naviguezVers("/Reservation/listeReservation.fxml");
        });
        btnTerrain.setOnAction(e -> {
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


        //List<Election> elections = new ArrayList<>(getAllElections());
        /*try {
            List<Election> electionFromService = electionService.recuperer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/


        // Set a custom cell factory for the ListView
        listViewE.setCellFactory(param -> new ListCell<Election>() {
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
                        AnchorPane anchorPane = fxmlLoader.load();
                        ELectionItemController eLectionItemController = fxmlLoader.getController();
                        eLectionItemController.setData(election);
                        setGraphic(anchorPane);
                        /******Select item ******/
                        listViewE.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                            @Override
                            public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                                currentElection = listViewE.getSelectionModel().getSelectedItem();
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
        listViewE.getItems().setAll(electionFromService);
    }



    @FXML
    private void searchauto() {
        String searchAttribute = idSearchWith.getValue();
        String searchKeyword = idSearch.getText();

        if (searchKeyword.isEmpty()) {
            // Reset to original state if the search keyword is empty
            refrechire();
            return;
        }

        try {
            List<Election> searchResults = electionService.searchElectionByNomStartingWithLetter(searchAttribute, searchKeyword);

            // Debug: Print the size of the search results
            System.out.println("Search results size: " + searchResults.size());

            // Clear and update the ListView with search results
            listViewE.getItems().clear();
            listViewE.getItems().addAll(searchResults);

            // Set the custom cell factory again for the ListView
            listViewE.setCellFactory(param -> new ListCell<Election>() {
                @Override
                protected void updateItem(Election election, boolean empty) {
                    super.updateItem(election, empty);

                    if (empty || election == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        System.out.println("Updating item: " + election.getNomE()); // Debug print

                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/Election/ElectionItem.fxml"));

                        try {
                            AnchorPane anchorPane = fxmlLoader.load();
                            ELectionItemController eLectionItemController = fxmlLoader.getController();
                            eLectionItemController.setData(election);
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
    public void modifyElection() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Election/ModifierElection.fxml"));
            Parent newPageRoot = loader.load();

            ModifierElectionController modifyElectionController = loader.getController();
            modifyElectionController.initializeValues(
                    currentElection.getNomE(), Date.valueOf(currentElection.getDateE()),
                    currentElection.getPosteE(), currentElection.getPeriodeP(),
                    currentElection.getImgEpath(), currentElection.getIdE());

            Scene newPageScene = new Scene(newPageRoot);
            Stage currentStage = (Stage) listViewE.getScene().getWindow();
            currentStage.setScene(newPageScene);
            currentStage.show();
        /*} catch (NullPointerException n) {
            showAlert("Please select an Election to modify");*/
        } catch (IOException e) {
            showAlert("Error loading the modification page. Please try again.");
        }
    }



    public void afficherCandidatE(ActionEvent actionEvent) {
        if (currentElection == null) {
            showAlert("Please select an Election to display the candidates.");
            return; // Exit the method if currentDesign is null
        }
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Election/AfficherCandidat.fxml"));
            Parent newPageRoot = loader.load();

            AfficherCandidatController afficherCandidatController= loader.getController();
            afficherCandidatController.recupererIdE(currentElection.getIdE());
            System.out.println("111111"+ currentElection.getIdE());


            Scene newPageScene = new Scene(newPageRoot);
            Stage currentStage = (Stage) listViewE.getScene().getWindow();
            currentStage.setScene(newPageScene);
            currentStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void sortData() {
        String selectedSortOption = idSort.getValue();

        if (selectedSortOption != null) {
            ObservableList<Election> items = listViewE.getItems();

            switch (selectedSortOption) {
                case "Nom Election":
                    Collections.sort(items, Comparator.comparing(Election::getNomE));
                    break;
                case "Date Election":
                    Collections.sort(items, Comparator.comparing(Election::getDateE));
                    break;
                case "Poste":
                    System.out.println("teeeeeeeeeeeeeeeeeeeeeeest");
                    items.sort(Comparator.comparing(Election::getPosteE));
                    System.out.println("teeeeeeeeeeeeeeeeeeeeeeest000");
                    break;
                case "Periode":
                    Collections.sort(items, Comparator.comparing(Election::getPeriodeP));
                    break;
                // Add more cases for custom sorting logic
                default:
                    System.out.println("Default case - no sorting");
                    break;
            }

            // Print the sorted data for debugging
            items.forEach(election -> System.out.println(election.getPosteE()));

            // Update the ListView with the sorted data
            listViewE.setItems(items);
        } else {
            System.out.println("Default case - initialize or display original data");
            // Default case, initialize or display original data in the ListView
            refrechire();
        }
    }

    @FXML
    void ShowStatisticElection(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Election/DashbordElection.fxml"));
            Parent root = loader.load();
            DashboardElection dashboardElection = loader.getController();
            //dashboardElection.loadStatistics();
            //dashboardController.loadStatisticsRating();

            idSearch.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToVote(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/AfficherVote.fxml"));
            idSearch.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void initialize() {
    }


}
