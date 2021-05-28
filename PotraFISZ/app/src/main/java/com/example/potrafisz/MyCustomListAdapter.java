package com.example.potrafisz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyCustomListAdapter extends ArrayAdapter<Category>{

    Context mCtx;
    int resource;
    List<Category> categoryList;
    public static String globalCategory;

    public MyCustomListAdapter(Context mCtx, int resource, List<Category> categoryList){
        super(mCtx, resource, categoryList);
        this.mCtx = mCtx;
        this.resource = resource;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(R.layout.my_item_list, null);

        Category category = categoryList.get(position);

        Button categoryButton =view.findViewById(R.id.categoryButton);

        categoryButton.setText(category.getCategory());

        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCtx = view.getContext();
                Intent intent = new Intent(mCtx, WordLearnActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(intent);
                globalCategory = categoryButton.getText().toString();
                System.out.println(globalCategory);
            }
        });
        return view;

    }
}
