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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.Article.Article;
import org.example.services.Article.ArticleService;
import org.example.utils.Session;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

    public class AffichageArticle {

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
        private Button btnArticlles;

        @FXML
        private Button btnReservation;

        @FXML
        private Button btnSignout;

        @FXML
        private Button btnStaff;

        @FXML
        private Button btnUsers;

        @FXML
        private Button btnReclamations;

        @FXML
        private Button btnajouter;

        @FXML
        private Button btncalendrier;

        @FXML
        private Button btnmodifier;

        @FXML
        private Button btnsupprimer;
        @FXML
        private Button generatePDFButton;

        @FXML
        private ScrollPane fetchScrollPane;

        @FXML
        private ListView<Article> listarticles;
        @FXML
        private Pagination pagination;

        @FXML
        private TextField searchField;
        private final int itemsPerPage = 5;
        @FXML
        void handleGeneratePDF(ActionEvent event) {
            // Use the selected file from the FileChooser to determine the save location
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le fichier PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            fileChooser.setInitialFileName("articles.pdf");
            File file = fileChooser.showSaveDialog(null); // Assuming 'null' is your Stage

            if (file != null) {
                try {
                    ObservableList<Article> articles = listarticles.getItems();
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath())); // Save to chosen path
                    document.open();

                    // Improved document formatting
                    Font titleFont = new Font(16);
                    Font textFont = new Font(12);

                    // Iterate over your articles
                    for (Article article : articles) {
                        document.add(new Paragraph("Article"));
                        document.add(new Paragraph(" "));
                        document.add(new Paragraph("Titre: "));
                        document.add(new Paragraph("Contenu: "));
                        document.add(new Paragraph("Image: "));

                        // Handle image insertion (replace "path_to_image" with the actual path or URL)
                        File initialDirectory = new File("C:/xampp/htdocs/Images/Articles/");
                        if (initialDirectory.exists() && initialDirectory.isDirectory()) {
                            fileChooser.setInitialDirectory(initialDirectory);
                        }

                        // Add other article properties as needed
                        document.add(new Paragraph("Article "));
                        document.add(new Paragraph("-----------------------------------------------------"));
                        document.add(new Paragraph("Titre"+ article.getTitreArticle()));
                        document.add(new Paragraph("-----------------------------------------------------"));
                        document.add(new Paragraph("Contenu "+ article.getContenuArticle()));
                        document.add(new Paragraph("-----------------------------------------------------"));
                        document.add(new Paragraph("Image"+ article.getImageArticle()));
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


        private SortedList<Article> sortedArticles;





        private final ArticleService service = new ArticleService();

        @FXML
        private void searchArticle(ActionEvent event) {
            String searchQuery = searchField.getText();

            try {
                ObservableList<Article> searchResults = FXCollections.observableArrayList(service.searchByTitle(searchQuery));
                listarticles.setItems(searchResults);
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
            ObservableList<Article> articles = FXCollections.observableArrayList(service.getall());


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
                btnajouter.setOnAction(e -> {
                    naviguezVers("/Article/ajouterarticle.fxml");
                });

            if(Session.getSession().getUser().getRole().equals("Journaliste")){
                btnMatch.setVisible(false);
                btnJoueurs.setVisible(false);
                btnBoutique.setVisible(false);
                btnContrats.setVisible(false);
                btnElection.setVisible(false);
                btnReservation.setVisible(false);
            }

            // Create a sorted list
            sortedArticles = new SortedList<>(articles);
            sortedArticles.setComparator(Comparator.comparing(Article::getTitreArticle));

            int pageCount = (int) Math.ceil((double) articles.size() / itemsPerPage);
            pagination.setPageCount(pageCount);
            pagination.setPageFactory(this::createPage);

            searchField.setOnKeyReleased(this::handleSearchFieldKeyReleased);

            searchField.setOnKeyReleased(this::handleSearchFieldKeyReleased);
            listarticles.setCellFactory(param -> new ListCell<>() {

            @Override
            protected void updateItem(Article item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (listarticles.getItems().contains(item)) {
                        GridPane gridPane = new GridPane();
                        gridPane.setHgap(10);

                        Label titleLabel = createLabel("Titre:");
                        Label titleValueLabel = createLabel(item.getTitreArticle());
                        Label contentLabel = createLabel("Contenu:");
                        Label contentValueLabel = createLabel(item.getContenuArticle());
                        Label imageLabel = createLabel("Image:");
                        ImageView imageView = new ImageView();
                        imageView.setFitHeight(50);
                        imageView.setFitWidth(50);
                        String absolutePath = "C:/xampp/htdocs/Images/Articles/"+item.getImageArticle();
                        File file = new File(absolutePath);

                        Image image = new Image(file.toURI().toString());
                        imageView.setImage(image);

                        Button deleteButton = new Button("Delete");
                        deleteButton.setOnAction(event -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation de suppression");
                            alert.setHeaderText("Voulez-vous vraiment supprimer cet élément ?");

                            ButtonType confirmButton = new ButtonType("Confirmer");
                            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
                            alert.getButtonTypes().setAll(confirmButton, cancelButton);

                            Optional<ButtonType> result = alert.showAndWait();

                            if (result.isPresent() && result.get() == confirmButton) {
                                try {
                                    service.remove(item.getIdArticle());
                                    boolean removed = articles.remove(item);
                                    System.out.println("Article removed: " + removed);
                                    listarticles.refresh(); // Refresh the ListView after deletion
                                } catch (SQLException e) {
                                    System.err.println("Erreur lors de la suppression : " + e.getMessage());
                                }

                            }
                        });


                        gridPane.add(titleLabel, 0, 0);
                        gridPane.add(titleValueLabel, 1, 0);
                        gridPane.add(contentLabel, 0, 1);
                        gridPane.add(contentValueLabel, 1, 1);
                        gridPane.add(imageLabel, 0, 2);
                        gridPane.add(imageView, 1, 2);
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




            listarticles.setItems(sortedArticles);

            searchField.setOnKeyReleased(this::handleSearchFieldKeyReleased);
        }

        private Node createPage(int pageIndex) {
            int fromIndex = pageIndex * itemsPerPage;
            int toIndex = Math.min(fromIndex + itemsPerPage, sortedArticles.size());
            List<Article> subList = sortedArticles.subList(fromIndex, toIndex);

            listarticles.setItems(FXCollections.observableArrayList(subList));

            return new Label(); // You can return any node here if you want to customize the page appearance
        }

        @FXML
        private void handleSearchFieldKeyReleased(KeyEvent event) {
            String searchQuery = searchField.getText().toLowerCase();

            try {
                ObservableList<Article> allArticles = FXCollections.observableArrayList(service.getall());

                // Filter the articles based on the search query
                ObservableList<Article> searchResults = allArticles.filtered(
                        article -> article.getTitreArticle().toLowerCase().contains(searchQuery)
                );

                if (searchResults.isEmpty()) {
                    // Show an alert if no results are found
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Search Results");
                    alert.setHeaderText(null);
                    alert.setContentText("No results found for: " + searchQuery);
                    alert.showAndWait();

                    // Set an empty list to hide all articles
                    listarticles.setItems(FXCollections.observableArrayList());
                } else {
                    // Show only the search results
                    listarticles.setItems(searchResults);
                }

            } catch (SQLException e) {
                System.err.println("Error during search: " + e.getMessage());
            }
        }


        @FXML
        void ajouterArticle(ActionEvent event) {
            try {
                Stage stage = (Stage) btnajouter.getScene().getWindow();
                stage.setScene(createScene("/Article/ajouterarticle.fxml"));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        @FXML
        void modifierArticle(ActionEvent event) {
            Article selectedarticle = listarticles.getSelectionModel().getSelectedItem();
            if (selectedarticle != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Article/modifierarticle.fxml"));
                    Parent root = loader.load();
                    ModifierArticle controller = loader.getController();
                    controller.initData(selectedarticle);

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


