package com.example.potrafisz.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.potrafisz.MainActivity;
import com.example.potrafisz.R;
import com.example.potrafisz.TestResult;
import com.example.potrafisz.data.Dictionary;
import com.example.potrafisz.data.DictionaryDatabase;

import java.util.List;
import java.util.Locale;

public class TestSolveActivity extends AppCompatActivity {

    private TextView wordsNumbersTx;
    private TextView timeLeft;
    private TextView word;
    private EditText meaning;
    private TextView goodTx;
    private Button nextBtn;
    private Button checkBtn;
    public static int licznik;

    public int getPkt() {
        return pkt;
    }

    public static int pkt;
    private CountDownTimer mCountDownTimer;
    private long START_TIME_IN_MILLIS = 0;
    private long mTimeLeftInMillis = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_solve);

        wordsNumbersTx = findViewById(R.id.wordsNumbersTx);
        timeLeft = findViewById(R.id.time_left);
        word = findViewById(R.id.word);
        meaning = findViewById(R.id.meaning);
        goodTx = findViewById(R.id.goodTx);
        nextBtn= findViewById(R.id.nextBtn);
        checkBtn= findViewById(R.id.checkBtn);
        licznik = 0;
        pkt = 0;



        DictionaryDatabase db = DictionaryDatabase.getDbInstance(this.getApplicationContext());
        List<Dictionary> words = db.dictionaryDao().getAll(MainActivity.language, TestActivity.testCategory);

        if(TestActivity.testCategory.equals("Bez kategorii (Wszystko)")) {
            List<Dictionary> words2 = db.dictionaryDao().getAll(MainActivity.language);
            words = words2;
        }
        timeLeft.setText(words.size()*15 + "s");
        wordsNumbersTx.setText("1/" + words.size());
        word.setText(words.get(0).getWord());


        START_TIME_IN_MILLIS = words.size()*15000;
        mTimeLeftInMillis = START_TIME_IN_MILLIS;

        startTimer();

        List<Dictionary> finalWords = words;
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(finalWords.get(licznik).getMeaning().equals(meaning.getText().toString())){
                    goodTx.setVisibility(View.VISIBLE);
                    goodTx.setText("DOBRZE!");
                    goodTx.setTextColor(getResources().getColor(R.color.green));
                    nextBtn.setVisibility(View.VISIBLE);
                    checkBtn.setVisibility(View.INVISIBLE);
                    pkt++;
                    licznik++;
                    meaning.getText().clear();
                }else{
                    goodTx.setTextColor(Color.RED);
                    goodTx.setText("ŹLE!");
                    goodTx.setVisibility(View.VISIBLE);
                    goodTx.setTextColor(getResources().getColor(R.color.red));
                    nextBtn.setVisibility(View.VISIBLE);
                    checkBtn.setVisibility(View.INVISIBLE);
                    licznik++;
                    meaning.getText().clear();
                }

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meaning.setFocusable(true);
                if (finalWords.size() == licznik) {
                    finish();
                    Intent intent = new Intent(TestSolveActivity.this, TestResult.class);
                    startActivity(intent);
                    pauseTimer();
                }
                else {
                    wordsNumbersTx.setText(licznik + 1 + "/" + finalWords.size());
                    word.setText(finalWords.get(licznik).getWord());
                    nextBtn.setVisibility(View.GONE);
                    checkBtn.setVisibility(View.VISIBLE);
                    goodTx.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                finish();
                Intent intent = new Intent(TestSolveActivity.this, TestResult.class);
                startActivity(intent);

            }
        }.start();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
    }

            private void updateCountDownText() {
                int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
                int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                timeLeft.setText(timeLeftFormatted);
            }

}