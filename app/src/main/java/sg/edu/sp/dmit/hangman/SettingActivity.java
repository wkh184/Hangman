package sg.edu.sp.dmit.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.io.Serializable;

public class SettingActivity extends AppCompatActivity {
    private Setting setting;
    private RadioGroup wordGroup;
    private RadioButton wordButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Intent intent = getIntent();
        setting = (Setting) intent.getSerializableExtra("setting");
        Switch soundSwitch = (Switch) findViewById(R.id.setting_sound);
        soundSwitch.setChecked(setting.isSound());
        wordGroup = (RadioGroup) findViewById(R.id.setting_word);
        if(setting.getWordCategory().equals(WordGenerator.ANIMAL_STR)){
            RadioButton radioButton = findViewById(R.id.radioAnimal);
            radioButton.setChecked(true);
        }else{
            RadioButton radioButton = findViewById(R.id.radioFruit);
            radioButton.setChecked(true);
        }
    }

    public void btnSave(View v) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        Switch soundSwitch = (Switch) findViewById(R.id.setting_sound);
        setting.setSound(soundSwitch.isChecked());
        wordGroup = (RadioGroup) findViewById(R.id.setting_word);
        int selected = wordGroup.getCheckedRadioButtonId();
        if(selected == R.id.radioAnimal){
            setting.setWordCategory(WordGenerator.ANIMAL_STR);
        }else{
            setting.setWordCategory(WordGenerator.FRUIT_STR);
        }
        intent.putExtra("source","setting");
        intent.putExtra("setting", (Serializable) setting);
        finish();
        startActivity(intent);
    }

}
