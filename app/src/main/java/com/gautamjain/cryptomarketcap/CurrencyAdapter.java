package com.gautamjain.cryptomarketcap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    private ArrayList<CurrencyModal> currencyModalArrayList;
    private Context context;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public CurrencyAdapter(ArrayList<CurrencyModal> currencyModalArrayList, Context context) {
        this.currencyModalArrayList = currencyModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CurrencyAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.currency_item, parent, false);
        return new CurrencyAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return currencyModalArrayList.size();
    }

    public void filterList(ArrayList<CurrencyModal> filteredList)
    {
        currencyModalArrayList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyAdapter.ViewHolder holder, int position) {

        CurrencyModal currency = currencyModalArrayList.get(position);
        holder.name.setText(currency.getName());
        holder.symbol.setText(currency.getSymbol());
        holder.price.setText("$" + df2.format(currency.getPrice()));

    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView symbol;
        private TextView name;
        private TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            symbol = itemView.findViewById(R.id.idSymbolCurrency);
            name = itemView.findViewById(R.id.idNameCurrency);
            price = itemView.findViewById(R.id.idPriceCurrency);
        }
    }
}
