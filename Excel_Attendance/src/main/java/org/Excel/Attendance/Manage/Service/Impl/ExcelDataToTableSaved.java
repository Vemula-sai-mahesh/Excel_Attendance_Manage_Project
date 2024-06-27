package org.Excel.Attendance.Manage.Service.Impl;

import org.Excel.Attendance.Manage.DTO.ExcelDataDTO;
import org.Excel.Attendance.Manage.DTO.SheetColumnDTO;
import org.Excel.Attendance.Manage.DTO.SheetDTO;
import org.Excel.Attendance.Manage.Domain.ExcelData;
import org.Excel.Attendance.Manage.Domain.SheetColumn;
import org.Excel.Attendance.Manage.Domain.Sheets;
import org.Excel.Attendance.Manage.Mapping.SheetColumnMapping;
import org.Excel.Attendance.Manage.Mapping.SheetMapping;
import org.Excel.Attendance.Manage.Service.ExcelDataService;
import org.Excel.Attendance.Manage.Service.SheetColumnService;
import org.Excel.Attendance.Manage.Service.SheetService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.awt.image.ImageObserver.ERROR;
import static java.sql.Types.BOOLEAN;
import static java.sql.Types.NUMERIC;
import static org.apache.poi.ss.usermodel.DataValidationConstraint.ValidationType.FORMULA;
import static org.apache.xmlbeans.impl.piccolo.xml.Piccolo.STRING;


@Service
@Transactional
public class ExcelDataToTableSaved {


    @Autowired
    private ExcelDataService excelDataService;

    @Autowired
    private SheetService sheetService;

    @Autowired
    private SheetColumnService sheetColumnService;

    @Autowired
    private SheetColumnMapping sheetColumnMapping;

    @Autowired
    private SheetMapping sheetMapping;

    @Autowired
    private DataSource dataSource;

    public  ExcelDataDTO ExcelDataToTableSave(MultipartFile file) throws IOException, SQLException {
        Workbook workbook = null;
        Connection connection = null;


        ExcelDataDTO excelDataDTO = new ExcelDataDTO();

        ExcelData excelData=excelDataService.save(file);
        excelDataDTO.setId(excelData.getId());
        excelDataDTO.setExcelName(excelData.getExcelName());
        List<SheetDTO> sheetDTOList =new ArrayList<>();

        // Ensure file is not empty
        if (file.isEmpty()) {
            throw new IllegalArgumentException("The uploaded file is empty");
        }

        // Convert MultipartFile to InputStream
        try (InputStream inputStream = file.getInputStream()) {
            // Create a Workbook instance from the InputStream
            workbook = WorkbookFactory.create(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to process the Excel file", e);
        }

        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets: ");

        // Establishing a connection to MySQL database
        connection = dataSource.getConnection();

        // Iterating over each sheet
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheets sheets =new Sheets();
            Sheet sheet = sheetIterator.next();


            String tableName = sheet.getSheetName().trim().replaceAll("[^a-zA-Z0-9_]", "_");
            sheets.setSheetName(tableName);
            sheets.setExcelData(excelData);
            System.out.println("Processing sheet: " + tableName);

            sheets=sheetService.save(sheets);

            SheetDTO sheetDTO =sheetMapping.toDTO(sheets);
            // Creating table in MySQL database using the sheet name and first row as column names
            List<String> colNames = createTable(connection, sheet, tableName);
            List<SheetColumnDTO> sheetColumnDTOList =new ArrayList<>();
            for (String colName : colNames) {

                SheetColumn sheetColumn = new SheetColumn();
                sheetColumn.setSheetColName(colName);
                sheetColumn.setSheet(sheets);
                sheetColumn=sheetColumnService.save(sheetColumn);
                SheetColumnDTO sheetColumnDTO= sheetColumnMapping.toDTO(sheetColumn);
                sheetColumnDTOList.add(sheetColumnDTO);
            }
            sheetDTO.setSheetColumnDTOList(sheetColumnDTOList);
            sheetDTOList.add(sheetDTO);
            excelDataDTO.setSheetDTOList(sheetDTOList);
            // Iterating over rows and inserting data into the MySQL database
            DataFormatter dataFormatter = new DataFormatter();
            FormulaEvaluator formulaEvaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();

            Iterator<Row> rowIterator = sheet.rowIterator();

            // Skip the header row
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                List<String> rowValues = new ArrayList<>();
                Iterator<Cell> cellIterator = row.cellIterator();
                int j = 0;
                while (j < colNames.size() || cellIterator.hasNext()) {

                    Cell cell = null;
                    String cellValue = "";
                    try {
                        cell = cellIterator.next();
                    } catch (Exception e) {
                        System.out.println("Exception in cell iterator for row " + row.getRowNum() + ": Cell is Empty for column: " + colNames.get(j));
                    }
                    cellValue = getCellValue(cell, dataFormatter, formulaEvaluator);
                    rowValues.add(cellValue);
                    j++;
                }

                // Log the number of columns and values
                System.out.println("Inserting into table: " + tableName);
                System.out.println("Columns: " + colNames.size() + ", Values: " + rowValues.size());

                // Ensure the number of values matches the number of columns
                if (rowValues.size() == colNames.size()) {
                    // Inserting data into MySQL database
                    insertData(connection, colNames, rowValues, tableName);
                } else {
                    System.err.println("Column count doesn't match value count at row: " + row.getRowNum());
                }
            }
        }
        workbook.close();

        return excelDataDTO;
    }

    private static String getCellValue(Cell cell, DataFormatter dataFormatter, FormulaEvaluator formulaEvaluator) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case FORMULA:
                return dataFormatter.formatCellValue(cell, formulaEvaluator);
            case NUMERIC:
            case STRING:
            case BOOLEAN:
            case ERROR:
            default:
                return dataFormatter.formatCellValue(cell);
        }
    }
    private static List<String> createTable(Connection connection, Sheet sheet, String tableName) throws SQLException {
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            throw new IllegalArgumentException("The sheet " + tableName + " has no header row.");
        }

        // Enclose table name in backticks and ensure it is sanitized
        StringBuilder createTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS `");
        createTableSQL.append(tableName).append("` (ID INT AUTO_INCREMENT PRIMARY KEY, ");

        Iterator<Cell> cellIterator = headerRow.cellIterator();
        List<String> colNames = new ArrayList<>();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String columnName = cell.getStringCellValue().replaceAll("[^a-zA-Z0-9_]", "_");
            colNames.add(columnName);
            createTableSQL.append("`").append(columnName).append("` VARCHAR(255), ");
        }

        // Remove the last comma and space
        createTableSQL.setLength(createTableSQL.length() - 2);
        createTableSQL.append(")");

        // Drop the table if it already exists
        String dropTableSQL = "DROP TABLE IF EXISTS `" + tableName + "`";
        try (PreparedStatement dropStatement = connection.prepareStatement(dropTableSQL)) {
            dropStatement.execute();
        }

        // Create the table
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL.toString())) {
            preparedStatement.execute();
        }
        return colNames;
    }


    private static void insertData(Connection connection, List<String> colNames, List<String> data, String tableName) throws SQLException {
        // Building the insert SQL dynamically based on the number of columns
        StringBuilder insertSQL = new StringBuilder("INSERT INTO `");
        insertSQL.append(tableName).append("` (");

        for (String colName : colNames) {
            insertSQL.append("`").append(colName).append("`, ");
        }

        // Remove the last comma and space
        insertSQL.setLength(insertSQL.length() - 2);
        insertSQL.append(") VALUES (");

        for (int i = 0; i < data.size(); i++) {
            insertSQL.append("?, ");
        }

        // Remove the last comma and space
        insertSQL.setLength(insertSQL.length() - 2);
        insertSQL.append(")");

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL.toString())) {
            for (int i = 0; i < data.size(); i++) {
                if(data.get(i).equals("")){
                    preparedStatement.setString(i + 1,null);
                }
                else{
                    preparedStatement.setString(i + 1, data.get(i));
                }

            }
            preparedStatement.executeUpdate();
        }
    }
}


