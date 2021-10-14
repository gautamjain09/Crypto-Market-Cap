package com.gautamjain.cryptomarketcap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ArrayList<CurrencyModal> arraylist;
    private CurrencyAdapter currencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.EditTextSearch);
        progressBar = findViewById(R.id.ProgressBar);

        arraylist = new ArrayList<>();
        currencyAdapter = new CurrencyAdapter(arraylist, this);

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(currencyAdapter);

        // API CALL
        getCurrencyData();

        //Search
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filterCurrency(s.toString());
            }
        });
    }

    private void filterCurrency(String currency)
    {
        ArrayList<CurrencyModal> filteredList = new ArrayList<>();
        for(CurrencyModal it : arraylist)
        {
            if(it.getName().toLowerCase().contains(currency.toLowerCase()))
            {
                filteredList.add(it);
            }
            else
            {
                currencyAdapter.filterList(filteredList);
            }
        }

//        if(filteredList.isEmpty())
//        {
//            Toast.makeText(this, "No such Currency found", Toast.LENGTH_SHORT).show();
//        }

    }

    private void getCurrencyData()
    {
        progressBar.setVisibility(View.VISIBLE);

        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        progressBar.setVisibility(View.GONE);
                        try
                        {
                            JSONArray dataArray = response.getJSONArray("data");

                            for(int i=0;i<dataArray.length();i++)
                            {
                                JSONObject dataObject = dataArray.getJSONObject(i);
                                String name = dataObject.getString("name");
                                String symbol = dataObject.getString("symbol");

                                JSONObject quoteObject = dataObject.getJSONObject("quote");
                                JSONObject USDObject = quoteObject.getJSONObject("USD");
                                double price  = USDObject.getDouble("price");

                                arraylist.add(new CurrencyModal(name, symbol, price));
                            }

                            currencyAdapter.notifyDataSetChanged();

                        }
                        catch(JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Fail to get Data!", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Fail to get Data!", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String , String> mp  = new HashMap<>();
                // Key, Value
                mp.put("X-CMC_PRO_API_KEY", "dfa4001b-fcf9-4344-8f40-13dae258c2b1");
                return mp;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

}