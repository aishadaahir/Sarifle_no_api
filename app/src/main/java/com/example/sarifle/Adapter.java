package com.example.sarifle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sarifle.database.DatabaseHelper;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    DatabaseHelper myDB;
    private Context context;
    private Activity activity;
    private ArrayList Id,Name,Sarifle,Tell,Tell2,Accountnum,Datereg;
    ItemClicklistner ItemClicklistner;


    Adapter(Activity activity, Context context, ArrayList Id, ArrayList Name, ArrayList Sarifle, ArrayList Tell,
                  ArrayList Tell2,ArrayList Accountnum,ArrayList Datereg,ItemClicklistner ItemClicklistner){
        this.activity = activity;
        this.context = context;
        this.Id = Id;
        this.Name = Name;
        this.Sarifle = Sarifle;
        this.Tell = Tell;
        this.Tell2 = Tell2;
        this.Accountnum = Accountnum;
        this.Datereg = Datereg;
        this.ItemClicklistner = ItemClicklistner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.accountant_layout, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.Name.setText(String.valueOf(Name.get(position)));
        holder.Sarifle.setText(String.valueOf(Sarifle.get(position)));
        holder.Accountnum.setText(String.valueOf(Accountnum.get(position)));
        holder.image.setImageResource(R.drawable.iconuser);

        holder.AddId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, home.class);
                intent.putExtra("ID", String.valueOf(Id.get(position)));
                intent.putExtra("Name", String.valueOf(Name.get(position)));
                intent.putExtra("Sarifle", String.valueOf(Sarifle.get(position)));
                intent.putExtra("Tell", String.valueOf(Tell.get(position)));
                intent.putExtra("Tell2", String.valueOf(Tell2.get(position)));
                intent.putExtra("Accountnum", String.valueOf(Accountnum.get(position)));
                intent.putExtra("image", String.valueOf(com.google.android.material.R.drawable.abc_ic_search_api_material));

                ItemClicklistner.onItem(position,intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return Id.size();
    }


    public interface ItemClicklistner {
        void onItem(int position,Intent intent);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Name,Accountnum,Sarifle;
        ImageView image;
        ImageButton AddId;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.Name);
            image = itemView.findViewById(R.id.imageView);
            Sarifle = itemView.findViewById(R.id.Sarifle);
            Accountnum = itemView.findViewById(R.id.Accountnum);
            AddId = itemView.findViewById(R.id.AddId);

        }



    }

}
