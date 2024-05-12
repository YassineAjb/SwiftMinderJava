package org.example.controllers.Election.Candidat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.Election.Candidat;
import org.example.services.Election.CandidatService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ModifierCandidatController {
    @FXML
    private ImageView imageCModif;

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
    private ImageView imageEModif;

    @FXML
    private TextField prenomTFCM;

    @FXML
    private Button btncalendrier;

    @FXML
    private TextField ageTFCM;

    @FXML
    private TextField nomTFCM;

    @FXML
    private TextField nomElectionTFCM;

    @FXML
    private Button btnAcceuil1;


    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            btnBoutique.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private final CandidatService ps = new CandidatService();


    private String selectedImagePathModifC;
    private String elpaaaathModifC;
    Candidat candidat = new Candidat();
    int idCandidat;

    public void initializeValues(String nomC, String prenomC, int ageC, String imgCpath, int idC) {


        nomTFCM.setText(nomC);
        prenomTFCM.setText(prenomC);
        ageTFCM.setText(String.valueOf(ageC));
        nomElectionTFCM.setText("ElectionTest");
        //nomElectionTFCM.setText(String.valueOf(ageC));
        //imgEpath.setText(imgEpath);

        // String imagePath = election.getImgEpath();
        InputStream stream = getClass().getResourceAsStream(imgCpath);
        Image image = new Image(stream);
        imageCModif.setImage(image);

        idCandidat = idC;
    }


    @FXML
    void chosirImageCModif(ActionEvent event) {
        Stage stage = (Stage) imageCModif.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File("C:/Users/yassi/IdeaProjects/ProjetAjbouni/ProjetPidev3A8/src/main/resources/images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG image", "*.png"),
                new FileChooser.ExtensionFilter("JPEG image", "*.jpg"),
                new FileChooser.ExtensionFilter("All images", "*.str")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            selectedImagePathModifC = selectedFile.getAbsolutePath();
            Path selectedPath = Paths.get(selectedImagePathModifC);
            Path resourcePath = Paths.get("C:/Users/yassi/IdeaProjects/ProjetAjbouni/ProjetPidev3A8/src/main/resources/images");
            Path relativePath = resourcePath.relativize(selectedPath);
            String elpaaaathModifC = "/images/" + relativePath;

            System.out.println("*****************************");
            System.out.println(selectedImagePathModifC);
            System.out.println(selectedPath);
            System.out.println(resourcePath);
            System.out.println(relativePath);
            System.out.println(elpaaaathModifC);
            System.out.println("******************************");

            candidat.setImgCpath(elpaaaathModifC);

            // Update the ImageView with the selected image
            Image image = new Image("file:" + selectedImagePathModifC);
            imageCModif.setImage(image);            //System.out.println(image);


        }
    }

    private boolean isAlphabetic(String input) {
        return input.matches("[a-zA-Z]+");
    }

    // Helper method to check if the age is within the specified range (25 to 99)
    private boolean isAgeValid(String ageStr) {
        try {
            int age = Integer.parseInt(ageStr);
            return age >= 25 && age <= 99;
        } catch (NumberFormatException e) {
            // If parsing fails, it's not a valid integer
            return false;
        }
    }
    @FXML
    void modifierCandidat(ActionEvent event) {
        elpaaaathModifC = candidat.getImgCpath();
        //int x;

        try {
            //x = ps.getIdEbynom(nomElectionTFCM.getText());
            //System.out.println("teeeeeeeeeeeeeest "+x);

            // Validate name (nomTFCM) and surname (prenomTFCM)
            if (isAlphabetic(nomTFCM.getText()) && isAlphabetic(prenomTFCM.getText())) {

                // Validate age (ageTFCM)
                if (isAgeValid(ageTFCM.getText())) {
                   // System.out.println("Value of x: " + x);

                    ps.modifierCC(nomTFCM.getText(), prenomTFCM.getText(), Integer.valueOf(ageTFCM.getText()), elpaaaathModifC,nomElectionTFCM.getText(), idCandidat
                    );
                    // Display a success message
                    showSuccessMessage("Candidat modified successfully!");
                    switchToDisplayAllCandidats();
                } else {
                    // Display an alert if the age is not a valid integer or outside the range
                    showValidationError("Invalid age. Please enter a valid integer between 25 and 99.");
                }
            } else {
                // Display an alert if the name or surname is not alphabetic
                showValidationError("Invalid name or surname. Please enter alphabetical characters only.");
            }
        } catch (SQLException e) {
            String errorMessage;

            // Check if the SQL exception is related to a foreign key constraint
            if (e.getErrorCode() == 1452 && e.getMessage().contains("foreign key")) {
                errorMessage = "Please check the Name of the Election. This name does not exist!";
            } else {
                errorMessage = e.getMessage();
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error while modifying an election");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
    }


    public void switchToDisplayAllCandidats() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Election/AfficherCandidat.fxml"));
            Parent newPageRoot = loader.load();


            Scene pageScene = new Scene(newPageRoot);
            Stage stage = (Stage) nomTFCM.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Helper method to show validation error in an Alert
    private void showValidationError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/AfficherCandidat.fxml"));
            nomTFCM.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
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
}