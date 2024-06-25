package org.Excel.Attendance.Manage.Repository;

import org.Excel.Attendance.Manage.Domain.Sheets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SheetRepository extends JpaRepository<Sheets, Integer> {
}
