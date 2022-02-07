package edu.neu.numad22sp_benjaminheflin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
                Context context = getApplicationContext();
                CharSequence text = "Email: heflin.be@northeastern.edu";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                break;

            case R.id.ClickyClicky:
                Intent clickyActivityIntent = new Intent(getApplicationContext(), ClickyActivity.class);
                startActivity(clickyActivityIntent);
                break;
        }


    }

}