package com.example.potrafisz.ui.langadd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.potrafisz.MainActivity;
import com.example.potrafisz.R;
import com.example.potrafisz.data.DictionaryDatabase;
import com.example.potrafisz.data.LanguageStorage;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class LangaddFragment extends Fragment {

    private LangaddViewModel langaddViewModel;
    private Spinner spinner;
    private TextView textNazwa;
    private EditText editTextNazwa;
    private Button addLanguage;
    private NavigationView navigationView;



    public static String chosenLanguage;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        langaddViewModel =
                new ViewModelProvider(this).get(LangaddViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addlanguage, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Dodaj język");


        return root;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.language_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        DictionaryDatabase db  = DictionaryDatabase.getDbInstance(MainActivity.appContext.getApplicationContext());
        List<String> languages = db.dictionaryDao().getAllLanguages();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                // TODO Auto-generated method stub
                String other = spinner.getSelectedItem().toString();


                final String language = other;
                System.out.println(other);
                addLanguage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveNewLanguage(language);
                        ((MainActivity) getActivity()).showItem(language);
                        Toast.makeText(getActivity(), "Dodano język do bazy! Znajdziesz go w menu po lewej stronie!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


    }

    private void findViews(View v) {
        spinner = v.findViewById(R.id.spinner);
        textNazwa = v.findViewById(R.id.textNazwa);
        editTextNazwa = v.findViewById(R.id.editTextNazwa);
        addLanguage = v.findViewById(R.id.add_language);
        navigationView = (NavigationView) v.findViewById(R.id.nav_view);;

    }




    private void saveNewLanguage(String language) {
        DictionaryDatabase db  = DictionaryDatabase.getDbInstance(MainActivity.appContext.getApplicationContext());

        LanguageStorage languageStorage = new LanguageStorage();

        languageStorage.language = language;
        db.dictionaryDao().addLanguage(languageStorage);


    }
}