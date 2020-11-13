package com.example.cardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WinnerActivity extends AppCompatActivity {

    private ImageView winnerImage;
    private TextView winner_LBL_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_end_of_match);

        winner_LBL_name = findViewById(R.id.winner_LBL_name);
        String name = getIntent().getStringExtra("winner");

        winner_LBL_name.setText("The winner is " +name+ " !");

        Button winner_BTN_restart = findViewById(R.id.winner_BTN_restart);

        winner_BTN_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart();
            }
        });

    }

    private void restart(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}