package prog2.fingrp;

import java.util.*;
import java.io.*;

public class CurriculumRecord {
    //Use linked hash map for easier editing of course codes.
    private ArrayList<Course> templateRecord;
    private ArrayList<Course> personalRecord;
    private ArrayList<Course> compiledRecord;

    public CurriculumRecord(){
        //Load test data... this isn't permanent.
        this.templateRecord = new ArrayList<Course>();
        templateRecord.add(new Course("CS111","Intro to Computing",1,1,2));
        templateRecord.add(new Course("CS122","Test1",1,2,1));
        templateRecord.add(new Course("CS123","T2t",1,2,3));
        templateRecord.add(new Course("CS212","tas",2,1,5));

        this.personalRecord = new ArrayList<Course>();
        Course data = new Course("CS122","God died for our sins",-1,-1,-1);
        data.setGrade(50);
        personalRecord.add(data);
        data = new Course("CS111", "AAAAAAAAA",-1,-1,-1);
        data.setGrade(95);
        personalRecord.add(data);
        data = new Course("CS212", "AAAAsdas",-1,-1,-1);
        data.setGrade(80);
        personalRecord.add(data);
        data = new Course("CS212", "Baba",-1,-1,-1);
        data.setGrade(75);
        personalRecord.add(data);

        compiledRecord = this.templateRecord;
        recompileRecord();

    }

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
                    if(personalData.getUnits() > 0)
                        courseData.setUnits(personalData.getUnits());
                    if(!personalData.getTitle().isBlank())
                        courseData.setTitle(personalData.getTitle());
                    if(personalData.getGrade() >= 0)
                        courseData.setGrade(personalData.getGrade());
                    if(personalData.getTerm() > 0)
                        courseData.setTerm(personalData.getTerm());
                    if(personalData.getYear() > 0)
                    courseData.setYear(personalData.getYear());

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
