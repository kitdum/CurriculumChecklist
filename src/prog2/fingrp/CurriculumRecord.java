package prog2.fingrp;

import java.util.*;
import java.io.*;

/**
 * Development notes:
 * We split the record into 2 array lists so changes made by the user can be stored separately
 * from the template course files.
 * 1. Compiled record
 *  - Initially the data from the template file. This is what gets passed to the GUI when
 *      they ask for data from the CurriculumRecord.
 *  - Sorting and filtering functions should return a modified version of this array list.
 * 2. Personal record
 *  - These are the changes made by the user. Refer to the mergeData function of the Course class
 *      to see what values constitute as a change.
 *  - This is what gets saved to the personal record .dat file using the saveChanges() function.
 *
 */

public class CurriculumRecord {
    private ArrayList<Course> templateRecord; //Possible use..? Remove later if not
    private ArrayList<Course> personalRecord;
    private ArrayList<Course> compiledRecord;

    public CurriculumRecord(InputStream templateRecord, InputStream personalRecord) throws IOException, ClassNotFoundException{
        this.templateRecord = (ArrayList<Course>) new ObjectInputStream(templateRecord).readObject();
        this.personalRecord = (ArrayList<Course>) new ObjectInputStream(personalRecord).readObject();

        //Early close the files. We won't be needing them anymore.
        templateRecord.close();
        personalRecord.close();
        compiledRecord = this.templateRecord;


        //Find the first match of the courses with changes and merge their data with the existing data.
        for (Course personalData: this.personalRecord){
            boolean matchFound = false;
            for(Course outputCourse: this.compiledRecord){
                if (outputCourse.equals(personalData)){
                    outputCourse.mergeData(personalData);
                    matchFound = true;
                    break;
                }
            }
            if (!matchFound) compiledRecord.add(personalData);
        }
    }

    public CurriculumRecord(InputStream templateRecord) throws IOException, ClassNotFoundException{
        this.compiledRecord = (ArrayList<Course>) new ObjectInputStream(templateRecord).readObject();
        this.personalRecord = new ArrayList<Course>();

        templateRecord.close();
    }

    public ArrayList<Course> getCourseList() {
        return compiledRecord;
    }

    //Saving file only outputs files.
    public void saveChanges(OutputStream out) throws IOException {
        ObjectOutputStream outputFile = new ObjectOutputStream(out);
        outputFile.writeObject(personalRecord);
        out.close();
    }

    public void editCourse(String courseCode, Course.CourseBuilder courseData) {
        //Edit or add to compiled list of courses
        boolean matchFound = false;
        for (Course outputCourse:compiledRecord){
            if (outputCourse.getCode().equals(courseCode)){
                outputCourse.mergeData(courseData);
                matchFound = true;
                break;
            }
        }

        //Course is not part of existing records
        if (!matchFound)
        {
            //Assume it is additional if first time being added after adding template record files.
            courseData.additionalStatus(true);
            compiledRecord.add(new Course(courseData));
            personalRecord.add(new Course(courseData));
            return;
        }

        //Check if course exists in already edited courses.
        matchFound = false;
        for(Course personalData: personalRecord){
            if (personalData.getCode().equals(courseCode)) {
                personalData.mergeData(courseData);
                matchFound = true;
                break;
            }
        }
        if (!matchFound) personalRecord.add(new Course(courseData));
    }

    //INCOMPLETE
    public ArrayList<Course> FilterByYearAndTerm(int year, int term){
        return compiledRecord;
    }

    public ArrayList<Course> FilterByCurriculum(boolean addtionalOnly){
        return compiledRecord;
    }

    public ArrayList<Course> SortByGrade(boolean descending){
        return compiledRecord;
    }

    public ArrayList<Course> SortByGPA(boolean descending){
        return compiledRecord;
    }

    public ArrayList<Course> SortByTitle(boolean descending){
        return compiledRecord;
    }
}
