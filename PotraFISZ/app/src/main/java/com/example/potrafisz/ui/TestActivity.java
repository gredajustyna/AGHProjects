package com.example.potrafisz.ui;

         import androidx.annotation.Nullable;
  import androidx.appcompat.app.AppCompatActivity;

          import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;
  import android.widget.AdapterView;
  import android.widget.ArrayAdapter;
  import android.widget.Button;
  import android.widget.Spinner;
  import android.widget.TextView;

         import com.example.potrafisz.MainActivity;
  import com.example.potrafisz.MyCustomListAdapter;
  import com.example.potrafisz.R;
  import com.example.potrafisz.data.Dictionary;
 import com.example.potrafisz.data.DictionaryDatabase;

          import java.util.List;
    public class TestActivity extends AppCompatActivity {

              private Button testStartButton;
              private Spinner categorySpinner;
              private TextView timeLabel;
              public static String testCategory;


              @Override
              protected void onCreate(@Nullable Bundle savedInstanceState) {
                  super.onCreate(savedInstanceState);
                  setContentView(R.layout.activity_test);

                  categorySpinner = findViewById(R.id.categorySpinner);
                  timeLabel = findViewById(R.id.time_label);
                  DictionaryDatabase db = DictionaryDatabase.getDbInstance(this.getApplicationContext());
                  List<String> categories = db.dictionaryDao().getAllCategory(MainActivity.language);


                  ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);
                  spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                  categorySpinner.setAdapter(spinnerAdapter);
                  testCategory = categorySpinner.getSelectedItem().toString();
                  categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                      public void onItemSelected(AdapterView<?> parent, View view,
                                                 int position, long id) {
                          // TODO Auto-generated method stub
                          testCategory = categorySpinner.getSelectedItem().toString();
                          List<Dictionary> words = db.dictionaryDao().getAll(MainActivity.language, testCategory);

                          if(testCategory.equals("Bez kategorii (Wszystko)")) {
                              List<Dictionary> words2 = db.dictionaryDao().getAll(MainActivity.language);
                              words = words2;
                          }

                          timeLabel.setText(words.size()*15 + "s");

                      }


                      public void onNothingSelected(AdapterView<?> parent) {
                          // TODO Auto-generated method stub

                      }


                  });
                  testStartButton = findViewById(R.id.testStartButton);
                  testStartButton.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          Intent intent = new Intent(view.getContext(), TestSolveActivity.class);
                          startActivity(intent);
                      }
                  });
              }
    }