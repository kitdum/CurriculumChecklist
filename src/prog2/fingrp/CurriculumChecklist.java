package prog2.fingrp;
import java.io.*;
import java.util.ArrayList;


//THIS IS A DEVELOPMENT CLASS. REMOVE AFTER USE.
public class CurriculumChecklist {
    public static void main(String[] args){
//        COURSE(CODE,TITLE,UNITS,YEAR,TERM('3' IF SHORT TERM),ELECTIVE,ADDITIONAL
        ArrayList<Course> listOfCourses = new ArrayList<>();

        try{
            readFromTxt(listOfCourses);
        }catch (IOException e){
            e.printStackTrace();
        }
        for(Course course:listOfCourses){
            System.out.println(course);
        }

        System.out.println();
        System.out.println();
        System.out.println();
        try{
            updateDAT(listOfCourses);
            readDAT(listOfCourses);
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }


    }
    public static void readFromTxt(ArrayList<Course> listOfCourses) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("CurriculumChecklist1.txt"));
        String temp = reader.readLine(); //Initialize first line in file
        int i = 0;
        while(temp != null){ //Goes through all the texts in the file
            String[] arrOfString;
            arrOfString = temp.split("/"); //Splits strings and places it in the array
            Course course = new Course(arrOfString[0],arrOfString[1],Integer.parseInt(arrOfString[2]),Integer.parseInt(arrOfString[3]),Integer.parseInt(arrOfString[4]), Boolean.parseBoolean(arrOfString[5]), Boolean.parseBoolean(arrOfString[6])); //Creates a Song class with the previously created array list
            listOfCourses.add(course); //Assigns the instance of that class into the listOfSongs

            temp = reader.readLine(); //Updates the line -- moving to the next line
            i++;
        }
        reader.close();
    }
    public static void addToTxt(ArrayList<Course> listOfCourse, Course course) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("CurriculumChecklist1.txt",true));
        writer.write(course.getCode() + "//" + course.getTitle() + "//" + course.getYear() + "//" + course.getTerm() + "//" + course.isElective() + "//" + course.isAdditional());
        writer.close();
        listOfCourse.add(course);
    }

    public static void updateDAT(ArrayList<Course> listOfCourses) throws IOException{
        ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream("res/test.dat"));
        for(Course course:listOfCourses){
            fileOut.writeObject(course);
        }
        fileOut.close();
    }
    public static void readDAT(ArrayList<Course> listOfCourses) throws IOException, ClassNotFoundException {
        ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream("res/test.dat"));
        Course inClass = (Course) fileIn.readObject();
        while(inClass != null) { //Iterate over the .dat file
            System.out.println(inClass.toString());
            inClass = (Course) fileIn.readObject();
        }
        fileIn.close();
    }
}
