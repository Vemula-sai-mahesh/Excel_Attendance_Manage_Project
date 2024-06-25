package org.Excel.Attendance.Manage.Repository;

import org.Excel.Attendance.Manage.Domain.SheetColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SheetColumnRepository extends JpaRepository<SheetColumn, Integer> {
}
