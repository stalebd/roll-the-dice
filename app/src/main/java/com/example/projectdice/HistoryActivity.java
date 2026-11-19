package com.example.projectdice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Sets up the ListView with data from HistoryManager
        ListView historyListView = findViewById(R.id.historyList);
        HistoryManager.loadHistory(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.listview_item,
                R.id.textView,
                HistoryManager.historyList
        );
        historyListView.setAdapter(adapter);

        // Clear history button
        ImageButton btnClearAll = findViewById(R.id.btnClearAll);
        btnClearAll.setOnClickListener(v -> {
            HistoryManager.clearHistory(this);
            adapter.notifyDataSetChanged();
        });

        // Back button
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}