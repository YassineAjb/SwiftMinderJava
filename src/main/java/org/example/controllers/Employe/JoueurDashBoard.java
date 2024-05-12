package org.example.controllers.Employe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Employe.Joueur;
import org.example.services.Employe.ServiceJoueur;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class JoueurDashBoard {
    @FXML
    private ImageView Image;

    @FXML
    private Label Nom;

    @FXML
    private Label Prenom;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    private Joueur joueur;
    
    private ServiceJoueur serviceJoueur;

    public Employe employe;


    public void setData(Joueur joueur) {
        this.joueur = joueur;
        Nom.setText(joueur.getNom());
        Prenom.setText(String.valueOf(joueur.getPrenom()));
        String imagePath = "C:\\xampp\\htdocs\\Images\\Joueurs\\" + joueur.getImagePath();
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        Image.setImage(image);
    }

    @FXML
    void modifier(ActionEvent event) throws SQLException {

        employe.Selected(joueur);

        employe.modifierMouseClicked();
    }

    @FXML
    void supprimer(ActionEvent event) throws IOException {
        try {
            ServiceJoueur serviceJoueur = new ServiceJoueur();
            serviceJoueur.supprimer(joueur.getId());

            employe.refreshJoueurs();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour définir le contrôleur AfficherJoueur
    public void setAfficherJoueurController(Employe EmployeController) {
        this.employe = EmployeController;
    }
}
