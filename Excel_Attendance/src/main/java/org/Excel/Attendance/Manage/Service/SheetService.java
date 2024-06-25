package org.Excel.Attendance.Manage.Service;

import org.Excel.Attendance.Manage.DTO.SheetDTO;
import org.Excel.Attendance.Manage.Domain.Sheets;

public interface SheetService {
    public Sheets save(Sheets sheets);
}
