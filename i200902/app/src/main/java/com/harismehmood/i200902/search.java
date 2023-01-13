package com.harismehmood.i200902;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class search extends AppCompatActivity{
    RecyclerView viewSearch ;
    RecyclerAdapterMain adapterSearch;
    ArrayList<contact_model> arrayContactsSearch;



    public void createRecyclerView(){
        viewSearch = findViewById(R.id.searchRecyclerView);
        viewSearch.setLayoutManager(new LinearLayoutManager(this));
        adapterSearch = new RecyclerAdapterMain(this,arrayContactsSearch);
        viewSearch.setAdapter(adapterSearch);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ImageButton searchButton = findViewById(R.id.searchButton);
        EditText search= findViewById(R.id.search);

        Db_helper myDBSearch;
        myDBSearch = new Db_helper(this);
        arrayContactsSearch= new ArrayList<contact_model>();
        arrayContactsSearch = myDBSearch.select_all();
        createRecyclerView();


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search.getText().toString().equals("")){
                    arrayContactsSearch = myDBSearch.select_all();
                }else{
                        //if it is text string
                        arrayContactsSearch = myDBSearch.search(search.getText().toString().toLowerCase());
                }
                if(arrayContactsSearch.size()==0){
                    Toast.makeText(search.this, "No Search found\n Try other keyword", Toast.LENGTH_SHORT).show();
                }
                createRecyclerView();
                search.setText("");
            }

        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}