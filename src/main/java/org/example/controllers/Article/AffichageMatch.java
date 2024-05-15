package org.example.controllers.Article;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.Article.Match;
import org.example.services.Article.MatchService;
import org.example.utils.Session;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AffichageMatch {

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
    private Button btnTerrain;

    @FXML
    private Button btnMatch;
    @FXML
    private Button btnArticlles;

    @FXML
    private Button btnReservation;

    @FXML
    private Button btnSignout;

    @FXML
    private Button ajouter;
    @FXML
    private Button btnUsers;

    @FXML
    private Button btnReclamations;

    @FXML
    private Button btncalafficher;

    @FXML
    private Button btnmodifier;

    @FXML
    private Button btnsupprimer;

    @FXML
    private ScrollPane fetchScrollPane;

    @FXML
    private ListView<Match> listMatches;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField searchField;



    private final int itemsPerPage = 5;
    private SortedList<Match> sortedMatches;
    private final MatchService service = new MatchService();

    @FXML
    void handleGeneratePDF(ActionEvent event) {
        // Use the selected file from the FileChooser to determine the save location
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        fileChooser.setInitialFileName("matches.pdf");
        File file = fileChooser.showSaveDialog(null); // Assuming 'null' is your Stage

        if (file != null) {
            try {
                ObservableList<Match> matches = listMatches.getItems();
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath())); // Save to chosen path
                document.open();

                // Improved document formatting
                for (Match match : matches) {
                    document.add(new Paragraph("Match"));
                    document.add(new Paragraph(" "));
                    document.add(new Paragraph("Adversaire: " + match.getAdversaireMatch()));
                    document.add(new Paragraph("Date: " + match.getDateMatch().toString()));
                    document.add(new Paragraph("Score: " + match.getScoreMatch()));
                    document.add(new Paragraph("-----------------------------------------------------"));
                }

                document.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Téléchargement Terminé");
                alert.setHeaderText(null);
                alert.setContentText("Le fichier PDF a été généré avec succès et est prêt pour le téléchargement.");
                alert.showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de Génération PDF");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur est survenue lors de la génération du fichier PDF : " + ex.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void search(ActionEvent event) {
        String searchQuery = searchField.getText();

        try {
            ObservableList<Match> searchResults = FXCollections.observableArrayList(service.searchByAdversaire(searchQuery));
            listMatches.setItems(searchResults);
        } catch (SQLException e) {
            System.err.println("Error during search: " + e.getMessage());
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

    @FXML
    public void initialize() throws SQLException {
        ObservableList<Match> matches = FXCollections.observableArrayList(service.getall());

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
        ajouter.setOnAction(e -> {
            naviguezVers("/Article/ajoutermatch.fxml");
        });
        btnSignout.setOnAction(e -> {
            Session.getSession().clearSession();
            naviguezVers("/User/Login.fxml");
        });

        sortedMatches = new SortedList<>(matches);
        sortedMatches.setComparator(Comparator.comparing(Match::getAdversaireMatch));

        int pageCount = (int) Math.ceil((double) matches.size() / itemsPerPage);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);

        searchField.setOnKeyReleased(this::handleSearchFieldKeyReleased);

        listMatches.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Match item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (listMatches.getItems().contains(item)) {
                        GridPane gridPane = new GridPane();
                        gridPane.setHgap(10);

                        Label adversaryLabel = createLabel("Adversaire:");
                        Label adversaryValueLabel = createLabel(item.getAdversaireMatch());
                        Label dateLabel = createLabel("Date:");
                        Label dateValueLabel = createLabel(item.getDateMatch().toString());
                        Label scoreLabel = createLabel("Score:");
                        Label scoreValueLabel = createLabel(String.valueOf(item.getScoreMatch()));

                        Button deleteButton = new Button("Delete");
                        deleteButton.setOnAction(event -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation de suppression");
                            alert.setHeaderText("Voulez-vous vraiment supprimer cet match?");

                            ButtonType confirmButton = new ButtonType("Confirmer");
                            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
                            alert.getButtonTypes().setAll(confirmButton, cancelButton);

                            Optional<ButtonType> result = alert.showAndWait();

                            if (result.isPresent() && result.get() == confirmButton) {
                                try {
                                    service.remove(item.getIdMatch());
                                    boolean removed = matches.remove(item);
                                    System.out.println("Match removed: " + removed);

                                    // Reconstruct the sorted list with the updated data and comparator
                                    sortedMatches = new SortedList<>(FXCollections.observableArrayList(matches));
                                    sortedMatches.setComparator(Comparator.comparing(Match::getAdversaireMatch));

                                    listMatches.setItems(sortedMatches); // Set the ListView with the new sorted list
                                    listMatches.refresh(); // Refresh the ListView after deletion
                                } catch (SQLException e) {
                                    System.err.println("Erreur lors de la suppression : " + e.getMessage());
                                }
                            }
                        });


                        gridPane.add(adversaryLabel, 0, 0);
                        gridPane.add(adversaryValueLabel, 1, 0);
                        gridPane.add(dateLabel, 0, 1);
                        gridPane.add(dateValueLabel, 1, 1);
                        gridPane.add(scoreLabel, 0, 2);
                        gridPane.add(scoreValueLabel, 1, 2);
                        gridPane.add(deleteButton, 2, 0, 1, 3);
                        setGraphic(gridPane);
                    } else {
                        setText(null);
                        setGraphic(null);
                    }
                }
            }

            private Label createLabel(String text) {
                Label label = new Label(text);
                label.setStyle("-fx-font-size: 14px;");
                return label;
            }
        });

        listMatches.setItems(sortedMatches);
        searchField.setOnKeyReleased(this::handleSearchFieldKeyReleased);
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, sortedMatches.size());
        List<Match> subList = sortedMatches.subList(fromIndex, toIndex);

        listMatches.setItems(FXCollections.observableArrayList(subList));

        return new Label(); // You can return any node here if you want to customize the page appearance
    }

    @FXML
    private void handleSearchFieldKeyReleased(javafx.scene.input.KeyEvent event) {
        String searchQuery = searchField.getText().toLowerCase();

        try {
            ObservableList<Match> allMatches = FXCollections.observableArrayList(service.getall());

            ObservableList<Match> searchResults = allMatches.filtered(
                    match -> match.getAdversaireMatch().toLowerCase().contains(searchQuery)
            );

            if (searchResults.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Results");
                alert.setHeaderText(null);
                alert.setContentText("No results found for: " + searchQuery);
                alert.showAndWait();

                listMatches.setItems(FXCollections.observableArrayList());
            } else {
                listMatches.setItems(searchResults);
            }

        } catch (SQLException e) {
            System.err.println("Error during search: " + e.getMessage());
        }
    }

    @FXML

    void ajouter(ActionEvent event) {
        try {
            Stage stage = (Stage) ajouter.getScene().getWindow();
            stage.setScene(createScene("/Article/ajoutermatch.fxml"));
            stage.setFullScreen(true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    @FXML
    void modifierMatch(ActionEvent event) {
        Match selectedMatch = listMatches.getSelectionModel().getSelectedItem();
        if (selectedMatch != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/modifiermatch.fxml"));
                Parent root = loader.load();
                ModifierMatch controller = loader.getController();
                controller.initData(selectedMatch);

                btnAcceuil.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private Scene createScene(String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        return new Scene(root);
    }

    private Scene createScene(Parent root) {
        return new Scene(root);
    }
}
