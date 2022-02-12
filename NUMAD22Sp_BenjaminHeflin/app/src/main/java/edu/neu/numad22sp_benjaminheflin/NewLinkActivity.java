package edu.neu.numad22sp_benjaminheflin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

                resultIntent.putExtra("Link Name", linkName);
                resultIntent.putExtra("Link Address", linkAddress);

                if (linkName.equals("")) {
                    resultIntent.putExtra("Problems", "Name");
                    setResult(RESULT_CANCELED, resultIntent);
                }
                else if (linkAddress.equals("")) {
                    resultIntent.putExtra("Problems", "Address");
                    setResult(RESULT_CANCELED, resultIntent);
                }
                else {
                    resultIntent.putExtra("Problems", "None");
                    setResult(RESULT_OK, resultIntent);
                }
                finish();
                break;

            case R.id.cancelButton:
                resultIntent.putExtra("Problems", "None");
                setResult(RESULT_CANCELED, resultIntent);
                finish();
                break;
        }


    }
}