package com.example.potrafisz.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DictionaryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addWord(Dictionary... Dictionaries);

    @Query("SELECT * FROM words_table WHERE language LIKE :language AND category LIKE :category")
    public abstract List<Dictionary> getAll(String language, String category);

    @Query("SELECT * FROM words_table WHERE language LIKE :language ")
    public abstract List<Dictionary> getAll(String language);

    @Query("SELECT word FROM words_table")
    public abstract List<String> getAll();

    @Query("SELECT DISTINCT category FROM words_table WHERE language LIKE :name")
    List<String> getAllCategory(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addCategory(CategoryStorage... categories);

    @Query("SELECT DISTINCT category FROM category_storage")
    List<String> getAllCategories();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addLanguage(LanguageStorage... languages);

    @Query("SELECT DISTINCT language FROM language_storage")
    List<String> getAllLanguages();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUsername(User... username);

    @Query("SELECT username FROM users_table")
    List<String> getUsername();

    @Query("SELECT time FROM users_table")
    List<Long> getTime();

    @Query("SELECT procent FROM users_table")
    List<Integer> getPercent();

    @Query("UPDATE users_table SET time = :time WHERE id = :id")
    void update(long time, int id);

    @Query("UPDATE users_table SET procent =:procent WHERE id = :id")
    void updatep(int procent, int id);

    @Delete
    void delete(Dictionary dictionary);
}
