package chernook.fit.lab3;

import java.io.Serializable;
import java.util.Date;

public class Education implements Serializable {
    private String university;
    private String faculty;
    private String speciality;
    private String dateFrom;
    private String dateTo;

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public Education(String university, String faculty, String speciality, String dateFrom, String dateTo) {
        this.university = university;
        this.faculty = faculty;
        this.speciality = speciality;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}
