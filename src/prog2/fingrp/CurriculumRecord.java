package prog2.fingrp;

import java.util.*;
import java.io.*;

public class CurriculumRecord {
    //Use linked hash map for easier editing of course codes.
    private ArrayList<Course> templateRecord;
    private ArrayList<Course> personalRecord;
    private ArrayList<Course> compiledRecord;

    public CurriculumRecord(InputStream templateRecord, InputStream personalRecord) throws IOException, ClassNotFoundException{
        this.templateRecord = (ArrayList<Course>) new ObjectInputStream(templateRecord).readObject();
        this.personalRecord = (ArrayList<Course>) new ObjectInputStream(personalRecord).readObject();

        compiledRecord = this.templateRecord;
        recompileRecord();
    }

    public CurriculumRecord(InputStream templateRecord) throws IOException, ClassNotFoundException{
        this.templateRecord = (ArrayList<Course>) new ObjectInputStream(templateRecord).readObject();
        this.personalRecord = new ArrayList<Course>();

        compiledRecord = this.templateRecord;

        recompileRecord();
    }

    private void recompileRecord() {
        for (Course personalData :
                personalRecord) {
            for (Course courseData :
                    compiledRecord) {
                if (courseData.getCode().equals(personalData.getCode())) {
                    courseData.mergeData(personalData);
                    //First match found. Consider it done.
                    break;
                }
                //No match found, add data to compiled record.
                compiledRecord.add(personalData);
            }
        }
    }

    public ArrayList<Course> getCourseList() {
        return compiledRecord;
    }

    public void saveChanges(OutputStream out) throws IOException {
        ObjectOutputStream outputFile = new ObjectOutputStream(out);
        outputFile.writeObject(personalRecord);
    }

    public void editCourse(String courseCode, Course courseData) {

    }
}
