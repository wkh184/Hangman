package sg.edu.sp.dmit.hangman;

import java.io.Serializable;

public class Setting implements Serializable{

    private boolean sound;
    private String wordCategory;

    public Setting() {
        this.sound = false;
        this.wordCategory = WordGenerator.ANIMAL_STR;
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public String getWordCategory() {
        return wordCategory;
    }

    public void setWordCategory(String wordCategory) {
        this.wordCategory = wordCategory;
    }
}
