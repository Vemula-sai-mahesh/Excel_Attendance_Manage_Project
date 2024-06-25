package org.Excel.Attendance.Manage.Service.Impl;

import org.Excel.Attendance.Manage.DTO.SheetDTO;
import org.Excel.Attendance.Manage.Domain.Sheets;
import org.Excel.Attendance.Manage.Repository.SheetRepository;
import org.Excel.Attendance.Manage.Service.SheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SheetServiceImpl implements SheetService {

    @Autowired
    private SheetRepository sheetRepository;


    @Override
    public Sheets save(Sheets sheets) {
        sheets=sheetRepository.save(sheets);
        return sheets;
    }
}
