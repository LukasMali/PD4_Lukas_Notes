package com.example.pd4_lukas_notes;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView noteList;
    private LinearLayout emptyView;
    private Button addFirstNoteButton;
    private Button createNoteButton;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        noteList = findViewById(R.id.note_list);
        emptyView = findViewById(R.id.empty_view);
        addFirstNoteButton = findViewById(R.id.add_first_note_button);
        createNoteButton = findViewById(R.id.create_note_button);

        // Load notes from SharedPreferences
        notes = loadNotes();

        // Set up the adapter for the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        noteList.setAdapter(adapter);

        // Update the visibility of the empty view and buttons
        updateEmptyViewVisibility();

        // "Add First Note" button logic
        addFirstNoteButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
        });

        // "Create New Note" button logic
        createNoteButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
        });

        // Short click to view a note
        noteList.setOnItemClickListener((parent, view, position, id) -> {
            String selectedNote = notes.get(position);
            Intent intent = new Intent(MainActivity.this, ViewNoteActivity.class);
            intent.putExtra("note_name", selectedNote);
            startActivity(intent);
        });

        // Long click to delete a note with confirmation
        noteList.setOnItemLongClickListener((parent, view, position, id) -> {
            String selectedNote = notes.get(position);

            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.delete_note_dialog_title))
                    .setMessage(getString(R.string.delete_note_dialog_message))
                    .setPositiveButton(getString(R.string.delete_note_dialog_yes), (dialog, which) -> {
                        deleteNote(selectedNote);
                        Toast.makeText(this, getString(R.string.note_deleted_message), Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton(getString(R.string.delete_note_dialog_no), null)
                    .show();

            return true;
        });
    }

    private ArrayList<String> loadNotes() {
        SharedPreferences prefs = getSharedPreferences("NotesApp", MODE_PRIVATE);
        ArrayList<String> notes = new ArrayList<>();
        Map<String, ?> allNotes = prefs.getAll();

        for (Map.Entry<String, ?> entry : allNotes.entrySet()) {
            notes.add(entry.getKey());
        }
        return notes;
    }

    private void updateEmptyViewVisibility() {
        if (notes.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE); // Show "Add First Note" button
            noteList.setVisibility(View.GONE); // Hide the ListView
            createNoteButton.setVisibility(View.GONE); // Hide "Create New Note" button
        } else {
            emptyView.setVisibility(View.GONE); // Hide "Add First Note" button
            noteList.setVisibility(View.VISIBLE); // Show the ListView
            createNoteButton.setVisibility(View.VISIBLE); // Show "Create New Note" button
        }
    }

    private void deleteNote(String noteName) {
        SharedPreferences prefs = getSharedPreferences("NotesApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(noteName);
        editor.apply();

        notes.remove(noteName);
        adapter.notifyDataSetChanged();
        updateEmptyViewVisibility();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_note) {
            startActivity(new Intent(this, AddNoteActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        notes.clear();
        notes.addAll(loadNotes());
        adapter.notifyDataSetChanged();
        updateEmptyViewVisibility();
    }
}
