package org.example.services.Election;

import javafx.scene.control.ListView;
import org.example.models.Election.Candidat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

import java.io.File;

import javafx.stage.FileChooser;


public class ExcelExporter {

    public void generateExcel(ListView<Candidat> listView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
        );
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet1");

            // Create header row
            // Row headerRow = sheet.createRow(0);
            //String[] headers = {
            //  "Username", "Email", "Password", "Role",
            // "Image", "Age", "Sexe"
            //};
            // En-têtes des colonnes
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nom");
            headerRow.createCell(1).setCellValue("Prenom");
            headerRow.createCell(2).setCellValue("Age");

            // Ajoutez les en-têtes des autres colonnes ici

            for (int i = 0; i < listView.getItems().size(); i++) {
                Row dataRow = sheet.createRow(i + 1); // +1 pour éviter d'écraser l'en-tête
                Candidat campaign = listView.getItems().get(i);

                dataRow.createCell(0).setCellValue(campaign.getNomC());
                dataRow.createCell(1).setCellValue(campaign.getPrenomC());
                dataRow.createCell(2).setCellValue( campaign.getAgeC());


            }

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                workbook.close();
                System.out.println("Excel generated successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}