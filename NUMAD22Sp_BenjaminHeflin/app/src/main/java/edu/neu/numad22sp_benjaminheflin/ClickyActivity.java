package edu.neu.numad22sp_benjaminheflin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ClickyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);
    }

    public void onClick(View view) {

        Button button = findViewById(view.getId());
        String buttonText = button.getText().toString();

        TextView textView = findViewById(R.id.ClickyText);
        textView.setText("Pressed: " + buttonText);
    }
}