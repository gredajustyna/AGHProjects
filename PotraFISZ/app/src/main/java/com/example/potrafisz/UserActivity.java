package com.example.potrafisz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.potrafisz.data.Dictionary;
import com.example.potrafisz.data.DictionaryDatabase;

import java.util.List;

public class UserActivity extends AppCompatActivity {
    private TextView txUser;
    private TextView wordsNumber;
    private TextView timeAll;
    private TextView percentTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        DictionaryDatabase db = DictionaryDatabase.getDbInstance(this.getApplicationContext());
        List<String> username = db.dictionaryDao().getUsername();

        List<String> words = db.dictionaryDao().getAll();

        txUser = findViewById(R.id.txUser);
        txUser.setText(username.get(0));

        wordsNumber = findViewById(R.id.wordsNumberField);
        String slowka = String.valueOf(words.size());
        wordsNumber.setText(slowka);

        List<Long> time = db.dictionaryDao().getTime();
        timeAll = findViewById(R.id.time_all);
        slowka = String.valueOf(time.get(0)/60);
        timeAll.setText(slowka);

        List<Integer> percent = db.dictionaryDao().getPercent();
        percentTx = findViewById(R.id.percent_fin);
        slowka = String.valueOf(percent.get(0));
        percentTx.setText(slowka);




    }
}