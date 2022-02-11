package edu.neu.numad22sp_benjaminheflin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.AboutMeButton:
                Intent aboutMeActivityIntent = new Intent(getApplicationContext(), AboutMeActivity.class);
                startActivity(aboutMeActivityIntent);
                break;

            case R.id.ClickyClickyButton:
                Intent clickyActivityIntent = new Intent(getApplicationContext(), ClickyActivity.class);
                startActivity(clickyActivityIntent);
                break;

            case R.id.LinkCollectorButton:
                Intent LinkCollectActivityIntent = new Intent(getApplicationContext(), LinkCollectorActivity.class);
                startActivity(LinkCollectActivityIntent);
                break;
        }


    }

}