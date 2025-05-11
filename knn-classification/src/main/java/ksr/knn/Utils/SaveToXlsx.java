package ksr.knn.Utils;

import ksr.knn.Classifier.ClassificationResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;


public class SaveToXlsx {
    static String filePath = "src/main/resources/results.xlsx";

    public static void saveData(String title, ClassificationResult cr) throws IOException {
        LocalDateTime time = LocalDateTime.now();
        File file = new File(filePath);
        Workbook workbook;
        try (FileInputStream fis = new FileInputStream(file)) {
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CellStyle boldStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        boldStyle.setFont(font);

        Sheet sheet = workbook.getSheetAt(0);
        int rowIdx = sheet.getPhysicalNumberOfRows();
        rowIdx += 1;
        Row row = sheet.createRow(rowIdx);

        Cell timeCell = row.createCell(0);
        timeCell.setCellValue(time.toString());
        timeCell.setCellStyle(boldStyle);
        rowIdx += 1;
        row = sheet.createRow(rowIdx);
        Cell titleCell = row.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(boldStyle);
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        row.createCell(1).setCellValue("Execution time[s]");
        row.createCell(2).setCellValue((float) cr.duration / 1000.0f);
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        row.createCell(1).setCellValue("Number of articles");
        row.createCell(2).setCellValue(cr.numOfArticles);
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        row.createCell(1).setCellValue("% of training set");
        row.createCell(2).setCellValue(cr.percentageOfTrainSet);
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        row.createCell(1).setCellValue("Training set size");
        row.createCell(2).setCellValue(cr.trainSet.size());
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        row.createCell(1).setCellValue("Training set distribution");
        row.createCell(2).setCellValue(cr.trainSetDistribution.toString());
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        row.createCell(1).setCellValue("Test set size");
        row.createCell(2).setCellValue(cr.testSet.size());
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        row.createCell(1).setCellValue("Test set distribution");
        row.createCell(2).setCellValue(cr.testSetDistribution.toString());
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        row.createCell(1).setCellValue("K");
        row.createCell(2).setCellValue(cr.k);
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        row.createCell(1).setCellValue("Distance metric");
        row.createCell(2).setCellValue(cr.dstMetric);
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        row.createCell(1).setCellValue("Words similarity measure");
        row.createCell(2).setCellValue(cr.wordsSimMeasure);
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        row.createCell(1).setCellValue("Classification features");
        row.createCell(2).setCellValue(cr.classificationFeatures.toString());
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        row.createCell(1).setCellValue("Correct predictions");
        row.createCell(2).setCellValue(cr.correctPredictions);
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        row.createCell(1).setCellValue("Incorrect predictions");
        row.createCell(2).setCellValue(cr.incorrectPredictions);
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        row.createCell(1).setCellValue("Accuracy");
        row.createCell(2).setCellValue(cr.accuracy);
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        row.createCell(1).setCellValue("Confusion matrix");
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        int cellIdx = 3;
        for (String key : cr.confusionMatrix.keySet()) {
            row.createCell(cellIdx).setCellValue(key);
            cellIdx++;
        }
        row.createCell(cellIdx).setCellValue("PREDICTED");
        rowIdx += 1;

        cellIdx = 2;
        for (Map.Entry<String, Map<String, Integer>> entry : cr.confusionMatrix.entrySet()) {
            row = sheet.createRow(rowIdx);
            row.createCell(cellIdx).setCellValue(entry.getKey());
            int inCellIdx = 3;
            for (Integer count : entry.getValue().values()) {
                row.createCell(inCellIdx).setCellValue(count);
                inCellIdx++;
            }
            rowIdx += 1;
        }
        row = sheet.createRow(rowIdx);
        row.createCell(cellIdx).setCellValue("REAL");
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        row.createCell(1).setCellValue("Scores matrix");
        rowIdx += 1;

        row = sheet.createRow(rowIdx);
        cellIdx = 3;
        Set<String> scoresMatrixFirstRow = cr.scoresMatrix.values().iterator().next().keySet();
        for (String key : scoresMatrixFirstRow) {
            row.createCell(cellIdx).setCellValue(key);
            cellIdx++;
        }
        rowIdx += 1;

        cellIdx = 2;
        for (Map.Entry<String, Map<String, Float>> entry : cr.scoresMatrix.entrySet()) {
            row = sheet.createRow(rowIdx);
            row.createCell(cellIdx).setCellValue(entry.getKey());
            int inCellIdx = 3;
            for (Float score : entry.getValue().values()) {
                double rounded = Math.round(score * 1000.0) / 1000.0;
                row.createCell(inCellIdx).setCellValue(rounded);
                inCellIdx++;
            }
            rowIdx += 1;
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        workbook.close();
    }
}
