package com.example.potrafisz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.potrafisz.data.CategoryStorage;
import com.example.potrafisz.data.Dictionary;
import com.example.potrafisz.data.DictionaryDatabase;
import com.example.potrafisz.ui.langlearn.LanglearnFragment;

import java.util.List;

public class AddWordActivity extends AppCompatActivity {
    private Spinner chooseCategory;
    private TextView categoryLabel;
    private EditText categoryTx;
    private LanglearnFragment langlearnFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        getSupportActionBar().setTitle("Dodaj słówko");
        chooseCategory = findViewById(R.id.chooseCategory);
        categoryLabel = findViewById(R.id.categoryLabel);
        categoryTx = findViewById(R.id.categoryTx);

        DictionaryDatabase db = DictionaryDatabase.getDbInstance(this.getApplicationContext());
        List<String> categories = db.dictionaryDao().getAllCategory(MainActivity.language);

        categories.add(0, "Stwórz kategorię");
        if(!categories.contains("Bez kategorii (Wszystko)")) {
            categories.add(0, "Bez kategorii (Wszystko)");
        }


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        chooseCategory.setAdapter(spinnerAdapter);

        chooseCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                String maker = chooseCategory.getSelectedItem().toString();
                if (maker.equals("Stwórz kategorię")) {
                    categoryLabel.setVisibility(view.VISIBLE);
                    categoryTx.setVisibility(view.VISIBLE);

                } else {
                    categoryLabel.setVisibility(view.INVISIBLE);
                    categoryTx.setVisibility(view.INVISIBLE);
                }
            }


            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }


        });
        Spinner category = findViewById(R.id.chooseCategory);
        final EditText word = findViewById(R.id.wordTx);
        final EditText meaning = findViewById(R.id.meaningTx);
        final EditText newCategory = findViewById(R.id.categoryTx);
        final String language = MainActivity.language;
        Button saveButton = findViewById(R.id.saveButton);




        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String napis = category.getSelectedItem().toString();
                if (napis.equals("Bez kategorii (Wszystko)")) {
                    saveNewWord(word.getText().toString(), meaning.getText().toString(), "Bez kategorii (Wszystko)", language);
                }
                else if (napis.equals("Stwórz kategorię")) {
                    saveNewWord(word.getText().toString(), meaning.getText().toString(), newCategory.getText().toString(), language);
                    saveNewCategory(newCategory.getText().toString());
                } else {
                    saveNewWord(word.getText().toString(), meaning.getText().toString(), category.getSelectedItem().toString(), language);
                }
                finish();

            }
        });


    }
    private void findViews(View v) {

        chooseCategory=v.findViewById(R.id.chooseCategory);
    }

    private void saveNewWord(String word, String meaning, String category, String language) {
        DictionaryDatabase db  = DictionaryDatabase.getDbInstance(this.getApplicationContext());

        Dictionary dictionary = new Dictionary();
        dictionary.word = word;
        dictionary.meaning = meaning;
        dictionary.category = category;
        dictionary.language = language;
        db.dictionaryDao().addWord(dictionary);

        finish();

    }

    private void saveNewCategory(String category) {
        DictionaryDatabase db  = DictionaryDatabase.getDbInstance(this.getApplicationContext());

        CategoryStorage categoryStorage = new CategoryStorage();
        categoryStorage.category = category;
        db.dictionaryDao().addCategory(categoryStorage);

        finish();

    }
}