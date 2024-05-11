package org.example.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import org.example.models.Boutique.Commande;
import org.example.services.Boutique.ServiceCommande;
import org.example.services.Boutique.ServiceProduit;

import java.io.FileOutputStream;
import java.util.List;

public class PDFGenerator {

    public static void genererPDFCommandes(List<Commande> commandes, String filePath) {
        Document document = new Document();

        final ServiceProduit serviceProduit = new ServiceProduit();
        final ServiceCommande serviceCommande = new ServiceCommande();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            Paragraph title = new Paragraph("Liste des commandes");
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);
            for (Commande commande : commandes) {
                document.add(new Paragraph("Produit : " + serviceProduit.afficherUnProduit(commande.getProduit().getId())));
                document.add(new Paragraph("Prix : " + commande.getSomme()));
                document.add(new Paragraph("Quantite : " + commande.getQuantite()));
                document.add(new Paragraph("------------------------"));
            }
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("TOTAL TTC: "+ serviceCommande.calculSomme(1)+" DT"));


            Image image = Image.getInstance("C:\\Users\\tun\\Desktop\\projet\\Oussamaassal2.0\\src\\main\\resources\\Boutique\\images\\thumbnail_logo.png");
            image.scaleToFit(400, 200); // Ajustez la taille de l'image selon vos besoins
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);

            document.close();
            System.out.println("Fichier PDF des commandes généré avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
