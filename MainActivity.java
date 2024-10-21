package com.example.amritaapp;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button plateDetection = findViewById(R.id.tilePlateDetection);
        Button textRecognition = findViewById(R.id.tileTextRecognition);


        plateDetection.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, NumberPlateDetectionActivity.class)));

        textRecognition.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, TextRecognitionActivity.class)));
    }
}