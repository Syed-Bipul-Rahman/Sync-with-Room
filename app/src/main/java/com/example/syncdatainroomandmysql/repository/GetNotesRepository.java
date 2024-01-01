package com.example.syncdatainroomandmysql.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.syncdatainroomandmysql.model.Allnotes;
import com.example.syncdatainroomandmysql.model.Notes;
import com.example.syncdatainroomandmysql.networks.ApiClient;
import com.example.syncdatainroomandmysql.networks.ApiInterface;
import com.example.syncdatainroomandmysql.roomdb.NotesDatabase;
import com.example.syncdatainroomandmysql.roomdb.NotesEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetNotesRepository {

    private static Context mContext;
    private static GetNotesRepository instance;
    private List<Notes> mStudents;
    private Allnotes allnotesModel;
    private MutableLiveData mLiveData;


    public static GetNotesRepository getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new GetNotesRepository();
        }
        return instance;
    }


    public MutableLiveData<List<Notes>> getNotesList() {


        if (mLiveData == null) {
            mLiveData = new MutableLiveData();
        }
        //calling to api
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Allnotes> call = apiService.allNotes();

        call.enqueue(new Callback<Allnotes>() {
            @Override
            public void onResponse(Call<Allnotes> call, Response<Allnotes> response) {
                //getting responses and set them to mlivedata
                allnotesModel = response.body();
                mStudents = allnotesModel.getData();
                mLiveData.setValue(mStudents);

// Save the data to Room database
                saveDataToRoom(mStudents);
            }

            @Override
            public void onFailure(Call<Allnotes> call, Throwable t) {
                Toast.makeText(mContext, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

        return mLiveData;
    }

    private void saveDataToRoom(List<Notes> notesList) {
        // Convert Notes to NotesEntity
        List<NotesEntity> notesEntities = new ArrayList<>();
        for (Notes notes : notesList) {
            NotesEntity entity = new NotesEntity(
                    notes.getId(),
                    notes.getNotes(),
                    notes.getUpdateat(),
                    notes.getColor()
            );
            notesEntities.add(entity);
        }

        // Insert data into Room database
        NotesDatabase database = NotesDatabase.getDatabase(mContext);

        // Use a background thread for database operations to avoid blocking the main thread
        AsyncTask.execute(() -> {
            database.notesDao().insertAll(notesEntities);
        });
    }

}
