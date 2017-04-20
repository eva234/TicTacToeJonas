package com.example.teamrocket.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
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
        TextView secondPlayerTextView = (TextView) findViewById(R.id.secondPlayerTextview);
        secondPlayerTextView.setTypeface(customTypeface);
    }

    private void startGame() {
        final int selectedItemPlayer1 = (int) firstPlayer.getItemAtPosition(firstPlayer.getSelectedItemPosition());
        final int selectedItemPlayer2 = (int) secondPlayer.getItemAtPosition(secondPlayer.getSelectedItemPosition());
        final String array[] = {"1", "3", "5"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wie viele Runden sollen gespielt werden?")
                .setSingleChoiceItems(array, 1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Spiel starten", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView listView = ((AlertDialog)dialog).getListView();
                        int checkedItem = listView.getCheckedItemPosition();
                        Log.i("####", "which: "+checkedItem);
                        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                        intent.putExtra("symbol1", selectedItemPlayer1);
                        intent.putExtra("symbol2", selectedItemPlayer2);
                        intent.putExtra("gamesToPlay", array[checkedItem]);
                        startActivity(intent);
                    }
                });

        if(selectedItemPlayer1 == selectedItemPlayer2) {
            Toast toast = Toast.makeText(this, "Es müssen unterschiedliche Symbole ausgewählt werden!", Toast.LENGTH_LONG);
            toast.show();
        } else {
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }


}
