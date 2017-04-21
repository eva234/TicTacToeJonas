package com.example.teamrocket.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class CustomAlert extends Dialog {

    private String message, title;
    private CustomAlertSettings alertType;
    private String okButtonText, cancelButtonText, defaultButtonText;
    private View.OnClickListener newGameButtonListener = null;
    private View.OnClickListener cancelButtonListener = null;
    private View.OnClickListener nextRoundButtonListener = null;
    private Button alertButtonCancel, alertButtonNewGame, alertButtonNextRound;
    private Context context;
    private View alertView;
    private TextView alertTitleView, alertMessageView;
    private Typeface customTypeface;


    public CustomAlert(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("####", "oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_view);

        setUpViews();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(context.getColor(R.color.transparent));
            window.setNavigationBarColor(context.getColor(R.color.transparent));
        }
    }

    @Override
    protected void onStart() {
        switch (alertType) {
            case NEW_GAME:
                setUpNewGameAlert();
                break;
            case NEXT_ROUND:
                setUpNextRoundAlert();
                break;
            case SELECT_ROUNDS_TO_PLAY:
                setUpSelectRoundsToPlayAlert();
                break;
            default:
                break;
        }
    }

    private void setUpViews() {
        customTypeface = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.Goethe));

        alertView = findViewById(R.id.alertView);

        alertTitleView = (TextView) findViewById(R.id.alertViewTitle);
        alertMessageView = (TextView) findViewById(R.id.alertViewMessage);
        alertButtonNextRound = (Button) findViewById(R.id.alertViewButtonNextRound);
        alertButtonCancel = (Button) findViewById(R.id.alertViewButtonCancel);
        alertButtonNewGame = (Button) findViewById(R.id.alertViewButtonNewGame);

        alertTitleView.setTypeface(customTypeface);
        alertMessageView.setTypeface(customTypeface);
        alertButtonNextRound.setTypeface(customTypeface);
        alertButtonCancel.setTypeface(customTypeface);
        alertButtonNewGame.setTypeface(customTypeface);
    }

    private void setUpNewGameAlert() {
        alertTitleView.setText(title);
        alertMessageView.setText(message);

        alertButtonCancel.setVisibility(View.VISIBLE);
        alertButtonNewGame.setVisibility(View.VISIBLE);

        alertButtonCancel.setOnClickListener(cancelButtonListener);
        alertButtonNewGame.setOnClickListener(newGameButtonListener);

        alertButtonNextRound.setVisibility(View.GONE);
    }

    private void setUpNextRoundAlert() {
        alertTitleView.setText(title);
        alertMessageView.setText(message);

        alertButtonNextRound.setVisibility(View.VISIBLE);

        alertButtonNextRound.setOnClickListener(nextRoundButtonListener);

        alertButtonCancel.setVisibility(View.GONE);
        alertButtonNewGame.setVisibility(View.GONE);
    }

    private void setUpSelectRoundsToPlayAlert() {
        alertTitleView.setText(title);

    }

    private void setNewGameButton(View.OnClickListener onClickListener) {
        this.newGameButtonListener = onClickListener;
    }

    private void setCancelButton(View.OnClickListener onClickListener) {
        this.cancelButtonListener = onClickListener;
    }

    private void setNextRoundButton(View.OnClickListener onClickListener) {
        this.nextRoundButtonListener = onClickListener;
    }

    private void setOnBackPressed(final View backButton) {
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN) {
                    backButton.callOnClick();
                }
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
    }

    public void startNewGameAlert(String message, View.OnClickListener cancelButtonListener, View.OnClickListener newGameButtonListener) {
        Log.i("###", "start new game alert");
        alertType = CustomAlertSettings.NEW_GAME;
        this.title = "Spiel beendet.";
        this.message = message;
        setCancelButton(cancelButtonListener);
        setNewGameButton(newGameButtonListener);
        show();
        setOnBackPressed(alertButtonCancel);
    }

    public void startNextRoundAlert(String message, View.OnClickListener listener) {
        alertType = CustomAlertSettings.NEXT_ROUND;
        this.title = "Nächste Runde";
        this.message = message;
        setNextRoundButton(listener);
        show();
        setOnBackPressed(alertButtonNextRound);
    }
}