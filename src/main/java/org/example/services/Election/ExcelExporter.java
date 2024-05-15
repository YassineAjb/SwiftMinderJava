package org.example.services.Election;

import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.models.Election.Candidat;

import java.io.File;
import java.io.FileOutputStream;


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
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nom");
            headerRow.createCell(1).setCellValue("Prenom");
            headerRow.createCell(2).setCellValue("Age");

            // Set color for the header row
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            for (Cell cell : headerRow) {
                cell.setCellStyle(headerStyle);
            }

// Display number of candidates
            Row countRow = sheet.createRow(1);
            countRow.createCell(0).setCellValue("Number of Candidates:");
            countRow.createCell(1).setCellValue(listView.getItems().size());

// Set width for column 0 to fit the content
            sheet.setColumnWidth(0, (int) (countRow.getCell(0).getStringCellValue().length() * 256 * 1.15)); // Adjust width as needed


            // Populate the Excel sheet with data
            int rowCount = 1; // Start from row 2 after header and count rows
            int totalAge = 0;
            for (Candidat candidat : listView.getItems()) {
                Row dataRow = sheet.createRow(++rowCount); // Increment row count for each candidate
                Cell cellNom = dataRow.createCell(0);
                cellNom.setCellValue(candidat.getNomC());

                Cell cellPrenom = dataRow.createCell(1);
                cellPrenom.setCellValue(candidat.getPrenomC());

                Cell cellAge = dataRow.createCell(2);
                cellAge.setCellValue(candidat.getAgeC());

                // Apply alternating colors to data rows
                CellStyle dataStyle = workbook.createCellStyle();
                dataStyle.setFillForegroundColor(rowCount % 2 == 0 ? IndexedColors.LIGHT_YELLOW.getIndex() : IndexedColors.WHITE.getIndex());
                dataStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellNom.setCellStyle(dataStyle);
                cellPrenom.setCellStyle(dataStyle);
                cellAge.setCellStyle(dataStyle);

                totalAge += candidat.getAgeC(); // Calculate total age while populating data
            }

            // Calculate average age
            int averageAge = totalAge / listView.getItems().size();

            // Create a final row for displaying average age
            Row averageRow = sheet.createRow(++rowCount);
            averageRow.createCell(0).setCellValue("Average Age:");
            averageRow.createCell(2).setCellValue(averageAge); // Set average age in the third cell

            // Set style for the average row
            CellStyle averageStyle = workbook.createCellStyle();
            averageStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            averageStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            for (Cell cell : averageRow) {
                cell.setCellStyle(averageStyle);
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