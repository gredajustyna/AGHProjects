package com.example.potrafisz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.potrafisz.data.Dictionary;
import com.example.potrafisz.data.DictionaryDatabase;

import java.util.List;

public class WordLearnActivity extends AppCompatActivity {

    private Button wordButton;
    private Button wordMeaningButton;
    private Button nextButton;
    private TextView wordsNumberTx;
    private long mStartTime = 0L;
    private TextView timeLabel;
    private Handler mHandler = new Handler();
    private static int global = 0;



    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        //Database
        DictionaryDatabase db  = DictionaryDatabase.getDbInstance(this.getApplicationContext());
        List<Dictionary> words = db.dictionaryDao().getAll(MainActivity.language, MyCustomListAdapter.globalCategory);
        List<Long> user = db.dictionaryDao().getTime();

        if(MyCustomListAdapter.globalCategory.equals("Bez kategorii (Wszystko)")) {
            List<Dictionary> words2 = db.dictionaryDao().getAll(MainActivity.language);
            words = words2;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_learn);
        long startTime = System.nanoTime();
        if(mStartTime==0L){
            mStartTime= SystemClock.uptimeMillis();
            mHandler.removeCallbacks(mUpdateTimeTask);
            mHandler.postDelayed(mUpdateTimeTask,100);
        }



        wordButton = findViewById(R.id.wordButton);
        wordMeaningButton = findViewById(R.id.wordMeaningButton);
        nextButton = findViewById(R.id.nextButton);
        timeLabel = findViewById(R.id.timeLabel);
        wordsNumberTx = findViewById(R.id.wordsNumberTx);

        wordButton.setText(words.get(0).getWord());
        wordMeaningButton.setText(words.get(0).getMeaning());
        wordsNumberTx.setText(1 + "/" + words.size());



        wordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                wordButton.setVisibility(View.INVISIBLE);
                wordMeaningButton.setVisibility(View.VISIBLE);



            }
        });
        wordMeaningButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                wordMeaningButton.setVisibility(View.INVISIBLE);
                wordButton.setVisibility(View.VISIBLE);


            }
        });

        List<Dictionary> finalWords = words;
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                global++;
                wordButton.setVisibility(View.INVISIBLE);
                wordMeaningButton.setVisibility(View.VISIBLE);
                int global_1 = global + 1;
                if(global < finalWords.size()){
                    wordButton.setText(finalWords.get(global).getWord());
                    wordMeaningButton.setText(finalWords.get(global).getMeaning());
                    wordsNumberTx.setText(global_1 + "/" + finalWords.size());
                }
                if(global == finalWords.size() - 1){
                    nextButton.setText("KONIEC");
                    nextButton.setBackgroundColor(Color.parseColor("#db675e"));
                }
                if(global == finalWords.size()){
                    long stopTime = System.nanoTime();
                    long time = stopTime - startTime;
                    long newTime = time/1000000000 + user.get(0);
                    finish();
                    global = 0;
                    db.dictionaryDao().update(newTime,1);
                }


            }
        });






    }
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            final long start = mStartTime;
            long millis = SystemClock.uptimeMillis() - start;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            timeLabel.setText("" + minutes + ":"
                    + String.format("%02d", seconds));
            mHandler.postDelayed(this, 200);
        }
    };
}