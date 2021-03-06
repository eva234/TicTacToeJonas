package com.example.teamrocket.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int player=0;
    private int iconsSet =0;
    private int symbolPlayer1=R.drawable.rsz_tic_tac_toe_x_white;
    private int symbolPlayer2=R.drawable.rsz_tic_tac_toe_o_white;
    private int[] gamesWon = {0, 0};
    private int gamesPlayed=0;
    private int gamesToPlay=1;
    int lastWinner = -1;

    private int[][] field = {
            {-1,-1,-1},
            {-1,-1,-1},
            {-1,-1,-1}
    };

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    private ImageView playerSymbol;
    private TextView counterPlayer1;
    private TextView counterPlayer2;
    private CustomAlert customAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpLayout();

        initCustomAlert();

        Intent intent = getIntent();
        int symbol1 = intent.getIntExtra("symbol1", R.drawable.rsz_tic_tac_toe_x);
        int symbol2 = intent.getIntExtra("symbol2", R.drawable.rsz_tic_tac_toe_o);
        gamesToPlay = Integer.parseInt(intent.getStringExtra("gamesToPlay"));
        Log.i("####", "games to play: "+gamesToPlay);

        switch (symbol1) {
            case R.drawable.rsz_tic_tac_toe_x:
                symbolPlayer1 = R.drawable.rsz_tic_tac_toe_x_white;
                break;
            case R.drawable.rsz_tic_tac_toe_o:
                symbolPlayer1 = R.drawable.rsz_tic_tac_toe_o_white;
                break;
            case R.drawable.rsz_tic_tac_toe_star:
                symbolPlayer1 = R.drawable.rsz_tic_tac_toe_star_white;
                break;
            default:
                symbolPlayer1 = R.drawable.rsz_tic_tac_toe_x_white;
        }

        switch (symbol2) {
            case R.drawable.rsz_tic_tac_toe_x:
                symbolPlayer2 = R.drawable.rsz_tic_tac_toe_x_white;
                break;
            case R.drawable.rsz_tic_tac_toe_o:
                symbolPlayer2 = R.drawable.rsz_tic_tac_toe_o_white;
                break;
            case R.drawable.rsz_tic_tac_toe_star:
                symbolPlayer2 = R.drawable.rsz_tic_tac_toe_star_white;
                break;
            default:
                symbolPlayer2 = R.drawable.rsz_tic_tac_toe_o_white;
        }
    }

    private void initCustomAlert() {
        customAlert = new CustomAlert(this, R.style.MyAlertDialog);
        customAlert.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    private void setUpLayout(){
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        playerSymbol = (ImageView) findViewById(R.id.playerSymbol);
        playerSymbol.setImageDrawable(getDrawable(symbolPlayer1));
        ImageView imageViewSymbol1 = (ImageView) findViewById(R.id.firstPlayerSymbolCounter);
        ImageView imageViewSymbol2 = (ImageView) findViewById(R.id.secondPlayerSymbolCounter);
        imageViewSymbol1.setImageDrawable(getDrawable(symbolPlayer1));
        imageViewSymbol2.setImageDrawable(getDrawable(symbolPlayer2));
        counterPlayer1 = (TextView)findViewById(R.id.fistPlayerCounterText);
        counterPlayer2 = (TextView)findViewById(R.id.secondPlayerCounterText);
        counterPlayer1.setText("" + gamesWon[0]);
        counterPlayer2.setText("" + gamesWon[1]);

        final Typeface customTypeface = Typeface.createFromAsset(this.getAssets(), getString(R.string.Goethe));
        TextView firstPlayerCounter = (TextView) findViewById(R.id.fistPlayerCounterText);
        firstPlayerCounter.setTypeface(customTypeface);
        TextView secondPlayerCounter = (TextView) findViewById(R.id.secondPlayerCounterText);
        secondPlayerCounter.setTypeface(customTypeface);
        TextView playersTurnText = (TextView) findViewById(R.id.playersTurnText);
        playersTurnText.setTypeface(customTypeface);


        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button thisButton = (Button) v;
                int tag = Integer.parseInt(thisButton.getTag().toString());
                int zeile = tag/3;
                int spalte = tag%3;
                Log.i("####","Background: " + thisButton.getBackground().toString());
                if (thisButton.getText()=="") {
                    field[zeile][spalte]=player;
                    if (player == 0) {
                        thisButton.setBackground(getDrawable(symbolPlayer1));
                        playerSymbol.setImageDrawable(getDrawable(symbolPlayer2));
                        player = 1;
                    } else {
                        thisButton.setBackground(getDrawable(symbolPlayer2));
                        playerSymbol.setImageDrawable(getDrawable(symbolPlayer1));
                        player = 0;
                    }
                    thisButton.setText("set");
                    iconsSet++;
                }

                String ausgabe="";
                for(int i=0; i<3; i++) {
                    for (int j=0; j<3; j++) {
                        ausgabe+=field[i][j];
                    }
                    ausgabe+="\n";
                }
                Log.i("####", ausgabe);

                if(checkEndGame()){
                    gamesPlayed++;
                    updatePlayerCounter();
                    if(!checkIfGameOver()){
                        customAlert.startNextRoundAlert("Spieler "+ lastWinner+" hat gewonnen.\nNächste Runde spielen!", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cleanGame();
                                customAlert.dismiss();
                            }
                        });
                    }
                }
            }
        };

        button1.setOnClickListener(buttonListener);
        button2.setOnClickListener(buttonListener);
        button3.setOnClickListener(buttonListener);
        button4.setOnClickListener(buttonListener);
        button5.setOnClickListener(buttonListener);
        button6.setOnClickListener(buttonListener);
        button7.setOnClickListener(buttonListener);
        button8.setOnClickListener(buttonListener);
        button9.setOnClickListener(buttonListener);
    }

    private void updatePlayerCounter() {
        counterPlayer1.setText("" + gamesWon[0]);
        counterPlayer2.setText("" + gamesWon[1]);
    }

    private boolean checkIfGameOver() {
        View.OnClickListener cancelButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View cancelButton) {
                customAlert.dismiss();
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        };

        View.OnClickListener newGameButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View newGameButton) {
                cleanAfterGameOver();
                customAlert.dismiss();
            }
        };

        if(gamesPlayed == gamesToPlay) {
            Log.i("####", "game over");
            String message;
            if(gamesWon[0] == gamesWon[1]) {
                message = "Unentschieden";
            } else {
                if(gamesWon[0] > gamesWon[1]) {
                    message = "Spieler 1 hat gewonnen!";
                } else {
                    message = "Spieler 2 hat gewonnen!";
                }
            }
            customAlert.startNewGameAlert(message, cancelButtonListener, newGameButtonListener);
            return true;
        }
        return false;
    }

    private boolean checkEndGame() {
        int winner;

        winner = checkHorizontal();
        if(checkIfWon(winner)) {
            return true;
        }
        winner = checkVertical();
        if(checkIfWon(winner)) {
            return true;
        }
        winner = checkDiagonal();
        if(checkIfWon(winner)) {
            return true;
        }
        if(iconsSet == 9) {
            return true;
        }

        return false;
    }

    private boolean checkIfWon(int player) {
        if(player != -1){
            gamesWon[player]++;
            player++;
            lastWinner = player;
            return true;
        }
        return false;
    }


    private int checkDiagonal() {
        if(field[0][0] == field[1][1] && field[0][0] == field[2][2] && field[0][0] !=-1) {
            Log.i("####","check Diagonal 1");
            return field[0][0];
        }else if(field[2][0] == field[1][1] && field[2][0] == field[0][2] && field[2][0] !=-1) {
            Log.i("####","check Diagonal 2");
            return field[2][0];
        }
        return -1;
    }

    private int checkVertical() {
        if(field[0][0] == field[1][0] && field[0][0] == field[2][0] && field[0][0] !=-1) {
            Log.i("####","check Vertical 1");
            return field[0][0];
        }else if(field[0][1] == field[1][1] && field[0][1] == field[2][1] && field[0][1] !=-1) {
            Log.i("####","check Vertical 2");
            return field[0][1];
        }else if(field[0][2] == field[1][2] && field[0][2] == field[2][2] && field[0][2] !=-1) {
            Log.i("####","check Vertical 3");
            return field[0][2];
        }
        return -1;
    }

    private int checkHorizontal() {
        if(field[0][0] == field[0][1] && field[0][0] == field[0][2] && field[0][0] !=-1) {
            Log.i("####","check Horizontal 1");
            return field[0][0];
        }else if(field[1][0] == field[1][1] && field[1][0] == field[1][2]&& field[1][0] !=-1) {
            Log.i("####","check Horizontal 2");
            return field[1][0];
        }else if(field[2][0] == field[2][1] && field[2][0] == field[2][2] && field[2][0] !=-1) {
           Log.i("####","check Horizontal 3");
            return field[2][0];
        }
        return -1;
    }

    public void cleanGame() {
        iconsSet = 0;

        button1.setBackground(getDrawable(R.color.transparent));
        button2.setBackground(getDrawable(R.color.transparent));
        button3.setBackground(getDrawable(R.color.transparent));
        button4.setBackground(getDrawable(R.color.transparent));
        button5.setBackground(getDrawable(R.color.transparent));
        button6.setBackground(getDrawable(R.color.transparent));
        button7.setBackground(getDrawable(R.color.transparent));
        button8.setBackground(getDrawable(R.color.transparent));
        button9.setBackground(getDrawable(R.color.transparent));

        button1.setText("");
        button2.setText("");
        button3.setText("");
        button4.setText("");
        button5.setText("");
        button6.setText("");
        button7.setText("");
        button8.setText("");
        button9.setText("");
        if(gamesPlayed%2!=0){
            playerSymbol.setImageDrawable(getDrawable(symbolPlayer2));
            player = 1;
        }else {
            playerSymbol.setImageDrawable(getDrawable(symbolPlayer1));
            player = 0;
        }


        for(int i=0; i<=2; i++) {
            for(int j=0; j<=2; j++) {
                field[i][j] = -1;
            }
        }

        lastWinner = -1;
    }

    private void cleanAfterGameOver(){
        Log.i("####","cleanAfterGameOver");
        gamesWon[0] = 0;
        gamesWon[1] = 0;
        counterPlayer1.setText("" + gamesWon[0]);
        counterPlayer2.setText("" + gamesWon[1]);
        gamesPlayed = 0;
        cleanGame();
    }

}
