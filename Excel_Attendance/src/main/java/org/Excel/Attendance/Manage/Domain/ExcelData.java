package org.Excel.Attendance.Manage.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ExcelData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExcelName() {
        return ExcelName;
    }

    public void setExcelName(String excelName) {
        ExcelName = excelName;
    }

    private String ExcelName;

}
