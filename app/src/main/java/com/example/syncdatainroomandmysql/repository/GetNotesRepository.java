package com.example.syncdatainroomandmysql.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.syncdatainroomandmysql.model.Allnotes;
import com.example.syncdatainroomandmysql.model.Notes;
import com.example.syncdatainroomandmysql.roomdb.NotesDatabase;
import com.example.syncdatainroomandmysql.roomdb.NotesEntity;
import com.example.syncdatainroomandmysql.networks.ApiClient;
import com.example.syncdatainroomandmysql.networks.ApiInterface;

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
    private MutableLiveData<List<Notes>> mLiveData;

    private GetNotesRepository(Context context) {
        mContext = context;
    }

    public static GetNotesRepository getInstance(Context context) {
        if (instance == null) {
            instance = new GetNotesRepository(context);
        }
        return instance;
    }

    public MutableLiveData<List<Notes>> getNotesList() {
        if (mLiveData == null) {
            mLiveData = new MutableLiveData<>();
        }

        if (isNetworkConnected()) {
            // If online, fetch data from the API and save to Room
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Allnotes> call = apiService.allNotes();

            call.enqueue(new Callback<Allnotes>() {
                @Override
                public void onResponse(Call<Allnotes> call, Response<Allnotes> response) {
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
        } else {
            // If offline, fetch data from Room database
            loadFromRoom();
        }

        return mLiveData;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        return false;
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

        AsyncTask.execute(() -> {
            database.notesDao().insertAll(notesEntities);
        });
    }

    private void loadFromRoom() {
        NotesDatabase database = NotesDatabase.getDatabase(mContext);

        // Use AsyncTask to perform database operations on a background thread
        new AsyncTask<Void, Void, List<NotesEntity>>() {
            @Override
            protected List<NotesEntity> doInBackground(Void... voids) {
                return database.notesDao().getAllNotesSync();
            }

            @Override
            protected void onPostExecute(List<NotesEntity> notesEntities) {
                // Convert NotesEntity to Notes
                List<Notes> notesList = new ArrayList<>();
                for (NotesEntity entity : notesEntities) {
                    Notes notes = new Notes();
                    notes.setId(entity.getId());
                    notes.setNotes(entity.getNotes());
                    notes.setUpdateat(entity.getUpdateAt());
                    notes.setColor(entity.getColor());
                    notesList.add(notes);
                }

                // Set data to LiveData
                mLiveData.setValue(notesList);
            }
        }.execute();
    }
}
