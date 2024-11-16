package com.example.pd4_lukas_notes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewNoteActivity extends AppCompatActivity {

    private String noteName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        TextView noteTitle = findViewById(R.id.note_title);
        TextView noteContent = findViewById(R.id.note_content);
        Button deleteButton = findViewById(R.id.delete_button);
        Button backButton = findViewById(R.id.back_button);

        noteName = getIntent().getStringExtra("note_name");

        if (noteName != null) {
            noteTitle.setText(noteName);

            SharedPreferences prefs = getSharedPreferences("NotesApp", MODE_PRIVATE);
            String content = prefs.getString(noteName, "");
            noteContent.setText(content);

            // Delete Note
            deleteButton.setOnClickListener(v -> deleteNote());

            // Navigate Back to Notes List
            backButton.setOnClickListener(v -> finish());
        }
    }

    private void deleteNote() {
        SharedPreferences prefs = getSharedPreferences("NotesApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(noteName);
        editor.apply();

        finish(); // Close ViewNoteActivity and return to MainActivity
    }
}
