package com.example.cisc482doodler;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button clearButton = findViewById(R.id.clearButton);
        DoodleView doodleCanvas = findViewById(R.id.doodleView);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleCanvas.clearButtonPressed();
            }
        });
    }

}