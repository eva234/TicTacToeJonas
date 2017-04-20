package com.example.teamrocket.tictactoe;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by teamrocket on 20.04.17.
 */

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setUpLayout();
    }

    private void setUpLayout() {
        ImageView startScreenImageView = (ImageView) findViewById(R.id.startScreenImageView);
        startScreenImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
        Typeface customTypeface = Typeface.createFromAsset(this.getAssets(), getString(R.string.Goethe));
        TextView clickToStartTextView = (TextView) findViewById(R.id.clickToStartTextView);
        clickToStartTextView.setTypeface(customTypeface);

    }
}
