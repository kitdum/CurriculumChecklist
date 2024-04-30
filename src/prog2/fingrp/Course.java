package prog2.fingrp;

import java.io.*;
import java.util.*;

public class Course implements Serializable, Comparable<Course> {
    public enum STATUS {
        COMPLETE,
        INCOMPLETE,
        NO_FINAL_EXAM,
        WITHDRAWN,
        DROPPED,
        UNTAKEN
    }

    private String code = "N/A";
    private String title = "N/A";
    private int units, term, year = 0;
    private float grade = 0;
    private Course[] prereqs = new Course[1];
    private STATUS status = STATUS.UNTAKEN;
    private boolean electiveStatus = false;
    private boolean additionalStatus = false;

    public Course(CourseBuilder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.units = builder.units;
        this.year = builder.year;
        this.term = builder.term;
        this.grade = builder.grade;
        this.prereqs = builder.prereqs;
        this.status = builder.status;
        this.electiveStatus = builder.electiveStatus;
        this.additionalStatus = builder.additionalStatus;
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
        if(source.isElective() != electiveStatus){
            this.setElective(source.isElective());
        }
    }

    //Default sorting order is based on course code
    public int compareTo(Course other) {
        return this.code.compareTo(other.getCode());
    }

    public String toString() {
        return String.format("%s | %s | Year: %d | Term: %d | Grade: %f", getCode(), getTitle(),getYear(),getTerm(),getGrade());
    }

    //This code makes me want to spill someone's guts.
    public static class CourseBuilder {
        private String code;
        private String title = "";
        private int units, term, year = -1;
        private float grade = -1;
        private Course[] prereqs = new Course[1];
        private STATUS status = STATUS.UNTAKEN;
        private boolean electiveStatus = false;
        private boolean additionalStatus = false;

        CourseBuilder(String code) {
            this.code = code;
        }

        public CourseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public CourseBuilder units(int units) {
            this.units = units;
            return this;
        }

        public CourseBuilder grade(float grade) {
            this.grade = grade;
            return this;
        }

        public CourseBuilder term(int term) {
            this.term = term;
            return this;
        }

        public CourseBuilder year(int year) {
            this.year = year;
            return this;
        }

        public CourseBuilder prereqs(Course[] prereqs) {
            this.prereqs = prereqs;
            return this;
        }

        public CourseBuilder status(STATUS status) {
            this.status = status;
            return this;
        }

        public CourseBuilder electiveStatus(boolean electiveStatus) {
            this.electiveStatus = electiveStatus;
            return this;
        }

        public CourseBuilder additionalStatus(boolean additionalStatus) {
            this.additionalStatus = additionalStatus;
            return this;
        }

    }
}
