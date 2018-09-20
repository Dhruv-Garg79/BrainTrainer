package com.example.android.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Random rand = new Random();
    TextView result;
    TextView score;
    TextView timer;
    TextView ques;
    Button playAgain;
    TextView[] options = new TextView[4];

    int scores = 0;
    int noOfQues = 0;
    int range = 101;
    int correctPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        score = findViewById(R.id.score);
        timer = findViewById(R.id.timer);
        ques = findViewById(R.id.ques);
        playAgain = findViewById(R.id.playAgain);

        options[0] = findViewById(R.id.option0);
        options[1] = findViewById(R.id.option1);
        options[2] = findViewById(R.id.option2);
        options[3] = findViewById(R.id.option3);

        final CountDownTimer countDownTimer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long l) {
                timer.setText(String.format("%ss", String.valueOf((int) l / 1000)));
            }

            @Override
            public void onFinish() {
                timer.setText("0s");
                setClickableOptions(false);
                result.setVisibility(View.VISIBLE);
                playAgain.setVisibility(View.VISIBLE);
                result.setText(String.format("Your Score : %s", score.getText()));
            }
        };

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateScreen();
                playAgain.setVisibility(View.INVISIBLE);
                playAgain.setText(R.string.play_again);
                result.setVisibility(View.INVISIBLE);
                countDownTimer.start();
                setClickableOptions(true);
                scores = 0;
                noOfQues = 0;
                updateScore();
            }
        });

        View.OnClickListener correctAnsCheckListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getTag().toString().equals(String.valueOf(correctPosition))){
                    scores++;
                }
                noOfQues++;
                updateScreen();
                updateScore();
            }
        };

        for (int i = 0 ; i < 4 ; i++){
            options[i].setOnClickListener(correctAnsCheckListener);
        }

        setClickableOptions(false);
    }

    private void updateScore() {
        score.setText(String.format("%s/%s", String.valueOf(scores), String.valueOf(noOfQues)));
    }

    public void updateScreen(){
        int a = rand.nextInt(range);
        int b = rand.nextInt(range);
        int ans = a + b;
        correctPosition = rand.nextInt(4);

        ques.setText(String.format("%s + %s", String.valueOf(a), String.valueOf(b)));

        for (int i = 0; i<4 ; i++){
            if (i == correctPosition){
                options[i].setText(String.valueOf(ans));
            } else {
                int value = ans - 20 + rand.nextInt(20);
                while (value == ans){
                    value = ans - 20 + rand.nextInt(20);
                }
                options[i].setText(String.valueOf(value));
            }
        }
    }

    public void setClickableOptions(boolean b){
        for (int i = 0 ; i < 4 ; i++){
            options[i].setClickable(b);
        }
    }
}