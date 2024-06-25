package org.Excel.Attendance.Manage.DTO;

import org.Excel.Attendance.Manage.Domain.Sheets;

public class SheetColumnDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSheetColName() {
        return sheetColName;
    }

    public void setSheetColName(String sheetColName) {
        this.sheetColName = sheetColName;
    }

    private String sheetColName;

}
