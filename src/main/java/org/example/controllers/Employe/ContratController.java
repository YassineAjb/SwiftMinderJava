package org.example.controllers.Employe;

import com.dlsc.gemsfx.daterange.DateRangePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.example.models.Employe.Contrat;
import org.example.models.Employe.ContratCell;
import org.example.models.Employe.Joueur;
import org.example.services.Employe.ServiceContrat;
import org.example.services.Employe.ServiceJoueur;
import org.example.utils.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ContratController {
    private final ServiceContrat serviceContrat = new ServiceContrat();

    private final ServiceJoueur serviceJoueur = new ServiceJoueur();
    @FXML
    private DateRangePicker ContratDateAjout;
    @FXML
    private Button btnArticlles;

    @FXML
    private DateRangePicker ContratDateModifier;

    @FXML
    private ComboBox<String> ContratEmployeId;

    @FXML
    private ComboBox<String> ContratEmployeIdModifier;

    @FXML
    private TextField IdContrat;

    @FXML
    private TextField IdContratAfficher;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnReclamations;

    @FXML
    private TextField IdContratModifier;

    @FXML
    private TextField IdContratSupprimer;

    @FXML
    private AnchorPane InterfaceContratAfficher;

    @FXML
    private AnchorPane InterfaceContratAjout;

    @FXML
    private AnchorPane InterfaceContratModifier;

    @FXML
    private AnchorPane InterfaceContratSupprimer;

    @FXML
    private TextField SalaireAjout;

    @FXML
    private TextField SalaireModifier;

    @FXML
    private Button ajouterContrat;

    @FXML
    private Button btnAcceuil;

    @FXML
    private Button btnBoutique;

    @FXML
    private Button btnJoueurs;

    @FXML
    private Button btnReservation;

    @FXML
    private Button btnElection;

    @FXML
    private Button btnSignout;

    @FXML
    private Button btnContrats;

    @FXML
    private Button btnMatch;

    @FXML
    private ListView<Contrat> listContrat;

    @FXML
    private ListView<Contrat> listContratAfficher;

    @FXML
    private ListView<Contrat> listContratModifier;

    @FXML
    private ListView<Contrat> listContratSupprimer;

    @FXML
    private Button modifierContrat;

    @FXML
    private Button supprimerContrat;

    private List<Contrat> contrats;
    
    @FXML
    private GridPane gridContrats;


    public void initialize() throws SQLException, IOException {
        InisialiserEmployeId(ContratEmployeId);
        InisialiserEmployeId(ContratEmployeIdModifier);
        //OverlayTextOnPdf();
        recupererListContrat(listContrat);
        recupererListContrat(listContratModifier);
        recupererListContrat(listContratSupprimer);
        //recupererListContrat(listContratAfficher);

        ajouterContrat.setOnAction(e -> {
            AjouterContrat(ContratDateAjout, ContratEmployeId, SalaireAjout);
        });
        modifierContrat.setOnAction(e -> {
            ModifierContrat(IdContratModifier, ContratDateModifier, ContratEmployeIdModifier, SalaireModifier);
        });
        supprimerContrat.setOnAction(e -> {
            SupprimerContrat(IdContratSupprimer);
        });

        listContratSupprimer.setOnMouseClicked((EventHandler<MouseEvent>) event -> {
            Selected(listContratSupprimer, IdContratSupprimer, ContratDateModifier, ContratEmployeIdModifier, SalaireModifier);
        });
        listContratModifier.setOnMouseClicked((EventHandler<MouseEvent>) event -> {
            Selected(listContratModifier, IdContratModifier, ContratDateModifier, ContratEmployeIdModifier, SalaireModifier);
        });

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

        recupererGridContrats();

    }

    private void InisialiserEmployeId(ComboBox<String> Id) throws SQLException {
        List<Joueur> JoueurOptions = serviceJoueur.afficher();
        ObservableList<String> JoueurIds = FXCollections.observableArrayList();
        for (Joueur joueur : JoueurOptions) {
            JoueurIds.add(String.valueOf(joueur.getId()));
        }
        Id.setItems(JoueurIds);
    }

    private void filterItems(String searchText, ListView<Contrat> listView) throws SQLException {
        // If the search text is empty, display all original items
        if (searchText == null || searchText.isEmpty()) {
            recupererListContrat(listView);
            return;
        }

        // Otherwise, filter the original items based on the search text
        ObservableList<Contrat> filteredList = FXCollections.observableArrayList();
        for (Contrat item : recupererListContrat(listView)) {
            if (item.getJoueur().getPosition().toLowerCase().equals(searchText.toLowerCase())) {
                filteredList.add(item);
            }
            if (item.getJoueur().getPiedfort().toLowerCase().equals(searchText.toLowerCase())) {
                filteredList.add(item);
            }
        }
        listView.setItems(filteredList);
    }

    private void AjouterContrat(DateRangePicker date, ComboBox EmployeId, TextField Salaire) {
        try {
            List<Contrat> Contrats = serviceContrat.afficher();

            for (Contrat contrat : Contrats) {
                if (Integer.parseInt(EmployeId.getValue().toString()) == contrat.getJoueur().getId()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Contrat existe");
                    alert.setContentText("Le joueur a deja un contrat.");
                    alert.showAndWait();
                    return;
                }
            }
            if (date.getValue() == null || EmployeId == null || Salaire.getText().isBlank()) {
                // Display a warning or error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Empty Fields");
                alert.setContentText("Please fill in all the required fields.");
                alert.showAndWait();
            } else {
                List<Joueur> Joueurs = serviceJoueur.afficher();
                for (Joueur joueur : Joueurs) {
                    if (joueur.getId() == Integer.parseInt(EmployeId.getValue().toString())) {
                        serviceContrat.ajouter(new Contrat(joueur, date, Integer.parseInt(Salaire.getText())));
                    }
                }

            }
            recupererListContrat(listContrat);

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void SupprimerContrat(TextField IdSupprimer) {
        try {
            serviceContrat.supprimer(Integer.parseInt(IdSupprimer.getText()));
            recupererListContrat(listContratSupprimer);
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

   /* private void GridContrat(ObservableList<Contrat> list) {
        try{
            gridContrats.getChildren().clear();
            gridContrats.setVgap(20); // Vertical gap between nodes
            int rowIndex = 1;
            List<Contrat> Contrats = serviceContrat.afficher();
            for (Contrat Contrat : list) {
                Button button = new Button("plus de details");
                button.setOnAction(event -> {
                    Stage popupStage = new Stage();
                    VBox popupContent = new VBox();
                    popupContent.getChildren().add(new Button("Close Popup"));
                    Scene popupScene = new Scene(popupContent, 200, 100);
                    popupStage.setScene(popupScene);
                    popupStage.setTitle("Popup Window");
                    popupStage.show();
                });
                button.setPrefWidth(300);
                Label label = new Label(Contrat.getNom()+"\n"+Contrat.getPrenom()+"\n"+Contrat.getAge());
                ImageView imageView = new ImageView(new Image(("images/"+Contrat.getImagePath())));
                imageView.setFitWidth(150); // Set the desired width
                imageView.setFitHeight(150); // Set the desired height
                gridContrats.setMargin(imageView, new Insets(20));
                gridContrats.addRow(rowIndex, imageView, label,button);
                rowIndex++;
            }
        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
*/


    private void ModifierContrat(TextField ContratId, DateRangePicker date, ComboBox EmployeId, TextField Salaire) {

        try {
            if (ContratId.getText().isEmpty() || date.getValue() == null || EmployeId == null || Salaire.getText().isBlank()) {
                // Display a warning or error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Empty Fields");
                alert.setContentText("Please fill in all the required fields.");
                alert.showAndWait();
            } else {
                List<Joueur> Joueurs = serviceJoueur.afficher();
                for (Joueur joueur : Joueurs) {
                    if (joueur.getId() == Integer.parseInt(EmployeId.getValue().toString())) {
                        serviceContrat.modifier(new Contrat(Integer.parseInt(ContratId.getText()), joueur, date, Integer.parseInt(Salaire.getText())));
                    }
                }

            }
            recupererListContrat(listContratModifier);
            System.out.println("modifier");
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void Clear(DateRangePicker dateRangePicker, TextField Salaire, ComboBox<String> ContratEmployeId, TextField ContratId) {
        dateRangePicker.setValue(null);
        Salaire.setText(null);
        ContratEmployeId.setValue(null);
        ContratId.setText(null);
    }

    //    private void Importer(ImageView importedImage,TextField img) {
//        fileChooser.setTitle("Select File");
//        File selectedFile = fileChooser.showOpenDialog(new Stage());
//        if (selectedFile != null) {
//            System.out.println("Selected file: " + selectedFile.getPath());
//            filePath = selectedFile.getName();
//            img.setText(filePath);
//            try {
//                Image image = new Image("images/" + filePath);
//                importedImage.setImage(image);
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        } else {
//            System.out.println("No file selected.");
//        }
//    }
    private void CreatePDF() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document,page);
        contentStream.setFont(PDType1Font.COURIER,14);
        contentStream.beginText();
        contentStream.newLineAtOffset(50,50);
        contentStream.showText("Hello, welcome to pdf box...");
        contentStream.endText();
        contentStream.close();
        document.save("newpdf");
        document.close();
        System.out.println("PDF created successfully...");
    }
//    private void OverlayTextOnPdf() throws IOException {
//
//        try {
//            PDDocument document = new PDDocument();
//            document.save(new File("Employee/Contrat.pdf"));
//
//            File fontFile = new File("ARIAL.TTF");
//            PDType0Font font = PDType0Font.load(document, fontFile);
//            // Iterate over each page in the document
//            for (int i = 0; i < document.getNumberOfPages(); i++) {
//                PDPage page = document.getPage(i);
//
//                // Create a content stream for adding text
//                PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
//
//                // Set the font and font size
//
//                contentStream.setFont(font, 12);
//
//                // Set the position for the text
//                contentStream.beginText();
//                contentStream.newLineAtOffset(100, 100);
//
//                // Add your text
//                contentStream.showText("Overlay Textaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//
//                // End the text content stream
//                contentStream.endText();
//                contentStream.close();
//            }
//
//            // Save the modified document
//            document.save(new File("src/main/resources/modifiedContrat.pdf"));
//            // Close the document
//            document.close();
//
//            System.out.println("Text overlay operation completed successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void InisialiserPoisitions(ComboBox<String> position) {
        ObservableList<String> positionOptions = FXCollections.observableArrayList("GK", "DC", "AL", "AD ", "MD", "MC", "MO", "AD", "AG", "AP", "SA");
        position.setItems(positionOptions);
    }

    private void ControlleDeSaisie(TextField AgeAjout, TextField HauteurAjout, TextField PoidsAjout, TextField NomAjout, TextField PrenomAjout) {
        AgeAjout.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String input = event.getCharacter();
            if (!input.matches("[0-9]")) {
                event.consume();
            }
        });

        HauteurAjout.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String input = event.getCharacter();
            if (!input.matches("[0-9]")) {
                event.consume();
            }
        });

        PoidsAjout.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String input = event.getCharacter();
            if (!input.matches("[0-9]")) {
                event.consume();
            }
        });

        NomAjout.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String input = event.getCharacter();
            if (!input.matches("[a-zA-Z ]")) {
                event.consume();
            }
        });

        PrenomAjout.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String input = event.getCharacter();
            if (!input.matches("[a-zA-Z ]")) {
                event.consume();
            }
        });
    }

    private void Clear(TextField IdAjout, TextField NomAjout, ComboBox PositionAjout, TextField HauteurAjout, TextField PoidsAjout, TextField PrenomAjout, TextField AgeAjout, TextField imageAjout, CheckBox DroiteAjout, CheckBox GaucheAjout, TextField PiedfortAjout) {
        IdAjout.setText(null);
        NomAjout.setText(null);
        PositionAjout.setValue(null);
        HauteurAjout.setText(null);
        PoidsAjout.setText(null);

        DroiteAjout.setSelected(true);
        GaucheAjout.setSelected(false);

        DroiteAjout.setSelected(false);
        GaucheAjout.setSelected(true);

        PrenomAjout.setText(null);
        AgeAjout.setText(null);
        imageAjout.setText(null);
        PiedfortAjout.setText(null);
    }

    private void Selected(ListView<Contrat> listContrat, TextField ContratId, DateRangePicker date, ComboBox EmployeId, TextField Salaire) {
        Clear(date, Salaire, EmployeId, ContratId);
        Contrat selectedContrat = listContrat.getSelectionModel().getSelectedItem();
        if (selectedContrat != null) {
            ContratId.setText(String.valueOf(selectedContrat.getId()));
            date.setValue(selectedContrat.getdate().getValue());
            Salaire.setText(String.valueOf(selectedContrat.getSalaire()));
            EmployeId.setValue(selectedContrat.getJoueur().getId());
        }
    }
    public void Selected(Contrat contrat) {

        Contrat selectedContrat = contrat;
        if (selectedContrat != null) {
            IdContrat.setText(String.valueOf(selectedContrat.getId()));
            ContratDateModifier.setValue(selectedContrat.getdate().getValue());
            SalaireModifier.setText(String.valueOf(selectedContrat.getSalaire()));
            ContratEmployeIdModifier.setValue(String.valueOf(selectedContrat.getJoueur().getId()));
        }
    }


    private ObservableList<Contrat> recupererListContrat(ListView<Contrat> listContrat) throws SQLException {
        List<Contrat> Contrats = serviceContrat.afficher(); // Assuming serviceContrat is your service class for managing Employe

        ObservableList<Contrat> observableArrayList = FXCollections.observableArrayList(Contrats);

        listContrat.setCellFactory(new Callback<ListView<Contrat>, ListCell<Contrat>>() {
            @Override
            public ListCell<Contrat> call(ListView<Contrat> listView) {
                return new ContratCell();
            }
        });

        listContrat.setItems(observableArrayList);
        return observableArrayList;
    }

    public void recupererGridContrats() throws SQLException {
        List<Contrat> contrats = serviceContrat.afficher();
        gridContrats.getChildren().clear();

        int column = 0;
        int row = 1;
        try {


            for (int i = 0; i < contrats.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Employee/ContratDashBoard.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                anchorPane.setPrefHeight(1160);
                if(column==5){
                    column =0;
                    row++;
                }
                ContratDashBoard ContratDashBoard = fxmlLoader.getController();
                ContratDashBoard.setData(contrats.get(i));
                ContratDashBoard.setAfficherJoueurController(this);
                gridContrats.add(anchorPane,column++,row);

                gridContrats.setMinWidth(1140);
                gridContrats.setPrefWidth(1140);
                gridContrats.setMaxWidth(1140);

                /*gridContrats.setMinHeight(300);
                gridContrats.setPrefHeight(300);
                gridContrats.setMaxHeight(300);*/

                gridContrats.setMargin(anchorPane,new Insets(10));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void ajouterMouseClicked(MouseEvent mouseEvent) throws SQLException {
        InterfaceContratAfficher.setVisible(false);
        InterfaceContratAjout.setVisible(true);
        InterfaceContratSupprimer.setVisible(false);
        InterfaceContratModifier.setVisible(false);
        recupererListContrat(listContrat);

    }
    public void refreshContrats() {
        try {
            contrats = serviceContrat.afficher();
            recupererGridContrats();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void naviguezVers(String URL) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(URL));
            btnContrats.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void afficherMouseClicked(MouseEvent mouseEvent) throws SQLException {
        InterfaceContratAjout.setVisible(false);
        InterfaceContratAfficher.setVisible(true);
        InterfaceContratSupprimer.setVisible(false);
        InterfaceContratModifier.setVisible(false);
        recupererGridContrats();

    }

    public void supprimerMouseClicked(MouseEvent mouseEvent) throws SQLException {
        InterfaceContratAjout.setVisible(false);
        InterfaceContratAfficher.setVisible(false);
        InterfaceContratSupprimer.setVisible(true);
        InterfaceContratModifier.setVisible(false);
        recupererListContrat(listContratSupprimer);

    }

    public void modifierMouseClicked(MouseEvent mouseEvent) throws SQLException {
        InterfaceContratAjout.setVisible(false);
        InterfaceContratAfficher.setVisible(false);
        InterfaceContratSupprimer.setVisible(false);
        InterfaceContratModifier.setVisible(true);
        recupererListContrat(listContratModifier);

    }

    public void modifierMouseClicked() throws SQLException {
        InterfaceContratAjout.setVisible(false);
        InterfaceContratAfficher.setVisible(false);
        InterfaceContratSupprimer.setVisible(false);
        InterfaceContratModifier.setVisible(true);
        recupererListContrat(listContratModifier);
    }
}
