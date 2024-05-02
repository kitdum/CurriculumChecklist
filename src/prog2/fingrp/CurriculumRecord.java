package prog2.fingrp;

import java.util.*;
import java.io.*;

public class CurriculumRecord {
    //Use linked hash map for easier editing of course codes.
    private ArrayList<Course> templateRecord; //Possible use..? Remove later if not
    private ArrayList<Course> personalRecord;
    private ArrayList<Course> compiledRecord;

    public CurriculumRecord(InputStream templateRecord, InputStream personalRecord) throws IOException, ClassNotFoundException{
        this.templateRecord = (ArrayList<Course>) new ObjectInputStream(templateRecord).readObject();
        this.personalRecord = (ArrayList<Course>) new ObjectInputStream(personalRecord).readObject();

        templateRecord.close();
        personalRecord.close();
        compiledRecord = this.templateRecord;

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

    public void editCourse(Course courseData) {
        //Edit or add to compiled list of courses
        boolean matchFound = false;
        for (Course outputCourse:compiledRecord){
            if (outputCourse.equals(courseData)){
                outputCourse.mergeData(courseData);
                matchFound = true;
                break;
            }
        }

        //Course is not part of existing records
        if (!matchFound)
        {
            //Assume it is additional if first time being added to records.
            courseData.setAdditional(true);
            compiledRecord.add(courseData);
        }

        matchFound = false;
        for(Course personalData: personalRecord){
            if (courseData.equals(personalData)) {
                personalData.mergeData(courseData);
                matchFound = true;
                break;
            }
        }
        if (!matchFound) personalRecord.add(courseData);
    }
}
