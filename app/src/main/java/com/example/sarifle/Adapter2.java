package com.example.sarifle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sarifle.database.DatabaseHelper2;
import com.example.sarifle.database.DatabaseHelper3;

import java.util.ArrayList;
import java.util.Collections;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.MyViewHolder> {
    DatabaseHelper2 myDB2;
    private Context context;
    private Activity activity;
    private String data;
    private ArrayList Id,Name,Sarifle,Tell,Tell2,Accountnum,Datereg;
//    ItemClicklistner ItemClicklistner;
    ItemClicklistner2 ItemClicklistner2;
    DatabaseHelper3 myDB3;

    private int highlightedPosition = -1;
    private int colorResource;
    private int selectedIndex = -1;// Initially no item is highlighted
    Adapter2(Activity activity, Context context, ArrayList Id, ArrayList Name, ArrayList Sarifle, ArrayList Tell,
            ArrayList Tell2,ArrayList Accountnum,ArrayList Datereg,String data,DatabaseHelper3 myDB3,DatabaseHelper2 myDB2,int colorResource
//            ,ItemClicklistner ItemClicklistner
            ,ItemClicklistner2 ItemClicklistner2){
        this.activity = activity;
        this.context = context;
        this.Id = Id;
        this.Name = Name;
        this.Sarifle = Sarifle;
        this.Tell = Tell;
        this.Tell2 = Tell2;
        this.Accountnum = Accountnum;
        this.Datereg = Datereg;
        this.data = data;
        this.myDB3 = myDB3;
        this.myDB2 = myDB2;
        this.colorResource = colorResource;
//        this.ItemClicklistner = ItemClicklistner;
        this.ItemClicklistner2 = ItemClicklistner2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_layout, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.Name2.setText(String.valueOf(Name.get(position)));
        holder.Sarifle2.setText(String.valueOf(Sarifle.get(position)));
        holder.Accountnum2.setText(String.valueOf(Accountnum.get(position)));
        holder.image.setImageResource(R.drawable.iconuser);

        Cursor cursor = myDB3.read();
        if(cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                data = cursor.getString(0);
            }

        }

        if(data == ""){
            if (position == highlightedPosition) {
                // Apply the highlight effect to the item view
                holder.layoutchange.setBackgroundColor(colorResource);
            } else {
                // Revert the item view to its default appearance
                holder.layoutchange.setBackgroundColor(Color.TRANSPARENT);
            }
        }
        else{
            if (data.equals(holder.Sarifle2.getText().toString())) {
                // Apply the highlight effect to the item view
                holder.layoutchange.setBackgroundColor(colorResource);
            } else {
                // Revert the item view to its default appearance
                holder.layoutchange.setBackgroundColor(Color.TRANSPARENT);
            }
        }
//
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, home.class);
                intent.putExtra("ID", String.valueOf(Id.get(position)));
                intent.putExtra("Name", String.valueOf(Name.get(position)));
                intent.putExtra("Sarifle", String.valueOf(Sarifle.get(position)));
                intent.putExtra("Tell", String.valueOf(Tell.get(position)));
                intent.putExtra("Tell2", String.valueOf(Tell2.get(position)));
                intent.putExtra("Accountnum", String.valueOf(Accountnum.get(position)));

                setHighlightedPosition(position);
                setSelectedIndex(position);
                myDB2.moveItemToFirstPosition(position);
                ItemClicklistner2.onItem(position,intent,data);
            }
        });

//        holder.DeleteId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, home.class);
//                intent.putExtra("ID", String.valueOf(Id.get(position)));
//
//                ItemClicklistner.onItem(position,intent);
//            }
//        });

    }

    public void setSelectedIndex(int position) {
        if (position==0) {
            return; // No change
        }

        int previousSelectedIndex = 0;

        if (previousSelectedIndex >= 0 && previousSelectedIndex < Id.size() && position >= 0 && position < Id.size()) {
//            Id,Name,Sarifle,Tell,Tell2,Accountnum,Datereg;
            Collections.swap(Id, previousSelectedIndex, position);
            Collections.swap(Name, previousSelectedIndex, position);
            Collections.swap(Sarifle, previousSelectedIndex, position);
            Collections.swap(Accountnum, previousSelectedIndex, position);
            Collections.swap(Tell, previousSelectedIndex, position);
            Collections.swap(Tell2, previousSelectedIndex, position);
//ss
            notifyItemMoved(position, previousSelectedIndex);
            notifyItemChanged(previousSelectedIndex);
            notifyItemChanged(position);
        }

    }

    public void setHighlightedPosition(int position) {
        highlightedPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Id.size();
    }


    public interface ItemClicklistner {
        void onItem(int position,Intent intent);
    }
    public interface ItemClicklistner2 {
        void onItem(int position,Intent intent,String data);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Name2,Tell,Tell2,Accountnum2,date,Sarifle2;
        ImageView image;
//        ImageButton DeleteId;

        ConstraintLayout layout,layoutchange;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name2 = itemView.findViewById(R.id.Name2);
            image = itemView.findViewById(R.id.imageView);
            Sarifle2 = itemView.findViewById(R.id.Sarifle2);
            Accountnum2 = itemView.findViewById(R.id.Accountnum2);
//            DeleteId = itemView.findViewById(R.id.DeleteId);
            layout = itemView.findViewById(R.id.layout);
            layoutchange = itemView.findViewById(R.id.layoutchange);
        }

    }




}
