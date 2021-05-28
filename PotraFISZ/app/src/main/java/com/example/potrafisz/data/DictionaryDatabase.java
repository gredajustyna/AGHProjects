package com.example.potrafisz.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities ={Dictionary.class, CategoryStorage.class, LanguageStorage.class, User.class}, version = 1, exportSchema = false)
public abstract class  DictionaryDatabase extends RoomDatabase {
    public abstract DictionaryDao dictionaryDao();

    public static DictionaryDatabase INSTANCE;

    public static DictionaryDatabase getDbInstance(Context context){

        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DictionaryDatabase.class, "dictionary_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
