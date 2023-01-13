package com.harismehmood.i200902;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class main_content extends AppCompatActivity {
    RecyclerView view ;
    RecyclerAdapterMain adapter;
    ArrayList<contact_model> arraycontacts;
    Db_helper myDB;

    protected void createRecyclerView(){
        view = findViewById(R.id.recyclerView);
        view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapterMain(this,arraycontacts);
        view.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

//        setContentView(R.layout.activity_main);
//        myDB = new Db_helper(this);
//        view = findViewById(R.id.recyclerView);
//        FloatingActionButton new_search= findViewById(R.id.new_search);
//        FloatingActionButton new_contact= findViewById(R.id.new_contact);
//        FloatingActionButton update_contact= findViewById(R.id.update_contact);
//        //create arrays of 10 dummy names and phone numbers
//        String[] names = {"John","Mary","Peter","Paul","James","David","Joseph","Andrew","Thomas","Matthew"};
//
//        String[] phones = {"1234567890","2345678901","3456789012","4567890123","5678901234","6789012345","7890123456","8901234567","9012345678","0123456789"};
//        //insert dummy data into database
//        Uri uri = Uri.parse("android.resource://com.example.contact/drawable/ic_men");
////        for(int i=0;i<5;i++){
////            myDB.insert(names[i],phones[i], uri.toString());
////        }
//
//
//        arraycontacts= new ArrayList<contact_model>();
//        arraycontacts = myDB.select_all();
//        createRecyclerView();
//
//        //new contact button listener
//        new_contact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(main_content.this, add.class);
//                startActivity(intent);
//            }
//        });
//
//        //new search button listener
//        new_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("new_search", "onClick: ");
//                Intent intent= new Intent(main_content.this, search.class);
//                startActivity(intent);
//            }
//        });
//
//        //update contact button listener
//        update_contact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("update_contact", "onClick: ");
//                Intent intent= new Intent(main_content.this, update.class);
//                startActivity(intent);
//            }
//        });
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        arraycontacts=myDB.select_all();
//        createRecyclerView();
//    }
}