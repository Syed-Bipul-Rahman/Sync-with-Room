package com.example.syncdatainroomandmysql.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.syncdatainroomandmysql.model.Notes;
import com.example.syncdatainroomandmysql.repository.GetNotesRepository;

import java.util.List;

public class NotesViewModels extends AndroidViewModel {

    GetNotesRepository getnotesRepository;

    public NotesViewModels(@NonNull Application application) {
        super(application);
        getnotesRepository = GetNotesRepository.getInstance(application);
    }

    public LiveData<List<Notes>> getAllNotice() {
        return getnotesRepository.getNotesList();
    }
}
