package com.example.baptiste.downloadvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;
    JsonPlaceHolder _webservice;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView =  (TextView)findViewById(R.id.firstText);
        button = (Button)findViewById(R.id.button);
        editText = (EditText)findViewById(R.id.editText);
        queue = Volley.newRequestQueue(this);
        String url = "http://www.google.com";

        downloadDataFromUrl(url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        _webservice = retrofit.create(JsonPlaceHolder.class);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*String readString = editText.getText().toString();
                textView.setText("");
                downloadDataFromUrl(readString);*/

                Call<List<User>> users = _webservice.listUsers();
                users.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, retrofit2.Response<List<User>> response) {
                        if (response.isSuccessful()){
                            List<User> userList = response.body();
                            StringBuilder stringBuilder = new StringBuilder();
                            for (User u: userList){
                                stringBuilder.append(u.name + " " + u.email + " \n");
                            }

                            textView.setText("");
                            textView.setText(stringBuilder.toString());

                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                    }
                });
            }
        });



    }

    public static boolean isMatch(String s, String pattern) {
        try {
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

    public void downloadDataFromUrl(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: " + response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

}