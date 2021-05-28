package com.example.potrafisz.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "liczba")
    public String liczba;

    @ColumnInfo(name = "procent")
    public int procent;

    @ColumnInfo(name = "time")
    public long time;

}

