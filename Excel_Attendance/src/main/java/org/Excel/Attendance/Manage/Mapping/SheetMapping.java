package org.Excel.Attendance.Manage.Mapping;

import org.Excel.Attendance.Manage.DTO.SheetDTO;
import org.Excel.Attendance.Manage.Domain.Sheets;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class SheetMapping {
    public SheetDTO toDTO(Sheets sheets) {
        SheetDTO dto = new SheetDTO();
        dto.setId(sheets.getId());
        dto.setSheetName(sheets.getSheetName());
        return dto;
    }
}
