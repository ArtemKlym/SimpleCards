package com.example.simplecards.recycleradapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplecards.R;
import com.example.simplecards.ShowCardsActivity;
import com.example.simplecards.database.MyDataBaseHelper;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    Context context;
    ArrayList<String> originText, translatedText;
    MyDataBaseHelper myDB;

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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView txtOrigin, txtTranslated, txtNumber;
        private ImageView imageSelect;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtOrigin = itemView.findViewById(R.id.txtOrigin);
            txtTranslated = itemView.findViewById(R.id.txtTranslated);
            txtNumber = itemView.findViewById(R.id.txtNumber);

            myDB = new MyDataBaseHelper(itemView.getContext());

            imageSelect = itemView.findViewById(R.id.imageView2);

            //Set onClickListener for imageButton
            imageSelect.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            showPopUpMenu(view);
        }

        //Show popUpMenu in ShowCardsActivity & set OnMenuItemCLickListener
        private void showPopUpMenu(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(),view);
            popupMenu.inflate(R.menu.show_cards_menu);


            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.delete_menu:
                            //If successfully removed from Database then remove from ArrayList in RecyclerView
                            if(myDB.deleteItem(originText.get(getAdapterPosition()),
                                    translatedText.get(getAdapterPosition()))
                            ){
                                //Removing from lists
                                originText.remove(originText.get(getAdapterPosition()));
                                translatedText.remove(translatedText.get(getAdapterPosition()));

                                //Go to the ShowCardsActivity to see changes
                                context.startActivity(new Intent(context, ShowCardsActivity.class));
                            }else{
                                Toast.makeText(context, "Fail to delete", Toast.LENGTH_SHORT).show();
                            }
                        return true;

                        default: return false;
                    }
                }
            });
            popupMenu.show();
        }
    }
}
