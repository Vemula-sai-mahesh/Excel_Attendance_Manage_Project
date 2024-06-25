package org.Excel.Attendance.Manage.Service;

import org.Excel.Attendance.Manage.DTO.SheetColumnDTO;
import org.Excel.Attendance.Manage.Domain.SheetColumn;

public interface SheetColumnService {
    SheetColumn save(SheetColumn sheetColumn);
}
