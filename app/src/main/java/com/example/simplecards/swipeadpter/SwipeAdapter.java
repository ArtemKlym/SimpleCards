package com.example.simplecards.swipeadpter;

import static java.util.Objects.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplecards.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class SwipeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> originWord,translatedWord;

    public SwipeAdapter(Context context, ArrayList<String> originWord, ArrayList<String> translatedWord) {
        this.context = context;
        this.originWord = originWord;
        this.translatedWord = translatedWord;
    }

    @Override
    public int getCount() {
        return originWord.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView;
        if(view == null){
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_koloda,viewGroup,false);
        }else{
            convertView = view;
        }
        TextView frontText = convertView.findViewById(R.id.txtShowOnCard);
        frontText.setText(originWord.get(i));
        return convertView;
    }
}
