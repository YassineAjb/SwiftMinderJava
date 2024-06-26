package org.example.controllers.Election.Election;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.models.Election.Election;
import org.example.models.Election.SMSSender;
import org.example.services.Election.ElectionService;
import org.example.services.Election.MailSenderService;
import org.example.utils.Session;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AjouterElectionController {
    private final ElectionService ps = new ElectionService();
    private String selectedImagePath;
    private String elpaaaath;
    Election election = new Election();

    @FXML
    private Button btnAcceuil;
    @FXML
    private Button btnTerrain;
    @FXML
    private Button btnBoutique;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnReclamations;
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
    private Button btnReservation;

    @FXML
    private Button btnSignout;


    @FXML
    private TextField nomTF;
    @FXML
    private DatePicker dateTF;
    @FXML
    private TextField periodeTF;
    @FXML
    private TextField posteTF;
    @FXML
    private ImageView imageAjout;
    MailSenderService mailSenderService = new MailSenderService() ;

//    @FXML
//    void chosirImageEAjout(ActionEvent event) {
//        Stage stage = (Stage) imageAjout.getScene().getWindow();
//
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open a file");
//        fileChooser.setInitialDirectory(new File("C:/Users/tun/Desktop/projet/JAVAFX/Oussamaassal2.0/src/main/resources/Election/images"));
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("PNG image", "*.png"),
//                new FileChooser.ExtensionFilter("JPEG image", "*.jpg"),
//                new FileChooser.ExtensionFilter("All images", "*.str")
//        );
//        File selectedFile = fileChooser.showOpenDialog(stage);
//
//        if (selectedFile != null) {
//            selectedImagePath = selectedFile.getAbsolutePath();
//            Path selectedPath = Paths.get(selectedImagePath);
//            Path resourcePath = Paths.get("C:/Users/tun/Desktop/projet/JAVAFX/Oussamaassal2.0/src/main/resources/Election/images");
//            Path relativePath = resourcePath.relativize(selectedPath);
//            String elpaaaath = "/Election/images/"+relativePath;
//
//            System.out.println("*****************************");
//            System.out.println(selectedImagePath);
//            System.out.println(selectedPath);
//            System.out.println(resourcePath);
//            System.out.println(relativePath);
//            System.out.println(elpaaaath);
//            System.out.println("******************************");
//
//            election.setImgEpath(elpaaaath);
//
//            // Update the ImageView with the selected image
//            Image image = new Image("file:" + selectedImagePath);
//            imageAjout.setImage(image);
//        //System.out.println(image);
//
//
//        }
//    }


    @FXML
    private void chosirImageEAjout(ActionEvent event) {
        Stage stage = (Stage) imageAjout.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File("C:/Users/yassi/OneDrive/Documents/JavaFx/src/main/resources/Election/images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG image", "*.png"),
                new FileChooser.ExtensionFilter("JPEG image", "*.jpg"),
                new FileChooser.ExtensionFilter("All images", "*.str")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                // Define the target directory and create it if it doesn't exist
                Path targetDir = Paths.get("C:/xampp/htdocs/Images/Elections");
                if (!Files.exists(targetDir)) {
                    Files.createDirectories(targetDir);
                }

                // Define the target file path
                Path targetFilePath = targetDir.resolve(selectedFile.getName());

                // Move the file to the target directory
                Files.move(selectedFile.toPath(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);

                // Get the relative path for the election object
                Path relativePath = targetDir.relativize(targetFilePath);
                String elpaaaath = relativePath.toString().replace("\\", "/");

                // Update the election object with the new image path
                election.setImgEpath(elpaaaath);

                // Update the ImageView with the moved image
                Image image = new Image(targetFilePath.toUri().toString());
                imageAjout.setImage(image);

                System.out.println("*****************************");
                System.out.println("Selected file path: " + selectedFile.getAbsolutePath());
                System.out.println("Target file path: " + targetFilePath);
                System.out.println("Relative path: " + relativePath);
                System.out.println("Image path in election object: " + elpaaaath);
                System.out.println("******************************");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("No file selected.");
        }
    }

    /********************Ajout sans cntrl de saisie ***********************/
 /*   @FXML
    void ajouterElection(ActionEvent event)  {
        elpaaaath = election.getImgEpath();
        try {
            ps.ajouter(new Election(nomTF.getText(), dateTF.getValue(), posteTF.getText(), periodeTF.getText(),elpaaaath));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }*/
public void initialize(URL location, ResourceBundle resources) {
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
}

@FXML
void ajouterElection(ActionEvent event) {
    elpaaaath = election.getImgEpath();
    try {
        // Validate name (nomTF)
        if (isAlphaDigit(nomTF.getText())) {

            // Validate position (posteTF)
            if (isAlphabetic(posteTF.getText())) {

                // Validate image path (elpaaaath)
                if (!elpaaaath.isEmpty()) {

                    // Validate period (periodeTF)
                    if (isPeriodValid(periodeTF.getText())) {
                        // If all validations pass, add the Election
                        ps.ajouter(new Election(nomTF.getText(), dateTF.getValue(), posteTF.getText(), periodeTF.getText(), elpaaaath));
                        mailSenderService.sendEmailToAdmins("Ajout Election", " Election ajouté avec succès ");
                        SMSSender xx = new SMSSender();
                        //xx.send_SMS("Mr", " L'administrateur");

                        showSuccessMessage("Election added successfully!");
                        Affichage();
                        // Clear the form fields after success
                        clearFormFields();
                    } else {
                        // Display an alert if the period is not valid
                        showValidationError("Invalid period. Please enter a numeric value followed by 'ans'.");
                    }
                } else {
                    // Display an alert if the image path is empty
                    showValidationError("Image path cannot be empty.");
                }
            } else {
                // Display an alert if the position is not alphanumeric
                showValidationError("Invalid Poste. Please enter alphanumeric characters only.");
            }
        } else {
            // Display an alert if the name is not alphanumeric
            showValidationError("Invalid name. Please enter alphanumeric characters only.");
        }
    } catch (SQLException e) {
        // Handle SQL exception
        String errorMessage;

        // Check if the SQL exception is related to a foreign key constraint
        if (e.getErrorCode() == 1452 && e.getMessage().contains("foreign key")) {
            errorMessage = "Please check the Name or Poste of the Election. This name or poste does not exist!!";
        } else {
            errorMessage = e.getMessage();
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}


    // Helper method to check if a string contains only alphabetical characters
    private boolean isAlphabetic(String input) {
        return input.matches("[a-zA-Z]+");
    }
    private boolean isAlphaDigit(String input) {
        return input.matches("[a-zA-Z0-9]+");
    }

    // Helper method to check if a string is a valid period (numeric value followed by 'ans')
    private boolean isPeriodValid(String input) {
        return input.matches("\\d+ans");
    }

    // Helper method to show validation error in an Alert
    private void showValidationError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to show success message in an Alert
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to clear form fields after successful addition
    private void clearFormFields() {
        nomTF.clear();
        dateTF.setValue(null);
        posteTF.clear();
        periodeTF.clear();
    }


    @FXML
    void naviguezAVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherElection.fxml"));
            nomTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    void Affichage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/AfficherElection.fxml"));
            nomTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Election/AfficherElection.fxml"));
            nomTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void naviguezVers(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            nomTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


}
