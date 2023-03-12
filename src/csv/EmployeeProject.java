package csv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class EmployeeProject {

    private int EmpID;
    private int ProjectID;
    private Date DateFrom;
    private Date DateTo;

    private String dateFormat="yyyy-MM-dd";

    public EmployeeProject(int empID, int projectID, Date dateFrom, Date dateTo) {
        EmpID = empID;
        ProjectID = projectID;
        DateFrom = dateFrom;
        DateTo = dateTo;
    }

    public EmployeeProject(String[] args)
    {
        this.setEmpID(Integer.parseInt(args[0]));
        this.setProjectID(Integer.parseInt(args[1]));

        String dateFrom = args[2];
        String dateTo = args[3];

        LocalDate now = LocalDate.now();

        Date dateNow = null;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {

            dateNow = new SimpleDateFormat(this.getDateFormat()).parse(now.toString());
        }catch(ParseException pe)
        {
            pe.fillInStackTrace();
        }

        if(dateTo.isEmpty() || dateTo.equals("NULL"))
        {
            this.setDateTo(dateNow);
        }else {

            try {
                this.setDateTo(formatter.parse(dateTo));
            }catch(ParseException pe)
            {
                pe.fillInStackTrace();
            }

        }

        if(dateFrom.isEmpty() || dateFrom.equals("NULL"))
        {
            this.setDateFrom(dateNow);
        }else{
            try {
                this.setDateFrom(formatter.parse(dateFrom));
            }catch(ParseException pe)
            {
                pe.fillInStackTrace();
            }
        }
    }


    public int getEmpID() {
        return EmpID;
    }

    public void setEmpID(int empID) {
        EmpID = empID;
    }

    public int getProjectID() {
        return ProjectID;
    }

    public void setProjectID(int projectID) {
        ProjectID = projectID;
    }

    public Date getDateFrom() {
        return DateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        DateFrom = dateFrom;
    }

    public Date getDateTo() {
        return DateTo;
    }

    public void setDateTo(Date dateTo){
        DateTo = dateTo;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public long dateDifference(){

        long time_difference = getDateTo().getTime() - getDateFrom().getTime();
        return (time_difference / (1000*60*60*24)) % 365;
    }

}
