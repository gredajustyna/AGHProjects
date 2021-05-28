package com.example.potrafisz.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "words_table")
public class Dictionary {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "word")
    public String word;

    @ColumnInfo(name = "meaning")
    public String meaning;

    @ColumnInfo(name ="language")
    public String language;

    @ColumnInfo(name ="category")
    public String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
