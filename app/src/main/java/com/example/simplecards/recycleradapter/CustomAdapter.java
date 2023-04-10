package com.example.simplecards.recycleradapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplecards.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    Context context;
    ArrayList<String> originText, translatedText;

    public CustomAdapter(Context context, ArrayList<String> originText, ArrayList<String> translatedText) {
        this.context = context;
        this.originText = originText;
        this.translatedText = translatedText;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_list_words,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtOrigin.setText(String.valueOf(originText.get(position)));
        holder.txtTranslated.setText(String.valueOf(translatedText.get(position)));
        holder.txtNumber.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return originText.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtOrigin, txtTranslated, txtNumber;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtOrigin = itemView.findViewById(R.id.txtOrigin);
            txtTranslated = itemView.findViewById(R.id.txtTranslated);
            txtNumber = itemView.findViewById(R.id.txtNumber);
        }
    }
}
