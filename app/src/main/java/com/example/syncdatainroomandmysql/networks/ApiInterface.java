package com.example.syncdatainroomandmysql.networks;

import com.example.syncdatainroomandmysql.model.Allnotes;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("shownotes.php")
    Call<Allnotes> allNotes();

}
