package sugaselvaraj.paypal.com.myweatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button button;

    TextView result;

    EditText city;

    ImageView weatherIcon;

//    http://api.openweathermap.org/data/2.5/weather?q=Chennai&appid=7197985411abc8d832054a34a64df3b6

    String baseURL = "http://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=7197985411abc8d832054a34a64df3b6";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        city = findViewById(R.id.getCity);
        result = findViewById(R.id.result);
        weatherIcon = findViewById(R.id.weatherIcon);

        requestQueue = MySingleton.getInstance(this).getRequestQueue();

        button.setEnabled(false);

        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(city.getText().toString().equals("")) {
                    button.setEnabled(false);
                }
                else {
                    button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myURL = baseURL+city.getText().toString()+API;
                sendApiRequest(myURL);
            }
        });
    }

    private void sendApiRequest(String url) {
        Log.i("URL", url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(JsonRequest.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray weatherArr = response.getJSONArray("weather");
                    for(int i=0; i<weatherArr.length(); i++) {
                        JSONObject weather = weatherArr.getJSONObject(i);
                        result.setText(weather.getString("main"));
                        String imageURL = "http://openweathermap.org/img/w/"+weather.getString("icon")+".png";
                        Picasso.with(getApplicationContext()).load(imageURL).resize(300, 300).into(weatherIcon);
                        weatherIcon.setVisibility(View.VISIBLE);
                        Log.d("Test", weather.getString("main"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result.setText(error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
