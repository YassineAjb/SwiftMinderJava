package org.example.controllers.Employe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Employe.Contrat;
import org.example.services.Employe.ServiceContrat;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class ContratDashBoard {
    @FXML
    private ImageView Image;

    @FXML
    private Label salaire;

    @FXML
    private Label date_debut;

    @FXML
    private Label date_fin;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    private Contrat contrat;

    private ServiceContrat serviceContrat;

    public ContratController contratController;



    public void setData(Contrat contrat) {
        this.contrat = contrat;
        salaire.setText(String.valueOf(contrat.getSalaire()));
        date_debut.setText(String.valueOf(Date.valueOf(contrat.getdate().getValue().getStartDate())));
        date_fin.setText(String.valueOf(Date.valueOf(contrat.getdate().getValue().getEndDate())));
        Image.setImage(new Image("/Employee/images/"+contrat.getJoueur().getImagePath()));
    }

    @FXML
    void modifier(ActionEvent event) throws SQLException {

        contratController.Selected(contrat);

        contratController.modifierMouseClicked();
    }

    @FXML
    void supprimer(ActionEvent event) throws IOException {
        try {

            serviceContrat.supprimer(contrat.getId());

            contratController.refreshContrats();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour définir le contrôleur AfficherJoueur
    public void setAfficherJoueurController(ContratController Controller) {
        this.contratController = Controller;
    }
}
