package model;

import java.io.*;
import java.util.ArrayList;

import view.Main;

public class HighScore implements Serializable ,Comparable<HighScore> {
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

    @Override
	public int compareTo(HighScore h) {
		return Integer.compare(this.score, h.score);
	}
    

}