package edu.neu.numad22sp_benjaminheflin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ServiceActivity extends AppCompatActivity {

    String my_url = "https://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=b87b13592b1365313fcf38215a638d0b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }

    public void callWebserviceHandler(View view) {
        PingWebServiceTask task = new PingWebServiceTask();
        task.execute(my_url);

    }

    private class PingWebServiceTask extends AsyncTask<String, Integer, String[]> {

        @Override
        protected String[] doInBackground(String... params) {

            String[] results = new String[2];

            try {
                URL url = new URL(params[0]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream inputStream = conn.getInputStream();
                results[0] = convertStreamToString(inputStream);

                return results;

            } catch (IOException e) {
                e.printStackTrace();
            }
            results[0] = "Something went wrong";
            return results;

        }

        @Override
        protected void onPostExecute(String... s) {
            super.onPostExecute(s);
            TextView result_view = findViewById(R.id.WeatherResponseText);
            result_view.setText(s[0]);
        }
    }

    private String convertStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next().replace(", ", ",\n") : "";
    }


}