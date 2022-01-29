package model;

import view.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class HighScoreList {

    private  ArrayList<HighScore> highScores=new ArrayList<>();

    public HighScoreList() {
    }

    //write  object list into file
    public void serialize() {

        try {
            FileOutputStream fileOut = new FileOutputStream("HighScores.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.highScores);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    //read object list from file
    public void deserialize() {

        try {
            FileInputStream fileIn = new FileInputStream("HighScores.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.highScores =  (ArrayList<HighScore>) in.readObject();
            in.close();
            fileIn.close();
        }
        catch (ClassNotFoundException c) {
            System.out.println("HighScore class not found");
            c.printStackTrace();
            return;
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public void sort() {
    Collections.sort(this.highScores,Collections.reverseOrder());
    }

    public ArrayList<HighScore> getHighScores() {
        return highScores;
    }

    public void setHighScores(ArrayList<HighScore> highScores) {
        this.highScores = highScores;
    }
}
