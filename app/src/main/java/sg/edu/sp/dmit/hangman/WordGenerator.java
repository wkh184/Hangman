package sg.edu.sp.dmit.hangman;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class WordGenerator {
    private static final String TAG = "WordGenerator";
    final static public String FRUIT_STR = "Fruit";
    final static public String ANIMAL_STR = "Animal";

    final static public int FRUIT_CODE = 0;
    final static public int ANIMAL_CODE = 1;

    private ArrayList<String[]> words;
    String strWordsFruit[] = {"APPLE","ORANGE","BANANA"};
    String strWordsAnimal[] = {"TIGER","MONKEY","MOUSE"};
    Random r = new Random();

    public WordGenerator() {
        words = new ArrayList<String[]>();
        words.add(FRUIT_CODE, strWordsFruit);
        words.add(ANIMAL_CODE, strWordsAnimal);
    }

    public String getSecretWord(String category){
        String strSecret;
        String[] strWords;
        Log.d(TAG, category);
        if(FRUIT_STR.equals(category)) {
            strWords = words.get(FRUIT_CODE);
        }else{
            strWords = words.get(ANIMAL_CODE);
        }
        int index = r.nextInt(strWords.length);
        strSecret = strWords[index];
        Log.d(TAG, strSecret);
        return strSecret;
    }

}
