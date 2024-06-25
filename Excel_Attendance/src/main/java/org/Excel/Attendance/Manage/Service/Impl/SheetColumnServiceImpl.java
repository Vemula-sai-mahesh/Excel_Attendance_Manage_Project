package org.Excel.Attendance.Manage.Service.Impl;

import org.Excel.Attendance.Manage.DTO.SheetColumnDTO;
import org.Excel.Attendance.Manage.Domain.SheetColumn;
import org.Excel.Attendance.Manage.Repository.SheetColumnRepository;
import org.Excel.Attendance.Manage.Service.SheetColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SheetColumnServiceImpl implements SheetColumnService {


    @Autowired
    private SheetColumnRepository sheetColumnRepository;


    @Override
    public SheetColumn save (SheetColumn sheetColumn) {
        sheetColumn=sheetColumnRepository.save(sheetColumn);
        return sheetColumn;
    }
}
