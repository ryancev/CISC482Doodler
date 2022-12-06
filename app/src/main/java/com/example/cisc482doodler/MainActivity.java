package com.example.cisc482doodler;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import top.defaults.colorpicker.ColorPickerPopup;

public class MainActivity extends AppCompatActivity implements ClearCanvasFragment.NoticeDialogListener {


    DoodleView doodleCanvas;

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        doodleCanvas.clearButtonPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button clearButton = findViewById(R.id.clearButton);
        Button colorButton = findViewById(R.id.colorButton);
        Button undoButton = findViewById(R.id.undoButton);
        Button redoButton = findViewById(R.id.redoButton);

        doodleCanvas = findViewById(R.id.doodleView);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ClearCanvasFragment clearCanvasFragment = new ClearCanvasFragment();


        SeekBar brushBar = findViewById(R.id.brushBar);
        brushBar.setMax(30);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCanvasFragment.show(fragmentManager, "clearCanvasFragment");
            }
        });

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerPopup.Builder(MainActivity.this).initialColor(doodleCanvas.getCurrColor())
                        .enableBrightness(true)
                        .enableAlpha(true)
                        .okTitle("Finish")
                        .cancelTitle("Cancel")
                        .showIndicator(true)
                        .showValue(false)
                        .build().show(v, new ColorPickerPopup.ColorPickerObserver() {
                            @Override
                            public void onColorPicked(int color) {
                                doodleCanvas.changeColor(color);
                            }
                        });
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleCanvas.undoButtonPressed();
            }
        });

        redoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleCanvas.redoButtonPressed();
            }
        });

        brushBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                doodleCanvas.changeWidth(seekBar.getProgress());
            }
        });
    }
}