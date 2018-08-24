package sg.edu.sp.dmit.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

public class EndGameActivity extends AppCompatActivity {
    private Score score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        Intent intent = getIntent();
        score = (Score)intent.getSerializableExtra("score");
        TextView played = findViewById(R.id.text_played);
        TextView won = findViewById(R.id.text_won);
        played.setText("Played: " + String.valueOf(score.getPlayed()));
        won.setText("Won: " + String.valueOf(score.getWon()));
    }

    public void btnContinue(View v) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("score", (Serializable) score);
        finish();
        startActivity(intent);
    }

    public void btnReset(View v) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        score = new Score();
        intent.putExtra("score", (Serializable) score);
        finish();
        startActivity(intent);
    }
}
