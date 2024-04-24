package prog2.fingrp;

import java.io.*;

public class CurriculumChecklist {
    public static void main(String[] args){
        Course c = new Course("CS123","Computer Programming",3,0.0f);
        try {
            ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream("res/test.dat"));
            fileOut.writeObject(c);
            fileOut.close();

            ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream("res/test.dat"));
            Course inClass = (Course) fileIn.readObject();
            System.out.println(inClass.toString());
        } catch (IOException e){
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
