package chernook.fit.lab3.data;

import java.io.Serializable;

public class Work implements Serializable {
    private String company;
    private String position;
    private String dateFrom;
    private String dateTo;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public Work(String company, String position, String dateFrom, String dateTo) {
        this.company = company;
        this.position = position;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}
