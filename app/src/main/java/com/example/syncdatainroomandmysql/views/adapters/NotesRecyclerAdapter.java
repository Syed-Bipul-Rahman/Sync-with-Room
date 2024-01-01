package com.example.syncdatainroomandmysql.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.syncdatainroomandmysql.databinding.NotesLayoutBinding;
import com.example.syncdatainroomandmysql.model.Notes;

import java.util.List;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.XViewholder> {
    Context context;
    List<Notes> notesList;

    public NotesRecyclerAdapter(Context context, List<Notes> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NotesRecyclerAdapter.XViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NotesLayoutBinding notesLayoutBinding = NotesLayoutBinding.inflate(layoutInflater, parent, false);
        return new XViewholder(notesLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesRecyclerAdapter.XViewholder holder, int position) {
        holder.notesLayoutBinding.notes.setText(notesList.get(position).getNotes().toString());
        holder.notesLayoutBinding.dates.setText(notesList.get(position).getUpdateat().toString());

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class XViewholder extends RecyclerView.ViewHolder {
        NotesLayoutBinding notesLayoutBinding;

        public XViewholder(NotesLayoutBinding notesLayoutBinding) {
            super(notesLayoutBinding.getRoot());

            this.notesLayoutBinding = notesLayoutBinding;

        }
    }
}
