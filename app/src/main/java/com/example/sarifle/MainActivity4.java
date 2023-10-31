package com.example.sarifle;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sarifle.api.ApiService;
import com.example.sarifle.api.Post;
import com.example.sarifle.database.DatabaseHelper;
import com.example.sarifle.database.DatabaseHelper2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity4 extends AppCompatActivity {



//    RecyclerView recyclerview;
    ImageButton Button;
    DatabaseHelper myDB;
    DatabaseHelper2 myDB2;
    ArrayList<String> Id,Name,Sarifle,Tell,Tell2,Accountnum,Datereg;

    RecyclerView recyclerView;

    Adapter Adapter;
    SearchView searchView;
    ProgressBar progressBar;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);
        progressBar = findViewById(R.id.progressBar);
        Button = findViewById(R.id.tton33);
        progressBar.setVisibility(View.GONE);


        myDB = new DatabaseHelper(MainActivity4.this);
        myDB2 = new DatabaseHelper2(MainActivity4.this);
        Id = new ArrayList<>();
        Name = new ArrayList<>();
        Sarifle = new ArrayList<>();
        Tell = new ArrayList<>();
        Tell2 = new ArrayList<>();
        Accountnum = new ArrayList<>();
        Datereg = new ArrayList<>();
        storeDataInArrays2();
        getData();
//        getPost();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Id = new ArrayList<>();
                Name = new ArrayList<>();
                Sarifle = new ArrayList<>();
                Tell = new ArrayList<>();
                Tell2 = new ArrayList<>();
                Accountnum = new ArrayList<>();
                Datereg = new ArrayList<>();
                storeArraysearch(query);
                getData();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Id = new ArrayList<>();
                Name = new ArrayList<>();
                Sarifle = new ArrayList<>();
                Tell = new ArrayList<>();
                Tell2 = new ArrayList<>();
                Accountnum = new ArrayList<>();
                Datereg = new ArrayList<>();
                storeArraysearch(newText);
                getData();
                return true;
            }
        });

         Button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(getApplicationContext(), home.class));
                 overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                 finish();
             }

         });


    }
    void storeArraysearch(String text){

        Cursor cursor = myDB.readsearch(text);
        if(cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                Id.add(cursor.getString(0));
                Name.add(cursor.getString(1));
                Sarifle.add(cursor.getString(2));
                Tell.add(cursor.getString(3));
                Tell2.add(cursor.getString(4));
                Accountnum.add(cursor.getString(5));
                Datereg.add(cursor.getString(6));
            }

        }


    }


    public void getPost(){
        Id = new ArrayList<>();
        Name = new ArrayList<>();
        Sarifle = new ArrayList<>();
        Tell = new ArrayList<>();
        Tell2 = new ArrayList<>();
        Accountnum = new ArrayList<>();
        Datereg = new ArrayList<>();
        Call<List<Post>> call = ApiService.getService2().getPost();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()){
                    List<Post> postList = response.body();
                    myDB.delete();
                    for (int i = 0; i < postList.size(); i++) {
                        Post post = postList.get(i);
                        String id = post.get_id();
                        String Name = post.getName();
                        String Sarifle = post.getSarifle();
                        String Tell = post.getTell();
                        String Tell2 = post.getTell2();
                        String Accountnum = post.getAccountnum();
                        String Datereg = post.getDatereg();

                        myDB.addData(id,Name,Tell,Tell2,Accountnum,Datereg,Sarifle);
                    }
                    progressBar.setVisibility(View.GONE);
                    storeDataInArrays();
                    getData();

                }
                else{

                    progressBar.setVisibility(View.GONE);
                    storeDataInArrays();
                    getData();
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                storeDataInArrays();
                getData();
            }
        });
    }

    private void getData() {

        Adapter = new Adapter(MainActivity4.this,this, Id,Name,Sarifle,Tell,Tell2,Accountnum,Datereg,new Adapter.ItemClicklistner() {
            @Override
            public void onItem(int position,Intent intent) {

                String Id = intent.getExtras().getString("ID");
                String Name = intent.getExtras().getString("Name");
                String Sarifle = intent.getExtras().getString("Sarifle");
                String Tell = intent.getExtras().getString("Tell");
                String Tell2 = intent.getExtras().getString("Tell2");
                String Accountnum = intent.getExtras().getString("Accountnum");
                myDB2.addData2(Id,Name,Tell,Tell2,Accountnum,Sarifle);
                Toast.makeText(MainActivity4.this, "Added Sarifle to your list", Toast.LENGTH_SHORT).show();

            }

        });
        recyclerView.setAdapter(Adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity4.this));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), home.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
    void storeDataInArrays2(){
        SQLiteDatabase db = myDB.getWritableDatabase();
        myDB.insertInitialData(db);
        db.close();
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                Id.add(cursor.getString(0));
                Name.add(cursor.getString(1));
                Sarifle.add(cursor.getString(2));
                Tell.add(cursor.getString(3));
                Tell2.add(cursor.getString(4));
                Accountnum.add(cursor.getString(5));
                Datereg.add(cursor.getString(6));
            }

        }
    }

    void storeDataInArrays(){

        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            SQLiteDatabase db = myDB.getWritableDatabase();
            myDB.insertInitialData(db);
            db.close();
        }else{
            while (cursor.moveToNext()){
                Id.add(cursor.getString(0));
                Name.add(cursor.getString(1));
                Sarifle.add(cursor.getString(2));
                Tell.add(cursor.getString(3));
                Tell2.add(cursor.getString(4));
                Accountnum.add(cursor.getString(5));
                Datereg.add(cursor.getString(6));
            }

        }


    }


}
