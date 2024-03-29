package com.example.syncdatainroomandmysql.roomdb;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;

import com.example.syncdatainroomandmysql.roomdb.NotesDao;
import com.example.syncdatainroomandmysql.roomdb.NotesEntity;

@Database(entities = {NotesEntity.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();

    private static volatile NotesDatabase INSTANCE;

    public static NotesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    NotesDatabase.class, "notes_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
