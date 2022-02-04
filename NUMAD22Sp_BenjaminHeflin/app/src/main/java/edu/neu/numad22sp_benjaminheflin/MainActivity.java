package edu.neu.numad22sp_benjaminheflin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.AboutMeButton:
                EditText personNameText = findViewById(R.id.personName);
                TextView textView = findViewById(R.id.textView);
                if (!personNameText.getText().toString().equals("")) {
                    textView.setText("Howdy " + personNameText.getText().toString() + "!" +
                            "\nMy name is Benjamin Heflin. Nice to meet you!" +
                            "\nFeel free to reach me at heflin.be@northeastern.edu" +
                            "\nSee you around!");
                } else {
                    textView.setText("Hello World!");
                }
                break;

            case R.id.ClickyClicky:
                Toast.makeText(getApplicationContext(), "Success!!!", Toast.LENGTH_SHORT).show();
                break;
        }


    }

}