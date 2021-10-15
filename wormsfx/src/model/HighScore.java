package model;

import java.io.*;

public class HighScore implements Serializable {
    private static final long serialVersionUID = 1L;
    private int score, level;
    private String name;

    public HighScore(int s, int l, String n) {
        score = s;
        setLevel(l);
        setName(n);
    }

    //Setter Getter
    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //Entscheidet, ob dieser HighScore größer, kleiner oder gleich dem Eingabewert ist
    public int compareTo(HighScore h) {
        return new Integer(this.score).compareTo(h.score);
    }

    //Wird aufgerufen, wenn eine leere Datei vorhanden ist, um Ausnahmen zu verhindern
    private static void initializeFile() {
        HighScore[] h = {new HighScore(0, 0, " "), new HighScore(0, 0, " "), new HighScore(0, 0, " "),
                new HighScore(0, 0, " "), new HighScore(0, 0, " "), new HighScore(0, 0, " "),
                new HighScore(0, 0, " "), new HighScore(0, 0, " "), new HighScore(0, 0, " "),
                new HighScore(0, 0, " ")};
        try {
            System.out.println("Hi1");
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("HighScores.dat"));
            o.writeObject(h);
            o.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Liest die .dat-Datei und gibt die entsprechenden Werte zurück
    public static HighScore[] getHighScores() {
        if (!new File("HighScores.dat").exists())
            initializeFile();
        try {
            ObjectInputStream o = new ObjectInputStream(new FileInputStream("HighScores.dat"));
            HighScore[] h = (HighScore[]) o.readObject();
            return h;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Fügt der .dat-Datei einen neuen HighScore hinzu und behält die richtige Reihenfolge
    public static void addHighScore(HighScore h) {
        HighScore[] highScores = getHighScores();
        highScores[highScores.length - 1] = h;
        for (int i = highScores.length - 2; i >= 0; i--) {
            if (highScores[i + 1].compareTo(highScores[i]) > 0) {
                HighScore temp = highScores[i];
                highScores[i] = highScores[i + 1];
                highScores[i + 1] = temp;
            }
        }
        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("HighScores.dat"));
            o.writeObject(highScores);
            o.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}