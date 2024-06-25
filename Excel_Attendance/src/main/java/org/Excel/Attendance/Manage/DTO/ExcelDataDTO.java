package org.Excel.Attendance.Manage.DTO;

import java.util.List;

public class ExcelDataDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SheetDTO> getSheetDTOList() {
        return sheetDTOList;
    }

    public void setSheetDTOList(List<SheetDTO> sheetDTOList) {
        this.sheetDTOList = sheetDTOList;
    }

    public String getExcelName() {
        return ExcelName;
    }

    public void setExcelName(String excelName) {
        ExcelName = excelName;
    }

    private String ExcelName;

    private List<SheetDTO> sheetDTOList;
}
