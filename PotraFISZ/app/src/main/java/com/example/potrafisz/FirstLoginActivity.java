package com.example.potrafisz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.potrafisz.data.CategoryStorage;
import com.example.potrafisz.data.DictionaryDatabase;
import com.example.potrafisz.data.User;

public class FirstLoginActivity extends AppCompatActivity {

    private Button startButton;
    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);

        startButton = findViewById(R.id.startButton);
        username = findViewById(R.id.username);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                saveUsername(name);
                finish();
            }
        });
    }


    private void saveUsername(String username) {
        DictionaryDatabase db  = DictionaryDatabase.getDbInstance(this.getApplicationContext());

        User user = new User();
        user.username = username;

        db.dictionaryDao().addUsername(user);

        finish();

    }
}