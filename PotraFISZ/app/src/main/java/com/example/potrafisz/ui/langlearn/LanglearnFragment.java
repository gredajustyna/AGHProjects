package com.example.potrafisz.ui.langlearn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.potrafisz.AddWordActivity;
import com.example.potrafisz.Category;
import com.example.potrafisz.MainActivity;
import com.example.potrafisz.MyCustomListAdapter;
import com.example.potrafisz.R;
import com.example.potrafisz.data.DictionaryDatabase;
import com.example.potrafisz.ui.TestActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LanglearnFragment extends Fragment {


    private LanglearnViewModel langlearnViewModel;
    private TextView languageName;
    private ListView wordList;
    ArrayAdapter<String> adapter;
    private FloatingActionButton test;
    private Button addWord;
    private Button refresh;
    Context mcontext;

    private List<Category> categoryList;

    public Button addNewWordButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        langlearnViewModel =
                new ViewModelProvider(this).get(LanglearnViewModel.class);
        View root = inflater.inflate(R.layout.fragment_langlearn, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Nauka języka");



        return root;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        categoryList = new ArrayList<>();
        languageName.setText(MainActivity.language);

        DictionaryDatabase db  = DictionaryDatabase.getDbInstance(MainActivity.appContext);
        List<String> categories = db.dictionaryDao().getAllCategory(MainActivity.language);


        MyCustomListAdapter adapter = new MyCustomListAdapter(MainActivity.appContext, R.layout.my_item_list, categoryList);


        for(int i = 0; i < categories.size(); i++){
            categoryList.add(new Category(categories.get(i)));
        }




        wordList.setAdapter(adapter);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(categoryList.size()>0){
                    Intent intent = new Intent(getContext(), TestActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity(), "W bazie nie ma jeszcze słówek do testów! Dodaj je, aby rozpocząć naukę!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        addWord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddWordActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

        categoryList = new ArrayList<>();
        languageName.setText(MainActivity.language);

        DictionaryDatabase db  = DictionaryDatabase.getDbInstance(MainActivity.appContext);
        List<String> categories = db.dictionaryDao().getAllCategory(MainActivity.language);


        MyCustomListAdapter adapter = new MyCustomListAdapter(MainActivity.appContext, R.layout.my_item_list, categoryList);


        for(int i = 0; i < categories.size(); i++){
            categoryList.add(new Category(categories.get(i)));
        }




        wordList.setAdapter(adapter);

    }

    private void findViews(View v) {
        languageName = v.findViewById(R.id.languageName);
        wordList = (ListView)v.findViewById(R.id.wordList);
        test=v.findViewById(R.id.testButton);
        addWord=v.findViewById(R.id.addWord);


    }
}