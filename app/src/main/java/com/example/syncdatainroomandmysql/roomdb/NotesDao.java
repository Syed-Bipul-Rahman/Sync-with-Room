package com.example.syncdatainroomandmysql.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NotesEntity> notes);

    @Query("SELECT * FROM notes_table")
    LiveData<List<NotesEntity>> getAllNotes();


    @Query("SELECT * FROM notes_table")
    List<NotesEntity> getAllNotesSync();

}
