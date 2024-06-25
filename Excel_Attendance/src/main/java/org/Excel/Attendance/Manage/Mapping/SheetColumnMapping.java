package org.Excel.Attendance.Manage.Mapping;

import org.Excel.Attendance.Manage.DTO.SheetColumnDTO;
import org.Excel.Attendance.Manage.Domain.SheetColumn;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class SheetColumnMapping {
    public SheetColumnDTO toDTO(SheetColumn sheetColumn) {
        SheetColumnDTO sheetColumnDTO = new SheetColumnDTO();
        sheetColumnDTO.setId(sheetColumn.getId());
        sheetColumnDTO.setSheetColName(sheetColumn.getSheetColName());

        return sheetColumnDTO;
    }
}
