package com.example.potrafisz.data;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category_storage")
public class CategoryStorage {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "category")
    public String category;
}
