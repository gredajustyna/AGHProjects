package com.example.potrafisz.data;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "language_storage")
public class LanguageStorage {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "language")
    public String language;
}
