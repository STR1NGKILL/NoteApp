package org.reallume.domain;

import javax.persistence.*;
import java.util.Calendar;

@Entity()
@Table(name = "statistics")
public class UserStat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long loginPerDay;

    private Long loginPerMonth;

    private Long loginPerYear;

    private Long notePerDay;

    private Long notePerMonth;

    private Long notePerYear;

    private Integer currentDay;
    private Integer currentMonth;
    private Integer currentYear;


    public UserStat() {
        this.loginPerDay = 0L;
        this.loginPerMonth = 0L;
        this.loginPerYear = 0L;
        this.notePerDay = 0L;
        this.notePerMonth = 0L;
        this.notePerYear = 0L;

        Calendar calendar = Calendar.getInstance();
        this.currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        this.currentMonth = calendar.get(Calendar.MONTH) + 1;
        this.currentYear = calendar.get(Calendar.YEAR);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoginPerDay() {
        return loginPerDay;
    }

    public void setLoginPerDay() {
        this.loginPerDay++;
    }

    public void setLoginPerDayReboot() {
        this.loginPerDay = 1L;
    }

    public Long getLoginPerMonth() {
        return loginPerMonth;
    }

    public void setLoginPerMonth() {
        this.loginPerMonth++;
    }

    public void setLoginPerMonthReboot() {
        this.loginPerMonth = 1L;
    }

    public Long getLoginPerYear() {
        return loginPerYear;
    }

    public void setLoginPerYear() {
        this.loginPerYear++;
    }

    public void setLoginPerYearReboot() {
        this.loginPerYear = 1L;
    }

    public Long getNotePerDay() {
        return notePerDay;
    }

    public void setNotePerDay() {
        this.notePerDay++;
    }

    public void setNotePerDayReboot() {
        this.notePerDay = 1L;
    }

    public Long getNotePerMonth() {
        return notePerMonth;
    }

    public void setNotePerMonth() {
        this.notePerMonth++;
    }

    public void setNotePerMonthReboot() {
        this.notePerMonth = 1L;
    }

    public Long getNotePerYear() {
        return notePerYear;
    }

    public void setNotePerYear() {
        this.notePerYear++;
    }

    public void setNotePerYearReboot() {
        this.notePerYear = 1L;
    }

    public Integer getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay() {
        Calendar calendar = Calendar.getInstance();

        this.currentDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public Integer getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth() {
        Calendar calendar = Calendar.getInstance();

        this.currentMonth = calendar.get(Calendar.MONTH);
    }

    public Integer getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear() {
        Calendar calendar = Calendar.getInstance();

        this.currentYear = calendar.get(Calendar.YEAR);
    }
}



