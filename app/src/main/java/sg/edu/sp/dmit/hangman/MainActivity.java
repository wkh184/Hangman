package sg.edu.sp.dmit.hangman;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Score score;
    private Setting setting;
    ImageSwitcher imageSwitcher;
//    TextView wordCategory;
    TextView textView;
    Button btn[] = new Button[26];
    int img[] = {R.drawable.img0,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8,
            R.drawable.img9,
            R.drawable.img10,};
    String strSecret="",strGuess="",strText="";
    int intError = 0;
//    MediaPlayer soundPlayer;
    MediaPlayer onSoundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageSwitcher = findViewById(R.id.imageSwitcher);
        textView = findViewById(R.id.textView);
//        wordCategory = findViewById(R.id.text_word_category);
        setting = new Setting();
        score = new Score();
//        soundPlayer = MediaPlayer.create(this, R.raw.battle);
        onSoundPlayer = MediaPlayer.create(this, R.raw.on);
        newGame();
//        if(setting.isSound()) {
//            soundPlayer.start();
//        }else{
//            soundPlayer.stop();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.action_intro:
//                Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
//                intent.putExtra("setting", (Serializable) setting);
//                startActivity(intent);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setTitle("Intro");
                alertDialog.setMessage("The word to guess is represented by a row of dashes, " +
                        "representing each letter of the word. In most variants, proper nouns, " +
                        "such as names, places, and brands, are not allowed. Slang words, sometimes " +
                        "referred to as informal or shortened words, are also not allowed. If the " +
                        "guessing player suggests a letter which occurs in the word, the other player " +
                        "writes it in all its correct positions. If the suggested letter or number " +
                        "does not occur in the word, the other player draws one element of a hanged " +
                        "man stick figure as a tally mark.\n" +
                        "\n" +
                        "The player guessing the word may, at any time, attempt to guess the whole " +
                        "word. If the word is correct, the game is over and the guesser wins. " +
                        "Otherwise, the other player may choose to penalize the guesser by adding " +
                        "an element to the diagram. On the other hand, if the other player makes " +
                        "enough incorrect guesses to allow his opponent to complete the diagram, " +
                        "the game is also over, this time with the guesser losing. However, the " +
                        "guesser can also win by guessing all the letters or numbers that appears " +
                        "in the word, thereby completing the word, before the diagram is completed.");
                alertDialog.show();
                return true;
            case R.id.action_about:
                AlertDialog.Builder alertDialogBuilderAbout = new AlertDialog.Builder(this);
                AlertDialog alertDialogAbout = alertDialogBuilderAbout.create();
                alertDialogAbout.setTitle("About");
                alertDialogAbout.setMessage("Hangman Version 1.0 by Wang Khong Hai");
                alertDialogAbout.show();
                return true;
            case R.id.action_setting:
                Intent intentSetting = new Intent(getApplicationContext(),SettingActivity.class);
                intentSetting.putExtra("setting", (Serializable) setting);
                startActivity(intentSetting);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();;
        if (onSoundPlayer != null){
            onSoundPlayer.release();
        }
    }

    private void newGame(){
        intError = 0;
        strGuess = "";
        strText = "";
//        wordCategory.setText(setting.getWordCategory());
        setupImageSwitcher();
        setup26Buttons();
        getSecretWord();
    }

    private void setupImageSwitcher(){
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setImageResource(R.drawable.img0);
                return imageView;
            }
        });
        Animation aniOut = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);
        Animation aniIn = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
        imageSwitcher.setOutAnimation(aniOut);
        imageSwitcher.setInAnimation(aniIn);
    }

    private void setup26Buttons(){
        GridLayout g = findViewById(R.id.gridLayout);
        for(int i=0; i<btn.length;i++){
            btn[i] = new Button(this,null,R.attr.buttonStyleSmall);
            btn[i].setText(""+(char)('A'+i));
            btn[i].setTag(""+(char)('A'+i));
            btn[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    strGuess += v.getTag();
                    Log.d(TAG, strGuess);
                    v.setEnabled(false);
                    if(strSecret.indexOf(v.getTag().toString())<0){
                        intError++;
                        imageSwitcher.setImageResource(img[intError]);
                        if(setting.isSound()){
                            onSoundPlayer.start();
                        }
                    }
                    strText = "";
                    strText = getAnswerString(strText);
//                    for(int i = 0; i<strSecret.length();i++){
//                        char ch = strSecret.charAt(i);
//                        if(strGuess.indexOf(ch)>=0){
//                            //Found
//                            strText += ch;
//                        }else{
//                            //Not found
//                            strText += "-";
//                        }
//                    }
                    textView.setText(strText);
                    if(strText.equals(strSecret)){
                        Log.d(TAG, "Solved");
                        score.recordWin(true);
//                        if(setting.isSound()){
//                            soundPlayer.start();
//                        }
                        endGame();;
                    }else{
                        if(intError == 10){
                            score.recordWin(false);
                            endGame();;
                        }
                    }
                }
            });
            g.addView(btn[i]);
        }
    }

    private String getAnswerString(String strText){
        long start = System.currentTimeMillis();
        for(int i = 0; i<strSecret.length();i++){
            char ch = strSecret.charAt(i);
            if(strGuess.indexOf(ch)>=0){
                //Found
                strText += ch;
            }else{
                //Not found
                strText += "-";
            }
        }
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        Log.d(TAG, "Elapsed = " + elapsed);
        return strText;
    }
    private void getSecretWord(){
        String category = setting.getWordCategory();
        WordGenerator wg = new WordGenerator();
        strSecret = wg.getSecretWord(category);
        for(int i = 0; i < strSecret.length(); i++){
            strText += "-";
        }
        textView.setText(strText);
    }

    private void endGame(){
        Intent intent = new Intent(getApplicationContext(),EndGameActivity.class);
        intent.putExtra("score", (Serializable) score);
        startActivity(intent);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        processExtraData();
//        if(setting.isSound()) {
//            Log.d(TAG, "Sound On");
//            soundPlayer.start();
//        }else{
//            Log.d(TAG, "Sound Off");
//            soundPlayer.stop();
//        }
    }

    private void processExtraData(){
        Intent intent = getIntent();
        //use the data received here
        String source = intent.getStringExtra("source");
        if("setting".equals(source)){
            setting = (Setting)intent.getSerializableExtra("setting");
        }else {
            score = (Score) intent.getSerializableExtra("score");
            setContentView(R.layout.activity_main);
            imageSwitcher = findViewById(R.id.imageSwitcher);
            textView = findViewById(R.id.textView);
            textView.invalidate();
            newGame();
        }
    }
}
