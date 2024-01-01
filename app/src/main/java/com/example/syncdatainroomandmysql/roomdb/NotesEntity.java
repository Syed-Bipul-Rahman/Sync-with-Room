package com.example.syncdatainroomandmysql.roomdb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class NotesEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "updateat")
    private String updateAt;

    @ColumnInfo(name = "color")
    private String color;

    // Constructors, getters, and setters

    public NotesEntity(@NonNull String id, String notes, String updateAt, String color) {
        this.id = id;
        this.notes = notes;
        this.updateAt = updateAt;
        this.color = color;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

