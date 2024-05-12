package org.example.controllers.Election.Candidat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.Election.Candidat;
import org.example.services.Election.CandidatService;
import org.example.services.Election.MailSenderService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AjouterCandidatController {


    @FXML
    private TextField nomTFCA;

    @FXML
    private TextField prenomTFCA;

    @FXML
    private Button btnReservation;

    @FXML
    private TextField nomElectionTFCA;
    @FXML
    private Button btncalendrier;

    @FXML
    private ImageView imageCAjout;

    @FXML
    private Button btnBoutique;

    @FXML
    private TextField ageTFCA;

    @FXML
    private Button btnAcceuil;

    @FXML
    private Button btnContrats;

    @FXML
    private Button btnElection;

    @FXML
    private Button btnJoueurs;

    @FXML
    private Button btnMatch;

    @FXML
    private Button btnSignout;
    private String selectedImagePathC;
    private String elpaaaathC;
    Candidat candidat = new Candidat();
    private final CandidatService ps = new CandidatService();

    MailSenderService mailSenderService = new MailSenderService() ;

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
    void chosirImageCAjout(ActionEvent event) {
        Stage stage = (Stage) imageCAjout.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File("C:\\Users\\tun\\Desktop\\projet\\Oussamaassal\\src\\main\\resources\\Election/images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG image", "*.png"),
                new FileChooser.ExtensionFilter("JPEG image", "*.jpg"),
                new FileChooser.ExtensionFilter("All images", "*.str")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            selectedImagePathC = selectedFile.getAbsolutePath();
            Path selectedPath = Paths.get(selectedImagePathC);
            Path resourcePath = Paths.get("C:\\Users\\tun\\Desktop\\projet\\Oussamaassal\\src\\main\\resources\\Election/images");
            Path relativePath = resourcePath.relativize(selectedPath);
            String elpaaaathC = "/images/"+relativePath;

            System.out.println("*****************************");
            System.out.println(selectedImagePathC);
            System.out.println(selectedPath);
            System.out.println(resourcePath);
            System.out.println(relativePath);
            System.out.println(elpaaaathC);
            System.out.println("******************************");

            candidat.setImgCpath(elpaaaathC);

            // Update the ImageView with the selected image
            Image image = new Image("file:" + selectedImagePathC);
            imageCAjout.setImage(image);            //System.out.println(image);


        }
    }
/*************************ajout sans cntrl saisie*********************/
   /* @FXML
    void ajouterCandidat(ActionEvent event) {
        elpaaaathC = candidat.getImgCpath();
        int x;
        try {
            x=ps.getIdEbynom(nomElectionTFCA.getText());
            ps.ajouter(new Candidat(nomTFCA.getText(), prenomTFCA.getText(), Integer.valueOf(ageTFCA.getText()),elpaaaathC,x));
            mailSenderService.sendEmail("Congratulations", "flousek waslet sa7pi");

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }*/

// Helper method to check if a string contains only alphabetical characters

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
    void ajouterCandidat(ActionEvent event) {
        elpaaaathC = candidat.getImgCpath();
        int x;

        try {
            x = ps.getIdEbynom(nomElectionTFCA.getText());

            // Validate name (nomTFCA) and surname (prenomTFCA)
            if (isAlphabetic(nomTFCA.getText()) && isAlphabetic(prenomTFCA.getText())) {

                // Validate age (ageTFCA)
                if (isAgeValid(ageTFCA.getText())) {

                    // Validate image path (elpaaaathC)
                    if (!elpaaaathC.isEmpty()) {
                        ps.ajouter(new Candidat(nomTFCA.getText(), prenomTFCA.getText(), Integer.valueOf(ageTFCA.getText()), elpaaaathC, x));
                        mailSenderService.sendEmail("Ajout Candidat", "Candidat ajouté avec succés ");
                        // Show success message
                        showSuccessMessage("Candidate added successfully!");

                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/Election/Affichercandidat.fxml"));
                            nomTFCA.getScene().setRoot(root);
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }


                    } else {
                        // Display an alert if image path is empty
                        showValidationError("Image path cannot be empty. Please select an image.");
                    }
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
            alert.setTitle("Error");
            alert.setContentText(errorMessage);
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
    void naviguezAVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/VoterMembre.fxml"));
            nomTFCA.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/AfficherCandidat.fxml"));
            ageTFCA.getScene().setRoot(root);
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




