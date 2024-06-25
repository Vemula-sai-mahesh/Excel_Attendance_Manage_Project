package org.Excel.Attendance.Manage.Service.Impl;

import org.Excel.Attendance.Manage.DTO.ExcelDataDTO;
import org.Excel.Attendance.Manage.Domain.ExcelData;
import org.Excel.Attendance.Manage.Repository.ExcelDataRepository;
import org.Excel.Attendance.Manage.Service.ExcelDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@Service
@Transactional
public class ExcelDataServiceImpl implements ExcelDataService {

    @Autowired
    private ExcelDataRepository excelDataRepository;

    @Override
    public ExcelData save( MultipartFile file) throws SQLException, IOException {
        ExcelData excelData = new ExcelData();
        excelData.setExcelName(file.getOriginalFilename());
        excelData=excelDataRepository.save(excelData);
        return excelData;
    }
}
