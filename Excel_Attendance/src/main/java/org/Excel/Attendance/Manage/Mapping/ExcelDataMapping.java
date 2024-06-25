package org.Excel.Attendance.Manage.Mapping;


import org.Excel.Attendance.Manage.Domain.ExcelData;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public class ExcelDataMapping {

    public ExcelData mapping(MultipartFile file) {
        ExcelData excelData = new ExcelData();
        excelData.setExcelName(file.getOriginalFilename());
        return excelData;
    }
}
