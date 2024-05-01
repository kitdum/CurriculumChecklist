package prog2.fingrp;

import java.util.*;
import java.io.*;

//THIS IS A DEVELOPMENT CLASS. REMOVE AFTER USE.
public class DevTest {
    public static void main(String[] args) {
        CurriculumRecord record;

        generateTemplate("res/CurriculumTemplate.txt");

        try{
            record = new CurriculumRecord(new FileInputStream("res/CS_template.dat"));
        } catch (IOException e){
            System.out.println("Error accessing curriculum template file");
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e){
            System.out.println("Error processing curriculum template file\nFile may not be invalid or corrupted");
            e.printStackTrace();
            return;
        }

        //Print out record test
        for(Course course: record.getCourseList()){
            System.out.println(course);
        }

        //Filter test.

        System.out.println("-----");
        System.out.println("Filtering to Year 1");
        System.out.println("-----");
        Collection<Course> filteredList = record.getCourseList().stream()
                .filter(e -> e.getYear() == 1).toList();

        for(Course course: filteredList){
            System.out.println(course);
        }

        //Edit test
        System.out.println("-----");
        System.out.println("Editing CS 111");
        System.out.println("-----");
        record.editCourse(new Course(new Course.CourseBuilder("CS 111")
                .title("Edit test 1")
                .grade(85)
        ));

        record.editCourse(new Course(new Course.CourseBuilder("CSE 1")
                .title("Elective edit test")
                .grade(85)
        ));
        record.editCourse(new Course(new Course.CourseBuilder("ADD 1")
                .title("Additional course test")
                .units(3)
                .year(3)
                .term(1)
                .grade(90)
        ));

        for(Course course: record.getCourseList()){
            System.out.println(course);
        }

        //Sort test.


    }

    static void generateTemplate(String templateLoc) {
        ArrayList<Course> templateList = new ArrayList<Course>();
        try (Scanner file = new Scanner(new FileInputStream(templateLoc))) {
            while (file.hasNextLine()) {
                String[] in = file.nextLine().split("/");
                templateList.add(new Course(new Course.CourseBuilder(in[0])
                        .title(in[1])
                        .units(Integer.parseInt(in[2]))
                        .year(Integer.parseInt(in[3]))
                        .term(Integer.parseInt(in[4]))
                        .electiveStatus(Boolean.parseBoolean(in[5]))
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream out =new ObjectOutputStream(new FileOutputStream("res/CS_template.dat"))){
            out.writeObject(templateList);
        } catch (IOException e){
            e.printStackTrace();
        }

    }


}
