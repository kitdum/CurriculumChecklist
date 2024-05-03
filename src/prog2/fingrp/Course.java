package prog2.fingrp;

import java.io.*;
import java.util.*;

/**
 * IMPORTANT NOTE:
 * Creating a course requires you to use a CourseBuilder class to allow optional parameters
 * without needing multiple constructors for each parameter.
 */
public class Course implements Serializable, Comparable<Course> {
    public enum STATUS {
        COMPLETE,
        NO_CREDIT,
        WITHDRAWL_WITH_PERMISSION,
        DROPPED,
        NOT_TAKEN
    }

    private String code;
    private String title = "N/A";
    private Integer units, term, year = 0;
    private Float grade = 0f;
    private Course[] prereqs;
    private STATUS status = STATUS.NOT_TAKEN;
    private Boolean electiveStatus = false;
    private Boolean additionalStatus = false;

    public Course(CourseBuilder builder) {
        mergeData(builder);
    }

    public String getCode() {
        if (!electiveStatus || status == STATUS.COMPLETE) {
            //If not elective or is an elective and complete
            return code;
        } else {
            //If is elective and not complete, change code to have no number component.
            return code.replaceAll("\s?[0-9]$", "");
        }
    }

    public String getTitle() {
        if (!electiveStatus || status == STATUS.INCOMPLETE) {
            return title;
        } else {
            return "ELECTIVE";
        }
    }

    public Integer getUnits() {
        return units;
    }

    public Float getGrade() {
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

    public void setUnits(int units) {
        this.units = units;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public void setPrereqs(Course[] prereqs) {
        this.prereqs = prereqs;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(int term) {
        if (term < 1 || term > 3) {
            throw new RuntimeException("Invalid term number");
        }
        this.term = term;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Boolean isElective() {
        return electiveStatus;
    }

    public void setElective(boolean electiveStatus) {
        this.electiveStatus = electiveStatus;
    }

    public Boolean isAdditional() {
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

    public void mergeData(CourseBuilder source) {
        if (source.code != null)
            this.setCode(source.code);
        if (source.units != null)
            this.setUnits(source.units);
        if (source.title != null)
            this.setTitle(source.title);
        if (source.grade != null)
            this.setGrade(source.grade);
        if (source.term != null)
            this.setTerm(source.term);
        if (source.year != null)
            this.setYear(source.year);
        if (source.electiveStatus != null)
            this.setElective(source.electiveStatus);
    }

    public void mergeData(Course source) {
        if (source.getCode() != null)
            this.setCode(source.code);
        if (source.getUnits() != null)
            this.setUnits(source.units);
        if (source.getTitle() != null)
            this.setTitle(source.title);
        if (source.getGrade() != null)
            this.setGrade(source.grade);
        if (source.getTerm() != null)
            this.setTerm(source.term);
        if (source.getYear() != null)
            this.setYear(source.year);
        if (source.isElective() != null)
            this.setElective(source.electiveStatus);
    }

    //Default sorting order is based on course code
    public int compareTo(Course other) {
        return this.code.compareTo(other.getCode());
    }

    public boolean equals(Course other) {
        return this.code.equals(other.code);
    }

    public String toString() {
        String addStatus = isAdditional() ? "True" : "False";
        String elecStatus = isElective() ? "True" : "False";
        return String.format("%s | %s | Year: %d | Term: %d | Grade: %f | Elective: %s | Additional: %s",
                getCode(), getTitle(), getYear(), getTerm(), getGrade(), elecStatus, addStatus);
    }

    //This code makes me want to spill someone's guts.
    public static class CourseBuilder {
        private String code;
        private String title;
        private Integer units, term, year;
        private Float grade;
        private Course[] prereqs;
        private STATUS status = STATUS.NOT_TAKEN;
        private Boolean electiveStatus;
        private Boolean additionalStatus;

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
