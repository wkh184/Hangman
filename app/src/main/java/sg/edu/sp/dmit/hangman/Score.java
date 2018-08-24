package sg.edu.sp.dmit.hangman;

import java.io.Serializable;
import java.util.ArrayList;

public class Score implements Serializable{
    private int played;
    private int won;
//    ArrayList history;

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public Score() {
        this.played = 0;
        this.won = 0;
//        this.history = new ArrayList();
    }

    public void recordWin(boolean won){
        this.played++;
        if(won){
            this.won++;
        }
    }
}
