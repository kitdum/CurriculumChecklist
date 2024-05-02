package prog2.fingrp;

import java.util.*;
import java.io.*;

//THIS IS A DEVELOPMENT CLASS. REMOVE AFTER USE.
public class DevTest {
    public static void main(String[] args) {
        CurriculumRecord record = null;
        File template = new File("res/CS_template.dat");
        File save = new File("res/record.dat");

        generateTemplate(new File("res/CurriculumTemplate.txt"));

        try {
            if (save.exists()) {
                record = new CurriculumRecord(new FileInputStream(template), new FileInputStream(save));
            } else {
                record = new CurriculumRecord(new FileInputStream(template));
            }
        } catch (IOException e){
            System.out.println("Error accessing file.");
        } catch (ClassNotFoundException e){
            System.out.println("Error processing file.");
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
                .title("Edit Test 1")
                .grade(75)
        ));

        record.editCourse(new Course(new Course.CourseBuilder("ADD1")
                .title("Additional Test 1")
                .grade(80)
        ));

        try (FileOutputStream file = new FileOutputStream("res/record.dat")) {
            record.saveChanges(file);
        } catch (IOException e){
            System.out.println("Error accessing file.");
        }

        record.editCourse(new Course(new Course.CourseBuilder("CSE 1")
                .title("Elective edit test")
                .grade(91)
        ));

        for(Course course: record.getCourseList()){
            System.out.println(course);
        }

        //Sort test.
        //For ease of filtering and sorting use Aggregate Operations on stream. Refer to Java docs.
        //Move filtering and sorting functionality to CurriculumRecord class.
        System.out.println("------\nSorting by Grade (Descending)\n-----");
        for(Course course: record.getCourseList().stream()
                .filter(e -> e.getGrade() > 0)
                .sorted((o1,o2) -> {
                    //If result is negative, returns o1.
                    //If result is positive returns o2.
                    return (int) (o1.getGrade() - o2.getGrade());
                }).toList()
        )
            System.out.println(course);


    }

    static void generateTemplate(File templateLoc) {
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
