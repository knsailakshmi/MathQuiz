package com.example.mathquiz;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btn_start, btn_ans0, btn_ans1, btn_ans2, btn_ans3;
    TextView tv_score, tv_questions, tv_timer, tv_bottomsg;
    ProgressBar prog_timer;
    Game g = new Game();
    int secondsRemaining = 30;
    CountDownTimer timer = new CountDownTimer(30000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {

            secondsRemaining--;
            tv_timer.setText(Integer.toString(secondsRemaining) + " sec ");
            prog_timer.setProgress(30 - secondsRemaining);
        }

        @Override
        public void onFinish() {

            btn_ans0.setEnabled(false);
            btn_ans1.setEnabled(false);
            btn_ans2.setEnabled(false);
            btn_ans3.setEnabled(false);
            tv_bottomsg.setText(" Time is up " + g.getNumberCorrect() + "/" + (g.getTotalQuestions() - 1));

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btn_start.setVisibility(View.VISIBLE);
                }
            },4000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start = findViewById(R.id.btn_start);
        btn_ans0 = findViewById(R.id.btn_ans0);
        btn_ans1 = findViewById(R.id.btn_ans1);
        btn_ans2 = findViewById(R.id.btn_ans2);
        btn_ans3 = findViewById(R.id.btn_ans3);

        tv_score = findViewById(R.id.tv_score);
        tv_bottomsg = findViewById(R.id.tv_bottomsg);
        tv_timer = findViewById(R.id.tv_timer);
        tv_questions = findViewById(R.id.tv_questions);

        prog_timer = findViewById(R.id.prog_timer);

        tv_timer.setText("0 Sec");
        tv_questions.setText("");
        tv_bottomsg.setText("Press Go");
        tv_score.setText("0 points");
        prog_timer.setProgress(0);

        View.OnClickListener startButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button start_btn = (Button) v;
                start_btn.setVisibility(View.INVISIBLE);
                secondsRemaining = 30;
                g = new Game();
                nextTurn();
                timer.start();
            }
        };
        View.OnClickListener answerButtonClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                Button buttonClicked = (Button) v;

                int answerSelected = Integer.parseInt(buttonClicked.getText().toString());

                g.checkAnswer(answerSelected);
                tv_score.setText(Integer.toString(g.getScore()));
                nextTurn();
            }
        };
        btn_start.setOnClickListener(startButtonClickListener);
        btn_ans0.setOnClickListener(answerButtonClickListener);
        btn_ans1.setOnClickListener(answerButtonClickListener);
        btn_ans2.setOnClickListener(answerButtonClickListener);
        btn_ans3.setOnClickListener(answerButtonClickListener);
    }

    private void nextTurn() {
        //create a new question.
        //set text on answer buttons.
        //enable answer buttons
        //start the timer.
        g.makeNewQuestion();
        int[] answer = g.getCurrentQuestion().getAnswerArray();

        btn_ans0.setText(Integer.toString(answer[0]));
        btn_ans1.setText(Integer.toString(answer[1]));
        btn_ans2.setText(Integer.toString(answer[2]));
        btn_ans3.setText(Integer.toString(answer[3]));

        btn_ans0.setEnabled(true);
        btn_ans1.setEnabled(true);
        btn_ans2.setEnabled(true);
        btn_ans3.setEnabled(true);

        tv_questions.setText(g.getCurrentQuestion().getQuestionPhrase());
        tv_bottomsg.setText(g.getNumberCorrect() + "/" + (g.getTotalQuestions() - 1));
    }
}

