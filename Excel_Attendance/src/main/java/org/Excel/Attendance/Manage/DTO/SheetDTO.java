package org.Excel.Attendance.Manage.DTO;

import org.Excel.Attendance.Manage.Domain.ExcelData;

import java.util.List;

public class SheetDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    private String sheetName;

    public List<SheetColumnDTO> getSheetColumnDTOList() {
        return sheetColumnDTOList;
    }

    public void setSheetColumnDTOList(List<SheetColumnDTO> sheetColumnDTOList) {
        this.sheetColumnDTOList = sheetColumnDTOList;
    }

    private List<SheetColumnDTO> sheetColumnDTOList;
}
