package org.Excel.Attendance.Manage.Service;

import org.Excel.Attendance.Manage.Domain.ExcelData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;


public interface ExcelDataService {

    ExcelData save(MultipartFile file) throws SQLException, IOException;
}
