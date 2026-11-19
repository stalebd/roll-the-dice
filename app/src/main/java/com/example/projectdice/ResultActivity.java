package com.example.projectdice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView resultImage = findViewById(R.id.resultImage);
        TextView resultText = findViewById(R.id.resultText);
        TextView diceNumOverlay = findViewById(R.id.diceNumOverlay);
        Button btnBack = findViewById(R.id.btnBack);

        int result = getIntent().getIntExtra("ROLL_RESULT", 1);
        int diceType = getIntent().getIntExtra("DICE_TYPE", 6);

        resultText.setText("Result: " + result);

        if (diceType == 6) {

            switch (result) {
                case 1: resultImage.setImageResource(R.drawable.d6r1); break;
                case 2: resultImage.setImageResource(R.drawable.d6r2); break;
                case 3: resultImage.setImageResource(R.drawable.d6r3); break;
                case 4: resultImage.setImageResource(R.drawable.d6r4); break;
                case 5: resultImage.setImageResource(R.drawable.d6r5); break;
                case 6: resultImage.setImageResource(R.drawable.d6r6); break;
            }
        } else if (diceType == 20) {
            resultImage.setImageResource(R.drawable.d120);

            // number on a d20 dice
            diceNumOverlay.setVisibility(View.VISIBLE);
            diceNumOverlay.setText(String.valueOf(result));
        }

        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(ResultActivity.this, MainActivity.class));
            finish();
        });
    }
}
