package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    CountDownTimer timer;
    TextView timerTextView;
    TextView tasksTextView, scoreTextView,statusTextView;
    Button playAgain,startGame;
    Button answer1,answer2,answer3,answer4;
    boolean playMode = false;
    int score = 0;//счетчик верных ответов
    int allScore = 0;//счетчик всех вопросов
    int productOfNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerTextView = findViewById(R.id.timerTextView);
        tasksTextView = findViewById(R.id.taskTextView);
        playAgain = findViewById(R.id.playAgainButton);
        scoreTextView = findViewById(R.id.scoreTextView);
        statusTextView = findViewById(R.id.statusTextView);

        //поиск кнопок
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
    }

    //кнопка начать игру при запуске
    public void firstStartGame(View view){
        //появление всех обЪектов игры
        timerTextView.setVisibility(View.VISIBLE);
        tasksTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        answer1.setVisibility(View.VISIBLE);
        answer2.setVisibility(View.VISIBLE);
        answer3.setVisibility(View.VISIBLE);
        answer4.setVisibility(View.VISIBLE);
        view.setVisibility(View.INVISIBLE);

        //старт игры с запуском таймера
        timerStart();
        playMode=true;
        generationNumbers();
        generationAnswerButton();
    }
    //таймер
    public void timerStart(){
        timer = new CountDownTimer(30100,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(millisUntilFinished/1000 + " s");
            }

            @Override
            public void onFinish() {
                statusTextView.setText("");
                playAgain.setVisibility(View.VISIBLE); //появление кнопки для перезапуска игры
                timer.cancel();
                playMode=false;
            }
        }.start();
    }

    public void generationNumbers(){
       int number1 = 1 + (int) (Math.random()*9);
       int number2 = 1 + (int) (Math.random()*9);
       tasksTextView.setText(number1 + " " + "*"+ " " + number2);
       productOfNumbers = number1 * number2;
    }

    public void generationAnswerButton(){
        int choose_right_button = 1+(int)(Math.random()*4);
        switch (choose_right_button){
            case 1:
                answer1.setTag(productOfNumbers);
                answer1.setText(String.valueOf(productOfNumbers));
                generationWrongAnswer(answer2);
                generationWrongAnswer(answer3);
                generationWrongAnswer(answer4);
                break;
            case 2:
                answer2.setTag(productOfNumbers);
                answer2.setText(String.valueOf(productOfNumbers));
                generationWrongAnswer(answer1);
                generationWrongAnswer(answer3);
                generationWrongAnswer(answer4);
                break;
            case 3:
                answer3.setTag(productOfNumbers);
                answer3.setText(String.valueOf(productOfNumbers));
                generationWrongAnswer(answer2);
                generationWrongAnswer(answer1);
                generationWrongAnswer(answer4);
                break;
            case 4:
                answer4.setTag(productOfNumbers);
                answer4.setText(String.valueOf(productOfNumbers));
                generationWrongAnswer(answer2);
                generationWrongAnswer(answer3);
                generationWrongAnswer(answer1);
                break;

        }
    }
    public void generationWrongAnswer(Button button){
        int randomNumber;
        do{
        randomNumber = 1+(int)(Math.random()*99);
        }
        while(randomNumber==productOfNumbers);
        button.setText(String.valueOf(randomNumber));
        button.setTag(randomNumber);
    }

    //события при нажатии кноки заново
    public void playAgainClick(View view){
        timerStart();
        playMode=true;
        nextTask();
        view.setVisibility(View.INVISIBLE);
        score = 0;
        allScore = 0;
        scoreTextView.setText(score+"/"+allScore);
    }

    //нажатие на блок из ответов
    public void clickAnswer(View view){
        if(playMode==true) {
            allScore++;
            if (Integer.parseInt(view.getTag().toString()) == productOfNumbers) {
                score++;
                statusTextView.setText("Correct!");
            }
            else statusTextView.setText("Wrong :(");
            scoreTextView.setText(score + "/" + allScore);
            nextTask();
        }
    }

    //прогрузка новых чисел
    public void nextTask(){
        generationNumbers();
        generationAnswerButton();
    }

}