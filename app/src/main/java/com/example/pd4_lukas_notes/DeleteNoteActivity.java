package com.example.pd4_lukas_notes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Map;

public class DeleteNoteActivity extends AppCompatActivity {

    private Spinner noteSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        noteSpinner = findViewById(R.id.note_spinner);
        Button deleteButton = findViewById(R.id.delete_button);

        loadNotes();

        deleteButton.setOnClickListener(v -> deleteNote());
    }

    private void loadNotes() {
        SharedPreferences prefs = getSharedPreferences("NotesApp", MODE_PRIVATE);
        ArrayList<String> noteNames = new ArrayList<>(prefs.getAll().keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, noteNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noteSpinner.setAdapter(adapter);
    }

    private void deleteNote() {
        String selectedNote = (String) noteSpinner.getSelectedItem();

        if (selectedNote != null) {
            SharedPreferences prefs = getSharedPreferences("NotesApp", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(selectedNote);
            editor.apply();

            Toast.makeText(this, getString(R.string.note_deleted_message), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, getString(R.string.no_note_selected_message), Toast.LENGTH_SHORT).show();
        }
    }
}
