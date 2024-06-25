package org.Excel.Attendance.Manage.Controller;

import org.Excel.Attendance.Manage.DTO.ExcelDataDTO;
import org.Excel.Attendance.Manage.Domain.ExcelData;
import org.Excel.Attendance.Manage.Service.ExcelDataService;
import org.Excel.Attendance.Manage.Service.Impl.ExcelDataToTableSaved;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/excelData")
public class ExcelDataController {

    @Autowired
    private ExcelDataToTableSaved excelDataToTableSaved;

    public ExcelDataController(ExcelDataToTableSaved excelDataToTableSaved) {

        this.excelDataToTableSaved = excelDataToTableSaved;
    }

    @PostMapping
    private ExcelDataDTO saveExcelData(@RequestParam("ExcelFile") MultipartFile file) throws SQLException, IOException {
        ExcelDataDTO excelDataDTO=excelDataToTableSaved.ExcelDataToTableSave(file);

        return excelDataDTO;
    }
}
