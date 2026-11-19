package com.example.projectdice;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textRoll;
    private ImageView d6Image;
    private ImageView d20Image;

    private Button btnHistory;
    private AnimationDrawable diceAnimation;
    private boolean isDiceClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textRoll = findViewById(R.id.textRoll);
        d6Image = findViewById(R.id.d6Image);
        d20Image = findViewById(R.id.d20Image);
        btnHistory = findViewById(R.id.btnHistory);

        HistoryManager.loadHistory(this);
        btnHistory.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HistoryActivity.class)));

    }

    public void onClick(View view) {

        if (isDiceClicked) {
            return;
        }
        isDiceClicked = true;

        textRoll.setVisibility(View.INVISIBLE);
        btnHistory.setVisibility(View.GONE);


        final ImageView selectedDice;
        final int selectedAnim;
        final int maxDiceValue;

        // Checking which dice the user selected, using its ID
        if (view.getId() == R.id.d6Image) {
            d20Image.setVisibility(View.GONE);
            selectedDice = d6Image;
            selectedAnim = R.drawable.d6_anim;
            maxDiceValue = 6;
        } else {
            d6Image.setVisibility(View.GONE);
            selectedDice = d20Image;
            selectedAnim = R.drawable.d20_anim;
            maxDiceValue = 20;
        }

        // Animation that centers the clicked dice
        float parentCenterY = findViewById(android.R.id.content).getHeight() / 2f;

        selectedDice.animate()
                .translationY(parentCenterY - (selectedDice.getHeight() / 2f) - selectedDice.getTop())
                .setDuration(400)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // Trigger the animation for the dice selected by the user
                        selectedDice.setBackgroundResource(selectedAnim);
                        diceAnimation = (AnimationDrawable) selectedDice.getBackground();
                        selectedDice.setImageResource(0);
                        diceAnimation.start();

                        final int rolledNum = new Random().nextInt(maxDiceValue) + 1;

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                diceAnimation.stop();

                                String diceType;
                                if (maxDiceValue == 6) {
                                    diceType = "d6";
                                } else {
                                    diceType = "d20";
                                }
                                HistoryManager.addResult(MainActivity.this, diceType + " → Wynik: " + rolledNum);

                                // Sending information about the dice type to ResultActivity
                                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                                intent.putExtra("ROLL_RESULT", rolledNum);
                                intent.putExtra("DICE_TYPE", maxDiceValue);

                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                    }
                }).start();
    }
}