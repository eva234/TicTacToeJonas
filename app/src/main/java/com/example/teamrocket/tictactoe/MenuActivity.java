package com.example.teamrocket.tictactoe;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    SimpleSpinnerAdapter adapter1, adapter2;
    Spinner firstPlayer, secondPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        firstPlayer = (Spinner) findViewById(R.id.spinner1);
        Integer[] items = new Integer[]{R.drawable.rsz_tic_tac_toe_o, R.drawable.rsz_tic_tac_toe_x, R.drawable.rsz_tic_tac_toe_star};
        adapter1 = new SimpleSpinnerAdapter(this, items);
        firstPlayer.setAdapter(adapter1);

        secondPlayer = (Spinner) findViewById(R.id.spinner2);
        adapter2 = new SimpleSpinnerAdapter(this, items);
        adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        secondPlayer.setAdapter(adapter2);

        ImageButton startButton = (ImageButton) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        Typeface customTypeface = Typeface.createFromAsset(this.getAssets(), getString(R.string.Goethe));
        TextView firstPlayerTextView = (TextView) findViewById(R.id.firstPlayerTextview);
        firstPlayerTextView.setTypeface(customTypeface);
    }

    private void startGame() {
        int selectedItemPlayer1 = (int) firstPlayer.getItemAtPosition(firstPlayer.getSelectedItemPosition());
        int selectedItemPlayer2 = (int) secondPlayer.getItemAtPosition(secondPlayer.getSelectedItemPosition());
        if(selectedItemPlayer1 == selectedItemPlayer2) {
            Toast toast = Toast.makeText(this, "Es müssen unterschiedliche Symbole ausgewählt werden!", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("symbol1", selectedItemPlayer1);
            intent.putExtra("symbol2", selectedItemPlayer2);
            startActivity(intent);
        }

    }


}
