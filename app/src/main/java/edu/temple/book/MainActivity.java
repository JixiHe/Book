package edu.temple.book;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView yearTextView, titleTextView;

    EditText comicIdEditText;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        yearTextView = findViewById(R.id.yearTextView);
        titleTextView = findViewById(R.id.titleTextView);

        comicIdEditText = findViewById(R.id.comicIdEditText);

        findViewById(R.id.displayButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://xkcd.com/" + comicIdEditText.getText().toString() + "/info.0.json";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    int year = response.getInt("year");
                                    String title = response.getString("title");

                                    yearTextView.setText(String.valueOf(year));
                                    titleTextView.setText(title);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT);
                    }
                });
                requestQueue.add(jsonObjectRequest);

            }
        });
    }
}
