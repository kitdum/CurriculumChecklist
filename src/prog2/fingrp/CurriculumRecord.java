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
        //Can't modify data in collection while looping so store it here in the meantime.
        ArrayList<Course> coursesToAdd = new ArrayList<Course>();


        personalLoop: for (Course personalData :
                personalRecord) {
            for (Course courseData :
                    compiledRecord) {
                if (courseData.equals(personalData)) {
                    courseData.mergeData(personalData);
                    //First match found. Consider it done.
                    continue personalLoop;
                }
            }
            coursesToAdd.add(personalData);
        }

        compiledRecord.addAll(coursesToAdd);
    }

    public ArrayList<Course> getCourseList() {
        return compiledRecord;
    }

    public void saveChanges(OutputStream out) throws IOException {
        ObjectOutputStream outputFile = new ObjectOutputStream(out);
        outputFile.writeObject(personalRecord);
    }

    public void editCourse(Course courseData) {
        for(Course personalData: personalRecord){
            if (courseData.equals(personalData)){
                personalData.mergeData(courseData);
                return;
            }
        }
        personalRecord.add(courseData);

        recompileRecord();
    }
}
