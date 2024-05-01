package prog2.fingrp;

import java.io.*;
import java.util.*;

public class Course implements Serializable, Comparable<Course> {
    public enum STATUS {
        COMPLETE,
        INCOMPLETE,
        NO_FINAL_EXAM,
        WITHDRAWN,
        DROPPED
    }

    private String code;
    private String title;
    private int units;
    private float grade;
    private Course[] prereqs;
    private int term;
    private int year;
    private STATUS status;
    private boolean electiveStatus = false;
    private boolean additionalStatus = false;

    public Course() {
        this.code = "N/A";
        this.title = "N/A";
        this.units = -1;
        this.prereqs = new Course[1];
    }

    public Course(String code, String title, int year, int term, int units, boolean electiveStatus, boolean additionalStatus) {
        this.code = code;
        this.title = title;
        this.units = units;
        this.year = year;
        this.term = term;
        this.electiveStatus = electiveStatus;
        this.additionalStatus = additionalStatus;
    }

    public Course(String code, String title, int year, int term, int units, boolean electiveStatus) {
        this.code = code;
        this.year = year;
        this.term = term;
        this.title = title;
        this.units = units;
        this.year = year;
        this.term = term;
        this.electiveStatus = electiveStatus;
    }

    public Course(String code, String title, int year, int term, int units, Course[] prereqs) {
        this(code,title,year,term,units);
        this.prereqs = prereqs;
    }

    public Course(String code, String title, int year, int term, int units) {
        this.code = code.toUpperCase();
        this.title = title;
        this.year = year;
        this.term = term;
        this.units = units;
    }

    public String getCode() {
        if (!electiveStatus || status == STATUS.COMPLETE) {
            //If not elective or is an elective and complete
            return code;
        } else{
            //If is elective and not complete.
            return code.replaceAll("\s?[0-9]$","");
        }
    }

    public String getTitle() {
        if (!electiveStatus || status == STATUS.INCOMPLETE) {
            return title;
        } else{
            return "ELECTIVE";
        }
    }

    public int getUnits() {
        return units;
    }

    public float getGrade() {
        return grade;
    }

    public Course[] getPrereqs() {
        return prereqs;
    }

    public void setCode(String code) {
        this.code = code.toUpperCase();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUnits(short units) {
        this.units = units;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public void setPrereqs(Course[] prereqs) {
        this.prereqs = prereqs;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        if(term < 1 || term > 3){
            throw new RuntimeException("Invalid term number");
        }
            this.term = term;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isElective() {
        return electiveStatus;
    }

    public void setElective(boolean electiveStatus) {
        this.electiveStatus = electiveStatus;
    }

    public boolean isAdditional() {
        return additionalStatus;
    }

    public void setAdditional(boolean additionalStatus) {
        this.additionalStatus = additionalStatus;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public STATUS getStatus() {
        return status;
    }

    public void mergeData(Course source){
        if(source.getUnits() > 0)
            this.setUnits(source.getUnits());
        if(!source.getTitle().isBlank())
            this.setTitle(source.getTitle());
        if(source.getGrade() >= 0)
            this.setGrade(source.getGrade());
        if(source.getTerm() > 0)
            this.setTerm(source.getTerm());
        if(source.getYear() > 0)
            this.setYear(source.getYear());
    }

    //Default sorting order is based on course code
    public int compareTo(Course other) {
        return this.code.compareTo(other.getCode());
    }

    public String toString() {
        return String.format("%s | %s | Year: %d | Term: %d | Grade: %f", getCode(), getTitle(),getYear(),getTerm(),getGrade());
    }


}
