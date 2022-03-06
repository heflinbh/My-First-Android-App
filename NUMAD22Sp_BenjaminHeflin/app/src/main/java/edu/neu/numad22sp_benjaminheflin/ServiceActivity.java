package edu.neu.numad22sp_benjaminheflin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ServiceActivity extends AppCompatActivity {

    String weather_url = "https://api.openweathermap.org/data/2.5/weather?lat=40&lon=85&appid=b87b13592b1365313fcf38215a638d0b";
    String image_url_front = "http://openweathermap.org/img/wn/";
    String image_url_back = "@2x.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }

    public void callWebserviceHandler(View view) {

        ImageView weather_image = findViewById(R.id.WeatherImageView);
        weather_image.setImageResource(R.drawable.ic_launcher_bh_foreground);

        Thread wait_thread = new Thread(new Runnable () {
            @Override
            public void run() {
                PleaseWaitServiceTask wait_task = new PleaseWaitServiceTask();
                wait_task.execute();
            }
        });

        Thread web_service_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                PingWebServiceTask task = new PingWebServiceTask();
                task.execute(weather_url);
            }
        });

        TextView textView = findViewById(R.id.WeatherResponseText);
        textView.setText("Please wait");

        wait_thread.start(); web_service_thread.start();
    }

    private class PleaseWaitServiceTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            TextView textView = findViewById(R.id.WeatherResponseText);

            if (String.valueOf(textView.getText()).contains("Please")) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                textView.setText("Please wait.");
            }
            if (String.valueOf(textView.getText()).contains("Please")) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                textView.setText("Please wait..");
            }
            if (String.valueOf(textView.getText()).contains("Please")) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                textView.setText("Please wait...");
            }
            if (String.valueOf(textView.getText()).contains("Please")) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                textView.setText("Please wait....");
            }

            return null;
        }
    }

    private class PingWebServiceTask extends AsyncTask<String, Integer, String[]> {

        String[] results = new String[2];

        @Override
        protected String[] doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream inputStream = conn.getInputStream();
                String response = convertStreamToString(inputStream);

                JSONObject full_json = new JSONObject(response);
                JSONArray parameters = full_json.getJSONArray("weather");
                JSONObject new_object = parameters.getJSONObject(0);

                String description = new_object.getString("description");
                String icon_id = new_object.getString("icon");

                Log.d("description", description);
                Log.d("icon", icon_id);
                conn.disconnect();

                URL image_url_connection = new URL(image_url_front + icon_id + image_url_back);
                conn = (HttpURLConnection) image_url_connection.openConnection();
                conn.setDoInput(true);
                conn.connect();

                inputStream = conn.getInputStream();
                Bitmap my_bitmap = BitmapFactory.decodeStream(inputStream);

                String my_bitmap_string = BitmapToString(my_bitmap);

                results[0] = description;
                results[1] = my_bitmap_string;

                conn.disconnect();

                return results;

            } catch (IOException | JSONException e) {
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

            Bitmap my_bitmap = StringToBitmap(s[1]);
            ImageView result_image = findViewById(R.id.WeatherImageView);
            result_image.setImageBitmap(my_bitmap);
        }
    }

    private String convertStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next().replace(", ", ",\n") : "";
    }

    private String BitmapToString (Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}