package com.example.potrafisz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.potrafisz.data.DictionaryDatabase;
import com.example.potrafisz.ui.TestSolveActivity;

import java.util.List;

public class TestResult extends AppCompatActivity {
    private Button finishButton;
    private int points;
    private int allPoints;
    private int percentage;
    private float res;
    private TextView pointLabel;
    private TextView percentLabel;
    private TextView congratsLabel;
    private TextView resultLabel;
    private ImageView catPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        finishButton = findViewById(R.id.finishButton);
        pointLabel = findViewById(R.id.pointLabel);
        percentLabel = findViewById(R.id.percentLabel);
        congratsLabel = findViewById(R.id.congratsLabel);
        resultLabel = findViewById(R.id.resultLabel);
        catPic = findViewById(R.id.catImageView);


        points = TestSolveActivity.pkt;
        allPoints = TestSolveActivity.licznik;
        if (allPoints!=0){
            res = (points*100/allPoints);
        }else res=0;
        percentage = Math.round(res);
        String per = Integer.toString(percentage);
        pointLabel.setText(Integer.toString(points));
        percentLabel.setText(per+"%");
        if(percentage>50){
            congratsLabel.setText("Nieźle! Pracuj dalej aby zostać poliglotą!");
            catPic.setImageResource(R.drawable.good_result);
            percentLabel.setTextColor(getResources().getColor(R.color.green));
        }else{
            congratsLabel.setText("Popracuj więcej, aby uzyskać lepszy wynik!");
            catPic.setImageResource(R.drawable.bad_result_cat);
            percentLabel.setTextColor(getResources().getColor(R.color.red));
        }

        //Database procent
        DictionaryDatabase db  = DictionaryDatabase.getDbInstance(this.getApplicationContext());
        List<Integer> percent = db.dictionaryDao().getPercent();
        int finPerc = percentage;
        if(percent.get(0) == 0){
            db.dictionaryDao().updatep(finPerc,1);

        }else{
            finPerc = (percentage + percent.get(0))/2;
            db.dictionaryDao().updatep(finPerc,1);

        }

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
}