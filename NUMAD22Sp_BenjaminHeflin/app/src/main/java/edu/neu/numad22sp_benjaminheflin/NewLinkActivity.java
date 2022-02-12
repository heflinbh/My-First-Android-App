package edu.neu.numad22sp_benjaminheflin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class NewLinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_link);
    }

    public void onClick(View view) {

        Intent resultIntent = new Intent();

        switch (view.getId()) {
            case R.id.submitButton:

                TextView textViewName = findViewById(R.id.website_name_input);
                TextView textViewAddress = findViewById(R.id.url_input);

                String linkName = textViewName.getText().toString();
                String linkAddress = textViewAddress.getText().toString();

                if (linkName.equals("")) {
                    Snackbar snackbar = Snackbar.make(view, "Please enter a valid name.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else if (linkAddress.equals("")) {
                    Snackbar snackbar = Snackbar.make(view, "Please enter a valid url.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else {
                    resultIntent.putExtra("Link Name", linkName);
                    resultIntent.putExtra("Link Address", linkAddress);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
                break;

            case R.id.cancelButton:
                setResult(RESULT_CANCELED, null);
                finish();
                break;
        }


    }
}