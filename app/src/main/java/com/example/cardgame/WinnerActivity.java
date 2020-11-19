package com.example.cardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_end_of_match);

        TextView  winner_LBL_name   = findViewById(R.id.winner_LBL_name);
        ImageView winner_IMG_winner = findViewById(R.id.winner_IMG_winner);

        String name = getIntent().getStringExtra("winner");

        if(name.equals("Draw"))
            winner_LBL_name.setText("We have a draw :(");
        else
            winner_LBL_name.setText("The winner is " +name+ " !");

        winner_IMG_winner.setImageResource(getIntent().getIntExtra("picId",0));


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