package org.example.controllers.Employe;

import com.dlsc.gemsfx.daterange.DateRangePicker;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.models.Employe.Contrat;
import org.example.models.Employe.Joueur;
import org.example.models.Employe.JoueurCell;
import org.example.services.Employe.ServiceJoueur;
import org.example.utils.Session;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

public class Employe {
    private final ServiceJoueur serviceJoueur= new ServiceJoueur();
    @FXML
    private TextField AgeAjout;

    @FXML
    private TextField AgeModifier;

    @FXML
    private CheckBox DroiteAjout;

    @FXML
    private CheckBox DroiteModifier;

    @FXML
    private CheckBox GaucheAjout;

    @FXML
    private CheckBox GaucheModifier;

    @FXML
    private TextField HauteurAjout;

    @FXML
    private TextField HauteurModifier;

    @FXML
    private TextField IdAjout;

    @FXML
    private TextField IdContrat;

    @FXML
    private TextField IdModifier;

    @FXML
    private TextField IdSupprimer;

    @FXML
    private AnchorPane IntrefaceContrat;

    @FXML
    private TextField NomAjout;

    @FXML
    private TextField NomModifier;

    @FXML
    private TextField PiedfortAjout;

    @FXML
    private TextField PiedfortModifier;

    @FXML
    private TextField PoidsAjout;

    @FXML
    private TextField LinkAjout;

    @FXML
    private TextField LinkModifier;

    @FXML
    private TextField PoidsModifier;

    @FXML
    private ComboBox<String > PositionAjout;

    @FXML
    private ComboBox<String> PositionModifier;

    @FXML
    private TextField PrenomAjout;

    @FXML
    private TextField PrenomModifier;

    @FXML
    private Button ajouterContrat;

    @FXML
    private Button ajouterJoueur;

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
    private Button clearJoueurAjout;

    @FXML
    private Button clearJoueurModifier;

    @FXML
    private GridPane gridJoueurs;

    @FXML
    private TextField imageModifier;

    @FXML
    private TextField imageAjout;

    @FXML
    private ImageView importedImageAjout;

    @FXML
    private ImageView importedImageModifier;
    @FXML
    private Button btnArticlles;

    @FXML
    private TextField shirtAjout;

    @FXML
    private TextField shirtModifier;

    @FXML
    private Button importerJoueurAjout;

    @FXML
    private Button importerJoueurModifier;

    @FXML
    private AnchorPane interfaceAffichage;

    @FXML
    private AnchorPane interfaceAjout;

    @FXML
    private AnchorPane interfaceModification;

    @FXML
    private AnchorPane interfaceSuppression;

    @FXML
    private ListView<Contrat> listContrat;

    @FXML
    private ListView<Joueur> listJoueur;

    @FXML
    private ListView<Joueur> listJoueurAjout;

    @FXML
    private ListView<Joueur> listJoueurModifier;

    @FXML
    private Button modifierContrat;

    @FXML
    private Button modifierJoueur;

    @FXML
    private Button supprimerContrat;

    @FXML
    private Button supprimerJoueur;

    @FXML
    private TextField searchSupprimer;

    @FXML
    private TextField searchModifier;

    @FXML
    private DateRangePicker ContratDateAjout;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnReclamations;

    @FXML
    private ComboBox<?> ContratEmployeId;

    @FXML
    private TextField SalaireAjout;

    @FXML
    private PieChart pieChart;

    private String filePath;

    private List<Joueur> joueurs;

    FileChooser fileChooser = new FileChooser();

    public void initialize() throws SQLException {

        ControlleDeSaisie( AgeAjout, HauteurAjout, PoidsAjout, NomAjout, PrenomAjout,shirtAjout);
        ControlleDeSaisie(AgeModifier, HauteurModifier, PoidsModifier, NomModifier, PrenomModifier,shirtModifier);

        InisialiserPoisitions(PositionAjout);
        InisialiserPoisitions(PositionModifier);

        try {
            recupererListJoueur(listJoueurAjout);
            recupererListJoueur(listJoueur);
            recupererListJoueur(listJoueurModifier);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PieChartPosition();


        DroiteAjout.setOnAction(event -> {
            PiedfortAjout.setText("Droite");
            GaucheAjout.setSelected(false); // Uncheck checkbox 2 if checkbox 1 is checked
        });
        DroiteModifier.setOnAction(event -> {
            PiedfortModifier.setText("Droite");
            GaucheModifier.setSelected(false); // Uncheck checkbox 2 if checkbox 1 is checked
        });

        GaucheAjout.setOnAction(event -> {
            PiedfortAjout.setText("Gauche");
            DroiteAjout.setSelected(false); // Uncheck checkbox 2 if checkbox 1 is checked
        });
        GaucheModifier.setOnAction(event -> {
            PiedfortModifier.setText("Gauche");
            DroiteModifier.setSelected(false); // Uncheck checkbox 2 if checkbox 1 is checked
        });

        clearJoueurAjout.setOnAction(event -> {
            Clear(IdAjout,NomAjout,PositionAjout,HauteurAjout,PoidsAjout,PrenomAjout,AgeAjout,imageAjout,DroiteAjout,GaucheAjout, PiedfortAjout, LinkAjout);
        });
        clearJoueurModifier.setOnAction(event -> {
            Clear(IdModifier, NomModifier, PositionModifier, HauteurModifier, PoidsModifier, PrenomModifier, AgeModifier, imageModifier, DroiteModifier, GaucheModifier, PiedfortModifier, LinkModifier);
        });

        listJoueur.setOnMouseClicked((EventHandler<MouseEvent>) event -> {
            Selected(listJoueur,IdSupprimer,NomAjout,PositionAjout,HauteurAjout,PoidsAjout,PrenomAjout,AgeAjout,imageAjout,DroiteAjout,GaucheAjout,importedImageAjout, PiedfortAjout,LinkAjout, shirtAjout);
        });
        listJoueurModifier.setOnMouseClicked((EventHandler<MouseEvent>) event -> {
            Selected(listJoueurModifier,IdModifier, NomModifier, PositionModifier, HauteurModifier, PoidsModifier, PrenomModifier, AgeModifier, imageModifier, DroiteModifier, GaucheModifier, importedImageModifier, PiedfortModifier,LinkModifier,shirtModifier);
        });


        importerJoueurAjout.setOnAction(e -> {
            Importer(importedImageAjout,imageAjout);
        });
        importerJoueurModifier.setOnAction(e -> {
            Importer(importedImageModifier,imageModifier);
        });

        ajouterJoueur.setOnAction(e ->{
            AjouterJoueur(NomAjout, PrenomAjout, AgeAjout, PositionAjout, HauteurAjout, PoidsAjout, DroiteAjout, GaucheAjout, PiedfortAjout, filePath, LinkAjout, shirtAjout);


        });

        modifierJoueur.setOnAction(e -> {
            ModifierJoueur(NomModifier, PrenomModifier, AgeModifier, PositionModifier, HauteurModifier, PoidsModifier, DroiteModifier, GaucheModifier, IdModifier, PiedfortModifier, imageModifier,LinkModifier);
        });

        supprimerJoueur.setOnAction(e -> {
            SupprimerJoueur(IdSupprimer);
        });
        ObservableList<Joueur> list = recupererListJoueur(listJoueur);
        //GridJoueur(list);
        recupererGridJoueur();
        filterItems(searchSupprimer.getText(),listJoueur);

        searchSupprimer.setOnKeyTyped(e -> {
            try {
                filterItems(searchSupprimer.getText(),listJoueur);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        searchModifier.setOnKeyTyped(e -> {
            try {
                filterItems(searchModifier.getText(),listJoueurModifier);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
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

    }
    private void filterItems(String searchText,ListView<Joueur> listView) throws SQLException {
        // If the search text is empty, display all original items
        if (searchText == null || searchText.isEmpty()) {
            recupererListJoueur(listView);
            return;
        }

        // Otherwise, filter the original items based on the search text
        ObservableList<Joueur> filteredList = FXCollections.observableArrayList();
        for (Joueur item : recupererListJoueur(listView)) {
            if (item.getPosition().toLowerCase().equals(searchText.toLowerCase())) {
                filteredList.add(item);
            }
            if (item.getPiedfort().toLowerCase().equals(searchText.toLowerCase())) {
                filteredList.add(item);
            }
        }
        listView.setItems(filteredList);
    }

    public static String getProjectPath() {
        Path currentPath = Paths.get("").toAbsolutePath();
        return currentPath.toString();
    }
    private void AjouterJoueur(TextField nomAjout, TextField prenomAjout, TextField ageAjout, ComboBox<String> positionAjout, TextField hauteurAjout, TextField poidsAjout, CheckBox DroiteAjout, CheckBox GaucheAjout, TextField PiedfortAjout, String filePath, TextField Link, TextField shirtnumber) {
        try {
            if (nomAjout.getText().isEmpty() || prenomAjout.getText().isEmpty() || ageAjout.getText().isEmpty() || positionAjout.getValue() == null || hauteurAjout.getText().isEmpty() || poidsAjout.getText().isEmpty() || PiedfortAjout.getText().isEmpty() || shirtnumber.getText().isEmpty()) {
                // Display a warning or error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Empty Fields");
                alert.setContentText("Please fill in all the required fields.");
                alert.showAndWait();
            }else{
                serviceJoueur.ajouter(new Joueur(positionAjout.getValue(),Integer.parseInt(hauteurAjout.getText()),Integer.parseInt(poidsAjout.getText()), PiedfortAjout.getText(), nomAjout.getText(), prenomAjout.getText(),Integer.parseInt(ageAjout.getText()), filePath, Link.getText() ,Integer.parseInt(shirtnumber.getText())));

            }

            recupererListJoueur(listJoueurAjout);
            List<Joueur> Js = null;
            try {
                Js = serviceJoueur.afficher();
                BitMatrix matrix = null;
                matrix = new MultiFormatWriter().encode(Js.get(Js.size()-1).getLink(), BarcodeFormat.QR_CODE,500,500);
                MatrixToImageWriter.writeToPath(matrix,"png", Paths.get(getProjectPath() + "\\src\\main\\resources\\Employee\\QR\\"+Js.get(Js.size()-1).getId()+".png"));
                System.out.println("QR CODE GENERATED");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (WriterException ex) {
                throw new RuntimeException(ex);
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

    private void SupprimerJoueur(TextField IdAjout) {
        try {
            serviceJoueur.supprimer(Integer.parseInt(IdAjout.getText()));
            recupererListJoueur(listJoueur);
        }catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void GridJoueur(ObservableList<Joueur> list) {
        try{
            gridJoueurs.getChildren().clear();
            gridJoueurs.setVgap(20); // Vertical gap between nodes

            int rowIndex = 1;
            List<Joueur> joueurs = serviceJoueur.afficher();
            for (Joueur joueur : list) {
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
                Label label = new Label(joueur.getNom()+"\n"+joueur.getPrenom()+"\n"+joueur.getAge());
                ImageView imageView = new ImageView(new Image(("/Employee/images/"+joueur.getImagePath())));
                label.getStyleClass().add("grid-cell");
                imageView.getStyleClass().add("grid-cell");
                imageView.setFitWidth(150); // Set the desired width
                imageView.setFitHeight(150); // Set the desired height
                gridJoueurs.setMargin(imageView, new Insets(20));
                gridJoueurs.addRow(rowIndex, imageView, label,button);
                rowIndex++;
            }
        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }




    private void ModifierJoueur(TextField nomAjout, TextField prenomAjout, TextField ageAjout, ComboBox<String> positionAjout, TextField hauteurAjout, TextField poidsAjout, CheckBox DroiteAjout, CheckBox GaucheAjout, TextField IdAjout, TextField PiedfortAjout, TextField filePath, TextField Link) {
        try{
            if (nomAjout.getText().isEmpty() || prenomAjout.getText().isEmpty() || ageAjout.getText().isEmpty() || positionAjout.getValue() == null || hauteurAjout.getText().isEmpty() || poidsAjout.getText().isEmpty() || (!DroiteAjout.isSelected() && !GaucheAjout.isSelected())) {
                // Display a warning or error message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Empty Fields");
                alert.setContentText("Please fill in all the required fields.");
                alert.showAndWait();
            }else {
                serviceJoueur.modifier(new Joueur(Integer.parseInt(IdAjout.getText()), positionAjout.getValue(), Integer.parseInt(hauteurAjout.getText()), Integer.parseInt(poidsAjout.getText()), PiedfortAjout.getText(), nomAjout.getText(), prenomAjout.getText(), Integer.parseInt(ageAjout.getText()), filePath.getText(), Link.getText()));
            }
            recupererListJoueur(listJoueurModifier);

        }catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void Importer(ImageView importedImage,TextField img) {
        fileChooser.setTitle("Select File");
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            System.out.println("Selected file: " + selectedFile.getPath());
            filePath = selectedFile.getName();
            img.setText(filePath);
            try {
                Image image = new Image("/Employee/images/" + filePath);
                importedImage.setImage(image);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("No file selected.");
        }
    }

    private void InisialiserPoisitions(ComboBox<String> position) {
        ObservableList<String> positionOptions = FXCollections.observableArrayList("GK","DC","AL","MD","MC","MO","AD","AG","AP","SA");
        position.setItems(positionOptions);
    }

    private void ControlleDeSaisie(TextField AgeAjout,TextField HauteurAjout,TextField PoidsAjout,TextField NomAjout,TextField PrenomAjout, TextField shirtAjout) {
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


        shirtAjout.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String input = event.getCharacter();
            if (!input.matches("[0-9]")) {
                event.consume();
            }
        });
    }

    private void Clear(TextField IdAjout, TextField NomAjout, ComboBox PositionAjout, TextField HauteurAjout, TextField PoidsAjout, TextField PrenomAjout, TextField AgeAjout, TextField imageAjout, CheckBox DroiteAjout, CheckBox GaucheAjout, TextField PiedfortAjout , TextField LinkAjout) {
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
        LinkAjout.setText(null);

    }

    private void Selected(ListView<Joueur> listJoueur, TextField IdModifier, TextField NomModifier, ComboBox PositionModifier, TextField HauteurModifier, TextField PoidsModifier, TextField PrenomModifier, TextField AgeModifier, TextField imageModifier, CheckBox DroiteModifier, CheckBox GaucheModifier, ImageView importedImageModifier, TextField PiedfortModifier, TextField Link, TextField shirtnumber) {
        Joueur selectedJoueur = listJoueur.getSelectionModel().getSelectedItem();
        if (selectedJoueur != null) {
            IdModifier.setText(String.valueOf(selectedJoueur.getId()));
            NomModifier.setText(selectedJoueur.getNom());
            PositionModifier.setValue(selectedJoueur.getPosition());
            HauteurModifier.setText(String.valueOf(selectedJoueur.getHauteur()));
            PoidsModifier.setText(String.valueOf(selectedJoueur.getPoids()));
            PiedfortModifier.setText(selectedJoueur.getPiedfort());
            if (selectedJoueur.getPiedfort().equals("Droite")) {
                DroiteModifier.setSelected(true);
                GaucheModifier.setSelected(false);
            } else if (selectedJoueur.getPiedfort().equals("Gauche")) {
                DroiteModifier.setSelected(false);
                GaucheModifier.setSelected(true);
            }

            PrenomModifier.setText(selectedJoueur.getPrenom());
            AgeModifier.setText(String.valueOf(selectedJoueur.getAge()));
            imageModifier.setText(selectedJoueur.getImagePath());
            Link.setText(selectedJoueur.getLink());
            shirtnumber.setText(String.valueOf(selectedJoueur.getShirtnum()));
            System.out.println("images/" + selectedJoueur.getImagePath());

            try {
                Image image = new Image("/Employee/images/" + selectedJoueur.getImagePath());
                importedImageModifier.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void Selected(Joueur joueur) {
        Joueur selectedJoueur = joueur;
        if (selectedJoueur != null) {
            IdModifier.setText(String.valueOf(selectedJoueur.getId()));
            NomModifier.setText(selectedJoueur.getNom());
            PositionModifier.setValue(selectedJoueur.getPosition());
            HauteurModifier.setText(String.valueOf(selectedJoueur.getHauteur()));
            PoidsModifier.setText(String.valueOf(selectedJoueur.getPoids()));
            PiedfortModifier.setText(selectedJoueur.getPiedfort());
            if (selectedJoueur.getPiedfort().equals("Droite")) {
                DroiteModifier.setSelected(true);
                GaucheModifier.setSelected(false);
            } else if (selectedJoueur.getPiedfort().equals("Gauche")) {
                DroiteModifier.setSelected(false);
                GaucheModifier.setSelected(true);
            }

            PrenomModifier.setText(selectedJoueur.getPrenom());
            AgeModifier.setText(String.valueOf(selectedJoueur.getAge()));
            imageModifier.setText(selectedJoueur.getImagePath());
            shirtModifier.setText(String.valueOf(selectedJoueur.getShirtnum()));
            System.out.println("images/" + selectedJoueur.getImagePath());

            try {
                Image image = new Image("/Employee/images/" + selectedJoueur.getImagePath());
                importedImageModifier.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private ObservableList<Joueur> recupererListJoueur(ListView<Joueur> listJoueur) throws SQLException {
        List<Joueur> joueurs = serviceJoueur.afficher(); // Assuming serviceJoueur is your service class for managing Employe
        ObservableList<Joueur> observableArrayList = FXCollections.observableArrayList(joueurs);

        listJoueur.setCellFactory(new Callback<ListView<Joueur>, ListCell<Joueur>>() {
            @Override
            public ListCell<Joueur> call(ListView<Joueur> listView) {
                return new JoueurCell();
            }
        });

        listJoueur.setItems(observableArrayList);
        return observableArrayList;
    }
    private int count(String pos) throws SQLException {
        int num=0;
        List<Joueur> joueurs = serviceJoueur.afficher();
        for(Joueur joueur : joueurs){
            if(joueur.getPosition().equals(pos)){
                num++;
            }
        }
        return num;
    }

    private void PieChartPosition() throws SQLException {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("AD",count("AD")),
                        new PieChart.Data("DC",count("DC")),
                        new PieChart.Data("AL",count("AL")),
                        new PieChart.Data("MD",count("MD")),
                        new PieChart.Data("MC",count("MC")),
                        new PieChart.Data("MO",count("MO")),
                        new PieChart.Data("GK",count("GK")),
                        new PieChart.Data("AG",count("AG")),
                        new PieChart.Data("AP",count("MC")),
                        new PieChart.Data("SA",count("SA")));
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                        data.getName(),"=",data.pieValueProperty()
                        )
                )
        );
        pieChart.getData().addAll(pieChartData);
    }

    public void recupererGridJoueur() throws SQLException {
        List<Joueur> joueurs = serviceJoueur.afficher();
        gridJoueurs.getChildren().clear();

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < joueurs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Employee/JoueurDashBoard.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                anchorPane.setPrefHeight(1160);
               if(column==4){
                    column =0;
                    row++;
                }
                JoueurDashBoard joueurDashBoard = fxmlLoader.getController();
                joueurDashBoard.setData(joueurs.get(i));
                joueurDashBoard.setAfficherJoueurController(this);
                gridJoueurs.add(anchorPane,column++,row);

                gridJoueurs.setMinWidth(1140);
                gridJoueurs.setPrefWidth(1140);
                gridJoueurs.setMaxWidth(1140);

                /*gridJoueurs.setMinHeight(300);
                gridJoueurs.setPrefHeight(300);
                gridJoueurs.setMaxHeight(300);*/

                gridJoueurs.setMargin(anchorPane,new Insets(10));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshJoueurs() {
        try {
            joueurs = serviceJoueur.afficher();
            recupererGridJoueur();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            listJoueur.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void ajouterMouseClicked(MouseEvent mouseEvent) throws SQLException {
        interfaceAffichage.setVisible(false);
        interfaceAjout.setVisible(true);
        interfaceSuppression.setVisible(false);
        interfaceModification.setVisible(false);
        recupererListJoueur(listJoueurAjout);
        recupererListJoueur(listJoueur);
        recupererListJoueur(listJoueurModifier);
    }

    public void afficherMouseClicked(MouseEvent mouseEvent) throws SQLException {
        interfaceAjout.setVisible(false);
        interfaceAffichage.setVisible(true);
        interfaceSuppression.setVisible(false);
        interfaceModification.setVisible(false);
        ObservableList<Joueur> list = recupererListJoueur(listJoueur);
        recupererGridJoueur();
    }
    public void supprimerMouseClicked(MouseEvent mouseEvent) throws SQLException {
        interfaceAjout.setVisible(false);
        interfaceAffichage.setVisible(false);
        interfaceSuppression.setVisible(true);
        interfaceModification.setVisible(false);
        recupererListJoueur(listJoueurAjout);
        recupererListJoueur(listJoueur);
        recupererListJoueur(listJoueurModifier);
    }
    public void modifierMouseClicked(MouseEvent mouseEvent) throws SQLException {
        interfaceAjout.setVisible(false);
        interfaceAffichage.setVisible(false);
        interfaceSuppression.setVisible(false);
        interfaceModification.setVisible(true);
        recupererListJoueur(listJoueurAjout);
        recupererListJoueur(listJoueur);
        recupererListJoueur(listJoueurModifier);
    }


    public void modifierMouseClicked() throws SQLException {
        interfaceAjout.setVisible(false);
        interfaceAffichage.setVisible(false);
        interfaceSuppression.setVisible(false);
        interfaceModification.setVisible(true);
        recupererListJoueur(listJoueurAjout);
        recupererListJoueur(listJoueur);
        recupererListJoueur(listJoueurModifier);
    }
}


