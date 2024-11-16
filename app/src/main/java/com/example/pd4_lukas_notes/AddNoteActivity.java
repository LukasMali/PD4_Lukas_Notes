package com.example.pd4_lukas_notes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private EditText noteName, noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteName = findViewById(R.id.note_name);
        noteContent = findViewById(R.id.note_content);
        Button saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String name = noteName.getText().toString().trim();
        String content = noteContent.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(content)) {
            Toast.makeText(this, getString(R.string.fields_required_message), Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("NotesApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(name, content);
        editor.apply();

        Toast.makeText(this, getString(R.string.note_saved_message), Toast.LENGTH_SHORT).show();
        finish();
    }
}
