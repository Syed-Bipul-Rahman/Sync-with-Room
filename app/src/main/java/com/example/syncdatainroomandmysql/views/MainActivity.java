package com.example.syncdatainroomandmysql.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.syncdatainroomandmysql.databinding.ActivityMainBinding;
import com.example.syncdatainroomandmysql.model.Allnotes;
import com.example.syncdatainroomandmysql.model.Notes;
import com.example.syncdatainroomandmysql.networks.ApiClient;
import com.example.syncdatainroomandmysql.networks.ApiInterface;
import com.example.syncdatainroomandmysql.viewmodels.NotesViewModels;
import com.example.syncdatainroomandmysql.views.adapters.NotesRecyclerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NotesRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();

        setContentView(view);

        NotesViewModels notesViewModel = new ViewModelProvider(this).get(NotesViewModels.class);
//set linearlayout manager to recyclerview
        binding.recylerveiw.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        notesViewModel.getAllNotice().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {

//setting all data to recyclerview
                adapter = new NotesRecyclerAdapter(MainActivity.this, notes);
                binding.recylerveiw.setAdapter(adapter);

            }
        });

    }
}