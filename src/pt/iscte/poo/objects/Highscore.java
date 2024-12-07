package pt.iscte.poo.objects;

public class Highscore implements Comparable<Highscore> {
    private int score;
    private String name;

    public Highscore(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + ": " + score;
    }

    @Override
    public int compareTo(Highscore o) {
        if (this.score > o.score) {
            return -1;
        }
        if (this.score < o.score) {
            return 1;
        }
        return 0;
    }
}
