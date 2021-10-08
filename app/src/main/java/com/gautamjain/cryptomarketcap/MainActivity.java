package com.gautamjain.cryptomarketcap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.EditTextSearch);
        progressBar = findViewById(R.id.ProgressBar);
        recyclerView = findViewById(R.id.RecyclerView);

    }
}