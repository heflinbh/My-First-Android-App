package edu.neu.numad22sp_benjaminheflin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

public class NewLinkActivity extends AppCompatActivity {

    ArrayList<String> top_level_domains;
    String COM = ".com";
    String RU = ".ru";
    String ORG = ".org";
    String NET = ".net";
    String IN = ".in";
    String IR = ".ir";
    String AU = ".au";
    String UK = ".uk";
    String UA = ".ua";
    String CA = ".ca";

    String WWW = "www.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_link);

        top_level_domains = new ArrayList<>(Arrays.asList(COM, RU, ORG, NET, IN, IR, AU, UK, UA, CA));
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

                else if (!linkAddress.startsWith(WWW)) {
                    Snackbar snackbar = Snackbar.make(view, "Url must start with 'www'.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

                else {
                    Boolean domainFlag = false;
                    for (String domain: top_level_domains) {
                        if (linkAddress.contains(domain)) {
                            domainFlag = true;
                            break;
                        }
                    }

                    if (domainFlag) {
                        resultIntent.putExtra("Link Name", linkName);
                        resultIntent.putExtra("Link Address", linkAddress);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }

                    else {
                        Snackbar snackbar = Snackbar.make(view, "Url must contain valid top level domain.", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }
                break;

            case R.id.cancelButton:
                setResult(RESULT_CANCELED, null);
                finish();
                break;
        }


    }
}