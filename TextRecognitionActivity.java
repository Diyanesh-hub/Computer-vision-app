package com.example.amritaapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;


public class TextRecognitionActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView resultTextView;
    private ActivityResultLauncher<Intent> takePictureLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);

        imageView = findViewById(R.id.capturedImage);
        resultTextView = findViewById(R.id.textResult);
        Button captureButton = findViewById(R.id.captureButton);

        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Bundle extras = data != null ? data.getExtras() : null;
                        Bitmap imageBitmap = (Bitmap) (extras != null ? extras.get("data") : null);
                        if (imageBitmap != null) {
                            imageView.setImageBitmap(imageBitmap);
                            runTextRecognition(imageBitmap);
                        }
                    }
                }
        );

        captureButton.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                takePictureLauncher.launch(takePictureIntent);
            }
        });
    }

    private void runTextRecognition(Bitmap bitmap) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        recognizer.process(image)
                .addOnSuccessListener(this::processTextRecognitionResult)
                .addOnFailureListener(e -> resultTextView.setText("Failed: " + e.getMessage()));
    }

    private void processTextRecognitionResult(Text result) {
        StringBuilder resultText = new StringBuilder();
        for (Text.TextBlock block : result.getTextBlocks()) {
            resultText.append(block.getText()).append("\n");
        }
        resultTextView.setText(resultText.toString());
    }

}