package com.example.inclassactivity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText title;
    private EditText author;
    private EditText pages;

    private Button addbtn;
    private Button savebtn;
    private Switch available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();
        getbooks();
        buttonaction();
    }
    public void getbooks(){
        ArrayList<Book> list = new ArrayList<>();

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("newBook", "");
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        list = gson.fromJson(json, type);

        if (list != null){
            for (int i = 0; i < list.size(); i++) {
                Log.d("myBook", list.get(i).toString());
            }
        }
    }

    public void buttonaction(){
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        SharedPreferences.Editor prefsEditor = prefs.edit();
        ArrayList<Book> list = new ArrayList<>();

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ttl = String.valueOf(title.getText());
                String name = String.valueOf(author.getText());
                String page = String.valueOf(pages.getText());
                boolean swtch = available.isChecked();
                
                list.add(new Book(ttl,name,page,swtch));

            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = gson.toJson(list);
                prefsEditor.putString("newBook", json);
                prefsEditor.commit();
            }
        });
    }
    public void setup(){
        title = findViewById(R.id.titleEdt);
        author = findViewById(R.id.authorEdt);
        pages = findViewById(R.id.numpagesEdt);
        addbtn = findViewById(R.id.addbtn);
        savebtn = findViewById(R.id.savebtn);
        available = findViewById(R.id.switch1);
    }
}