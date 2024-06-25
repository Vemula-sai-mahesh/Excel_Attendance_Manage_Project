package org.Excel.Attendance.Manage.Domain;

import jakarta.persistence.*;

@Entity
public class SheetColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSheetColName() {
        return sheetColName;
    }

    public void setSheetColName(String sheetColName) {
        this.sheetColName = sheetColName;
    }

    public Sheets getSheet() {
        return sheet;
    }

    public void setSheet(Sheets sheet) {
        this.sheet = sheet;
    }

    private String sheetColName;

    @ManyToOne
    private Sheets sheet;
}
